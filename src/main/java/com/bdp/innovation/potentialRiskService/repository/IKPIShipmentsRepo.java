package com.bdp.innovation.potentialRiskService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bdp.innovation.potentialRiskService.model.KPIShipments;

public interface IKPIShipmentsRepo extends JpaRepository<KPIShipments, Long> {

	public List<KPIShipments> findByShipmentType(Integer shipmentType);

}
