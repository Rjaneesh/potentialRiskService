package com.bdp.innovation.potentialRiskService.daoimpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.bdp.innovation.potentialRiskService.common.Constants;
import com.bdp.innovation.potentialRiskService.common.DateUtils;
import com.bdp.innovation.potentialRiskService.common.QueryConstants;
import com.bdp.innovation.potentialRiskService.dao.IshipmentsDao;
import com.bdp.innovation.potentialRiskService.dto.PreAlertShipmentsDto;
import com.bdp.innovation.potentialRiskService.exceptionHandler.BaseException;
import com.bdp.innovation.potentialRiskService.model.ApplicationConfig;
import com.bdp.innovation.potentialRiskService.model.EventManagement;
import com.bdp.innovation.potentialRiskService.model.EventView;
import com.bdp.innovation.potentialRiskService.model.PreAlertShipmentEventMap;
import com.bdp.innovation.potentialRiskService.model.PreAlertShipments;
import com.bdp.innovation.potentialRiskService.model.SchedulerEventProcessLog;
import com.bdp.innovation.potentialRiskService.model.ShipmentRawData;
import com.bdp.innovation.potentialRiskService.repository.ApplicationConfigRepository;
import com.bdp.innovation.potentialRiskService.repository.ArrShipmentEventMapRepo;
import com.bdp.innovation.potentialRiskService.repository.AtRiskShipmentEventMapRepo;
import com.bdp.innovation.potentialRiskService.repository.EventManagementRepository;
import com.bdp.innovation.potentialRiskService.repository.EventViewRepository;
import com.bdp.innovation.potentialRiskService.repository.PreAlertShipmentEventMapRepo;
import com.bdp.innovation.potentialRiskService.repository.PreAlertShipmentsRepository;
import com.bdp.innovation.potentialRiskService.repository.SchedulerEventProcessLogRepo;
import com.bdp.innovation.potentialRiskService.repository.ShipmentRawDataRepository;
import com.google.gson.Gson;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Repository
@Service
public class ShipmentsDaoImpl implements IshipmentsDao {

//	@Autowired
//	private ApplicationConfigRepository configRepository;

	@PersistenceContext
	private EntityManager primaryEntityManager;
//
//	@PersistenceContext(unitName = "secondaryDataSource")
//	private EntityManager secondaryEntityManager;

	@Autowired
	private ShipmentRawDataRepository shipmentRawDataRepository;

	@Autowired
	private EventViewRepository eventViewRepository;

	@Autowired
	private EventManagementRepository eventManagementRepo;

	@Autowired
	private SchedulerEventProcessLogRepo schedulerEventProcessLogRepo;

	@Autowired
	private AtRiskShipmentEventMapRepo atRiskShipmentEventMapRepo;

	@Autowired
	private PreAlertShipmentEventMapRepo preAlertShipmentEventMapRepo;

	@Autowired
	private ArrShipmentEventMapRepo arrShipmentEventMapRepo;

