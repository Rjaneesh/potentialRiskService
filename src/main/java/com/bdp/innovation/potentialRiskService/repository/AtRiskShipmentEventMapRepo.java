package com.bdp.innovation.potentialRiskService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.bdp.innovation.potentialRiskService.model.AtRiskShipmentEventMap;

import jakarta.transaction.Transactional;

public interface AtRiskShipmentEventMapRepo extends JpaRepository<AtRiskShipmentEventMap, Long> {


    @Modifying
    @Transactional
    @Query(value = "DELETE arsem FROM at_risk_shipment_event_map AS arsem JOIN rm_event_management AS em ON em.id = arsem.event_id WHERE em.`status` = 0 ", nativeQuery = true)
    void deleteInactiveMMappers();

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM `at_risk_shipment_event_map` AS arsem WHERE arsem.`event_id` in(:eventIds)", nativeQuery = true)
    void deleteByEventIds(List<Integer> eventIds);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM `at_risk_shipment_event_map` AS arsem WHERE EXISTS ( \r\n" + "SELECT 1 FROM `pre_alert_shipment_event_map` AS psem \r\n" + "WHERE psem.`shipment_id` = arsem.`shipment_id` \r\n" + "AND psem.`event_id` = arsem.`event_id`)", nativeQuery = true)
    void deleteByPreAlertShipmentMatch();

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM `pre_alert_shipment_event_map` AS psem WHERE EXISTS ( SELECT 1 \r\n" + "FROM `arrived_shipment_event_map` AS asem WHERE asem.`shipment_id` = psem.`shipment_id` \r\n" + "AND asem.`event_id` = psem.`event_id`) ", nativeQuery = true)
    void deleteByArrShipmentMatch();

    @Query(value = "SELECT asem.* FROM at_risk_shipment_event_map asem WHERE NOT EXISTS (SELECT * " + "FROM history_event_shipment_map hesm WHERE hesm.past_date = CURDATE() AND hesm.event_id = asem.event_id AND hesm.shipment_id = asem.shipment_id)", nativeQuery = true)
    List<AtRiskShipmentEventMap> findUniqueMappers();

}
