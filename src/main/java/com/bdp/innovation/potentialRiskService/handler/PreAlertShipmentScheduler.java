package com.bdp.innovation.potentialRiskService.handler;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.bdp.innovation.potentialRiskService.common.CommonUtils;
import com.bdp.innovation.potentialRiskService.serviceimpl.PreAlertEventProcessor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PreAlertShipmentScheduler implements RequestHandler<Object, Map<String, Object>> {

	@Autowired
	private PreAlertEventProcessor preAlertEventProcessor;

	@Override
    public Map<String, Object> handleRequest(Object input, Context context) {
		log.info("Entering :: PreAlertShipmentScheduler :: handleRequest");
        try {
        	preAlertEventProcessor.processPotentialRiskEvent();
            return CommonUtils.createResponse(null, "Scheduler executed successfully", HttpStatus.OK);
        } catch (Exception e) {
        	log.info("Getting Error inside :: PreAlertShipmentScheduler :: handleRequest");
            return CommonUtils.handleException(e);
        }
    }

}
