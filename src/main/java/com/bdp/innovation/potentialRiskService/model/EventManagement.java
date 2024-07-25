package com.bdp.innovation.potentialRiskService.model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@EqualsAndHashCode(callSuper = false)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rm_event_management", indexes = { @Index(name = "idx_IdIndex", columnList = "id"),
		@Index(name = "idx_event_idIndex", columnList = "event_id"),
		@Index(name = "idx_eventTypeIndex", columnList = "id,event_type"),
		@Index(name = "idx_eventIdIndex", columnList = "id,event_id"),
		@Index(name = "idx_riskScoreIndex", columnList = "id,risk_score"),
		@Index(name = "idx_alertCategoryIndex", columnList = "id,alert_category"),
		@Index(name = "idx_subAlertCategoryIndex", columnList = "id,sub_alert_category"),
		@Index(name = "idx_favoritesIndex", columnList = "id,favorites"),
		@Index(name = "idx_approvalStatusIndex", columnList = "id,approved_status"),
		@Index(name = "idx_statusIndex", columnList = "status") })
public class EventManagement extends BaseModel {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "event_id")
	private String eventId;

	@Column(name = "event_title")
	private String eventTitle;

	@Column(name = "event_type")
	private String eventType;

	@Enumerated(EnumType.STRING)
	@Column(name = "risk_score", nullable = false)
	private RiskScore riskScore;

	@ManyToOne
	@JoinColumn(name = "alert_category", nullable = false)
	@Fetch(FetchMode.JOIN)
	private EventAlertCategory alertCategory;

	@ManyToOne
	@JoinColumn(name = "sub_alert_category", nullable = false)
	@Fetch(FetchMode.JOIN)
	private EventAlertSubCategory subAlertCategory;

	@Column(name = "country")
	private String country;

	@Column(name = "state")
	private String state;

	@Column(name = "city")
	private String city;

	@Column(name = "event_location")
	private String eventLocation;

	@Column(name = "country_region")
	private String countryRegion;

	@Column(name = "favorites")
	private Integer favorites;

	@Column(name = "status")
	private Integer status;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "start_date")
	private Date startDate;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "end_date")
	private Date endDate;

	@Column(name = "comments")
	private String comments;

	@Column(name = "source")
	private String source;

	@Column(name = "source_url")
	private String sourceUrl;

	@Builder.Default
	@Column(name = "event_radius")
	private Double eventRadius = Double.valueOf(0);

	@Column(name = "total_ship_associated")
	private Integer totalShipAssociated;
	
	@Column(name = "total_arr_ship_associated")
	private Integer totalArrShipAssociated;

	@Column(name = "total_plant_wh_associated")
	private Integer totalPlantsOrWHAssociated;

	@Column(name = "total_oceanport_associated")
	private Integer totalOceanPortAssociated;

	@Column(name = "total_airport_associated")
	private Integer totalAirportAssociated;
	
	@Column(name = "total_prealert_ship_associated")
	private Integer totalPreAlertShipAssociated;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "at_risk_shipment_event_map", joinColumns = {
			@JoinColumn(name = "event_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "shipment_id", referencedColumnName = "shipment_id") }, indexes = {
							@Index(name = "idx_event_shipment_event_id", columnList = "event_id"),
							@Index(name = "idx_event_shipmentr_shipment_id", columnList = "shipment_id") })
	@Fetch(FetchMode.SUBSELECT)
	private List<AtRiskShipments> shipmentDetails;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "pre_alert_shipment_event_map", joinColumns = {
			@JoinColumn(name = "event_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "shipment_id", referencedColumnName = "shipment_id") }, indexes = {
							@Index(name = "idx_event_shipment_event_id", columnList = "event_id"),
							@Index(name = "idx_event_shipmentr_shipment_id", columnList = "shipment_id") })
	@Fetch(FetchMode.SUBSELECT)
	private List<PreAlertShipments> preAlertShipments;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "arrived_shipment_event_map", joinColumns = {
			@JoinColumn(name = "event_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "shipment_id", referencedColumnName = "shipment_id") }, indexes = {
							@Index(name = "idx_event_arr_shipment_event_id", columnList = "event_id"),
							@Index(name = "idx_event_arr_shipmentr_shipment_id", columnList = "shipment_id") })
	@Fetch(FetchMode.SUBSELECT)
	private List<ArrivedShipments> arrShipmentDetails;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "rm_event_asset_map", joinColumns = {
			@JoinColumn(name = "event_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "asset_id", referencedColumnName = "id") }, indexes = {
							@Index(name = "idx_event_asset_event_id", columnList = "event_id"),
							@Index(name = "idx_event_asset_shipment_id", columnList = "asset_id") })
	@Fetch(FetchMode.SUBSELECT)
	private List<AssetManagement> assetManagements;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "rm_event_globalocean_map", joinColumns = {
			@JoinColumn(name = "event_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "globaloceanport_id", referencedColumnName = "id") }, indexes = {
							@Index(name = "idx_event_globalocean_event_id", columnList = "event_id"),
							@Index(name = "idx_event_globalocean_globalocean_id", columnList = "globaloceanport_id") })
	@Fetch(FetchMode.SUBSELECT)
	private List<GlobalOceanportMaster> globalOceanports;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "rm_event_globalair_map", joinColumns = {
			@JoinColumn(name = "event_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "globalairport_id", referencedColumnName = "id") }, indexes = {
							@Index(name = "idx_event_globalair_event_id", columnList = "event_id"),
							@Index(name = "idx_event_globalair_globalairport_id", columnList = "globalairport_id") })
	@Fetch(FetchMode.SUBSELECT)
	private List<GlobalAirportMaster> globalAirports;

	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL})
	@JoinTable(name = "rm_event_polygon_map", joinColumns = {
			@JoinColumn(name = "event_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "polygon_id", referencedColumnName = "id") }, indexes = {
							@Index(name = "idx_event_polygon_event_id", columnList = "event_id"),
							@Index(name = "idx_event_polygon_polygon_id", columnList = "polygon_id") })
	@Fetch(FetchMode.SUBSELECT)
	private List<EventPolygon> eventPolygon;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "rm_event_vessel_map",
			joinColumns = {@JoinColumn(name = "event_id", referencedColumnName = "id") },
			inverseJoinColumns = {@JoinColumn(name = "vessel_id", referencedColumnName = "id") },
			indexes = {@Index(name = "idx_event_id", columnList = "event_id"),
						@Index(name = "idx_vessel_id", columnList = "vessel_id")})
	@Fetch(FetchMode.SUBSELECT)
	private List<VesselDetails> vesselDetails;

	@Column(name = "is_deleted")
	private Boolean isDeleted;

	@Column(name = "approved_status")
	private Integer approvedStatus;

	@Column(name = "approved_by")
	private String approvedBy;

	@Column(name = "event_description")
	private String eventDescription;

	@Column(name = "latitude")
	private Double latitude;

	@Column(name = "longitude")
	private Double longitude;

	@Column(name = "address")
	private String address;

	@Column(name = "postal_code")
	private String postalCode;

	@Column(name = "approved_count")
	private Integer approvedCount;

	@Column(name = "reason_for_rejection")
	private String reasonForRejection;

	@Column(name = "reason_for_change")
	private String reasonForChange;

	@Column(name = "fence_type")
	private String fenceType;

	@Column(name = "impact_area")
	private Double impactArea;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "last_updated_date")
	private Date lastUpdatedDate;

	@Transient
	private Date preAlertLastUpdatedDate;

	@Transient
	private Date arrivedLastUpdatedDate;

	@Column(name = "is_viewed")
	private Boolean isViewed;

	@Column(name = "is_pin_on_map")
	private Boolean isPinOnMap;
	
	@Column(name = "is_global_event")
	private Integer isGlobalEvent;
	
	@Column(name = "event_mode")
	private Integer eventMode;

	@Transient
	private Integer totalContainer;

	@Transient
	private Integer totalAssets;

	@Transient
	private Number isFavorite;

	@Transient
	private Map<Integer, List<EventPolygon>> polygonList;

	@Transient
	private Integer alertId;

	@Transient
	private String alertName;

	@Transient
	private Boolean isPast;

	@Transient
	private Integer potentialRiskShipmentCount;

	@Transient
	private List<String> potentialShipmentDetails;
	
