package com.bdp.innovation.potentialRiskService.dao;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bdp.innovation.potentialRiskService.model.SchedulerLog;
import com.bdp.innovation.potentialRiskService.model.SchedulerStatus;

@Service
public interface ISchedulerStatusDao {

	Boolean updateScheduledLogs(String scheduledName, String expression);

	void saveSchedulor(SchedulerStatus schedulerStatus);

	List<SchedulerStatus> getAllSchedulerStatus();

	SchedulerStatus findSchedulerStatus(String schedulerName);

	void updateScheduledStatus(String schedulerName, String error);

	void checkCurrentDate();

	void saveSchedulerLog(String name, Date startTime, Date endTime);

	List<SchedulerLog> saveAllSchedulerLogs(List<SchedulerLog> logList);

	Integer saveSchedulerLogWithStatus(String schedulerName, Integer sId, Boolean isEnded);

}
