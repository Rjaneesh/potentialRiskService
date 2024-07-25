package com.bdp.innovation.potentialRiskService.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdp.innovation.potentialRiskService.common.Commons;
import com.bdp.innovation.potentialRiskService.dao.ISchedulerStatusDao;
import com.bdp.innovation.potentialRiskService.dao.IshipmentsDao;
import com.bdp.innovation.potentialRiskService.geofence.GeoFenceUtility;

@Service
public class PreAlertEventProcessor {

	@Autowired
	private GeoFenceUtility geoFenceUtility;

	@Autowired
	ISchedulerStatusDao schedulerStatusDao;

	@Autowired
	private Commons commons;

	@Autowired
	private IshipmentsDao ishipmentsDao;

	@Autowired
	private NavigatorVesselUtility navigatorVesselUtility;

	public void processPotentialRiskEvent() {
		new Thread(new PreAlertEventService(ishipmentsDao, schedulerStatusDao, geoFenceUtility, commons,
				navigatorVesselUtility)).start();
	}

}