//	@Transient
//	private List<VesselDetailsForEvent> totalVesselAssociated;
	

	public EventManagement(Integer id) {
		this.id = id;
	}

	public EventManagement(Integer id, String eventId, Integer status, String eventLocation) {
		super();
		this.id = id;
		this.eventId = eventId;
		this.status = status;
		this.eventLocation = eventLocation;
	}

	public EventManagement(Integer id, String eventId, String eventTitle, String eventType, RiskScore riskScore,
			EventAlertCategory alertCategory, EventAlertSubCategory subAlertCategory, String country, String state,
			String city, String eventLocation, Integer favorites, Integer status, Date startDate, Date endDate,
			String comments, String source, String sourceUrl, Double eventRadius, Integer totalShipAssociated,
			Integer totalPlantsOrWHAssociated, Integer totalOceanPortAssociated, Integer totalAirportAssociated,
			Boolean isDeleted, Integer approvedStatus, String approvedBy, String eventDescription, Double latitude,
			Double longitude, String address, String postalCode, Integer approvedCount, String reasonForRejection) {
		super();
		this.id = id;
		this.eventId = eventId;
		this.eventTitle = eventTitle;
		this.eventType = eventType;
		this.riskScore = riskScore;
		this.alertCategory = alertCategory;
		this.subAlertCategory = subAlertCategory;
		this.country = country;
		this.state = state;
		this.city = city;
		this.eventLocation = eventLocation;
		this.favorites = favorites;
		this.status = status;
		this.startDate = startDate;
		this.endDate = endDate;
		this.comments = comments;
		this.source = source;
		this.sourceUrl = sourceUrl;
		this.eventRadius = eventRadius;
		this.totalShipAssociated = totalShipAssociated;
		this.totalPlantsOrWHAssociated = totalPlantsOrWHAssociated;
		this.totalOceanPortAssociated = totalOceanPortAssociated;
		this.totalAirportAssociated = totalAirportAssociated;
		this.isDeleted = isDeleted;
		this.approvedStatus = approvedStatus;
		this.approvedBy = approvedBy;
		this.eventDescription = eventDescription;
		this.latitude = latitude;
		this.longitude = longitude;
		this.address = address;
		this.postalCode = postalCode;
		this.approvedCount = approvedCount;
		this.reasonForRejection = reasonForRejection;
	}