	@Autowired
	private PreAlertShipmentsRepository preAlertShipmentsRepository;

//	@Override
//	public Integer getDatabaseType() {
//		Integer type = 0;
//		try {
//			List<ApplicationConfig> databaseTypes = getApplicationConfig();
//			if (CollectionUtils.isNotEmpty(databaseTypes)) {
//				ApplicationConfig applicationConfig = databaseTypes.get(0);
//				if (ObjectUtils.isNotEmpty(applicationConfig)) {
//					String configValue = applicationConfig.getConfigValue();
//					if (ObjectUtils.isNotEmpty(configValue)) {
//						type = Integer.valueOf(configValue);
//					}
//				}
//			}
//		} catch (Exception exception) {
//			log.error("Error {}", exception.getMessage());
//		}
//		return type;
//	}
//
//	@Cacheable(value = "getApplicationConfig", unless = "#result == null")
//	public List<ApplicationConfig> getApplicationConfig() {
//		return configRepository.findByApplicationNameAndStatus("DATABASE_TYPE", Boolean.TRUE);
//	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PreAlertShipmentsDto> getPreAlertShipmentDetails() throws Exception {
		Query query = null;
		List<PreAlertShipmentsDto> shipments = null;
		try {
//			if (getDatabaseType().equals(1)) {
//				query = secondaryEntityManager.createNativeQuery(QueryConstants.PRE_ALERT_SHIPMENT_FROM_NAVIGATOR,
//						PreAlertShipmentsDto.class);
//			} else {
				query = primaryEntityManager.createNativeQuery(QueryConstants.PRE_ALERT_SHIPMENT_FROM_NAVIGATOR,
						PreAlertShipmentsDto.class);
//			}
			shipments = query.getResultList();
		} catch (Exception e) {
			log.error("Error in getPreAlertShipmentDetails() : ", e.getMessage());
			throw new BaseException("Unable fetch prealert shipments from data source. !!");
		}
		return shipments;
	}

	@Override
	public void saveShipmentRawData(List<?> object, Integer schedulerId) {
		String json = null;
		ShipmentRawData shipmentRawData = null;
		if (ObjectUtils.isNotEmpty(object) && ObjectUtils.isNotEmpty(schedulerId)) {
			try {
				json = new Gson().toJson(object);
				shipmentRawData = ShipmentRawData.builder().batchId(schedulerId).shipmentData(json).build();
			} catch (Exception exception) {
				json = new Gson().toJson(exception);
				shipmentRawData = ShipmentRawData.builder().batchId(schedulerId).shipmentData(json).build();
			}
			shipmentRawDataRepository.saveAndFlush(shipmentRawData);
		}
	}

	@Override
	public List<EventView> getCurrentRiskActiveGlobalEvents() {
		Date currentStartDate = DateUtils.getStartDateOfDay(DateUtils.getDateInstance());
		List<Integer> approvedStatusList = getAppovedStatusId();
		return eventViewRepository
				.findByStatusAndApprovedStatusInAndIsDeletedAndEndDateGreaterThanEqualAndIsGlobalEvent(
						Integer.valueOf(1), approvedStatusList, Boolean.FALSE, currentStartDate, Integer.valueOf(1));
	}

	private static List<Integer> getAppovedStatusId() {
		List<Integer> approvedStatusList = new ArrayList<>();
		approvedStatusList.add(Constants.APPROVAL_APPROVED);
		approvedStatusList.add(Constants.APPROVAL_RSS);
		approvedStatusList.add(Constants.APPROVAL_RESEND);
		approvedStatusList.add(Constants.APPROVAL_PC);
		return approvedStatusList;
	}

	@Override
	public List<EventManagement> getEventListByIds(List<Integer> ids) {
		return eventManagementRepo.findByIdIn(ids);

	}

	@Override
	public void saveSchedulerEventProcessLogs(List<SchedulerEventProcessLog> eventProcessLogs) {
		schedulerEventProcessLogRepo.saveAllAndFlush(eventProcessLogs);
	}

	@Override
	public void removeSelfShipmentMappersByType(Integer shipmentType, List<Integer> eventIds) {
		if (ObjectUtils.isNotEmpty(shipmentType) && CollectionUtils.isNotEmpty(eventIds)) {
			try {
				if (shipmentType.equals(Constants.ATRISK_SHIPMENT)) {
					atRiskShipmentEventMapRepo.deleteByEventIds(eventIds);
				} else if (shipmentType.equals(Constants.PREALERT_SHIPMENT)) {
					preAlertShipmentEventMapRepo.deleteByEventIds(eventIds);
				} else if (shipmentType.equals(Constants.ARRIVED_SHIPMENT)) {
					arrShipmentEventMapRepo.deleteByEventIds(eventIds);
				}
			} catch (Exception e) {
				log.error("Error : removeSelfShipmentMappersByType : {}", e.getMessage());
			}
		}
	}

	@Override
	public void savePreAlertShipments(List<PreAlertShipments> shipments) {
		preAlertShipmentsRepository.saveAllAndFlush(shipments);
	}

	@Override
	public void savePreAlertMapper(List<PreAlertShipmentEventMap> maps) {
		preAlertShipmentEventMapRepo.saveAllAndFlush(maps);
	}

	@Override
	public void removeInactiveMappers(Integer shipmentType) {
		if (ObjectUtils.isNotEmpty(shipmentType)) {
			try {
				if (shipmentType.equals(Constants.ATRISK_SHIPMENT)) {
					atRiskShipmentEventMapRepo.deleteInactiveMMappers();
				} else if (shipmentType.equals(Constants.PREALERT_SHIPMENT)) {
					preAlertShipmentEventMapRepo.deleteInactiveMMappers();
				} else if (shipmentType.equals(Constants.ARRIVED_SHIPMENT)) {
					arrShipmentEventMapRepo.deleteInactiveMMappers();
				}
			} catch (Exception e) {
				log.error("Error : removeSelfShipmentMappersByType : {}", e.getMessage());
			}
		}
	}

	@Override
	public void removeOtherShipmentMappersByType(Integer shipmentType) {
		if (ObjectUtils.isNotEmpty(shipmentType)) {
			try {
				if (shipmentType.equals(Constants.ATRISK_SHIPMENT)) {
					preAlertShipmentEventMapRepo.deleteByAtRiskShipmentMatch();
					arrShipmentEventMapRepo.deleteByAtRiskShipmentMatch();
				} else if (shipmentType.equals(Constants.PREALERT_SHIPMENT)) {
					arrShipmentEventMapRepo.deleteByPreAlertShipmentMatch();
					atRiskShipmentEventMapRepo.deleteByPreAlertShipmentMatch();
				} else if (shipmentType.equals(Constants.ARRIVED_SHIPMENT)) {
					atRiskShipmentEventMapRepo.deleteByArrShipmentMatch();
					preAlertShipmentEventMapRepo.deleteByArrShipmentMatch();
				}
			} catch (Exception e) {
				log.error("Error : removeOtherShipmentMappersByType : {}", e.getMessage());
			}
		}
	}

}
