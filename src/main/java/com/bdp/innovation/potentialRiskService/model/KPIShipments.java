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
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "kpi_shipment_table",
indexes = {
		@Index(name = "idx_id", columnList = "id"),
		@Index(name = "idx_shipment_id_rm", columnList = "shipment_id_rm"),
		@Index(name = "idx_bdp_reference_number", columnList = "bdp_reference_number"),
		@Index(name = "idx_shipment_type", columnList = "shipment_type"),
		@Index(name = "idx_log_date", columnList = "log_date"),
		@Index(name = "idx_ge_id", columnList = "ge_id"),
		@Index(name = "idx_sbu_id", columnList = "sbu_id")
	})
public class KPIShipments {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "shipment_type")
	private Integer shipmentType;

	@Column(name = "bdp_reference_number")
	private String bdpReferenceNumber;

	@Column(name = "purchase_order_number")
	private String purchaseOrderNumber;

	@Column(name = "shipment_id_rm")
	private Integer shipmentIdRm;

	@Column(name = "shipment_id_nav")
	private Integer shipmentIdNav;

	@Column(name = "carrier_name")
	private String carrierName;

	@Column(name = "voyage_flight_number")
	private String voyageFlightNumber;

	@Column(name = "vessel_name")
	private String vesselName;

	@Column(name = "latitude")
	private Double latitude;

	@Column(name = "longitude")
	private Double longitude;

	@Column(name = "ge_id")
	private Integer geId;

	@Column(name = "group_code")
	private Integer groupCode;

	@Column(name = "port_of_arrival")
	private String portOfArrival;

	@Column(name = "port_of_departure")
	private String portOfDeparture;

	@Column(name = "port_of_dep_est_dt")
	private Date portOfDepEstDt;

	@Column(name = "port_of_dep_act_dt")
	private Date portOfDepActDt;

	@Column(name = "port_of_arrvl_est_dt")
	private Date portOfArrvlEstDt;

	@Column(name = "port_of_arrvl_act_dt")
	private Date portOfArrvlActDt;

	@Column(name = "sbu_id")
	private Integer sbuId;

	@Column(name = "sbu_description")
	private String sbuDescription;

	@Column(name = "predictive_eta")
	private Date predictiveEta;

	@Column(name = "is_ready_to_serve")
	private Integer isReadyToServe;

	@Column(name = "last_scheduled_on")
	private Date lastScheduledOn;

	@Column(name = "log_date")
	private Date logDate;
	
	@Column(name = "bdp_service_code")
	private String bdpServiceCode;

}
