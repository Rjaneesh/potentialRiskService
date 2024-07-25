package com.bdp.innovation.potentialRiskService.serviceimpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import com.bdp.innovation.potentialRiskService.common.CommonUtils;
import com.bdp.innovation.potentialRiskService.common.Commons;
import com.bdp.innovation.potentialRiskService.common.Constants;
import com.bdp.innovation.potentialRiskService.common.ListUtils;
import com.bdp.innovation.potentialRiskService.dao.ISchedulerStatusDao;
import com.bdp.innovation.potentialRiskService.dao.IshipmentsDao;
import com.bdp.innovation.potentialRiskService.dto.PreAlertShipmentsDto;
import com.bdp.innovation.potentialRiskService.geofence.GeoFenceUtility;
import com.bdp.innovation.potentialRiskService.model.EventManagement;
import com.bdp.innovation.potentialRiskService.model.EventView;
import com.bdp.innovation.potentialRiskService.model.PreAlertShipmentEventMap;
import com.bdp.innovation.potentialRiskService.model.PreAlertShipments;
import com.bdp.innovation.potentialRiskService.model.SchedulerEventProcessLog;

public class PreAlertEventService implements Runnable {

	private Logger log = LoggerFactory.getLogger(PreAlertEventService.class);
	private IshipmentsDao shipmentDetailsDao;
	private ISchedulerStatusDao schedulerLogsDao;
	private GeoFenceUtility geoFenceUtility;
	private Commons commons;
	private NavigatorVesselUtility navigatorVesselUtility;

	public PreAlertEventService(IshipmentsDao shipmentDetailsDao, ISchedulerStatusDao schedulerLogsDao,
			GeoFenceUtility geoFenceUtility, Commons commons, NavigatorVesselUtility navigatorVesselUtility) {
		this.shipmentDetailsDao = shipmentDetailsDao;
		this.schedulerLogsDao = schedulerLogsDao;
		this.geoFenceUtility = geoFenceUtility;
		this.commons = commons;
		this.navigatorVesselUtility = navigatorVesselUtility;
	}

	@Override
	public void run() {
		Integer schedulerLogId = null;
		try {
			schedulerLogId = schedulerLogsDao.saveSchedulerLogWithStatus(Constants.PREALERT_SHIPMENT_SCH, null,
					Boolean.FALSE);
			findAffectedPreAlertShipments(schedulerLogId);
		} catch (Exception e) {
			log.error("ERROR WHILE RUNNING PREALERT SCHEDULER : {}", e.getMessage());
		} finally {
			// REMOVING Inactive mappers
			shipmentDetailsDao.removeInactiveMappers(Constants.PREALERT_SHIPMENT);
			// REMOVING THE MAPPING IF SAME SHIPMENTS ARE MAPPED WITH OTHER TYPE OF SHIPMENT(AT RISK OR ARRIVED) FOR SAME EVENT.
			shipmentDetailsDao.removeOtherShipmentMappersByType(Constants.PREALERT_SHIPMENT);
			commons.updateAssociatedShipments(Constants.PREALERT_SHIPMENT_SCH); // UPDATING MAPPING FOR KPI
			schedulerLogsDao.saveSchedulerLogWithStatus(Constants.PREALERT_SHIPMENT_SCH, schedulerLogId, Boolean.TRUE);
		}
	}

