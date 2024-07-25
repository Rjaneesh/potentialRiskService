package com.bdp.innovation.potentialRiskService.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bdp.innovation.potentialRiskService.common.CommonUtils;
import com.bdp.innovation.potentialRiskService.common.Constants;
import com.bdp.innovation.potentialRiskService.endpoint.IPreAlertEventServiceEndPoint;
import com.bdp.innovation.potentialRiskService.serviceimpl.PreAlertEventProcessor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping(value = { "riskmonitor-api/api/v1/pre-alert-scheduler", "/api/*/" })
public class PreAlertEventController implements IPreAlertEventServiceEndPoint {

	@Autowired
	private PreAlertEventProcessor preAlertEventProcessor;

	@Override
	public ResponseEntity<Map<String, Object>> executePreAlertScheduler() {
		try {
			preAlertEventProcessor.processPotentialRiskEvent();
		} catch (Exception e) {
			log.error("Getting Error inside :: PreAlertEventController :: executePreAlertScheduler");
			log.error(e.getMessage());
			return CommonUtils.buildErrorResponse(Constants.ERROR_RESPONSE, e.getMessage());
		}
		return CommonUtils.buildResponseMessage("Successfully execute the executePreAlertScheduler.", null);
	}

}
