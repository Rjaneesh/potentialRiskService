package com.bdp.innovation.potentialRiskService.common;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdp.innovation.potentialRiskService.dto.BDPRefDto;
import com.bdp.innovation.potentialRiskService.geofence.GeoFenceUtility;
import com.bdp.innovation.potentialRiskService.model.KPIEventShipmentMap;
import com.bdp.innovation.potentialRiskService.model.KPIShipments;
import com.bdp.innovation.potentialRiskService.repository.IKPIEventShipmentMapRepo;
import com.bdp.innovation.potentialRiskService.repository.IKPIShipmentsRepo;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;

@SuppressWarnings({"deprecation", "unused", "unchecked"})
@Slf4j
@Service
public class Commons {

    @Autowired
    private IKPIShipmentsRepo kpiShipmentsRepo;

    @Autowired
    private GeoFenceUtility geoFenceUtility;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private IKPIEventShipmentMapRepo kpiEventShipmentMapRepo;


    public void checkCurrentDate() {
        Object obj = entityManager.createNativeQuery("select now()").getSingleResult();
        log.info("----------------------------------------DB TIME:: " + obj);
    }

    /**
     * @param schedulerName
     */
    public void updateAssociatedShipments(String schedulerName) {
        Integer shipmentType = null;
        try {
            switch (schedulerName) {
                case Constants.VESSEL_INFO_SCH:
                    processKpiShipments(Constants.ATRISK_SHIPMENT);
                    break;
                case Constants.PREALERT_SHIPMENT_SCH:
                    processKpiShipments(Constants.PREALERT_SHIPMENT);
                    break;
                case Constants.ARRIVED_SHIPMENT_SCH:
                    processKpiShipments(Constants.ARRIVED_SHIPMENT);
                    break;
            }
        } catch (Exception e) {
            log.error("Error:: {}", e.getMessage());
        }
    }

    /**
     * @param shipmentType
     */
    private void processKpiShipments(Integer shipmentType) {
        Date currentDate = new Date();
        List<BDPRefDto> oldShipmentList = null;
        List<KPIShipments> newShipmentList = null;
        List<KPIEventShipmentMap> newEventShipmentList = null;
        List<KPIEventShipmentMap> oldEventShipmentList = null;
        HashMap<String, Object> queryParams = new HashMap<>();
        if (ObjectUtils.isNotEmpty(shipmentType)) {
            try {
                if (shipmentType.equals(Constants.ATRISK_SHIPMENT)) {
                    updateKPIAtRiskShipments(queryParams);
                }
                else if (shipmentType.equals(Constants.PREALERT_SHIPMENT)) {
                    updateKPIPreAlertShipments(queryParams);
                }
                else if (shipmentType.equals(Constants.ARRIVED_SHIPMENT)) {
                    updateKPIArrivedShipments(queryParams);
                }
            } catch (Exception e) {
                log.error("Error: {}", e.getMessage());
            }
        }
    }

    private void updateKPIAtRiskShipments(HashMap<String, Object> queryParams) {
        List<BDPRefDto> oldShipmentList = null;
        List<KPIShipments> newShipmentList = null;
        List<KPIEventShipmentMap> newEventShipmentList = null;
        List<KPIEventShipmentMap> oldEventShipmentList = null;
        newShipmentList = createNativeQuery(KPIShipments.class, QueryConstants.SELECT_KPI_SHIPMENTS).getResultList();
        newEventShipmentList = createNativeQuery(KPIEventShipmentMap.class, QueryConstants.GET_AT_RISK_SHIPMENT_MAPPERS).getResultList();
        if (CollectionUtils.isNotEmpty(newShipmentList)) {
            newShipmentList.forEach(s -> s.setShipmentType(Constants.AT_RISK_SHIPMENT_KPI));
        }
        if (CollectionUtils.isNotEmpty(newEventShipmentList)) {
            newEventShipmentList.forEach(s -> s.setKpiType(Constants.AT_RISK_SHIPMENT_KPI));
        }
        queryParams.put("shipmentType", Constants.AT_RISK_SHIPMENT_KPI);
        oldShipmentList = (List<BDPRefDto>) getResultList(BDPRefDto.class, QueryConstants.GET_KPI_SHIPMENTS, queryParams);
        oldEventShipmentList = kpiEventShipmentMapRepo.findByKpiType(Constants.AT_RISK_SHIPMENT_KPI);
        updateKPIShipments(oldShipmentList, newShipmentList);
        updateKPIEventShipmentMappers(oldEventShipmentList, newEventShipmentList);
    }

