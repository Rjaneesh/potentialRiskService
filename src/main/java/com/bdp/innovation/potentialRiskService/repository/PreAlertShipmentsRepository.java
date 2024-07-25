package com.bdp.innovation.potentialRiskService.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bdp.innovation.potentialRiskService.model.PreAlertShipments;

public interface PreAlertShipmentsRepository extends JpaRepository<PreAlertShipments, Integer> {
	
}