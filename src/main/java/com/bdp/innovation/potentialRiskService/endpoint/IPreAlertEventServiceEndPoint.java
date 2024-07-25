package com.bdp.innovation.potentialRiskService.endpoint;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;

import com.bdp.innovation.potentialRiskService.common.Constants;


public interface IPreAlertEventServiceEndPoint {
	@PostMapping("/executePreAlertScheduler")
	@PreAuthorize(Constants.ROLE_EVENT_MANAGER)
	public ResponseEntity<Map<String, Object>> executePreAlertScheduler();
}