	/**
	 * @param schedulerId
	 */
	private void findAffectedPreAlertShipments(Integer schedulerId) {
		log.info("---------- PREALERT SHIPMENT SCHEDULER PROCESS STARTED ------------");
		PreAlertShipments originShipment = null;
		List<PreAlertShipments> preAlertShipments = null;
		List<PreAlertShipments> preAlertShipmenList = null;
		List<PreAlertShipmentsDto> preAlertShipmentsDto = null;
		try {
			// FETCHING ALL PREALERT SHIPMENT FROM DATA SOURCE.
			preAlertShipmentsDto = shipmentDetailsDao.getPreAlertShipmentDetails();
			// COPPY DATA FROM DTO TO ENTITY CLASS
			if (!CollectionUtils.isEmpty(preAlertShipmentsDto)) {
				preAlertShipments = new ArrayList<>();
				// SAVING ROW DATA IN DB FOR REFERENCE.
				shipmentDetailsDao.saveShipmentRawData(preAlertShipmentsDto, schedulerId);
				preAlertShipments = preAlertShipmentsDto.stream().map(ship -> {
					PreAlertShipments shipment = null;
					try {
						shipment = new PreAlertShipments();
						BeanUtils.copyProperties(ship, shipment);
						shipment.setImoNumber((ObjectUtils.isNotEmpty(ship.getImoNumber())
								&& !ship.getImoNumber().equalsIgnoreCase("UNKNOWN")
								&& !ship.getImoNumber().equalsIgnoreCase("null"))
										? Integer.valueOf(ship.getImoNumber().trim())
										: Integer.valueOf(0));
					} catch (Exception e) {
						log.error("Error due to invalid imo number : " + e.getMessage());
					}
					return shipment;
				}).toList();
			}
			if (!CollectionUtils.isEmpty(preAlertShipments)) {
				preAlertShipmenList = new ArrayList<>();
				for (PreAlertShipments preAlertShipment : preAlertShipments) {
					try {
						originShipment = new PreAlertShipments();
						BeanUtils.copyProperties(preAlertShipment, originShipment);
						if (!originShipment.getOriginRegion().equals(originShipment.getDestinationRegion())) {
							originShipment.setCheckingFor(Constants.ORIGIN);
							preAlertShipmenList.add(originShipment);
							// CREATING A DUPLICATE COPPY OF SHIPMENT FOR DESTINATION PORT
							PreAlertShipments destinationShipment = new PreAlertShipments();
							BeanUtils.copyProperties(originShipment, destinationShipment);
							destinationShipment.setPortUnlocCode(originShipment.getDestinationRegion());
							destinationShipment.setLatitude(originShipment.getLatitudeArr());
							destinationShipment.setLongitude(originShipment.getLongitudeArr());
							destinationShipment.setCheckingFor(Constants.DESTINATION);
							preAlertShipmenList.add(destinationShipment);
							// IN CASE OF TRANSHIPMENT, CREATING DUPLICATE COPPY OF SHIPMENT WITH RESPECT TO
							// TRSH PORT
							if (!ObjectUtils.isEmpty(originShipment.getTrshp1ArrvlLoc())) {
								addShipmentForTrshipPort(preAlertShipmenList, originShipment, Constants.TRSH_1);
								if (!ObjectUtils.isEmpty(originShipment.getTrshp2ArrvlLoc())) {
									addShipmentForTrshipPort(preAlertShipmenList, originShipment, Constants.TRSH_2);
									if (!ObjectUtils.isEmpty(originShipment.getTrshp3ArrvlLoc())) {
										addShipmentForTrshipPort(preAlertShipmenList, originShipment, Constants.TRSH_3);
									}
								}
							}
						}
					} catch (Exception e) {
						log.error("ERROR WHILE COPPING DATA FOR DESTINATION AND TRSHIP PORT : " + e.getMessage());
					}
				}
				validateAffactedShipments(preAlertShipmenList);
			}
			log.info("--------------PREALERT SHIPMENT SCHEDULER PROCESS COMPLETED -------------------- ");
		} catch (Exception e) {
			log.error("PreAlertEvenService:getShipmentsFromNav():{}", e.getMessage());
		}
	}

