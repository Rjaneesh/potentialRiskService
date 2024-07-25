package com.bdp.innovation.potentialRiskService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bdp.innovation.potentialRiskService.model.KPIEventShipmentMap;

public interface IKPIEventShipmentMapRepo extends JpaRepository<KPIEventShipmentMap, Long> {

	@Query(value = "SELECT * FROM kpi_event_shipment_map esm WHERE esm.kpi_type IN (:kpiType) AND DATE(esm.log_date) = CURDATE() ", 
			nativeQuery = true)
	public List<KPIEventShipmentMap> findByKpiType(Integer kpiType);

}
