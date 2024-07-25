package com.bdp.innovation.potentialRiskService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bdp.innovation.potentialRiskService.model.SchedulerStatus;

public interface SchedulerStatusRepository extends JpaRepository<SchedulerStatus, Integer> {

	List<SchedulerStatus> findAllBySchedulerName(String schedulerName);

}