	/**
	 * @param preAlertShipmenList
	 * @param alertShipment
	 * @param trshipType
	 */
	private void addShipmentForTrshipPort(List<PreAlertShipments> preAlertShipmenList, PreAlertShipments alertShipment,
			String trshipType) {
		PreAlertShipments trship = new PreAlertShipments();
		BeanUtils.copyProperties(alertShipment, trship);
		if (trshipType.equals(Constants.TRSH_1)) {
			trship.setPortUnlocCode(alertShipment.getTrshp1ArrvlLoc());
			trship.setLatitude(alertShipment.getTrshp1Lat());
			trship.setLongitude(alertShipment.getTrshp1Lng());
			trship.setCheckingFor(Constants.TRSH_1);
		} else if (trshipType.equals(Constants.TRSH_2)) {
			trship.setPortUnlocCode(alertShipment.getTrshp2ArrvlLoc());
			trship.setLatitude(alertShipment.getTrshp2Lat());
			trship.setLongitude(alertShipment.getTrshp2Lng());
			trship.setCheckingFor(Constants.TRSH_2);
		} else if (trshipType.equals(Constants.TRSH_3)) {
			trship.setPortUnlocCode(alertShipment.getTrshp3ArrvlLoc());
			trship.setLatitude(alertShipment.getTrshp3Lat());
			trship.setLongitude(alertShipment.getTrshp3Lng());
			trship.setCheckingFor(Constants.TRSH_3);
		}
		preAlertShipmenList.add(trship);
	}

	/**
	 * @param preAlertShipmentDetails
	 * @param eventManagement
	 */
	private void validateAffactedShipments(List<PreAlertShipments> preAlertShipmentDetails) {
		log.info("PreAlertEvenService::getShipmentDataFromNav");
		Map<String, List<PreAlertShipments>> portWiseShipmentMap = null;
		try {
			int totalPageNo = 50;
			List<EventView> activeEvenViewtList = shipmentDetailsDao.getCurrentRiskActiveGlobalEvents();
			if (!activeEvenViewtList.isEmpty() && !CollectionUtils.isEmpty(activeEvenViewtList)) {
				ListUtils.doPaginated(activeEvenViewtList, totalPageNo, paggedEventData -> {
//					List<Integer> events = paggedEventData.stream().map(EventView::getId).collect(Collectors.toList());
					List<Integer> events = Arrays.asList(183465);
					List<EventManagement> activeEventList = shipmentDetailsDao.getEventListByIds(events);
					batchWiseEventUpdate(preAlertShipmentDetails, portWiseShipmentMap, activeEventList);
				});
			}
		} catch (Exception e) {
			log.error("PreAlertEvenService:getShipmentDataFromNav:{}", e.getMessage());
			throw new IllegalArgumentException(e.getMessage());
		}
	}

	private void batchWiseEventUpdate(List<PreAlertShipments> preAlertShipmentDetails,
			Map<String, List<PreAlertShipments>> portWiseShipmentMap, List<EventManagement> activeEventList) {
		log.info("Entry::PreAlertEvenService::batchWiseEventUpdate");
		List<PreAlertShipments> preAlertShipments;
		int size = activeEventList.size();

		for (EventManagement preAlertEvent : activeEventList) {

			// Removing already mapped shipments
			preAlertEvent.setPreAlertShipments(null);
			preAlertEvent.setTotalPreAlertShipAssociated(0);

			log.info(preAlertEvent.getEventId(), " ::: EVENT SIZE ::: {}", size--);
			try {
				preAlertShipments = new ArrayList<>();
				if (!CollectionUtils.isEmpty(preAlertShipmentDetails)) {
					portWiseShipmentMap = preAlertShipmentDetails.stream()
							.collect(Collectors.groupingBy(PreAlertShipments::getPortUnlocCode));
					if (!CollectionUtils.isEmpty(portWiseShipmentMap)) {
						preAlertShipments = navigatorVesselUtility.findAffectedPortWiseShipment(geoFenceUtility,
								preAlertEvent, portWiseShipmentMap);
						if (!CollectionUtils.isEmpty(preAlertShipments)) {
							preAlertEvent.setPreAlertShipments(preAlertShipments);
						} else {
							preAlertEvent.setPreAlertShipments(null);
						}
					}
				}
			} catch (Exception e) {
				log.error("ERROR WHILE MAPPING SHIPMENTS WITH EVENT ID :{} ", preAlertEvent.getEventId());
			}
		}
		// SAVING PREALERT SHIPMENT DATA IN SHIPMENTS_TABLE(COMMON).
		saveShipmentsTable(activeEventList);
	}

