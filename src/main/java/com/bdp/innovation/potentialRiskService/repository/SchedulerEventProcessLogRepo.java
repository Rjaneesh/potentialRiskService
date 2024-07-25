package com.bdp.innovation.potentialRiskService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bdp.innovation.potentialRiskService.model.SchedulerEventProcessLog;

public interface SchedulerEventProcessLogRepo extends JpaRepository<SchedulerEventProcessLog, Long>  {
	@Query(value = "SELECT * FROM scheduler_event_process_log sepl WHERE sepl.event_id =:eventId ORDER BY sepl.id DESC", nativeQuery = true)
    List<SchedulerEventProcessLog> findByEventId(Integer eventId);
}