//	public EventManagement(Integer id, Date createdDate, String eventId, RiskScore riskScore, String eventTitle,
//			EventAlertCategory alertCategory, EventAlertSubCategory subAlertCategory, String eventDescription,
//			String eventLocation, String city, String country, Integer totalShipAssociated,
//			List<AssetManagement> assetManagements, Date lastUpdatedDate) {
//		super();
//		this.id = id;
//		this.setCreatedDate(createdDate);
//		this.eventId = eventId;
//		this.riskScore = riskScore;
//		this.eventTitle = eventTitle;
//		this.alertCategory = alertCategory;
//		this.subAlertCategory = subAlertCategory;
//		this.eventDescription = eventDescription;
//		this.eventLocation = eventLocation;
//		this.city = city;
//		this.country = country;
//		this.totalShipAssociated = totalShipAssociated;
//		this.assetManagements = assetManagements;
//		this.lastUpdatedDate = lastUpdatedDate;
//
//	}

	public EventManagement(Integer id, String eventId, String eventTitle, String eventType, RiskScore riskScore,
			EventAlertCategory alertCategory, EventAlertSubCategory subAlertCategory, String country, String state,
			String city, String eventLocation, Integer favorites, Integer status, Date startDate, Date endDate,
			String comments, String source, String sourceUrl, Double eventRadius, Integer totalShipAssociated,
			Integer totalPlantsOrWHAssociated, Integer totalOceanPortAssociated, Integer totalAirportAssociated,
			Boolean isDeleted, Integer approvedStatus, String approvedBy, String eventDescription, Double latitude,
			Double longitude, String address, String postalCode, Integer approvedCount, String reasonForRejection,
			List<AtRiskShipments> shipmentDetails, List<ArrivedShipments> arrShipmentDetails) {
		super();
		this.id = id;
		this.eventId = eventId;
		this.eventTitle = eventTitle;
		this.eventType = eventType;
		this.riskScore = riskScore;
		this.alertCategory = alertCategory;
		this.subAlertCategory = subAlertCategory;
		this.country = country;
		this.state = state;
		this.city = city;
		this.eventLocation = eventLocation;
		this.favorites = favorites;
		this.status = status;
		this.startDate = startDate;
		this.endDate = endDate;
		this.comments = comments;
		this.source = source;
		this.sourceUrl = sourceUrl;
		this.eventRadius = eventRadius;
		this.totalShipAssociated = totalShipAssociated;
		this.totalPlantsOrWHAssociated = totalPlantsOrWHAssociated;
		this.totalOceanPortAssociated = totalOceanPortAssociated;
		this.totalAirportAssociated = totalAirportAssociated;
		this.isDeleted = isDeleted;
		this.approvedStatus = approvedStatus;
		this.approvedBy = approvedBy;
		this.eventDescription = eventDescription;
		this.latitude = latitude;
		this.longitude = longitude;
		this.address = address;
		this.postalCode = postalCode;
		this.approvedCount = approvedCount;
		this.reasonForRejection = reasonForRejection;
		this.shipmentDetails = shipmentDetails;
		this.arrShipmentDetails = arrShipmentDetails;
	}
}
