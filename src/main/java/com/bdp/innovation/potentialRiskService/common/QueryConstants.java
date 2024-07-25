package com.bdp.innovation.potentialRiskService.common;

public interface QueryConstants {

	//NEW PREALERT SHIPMENT QUERY US: 40852
		public static final String PRE_ALERT_SHIPMENT_FROM_NAVIGATOR = "SELECT sd.shipment_id AS shipmentId, sd.bdp_reference_number AS bdpReferenceNumber, sd.group_code AS groupCode,sd.bol_awb_number AS blNumber, \r\n"
				+ "			sd.booking_number AS bookingNumber, sd.geid AS geId, IFNULL(sd.carrier_name, sd.carrier_scac_code) AS carrierName, sd.carrier_scac_code as carrierScacCode, \r\n"
				+ "			sd.order_number AS orderNumber, sd.purchase_order_number AS purchaseOrderNumber, \r\n"
				+ "			sd.container_numbers AS containerNumbers, \r\n"
				+ "			CAST(pdep.port_lat AS DOUBLE) AS latitude, CAST(pdep.port_lng AS DOUBLE) AS longitude, CAST(parr.port_lat AS DOUBLE) AS latitudeArr, \r\n"
				+ "			CAST(parr.port_lng AS DOUBLE) AS longitudeArr,sd.port_of_dep_est_dt AS etd,sd.port_of_dep_act_dt AS atd,sd.port_of_arrvl_est_dt AS eta, \r\n"
				+ "			sd.vessel_name AS vesselName,sd.imo_number AS imoNumber,sd.voyage_flight_number AS voyage,sd.port_of_departure AS originRegion, \r\n"
				+ "			sd.port_of_arrival AS destinationRegion,sd.sbu_id AS sbuId,sd.sbu_description AS sbuDescription,NOW() AS lastScheduledOn,sd.deviations AS deviations, sd.booking_deviations AS bookingDeviations, sd.status_code AS statusCode, \r\n"
				+ "			pdep.port_unloc_code AS portUnlocCode,sd.trshp_1_arrvl_loc AS trshp1ArrvlLoc,sd.trshp_1_arrvl_est_dt AS trshp1ArrvlEstDt, \r\n"
				+ "			sd.trshp_1_dep_est_dt AS trshp1DepEstDt,sd.trshp_2_arrvl_loc AS trshp2ArrvlLoc,sd.trshp_2_arrvl_est_dt AS trshp2ArrvlEstDt, \r\n"
				+ "			sd.trshp_2_dep_est_dt AS trshp2DepEstDt,sd.trshp_3_arrvl_loc AS trshp3ArrvlLoc,sd.trshp_3_arrvl_est_dt AS trshp3ArrvlEstDt, \r\n"
				+ "			sd.trshp_3_dep_est_dt AS trshp3DepEstDt,sd.predictive_eta AS predictiveEta, \r\n"
				+ "			sd.is_ready_to_serve AS isReadyToServe, sd.bdp_service_code AS bdpServiceCode, sd.sale_term_code as saleTermCode \r\n"
				+ "			FROM Risk_Monitor.rm_shipment_details_sync sd \r\n"
				+ "			JOIN Risk_Monitor.rm_ports_unloc pdep ON (pdep.port_unloc_code = sd.port_of_departure) \r\n"
				+ "			JOIN Risk_Monitor.rm_ports_unloc parr ON (parr.port_unloc_code = sd.port_of_arrival) \r\n"
				+ "			JOIN Risk_Monitor.rm_parent_company p ON sd.group_code = p.group_code \r\n"
				+ "			WHERE sd.status_code IN (0,1,5) AND sd.port_of_dep_est_dt IS NOT NULL AND sd.port_of_arrvl_est_dt IS NOT NULL AND sd.is_valid=1 \r\n"
				+ "			GROUP BY sd.bdp_reference_number ";
		
		public static final String SELECT_KPI_SHIPMENTS = "SELECT sd.bdp_reference_number AS bdpReferenceNumber, sd.purchase_order_number AS purchaseOrderNumber, sd.shipment_id AS shipmentIdRm, \r\n"
				+ "sd.shipment_id AS shipmentIdNav, sd.carrier_name AS carrierName, sd.voyage_flight_number AS voyageFlightNumber, sd.vessel_name AS vesselName, \r\n"
				+ "sd.latitude AS latitude, sd.longitude AS longitude, sd.ge_id AS geId, sd.group_code AS groupCode, sd.port_of_arrival AS portOfArrival, \r\n"
				+ "sd.port_of_departure AS portOfDeparture, sd.port_of_dep_est_dt AS portOfDepEstDt, sd.port_of_dep_act_dt AS portOfDepActDt, \r\n"
				+ "sd.port_of_arrvl_est_dt AS portOfArrvlEstDt, sd.sbu_id AS sbuId, sd.sbu_description AS sbuDescription, sd.predictive_eta AS predictiveEta, \r\n"
				+ "sd.is_ready_to_serve AS isReadyToServe, sd.last_scheduled_on AS lastScheduledOn, NOW() AS logDate, sd.bdp_service_code as bdpServiceCode \r\n"
				+ "FROM Risk_Monitor.shipments_table sd ";
		
		public static final String GET_AT_RISK_SHIPMENT_MAPPERS = "SELECT asm.event_id AS eventId, asm.shipment_id AS shipmentId, st.bdp_reference_number AS bdpRefNo, NOW() AS logDate \r\n"
				+ "FROM at_risk_shipment_event_map asm JOIN shipments_table st ON st.shipment_id = asm.shipment_id \r\n"
				+ "JOIN rm_event_management em ON em.id = asm.event_id WHERE em.`status` = 1 ";
		

		public static final String GET_KPI_SHIPMENTS = "SELECT kt.bdp_reference_number AS bdpReferenceNumber, kt.log_date AS logDate FROM kpi_shipment_table kt WHERE kt.shipment_type IN (:shipmentType) \r\n"
				+ "AND DATE(kt.log_date) = CURDATE() ";
		
		public static final String GET_PREALERT_SHIPMENT_MAPPERS = "SELECT psem.event_id AS eventId, psem.shipment_id AS shipmentId, st.bdp_reference_number AS bdpRefNo, NOW() AS logDate \r\n"
				+ "FROM pre_alert_shipment_event_map psem JOIN shipments_table st ON st.shipment_id = psem.shipment_id \r\n"
				+ "JOIN rm_event_management em ON em.id = psem.event_id WHERE em.`status` = 1 ";
		
		public static final String GET_ARRIVED_SHIPMENT_MAPPERS = "SELECT asem.event_id AS eventId, asem.shipment_id AS shipmentId, st.bdp_reference_number AS bdpRefNo, NOW() AS logDate \r\n"
				+ "FROM arrived_shipment_event_map asem JOIN shipments_table st ON st.shipment_id = asem.shipment_id \r\n"
				+ "JOIN rm_event_management em ON em.id = asem.event_id WHERE em.`status` = 1 ";
		
}
