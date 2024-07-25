package com.bdp.innovation.potentialRiskService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bdp.innovation.potentialRiskService.model.EventManagement;

public interface EventManagementRepository extends JpaRepository<EventManagement, Integer> {

	List<EventManagement> findByIdIn(List<Integer> ids);

}