	/**
	 * @param activeEventList
	 */
	private void saveShipmentsTable(List<EventManagement> activeEventList) {
		List<Integer> eventIds = null;
		List<SchedulerEventProcessLog> eventProcessLogs = null;
		List<PreAlertShipments> preAlertShipments = null;
		List<PreAlertShipmentEventMap> preShipmentEventMaps = null;
		if (!CollectionUtils.isEmpty(activeEventList)) {
			eventIds = new ArrayList<>();
			eventProcessLogs = new ArrayList<>();
			preAlertShipments = new ArrayList<>();
			preShipmentEventMaps = new ArrayList<>();
			for (EventManagement eventManagement : activeEventList) {
				List<PreAlertShipments> shipmentList = null;
				List<PreAlertShipmentEventMap> mappersList = null;
				if (ObjectUtils.isNotEmpty(eventManagement)) {
					// 1.GETTING ELIGIBLE SHIPMENTS DATA FOR SHIPMENT TABLE
					if (!CollectionUtils.isEmpty(eventManagement.getPreAlertShipments())) {
						shipmentList = eventManagement.getPreAlertShipments().stream().map(arr -> {
							PreAlertShipments shipments = new PreAlertShipments();
							BeanUtils.copyProperties(arr, shipments);
							shipments.setUpdatedDate(new Date());
							shipments.setUpdatedBy(Constants.PREALERT_SHIPMENT_SCH);
							return shipments;
						}).toList();
						preAlertShipments.addAll(shipmentList);
					}
					// 2. CREATE EVENTS-SHIPMENT MAP DATA FOR MAPPER TABLE
					if (!CollectionUtils.isEmpty(shipmentList)) {
						mappersList = shipmentList.stream().map(sd -> {
							return PreAlertShipmentEventMap.builder().eventId(eventManagement.getId())
									.shipmentId(sd.getShipmentId()).build();
						}).toList();
						preShipmentEventMaps.addAll(mappersList);
					}
					eventIds.add(eventManagement.getId());
					eventProcessLogs.add(SchedulerEventProcessLog.builder().eventId(eventManagement.getId())
							.shipmentCount(CollectionUtils.isEmpty(shipmentList) ? 0 : shipmentList.size())
							.schedulerType(Constants.PREALERT_SHIPMENT).recordDate(new Date()).build());
				}
			}
			// 3. SAVE TO SCHEDULEREVENTPROCESSLOGS
			if (!CollectionUtils.isEmpty(eventProcessLogs)) {
				shipmentDetailsDao.saveSchedulerEventProcessLogs(eventProcessLogs);
			}
			// 4.REMOVE PREVIOUS ENTRIES OF SAME EVENTS
			if (!CollectionUtils.isEmpty(eventIds)) {
				shipmentDetailsDao.removeSelfShipmentMappersByType(Constants.PREALERT_SHIPMENT, eventIds);
			}
			// 5.SAVE DISTINCT UPDATED SHIPMENT DATA TO SHIPMENT TABLE.
			if (!CollectionUtils.isEmpty(preAlertShipments)) {
				preAlertShipments = preAlertShipments.stream()
						.filter(CommonUtils.distinctByKey(PreAlertShipments::getShipmentId)).toList();
				shipmentDetailsDao.savePreAlertShipments(preAlertShipments);
			}
			// 6.SAVE TO MAPPER TABLE.
			if (!CollectionUtils.isEmpty(preShipmentEventMaps)) {
				shipmentDetailsDao.savePreAlertMapper(preShipmentEventMaps);
			}
		}
	}
}
