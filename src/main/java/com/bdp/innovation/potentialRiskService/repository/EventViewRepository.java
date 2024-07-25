package com.bdp.innovation.potentialRiskService.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bdp.innovation.potentialRiskService.model.EventView;

public interface EventViewRepository extends JpaRepository<EventView, Integer> {
	List<EventView> findByStatusAndApprovedStatusInAndIsDeletedAndEndDateGreaterThanEqual(Integer valueOf,
			List<Integer> approvedStatusList, Boolean false1, Date currentStartDate);

	List<EventView> findByStatusAndApprovedStatusInAndIsDeletedAndEndDateGreaterThanEqualAndIsGlobalEvent(
			Integer valueOf, List<Integer> approvedStatusList, Boolean false1, Date currentStartDate, Integer isGlobal);
}
