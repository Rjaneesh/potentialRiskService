package com.bdp.innovation.potentialRiskService.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bdp.innovation.potentialRiskService.dto.PreAlertShipmentsDto;
import com.bdp.innovation.potentialRiskService.model.EventManagement;
import com.bdp.innovation.potentialRiskService.model.EventView;
import com.bdp.innovation.potentialRiskService.model.PreAlertShipmentEventMap;
import com.bdp.innovation.potentialRiskService.model.PreAlertShipments;
import com.bdp.innovation.potentialRiskService.model.SchedulerEventProcessLog;

@Service
public interface IshipmentsDao {

	List<PreAlertShipmentsDto> getPreAlertShipmentDetails() throws Exception;

//	Integer getDatabaseType();

	void saveShipmentRawData(List<?> object, Integer schedulerId);

	List<EventView> getCurrentRiskActiveGlobalEvents();

	List<EventManagement> getEventListByIds(List<Integer> ids);

	void saveSchedulerEventProcessLogs(List<SchedulerEventProcessLog> eventProcessLogs);

	void removeSelfShipmentMappersByType(Integer shipmentType, List<Integer> eventIds);

	void savePreAlertShipments(List<PreAlertShipments> shipments);

	void savePreAlertMapper(List<PreAlertShipmentEventMap> maps);

	void removeInactiveMappers(Integer shipmentType);

	void removeOtherShipmentMappersByType(Integer shipmentType);

}