    private void updateKPIPreAlertShipments(HashMap<String, Object> queryParams) {
        List<BDPRefDto> oldShipmentList = null;
        List<KPIShipments> newShipmentList = null;
        List<KPIEventShipmentMap> newEventShipmentList = null;
        List<KPIEventShipmentMap> oldEventShipmentList = null;
        newShipmentList = createNativeQuery(KPIShipments.class, QueryConstants.SELECT_KPI_SHIPMENTS).getResultList();
        newEventShipmentList = createNativeQuery(KPIEventShipmentMap.class, QueryConstants.GET_PREALERT_SHIPMENT_MAPPERS).getResultList();
        if (CollectionUtils.isNotEmpty(newShipmentList)) {
            newShipmentList.forEach(s -> s.setShipmentType(Constants.PREALERT_SHIPMENT_KPI));
        }
        if (CollectionUtils.isNotEmpty(newEventShipmentList)) {
            newEventShipmentList.forEach(s -> s.setKpiType(Constants.PREALERT_SHIPMENT_KPI));
        }
        queryParams.put("shipmentType", Constants.PREALERT_SHIPMENT_KPI);
        oldShipmentList = (List<BDPRefDto>) getResultList(BDPRefDto.class, QueryConstants.GET_KPI_SHIPMENTS, queryParams);
        oldEventShipmentList = kpiEventShipmentMapRepo.findByKpiType(Constants.PREALERT_SHIPMENT_KPI);
        updateKPIShipments(oldShipmentList, newShipmentList);
        updateKPIEventShipmentMappers(oldEventShipmentList, newEventShipmentList);
    }

    private void updateKPIArrivedShipments(HashMap<String, Object> queryParams) {
        List<BDPRefDto> oldShipmentList = null;
        List<KPIShipments> newShipmentList = null;
        List<KPIEventShipmentMap> newEventShipmentList = null;
        List<KPIEventShipmentMap> oldEventShipmentList = null;
        newShipmentList = createNativeQuery(KPIShipments.class, QueryConstants.SELECT_KPI_SHIPMENTS).getResultList();
        newEventShipmentList = createNativeQuery(KPIEventShipmentMap.class, QueryConstants.GET_ARRIVED_SHIPMENT_MAPPERS).getResultList();
        if (CollectionUtils.isNotEmpty(newShipmentList)) {
            newShipmentList.forEach(s -> s.setShipmentType(Constants.ARRIVED_SHIPMENT_KPI));
        }
        if (CollectionUtils.isNotEmpty(newEventShipmentList)) {
            newEventShipmentList.forEach(s -> s.setKpiType(Constants.ARRIVED_SHIPMENT_KPI));
        }
        queryParams.put("shipmentType", Constants.ARRIVED_SHIPMENT_KPI);
        oldShipmentList = (List<BDPRefDto>) getResultList(BDPRefDto.class, QueryConstants.GET_KPI_SHIPMENTS, queryParams);
        oldEventShipmentList = kpiEventShipmentMapRepo.findByKpiType(Constants.ARRIVED_SHIPMENT_KPI);
        updateKPIShipments(oldShipmentList, newShipmentList);
        updateKPIEventShipmentMappers(oldEventShipmentList, newEventShipmentList);
    }

