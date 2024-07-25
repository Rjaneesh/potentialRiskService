package com.bdp.innovation.potentialRiskService.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.bdp.innovation.potentialRiskService.common.Constants;
import com.bdp.innovation.potentialRiskService.geofence.GeoFenceUtility;
import com.bdp.innovation.potentialRiskService.geofence.GeoFenceUtility.Coordinate;
import com.bdp.innovation.potentialRiskService.model.EventManagement;
import com.bdp.innovation.potentialRiskService.model.EventPolygon;
import com.bdp.innovation.potentialRiskService.model.PreAlertShipments;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Service
public class NavigatorVesselUtility {
	public static List<PreAlertShipments> findAffectedPortWiseShipment(GeoFenceUtility geoFence,
			EventManagement eventManagement, Map<String, List<PreAlertShipments>> porttWiseShipmentMap) {
		List<PreAlertShipments> shipmentDetailsList = null;
		try {
			if (!ObjectUtils.isEmpty(porttWiseShipmentMap)) {
				shipmentDetailsList = new ArrayList<>();
				for (Entry<String, List<PreAlertShipments>> sDetailsMap : porttWiseShipmentMap.entrySet()) {
					PreAlertShipments sDetails = sDetailsMap.getValue().get(0);
					if (ObjectUtils.isEmpty(sDetails.getLongitude()) || (ObjectUtils.isEmpty(sDetails.getLatitude()))) {
						continue;
					}
					Coordinate point = new Coordinate(sDetails.getLongitude(), sDetails.getLatitude());
					if (!ObjectUtils.isEmpty(eventManagement.getFenceType())
							&& eventManagement.getFenceType().equals(Constants.FENCE_CIRCLE)) {
						Coordinate center = new Coordinate(eventManagement.getLongitude(),
								eventManagement.getLatitude());
						if (geoFence.isInsideCircle(center, eventManagement.getEventRadius(), point)) {
							shipmentDetailsList = dateValidationWithRiskEventDate(eventManagement, shipmentDetailsList, sDetailsMap);
						}
					} else if (!ObjectUtils.isEmpty(eventManagement.getFenceType())
							&& eventManagement.getFenceType().equals(Constants.FENCE_POLYGON)) {
						eventManagement.setPolygonList(eventManagement.getEventPolygon().stream()
								.collect(Collectors.groupingBy(EventPolygon::getGroupId)));
						try {
							for (Entry<Integer, List<EventPolygon>> map : eventManagement.getPolygonList().entrySet()) {
								List<EventPolygon> pligonList = map.getValue();
								List<Coordinate> cordinates = pligonList.stream()
										.map(coordinate -> new Coordinate(coordinate.getLongitude(),
												coordinate.getLatitude()))
										.collect(Collectors.toList());
								if (geoFence.isInsidePolygon(cordinates, point)) {
									shipmentDetailsList = dateValidationWithRiskEventDate(eventManagement, shipmentDetailsList, sDetailsMap);
								}
							}
						} catch (Exception e) {

							log.error("findAffectedPortWiseShipment : "+ e.getMessage());
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Error at findAffectedEventWiseShipment : ", e);
		}
		return shipmentDetailsList;
	}
	
	private static List<PreAlertShipments> dateValidationWithRiskEventDate(EventManagement eventManagement,
			List<PreAlertShipments> shipmentDetailsList, Entry<String, List<PreAlertShipments>> sDetailsMap) {
		List<PreAlertShipments> shipment = sDetailsMap.getValue().parallelStream()
				.filter(sd -> (Constants.ORIGIN.equals(sd.getCheckingFor())
						&& sd.getEtd().after(eventManagement.getStartDate())
				        && sd.getEtd().before(eventManagement.getEndDate()))
				
		        || (Constants.DESTINATION.equals(sd.getCheckingFor())
		        		&&(!ObjectUtils.isEmpty(sd.getPredictiveEta()) ? sd.getPredictiveEta() : sd.getEta()).after(eventManagement.getStartDate()))
						&& ((!ObjectUtils.isEmpty(sd.getPredictiveEta()) ? sd.getPredictiveEta() : sd.getEta()).before(eventManagement.getEndDate()))
		       
		        || (Constants.TRSH_1.equals(sd.getCheckingFor())
		        		&& !ObjectUtils.isEmpty(sd.getTrshp1ArrvlEstDt()) 
		        		&& sd.getTrshp1ArrvlEstDt().after(eventManagement.getStartDate())
		        		&& sd.getTrshp1ArrvlEstDt().before(eventManagement.getEndDate()))
		        
		        || (Constants.TRSH_1.equals(sd.getCheckingFor())
		        		&& !ObjectUtils.isEmpty(sd.getTrshp1DepEstDt()) 
		        		&& sd.getTrshp1DepEstDt().after(eventManagement.getStartDate())
		        		&& sd.getTrshp1DepEstDt().before(eventManagement.getEndDate()))
		        
		        || (Constants.TRSH_2.equals(sd.getCheckingFor())
		        		&& !ObjectUtils.isEmpty(sd.getTrshp2ArrvlEstDt()) 
		        		&& sd.getTrshp2ArrvlEstDt().after(eventManagement.getStartDate())
		        		&& sd.getTrshp2ArrvlEstDt().before(eventManagement.getEndDate()))
		        
		        || (Constants.TRSH_2.equals(sd.getCheckingFor())
		        		&& !ObjectUtils.isEmpty(sd.getTrshp2DepEstDt()) 
		        		&& sd.getTrshp2DepEstDt().after(eventManagement.getStartDate())
		        		&& sd.getTrshp2DepEstDt().before(eventManagement.getEndDate()))
		        
		        || (Constants.TRSH_3.equals(sd.getCheckingFor())
		        		&& !ObjectUtils.isEmpty(sd.getTrshp3ArrvlEstDt()) 
		        		&& sd.getTrshp3ArrvlEstDt().after(eventManagement.getStartDate())
		        		&& sd.getTrshp3ArrvlEstDt().before(eventManagement.getEndDate()))
		        
		        || (Constants.TRSH_3.equals(sd.getCheckingFor())
		        		&& !ObjectUtils.isEmpty(sd.getTrshp3DepEstDt()) 
		        		&& sd.getTrshp3DepEstDt().after(eventManagement.getStartDate())
		        		&& sd.getTrshp3DepEstDt().before(eventManagement.getEndDate()))
		        ).collect(Collectors.toList());
		
		if (!CollectionUtils.isEmpty(shipment)) {
			shipmentDetailsList.addAll(shipment);
		}
		if (!CollectionUtils.isEmpty(shipmentDetailsList)) {
			shipmentDetailsList = shipmentDetailsList.stream().distinct().collect(Collectors.toList());
		}
		return shipmentDetailsList;
	}
	
}
