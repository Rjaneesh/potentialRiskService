package com.bdp.innovation.potentialRiskService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bdp.innovation.potentialRiskService.model.PreAlertShipmentEventMap;

import jakarta.transaction.Transactional;

@Repository
public interface PreAlertShipmentEventMapRepo extends JpaRepository<PreAlertShipmentEventMap, Long> {

	@Modifying
	@Transactional
	@Query(value = "DELETE psem FROM pre_alert_shipment_event_map AS psem JOIN rm_event_management AS em ON em.id = psem.event_id WHERE em.`status` = 0 ", nativeQuery = true)
	void deleteInactiveMMappers();

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM `pre_alert_shipment_event_map` AS psem WHERE psem.`event_id` in(:eventIds)", nativeQuery = true)
	void deleteByEventIds(List<Integer> eventIds);

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM `pre_alert_shipment_event_map` AS psem WHERE EXISTS ( SELECT 1 \r\n"
			+ "FROM `at_risk_shipment_event_map` AS arse WHERE arse.`shipment_id` = psem.`shipment_id` \r\n"
			+ "AND arse.`event_id` = psem.`event_id`) ", nativeQuery = true)
	void deleteByAtRiskShipmentMatch();

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM `pre_alert_shipment_event_map` AS psem WHERE EXISTS ( SELECT 1 \r\n"
			+ "FROM `arrived_shipment_event_map` AS asem WHERE asem.`shipment_id` = psem.`shipment_id` \r\n"
			+ "AND asem.`event_id` = psem.`event_id`) ", nativeQuery = true)
	void deleteByArrShipmentMatch();

}
