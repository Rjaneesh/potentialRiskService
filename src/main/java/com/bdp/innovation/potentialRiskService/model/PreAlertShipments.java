package com.bdp.innovation.potentialRiskService.model;


import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shipments_table")
public class PreAlertShipments {

	@Id
	@Column(name = "shipment_id", unique = true)
	private Integer shipmentId;

	@Column(name = "bdp_reference_number")
	private String bdpReferenceNumber;

	@Column(name = "total_container")
	private Integer totalContainer;

	@Column(name = "group_code")
	private Integer groupCode;

	@Column(name = "bl_number")
	private String blNumber;

	@Column(name = "booking_number")
	private String bookingNumber;

	@Column(name = "ge_id")
	private Integer geId;

	@Column(name = "carrier_name")
	private String carrierName;
	
	@Column(name = "carrier_scac_code")
    private String carrierScacCode;

	@Column(name = "order_number")
	private String orderNumber;

	@Column(name = "purchase_order_number")
	private String purchaseOrderNumber;

	@Column(name = "container_numbers", length = 6000)
	private String containerNumbers;

	@Column(name = "latitude")
	private Double latitude;

	@Column(name = "longitude")
	private Double longitude;

	@Column(name = "port_of_dep_est_dt")
	private Date etd;

	@Column(name = "port_of_dep_act_dt")
	private Date atd;

	@Column(name = "port_of_arrvl_est_dt")
	private Date eta;
	
//	@Column(name = "port_of_arrvl_act_dt")
//	private Date ata;

	@Column(name = "vessel_name")
	private String vesselName;

	@Column(name = "imo_number")
	private Integer imoNumber;

	@Column(name = "voyage_flight_number")
	private String voyage;

	@Column(name = "port_of_departure")
	private String originRegion;

	@Column(name = "port_of_arrival")
	private String destinationRegion;

	@Column(name = "sbu_id")
	private Integer sbuId;

	@Column(name = "sbu_description")
	private String sbuDescription;

	@Column(name = "last_scheduled_on")
	private Date lastScheduledOn;

	@Column(name = "booking_deviations")
	private Double bookingDeviations;

	@Column(name = "deviations")
	private Double deviations;

	@Column(name = "status_code")
	private Integer statusCode;

	@Column(name = "predictive_eta")
	private Date predictiveEta;

	@Column(name = "is_ready_to_serve")
	private Integer isReadyToServe;

	@Transient
	private String portUnlocCode;

	@Transient
	private Double latitudeArr;

	@Transient
	private Double longitudeArr;

	@Transient
	private String checkingFor;

	@Column(name = "trshp_1_arrvl_loc")
	private String trshp1ArrvlLoc;

	@Column(name = "trshp_1_arrvl_est_dt")
	private Date trshp1ArrvlEstDt;

	@Column(name = "trshp_1_dep_est_dt")
	private Date trshp1DepEstDt;

	@Column(name = "trshp_2_arrvl_loc")
	private String trshp2ArrvlLoc;

	@Column(name = "trshp_2_arrvl_est_dt")
	private Date trshp2ArrvlEstDt;

	@Column(name = "trshp_2_dep_est_dt")
	private Date trshp2DepEstDt;

	@Column(name = "trshp_3_arrvl_loc")
	private String trshp3ArrvlLoc;

	@Column(name = "trshp_3_arrvl_est_dt")
	private Date trshp3ArrvlEstDt;

	@Column(name = "trshp_3_dep_est_dt")
	private Date trshp3DepEstDt;

	@Transient
	private Boolean isTranship;

	@Transient
	private Double trshp1Lat;

	@Transient
	private Double trshp1Lng;

	@Transient
	private Double trshp2Lat;

	@Transient
	private Double trshp2Lng;

	@Transient
	private Double trshp3Lat;

	@Transient
	private Double trshp3Lng;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "updated_date")
	private Date updatedDate;
	
	@Column(name = "bdp_service_code")
	private String bdpServiceCode;
	
	@Column(name = "mode_of_transportation")
	private String	modeOfTransportation;
	
	@Transient
	private String portOfDepartureName;
	
	@Transient
	private String portOfArrivalName;
	
	@Transient
	private String originCountryName;
	
	@Transient
	private String destinationCountryName;
	
	@Transient
	private String originRegionName;
	
	@Transient
	private String destinationRegionName;
	
	@Transient
	private String groupName;
	
	@Column(name = "sale_term_code")
	private String saleTermCode;
}