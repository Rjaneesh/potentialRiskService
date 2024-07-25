package com.bdp.innovation.potentialRiskService.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bdp.innovation.potentialRiskService.model.ArrShipmentEventMap;

import jakarta.transaction.Transactional;

@Repository
public interface ArrShipmentEventMapRepo extends JpaRepository<ArrShipmentEventMap, Long> {

	@Modifying
	@Transactional
	@Query(value = "DELETE asem FROM arrived_shipment_event_map AS asem JOIN rm_event_management AS em ON em.id = asem.event_id WHERE em.`status` = 0 ", nativeQuery = true)
	void deleteInactiveMMappers();

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM `arrived_shipment_event_map` AS asem WHERE asem.`event_id` in(:eventIds)", nativeQuery = true)
	void deleteByEventIds(List<Integer> eventIds);

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM `arrived_shipment_event_map` AS asem WHERE EXISTS ( \r\n"
			+ "SELECT 1 FROM `at_risk_shipment_event_map` AS arsem \r\n"
			+ "WHERE arsem.`shipment_id` = asem.`shipment_id` AND arsem.`event_id` = asem.`event_id`) ", nativeQuery = true)
	void deleteByAtRiskShipmentMatch();

	@Modifying
	@Transactional
	@Query(value = "DELETE FROM `arrived_shipment_event_map` AS asem WHERE EXISTS ( \r\n"
			+ "SELECT 1 FROM `pre_alert_shipment_event_map` AS psem \r\n"
			+ "WHERE psem.`shipment_id` = asem.`shipment_id` AND psem.`event_id` = asem.`event_id`) ", nativeQuery = true)
	void deleteByPreAlertShipmentMatch();

}