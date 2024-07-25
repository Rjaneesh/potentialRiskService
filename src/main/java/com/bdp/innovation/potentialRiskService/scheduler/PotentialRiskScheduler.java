package com.bdp.innovation.potentialRiskService.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bdp.innovation.potentialRiskService.dao.ISchedulerStatusDao;
import com.bdp.innovation.potentialRiskService.serviceimpl.PreAlertEventProcessor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PotentialRiskScheduler {
	
    @Autowired
    private PreAlertEventProcessor preAlertEventProcessor;
    
    @Autowired
	private ISchedulerStatusDao schedulerStatusDao ;

    @Scheduled(cron = "0 30 3 * * *")
    public void preAlertShipment() {
        if (updateScheduledLogs("PREALERTSHIPMENT", "0 30 3 * * *")) {
        	preAlertEventProcessor.processPotentialRiskEvent();
        }
    }
    
    private Boolean updateScheduledLogs(String scheduledName, final String expression) {
        return schedulerStatusDao.updateScheduledLogs(scheduledName, expression);
    }
}