    /**
     * @param oldShipmentList
     * @param newShipmentList
     */
    private void updateKPIShipments(List<BDPRefDto> oldShipmentList, List<KPIShipments> newShipmentList) {
        List<KPIShipments> filteredShipments = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(newShipmentList)) {
            if (CollectionUtils.isNotEmpty(oldShipmentList)) {
                for (KPIShipments shipments : newShipmentList) {
                    if (!isExistingShipmentRecord(shipments, oldShipmentList)) {
                        filteredShipments.add(shipments);
                    }
                }
            } else {
                filteredShipments.addAll(newShipmentList);
            }
        }
        if (CollectionUtils.isNotEmpty(filteredShipments)) {
            kpiShipmentsRepo.saveAllAndFlush(filteredShipments);
            log.info("KpiShipments Saved succesfully...");
        }
    }

    /**
     * @param oldEventShipmentList
     * @param newEventShipmentList
     */
    private void updateKPIEventShipmentMappers(List<KPIEventShipmentMap> oldEventShipmentList, List<KPIEventShipmentMap> newEventShipmentList) {
        List<KPIEventShipmentMap> filteredMappers = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(newEventShipmentList)) {
            if (CollectionUtils.isNotEmpty(oldEventShipmentList)) {
                for (KPIEventShipmentMap eventShipmentMap : newEventShipmentList) {
                    if (!isExistingMapperRecord(eventShipmentMap, oldEventShipmentList)) {
                        filteredMappers.add(eventShipmentMap);
                    }
                }
            } else {
                filteredMappers.addAll(newEventShipmentList);
            }
        }
        if (CollectionUtils.isNotEmpty(filteredMappers)) {
            kpiEventShipmentMapRepo.saveAllAndFlush(filteredMappers);
            log.info("KpiEventShipmentMap Saved succesfully...");
        }
    }

    /**
     * @param shipments
     * @param oldShipmentList
     * @return
     */
    private boolean isExistingShipmentRecord(KPIShipments shipments, List<BDPRefDto> oldShipmentList) {
        if (ObjectUtils.isEmpty(shipments) && CollectionUtils.isEmpty(oldShipmentList)) return false;
        LocalDate currentDate = DateUtils.convertDateToLocalDate(shipments.getLogDate());
        return oldShipmentList.parallelStream().anyMatch(s -> s.getBdpReferenceNumber().equalsIgnoreCase(shipments.getBdpReferenceNumber()) && currentDate.equals(DateUtils.convertDateToLocalDate(s.getLogDate())));
    }

    /**
     * @param eventShipmentMap
     * @param existingMap
     * @return boolean
     */
    private boolean isExistingMapperRecord(KPIEventShipmentMap eventShipmentMap, List<KPIEventShipmentMap> existingMap) {
        if (CollectionUtils.isEmpty(existingMap) || ObjectUtils.isEmpty(eventShipmentMap)) return false;
        LocalDate currentDate = DateUtils.convertDateToLocalDate(eventShipmentMap.getLogDate());
        return existingMap.parallelStream().anyMatch(m -> (currentDate.equals(DateUtils.convertDateToLocalDate(m.getLogDate())) && m.getEventId().equals(eventShipmentMap.getEventId()) && m.getBdpRefNo().equals(eventShipmentMap.getBdpRefNo())));
    }

    /**
     * @param className
     * @param query
     * @param queryParams
     * @return List<?>
     */
    public List<?> getResultList(Class<?> className, String query, Map<String, Object> queryParams) {
        List<?> resultList = null;
        if (ObjectUtils.isNotEmpty(className) && ObjectUtils.isNotEmpty(query)) {
//            Query queryObj = entityManager.createNativeQuery(query).unwrap(NativeQuery.class).setResultTransformer(Transformers.aliasToBean(className));
        	jakarta.persistence.Query queryObj = entityManager.createNativeQuery(query).unwrap(NativeQuery.class).setResultTransformer(Transformers.aliasToBean(className));
        	for (Entry<String, Object> param : queryParams.entrySet()) {
                queryObj.setParameter(param.getKey(), param.getValue());
            }
            resultList = queryObj.getResultList();
        }
        return resultList;
    }
    
    /**
     * @param className
     * @param query
     * @return
     */
    public Query createNativeQuery(Class<?> className, String query) {
        return entityManager.createNativeQuery(query).unwrap(NativeQuery.class).setResultTransformer(Transformers.aliasToBean(className));
    }

}
