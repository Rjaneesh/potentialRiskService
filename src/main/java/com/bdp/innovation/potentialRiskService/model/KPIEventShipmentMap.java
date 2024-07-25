package com.bdp.innovation.potentialRiskService.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "kpi_event_shipment_map",
indexes = {
		@Index(name = "idx_id", columnList = "id"),
		@Index(name = "idx_shipment_id", columnList = "shipment_id"),
		@Index(name = "idx_bdp_ref_no", columnList = "bdp_ref_no"),
		@Index(name = "idx_kpi_type", columnList = "kpi_type"),
		@Index(name = "idx_log_date", columnList = "log_date"),
		@Index(name = "idx_event_id", columnList = "event_id")
	})
public class KPIEventShipmentMap {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "event_id")
	private Integer eventId;

	@Column(name = "shipment_id")
	private Integer shipmentId;

	@Column(name = "bdp_ref_no")
	private String bdpRefNo;

	@Column(name = "kpi_type")
	private Integer kpiType;

	@Column(name = "log_date")
	private Date logDate;

}
