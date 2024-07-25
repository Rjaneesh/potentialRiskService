package com.bdp.innovation.potentialRiskService.common;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class Constants {
	
	public static final Integer ATRISK_SHIPMENT = 1;
	public static final Integer PREALERT_SHIPMENT = 2;
	public static final Integer ARRIVED_SHIPMENT = 3;

	// Schedulers
	public static final String NWS_SCH = "NWSSCHEDULER";
	public static final String RSS_FEEDS_SCH = "RSSFEEDSSCHEDULER";
	public static final String VESSEL_INFO_SCH = "VESSELINFORMATION";
	public static final String SWIC_EVENT_SCH = "SWICEVENTSCHEDULER";
	public static final String CLEAR_ALL_CACHE_SCH = "CLEAR_ALL_CACHES";
	public static final String CLEAR_SPECIFIC_CACHE_SCH = "CLEAR_SPECIFIC_CACHES";
	public static final String PREALERT_SHIPMENT_SCH = "PREALERTSHIPMENT";
	public static final String ARRIVED_SHIPMENT_SCH = "ARRIVEDSHIPMENT";
	public static final String EVENT_HISTORY_SCH = "EVENTHISTORYSCHEDULAR";
	public static final String DB_CONNECTION_SCH = "DATABASECONNECTIONSCH";
	public static final String FLEETMON_EVENT_SCH = "FLEETMONEVENTSCHEDULER";
	public static final String PORT_CONGESTION_SCH = "PORTCONGESTIONSCHEDULER";
	public static final String SHIPMENTS_HISTORY_SCH = "SHIPMENTSHISTORYSCH";
	public static final String UPDATE_INACTIVE_EVENT_SCH = "TRIGGERUPDATEINACTIVEEVENT";
	public static final String SEND_MAIL_SUBSCRIBER_SCH = "SENDEMAILFORSUBSCRIBEDUSERS";

	// KPI IDs
	public static final Integer HIGH_KPI = 1;
	public static final Integer LOW_KPI = 2;
	public static final Integer MEDIUM_KPI = 3;
	public static final Integer PLANT_KPI = 4;
	public static final Integer WAREHOUSE_KPI = 5;
	public static final Integer SUPPLIER_KPI = 6;
	public static final Integer AT_RISK_SHIPMENT_KPI = 7;
	public static final Integer GLOBALOCEANPORT_KPI = 8;
	public static final Integer GLOBALAIRPORT_KPI = 9;
	public static final Integer PREALERT_SHIPMENT_KPI = 11;
	public static final Integer ARRIVED_SHIPMENT_KPI = 12;
	public static final Integer POI_KPI = 13;

	// Date Formatter
	public static final String MM_DD_YYYY = "MM/dd/yyyy";
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String MM_dd_yy = "MM/dd/yy";
	public static final String DD_MM_YYYY = "dd/MM/yyyy";
	public static final String DD_MM_YY = "dd-MM-yyyy";

	// For status code and message
	public static final int SUCCESS_CODE = 200;
	public static final int ERROR_CODE = 400;
	public static final int FORBIDDEN = 403;
	public static final String SUCCESS_MESSAGE = "success";
	public static final String ERROR_MESSAGE = "fail";
	public static final String FORBIDDEN_MESSAGE = "forbidden";

	public static final Integer APPROVAL_CREATED = Integer.valueOf(-1);
	public static final Integer APPROVAL_REJECTED = Integer.valueOf(0);
	public static final Integer APPROVAL_APPROVED = Integer.valueOf(1);
	public static final Integer APPROVAL_PENDING = Integer.valueOf(2);
	public static final Integer APPROVAL_RESEND = Integer.valueOf(3);
	public static final Integer APPROVAL_RSS = Integer.valueOf(4);
	public static final Integer APPROVAL_NWS = Integer.valueOf(4);
	public static final Integer APPROVAL_PC = Integer.valueOf(5);

	public static final String SHIPMENTS = "Shipment";
	public static final String ASSETS = "Asset";
	public static final String STATIONARY = "STATIONARY";
	public static final String NON_STATIONARY = "NON_STATIONARY";

	public static final String KPI_ASSET_GLOBAL_AIRPORT = "GlobalAirports";
	public static final String KPI_ASSET_GLOBAL_OCEANPORT = "GlobalOceanport";
	public static final String KPI_ASSET_WAREHOUSE = "Warehouse";
	public static final String KPI_ASSET_PLANT = "Plant";
	public static final String KPI_ASSET_GLOBAL_SUPPLIER = "Supplier";

	public static final String All_APPROVAL_EVENTS = "ALL";
	public static final String PENDING_APPROVAL_EVENTS = "PENDING_APPROVAL";
	public static final String APPROVED_APPROVAL_EVENTS = "APPROVED_APPROVAL";
	public static final String REJECTED_APPROVAL_EVENTS = "REJECTED_APPROVAL";
	public static final String RSS_FEED_HISTORY_EVENTS = "RSS_FEED_HISTORY";

	public static final String APPROVED_EVENTS = "APPROVED";
	public static final String PENDING_EVENTS = "PENDING";
	public static final String REJECTED_EVENTS = "REJECTED";

	// KPI mapping names
	public static final String KPI_HIGH_RISK_ENENT = "High";
	public static final String KPI_MEDIUM_RISK_ENENT = "Medium";
	public static final String KPI_LOW_RISK_ENENT = "Low";
	public static final String KPI_PLANT = "Plant";
	public static final String KPI_WAREHOUSE = "Warehouse";
	public static final String KPI_SUPPLIER = "Supplier";
	public static final String KPI_GLOBAL_OCEANPORT = "GlobalOceanport";
	public static final String KPI_GLOBAL_AIRPORT = "GlobalAirports";
	public static final String KPI_SHIPMENT = "Shipment";
	public static final String KPI_ARRIVED_SHIPMENT = "ArrivedShipment";
	public static final String KPI_POTENTIAL_RISK_SHIPMENT = "PotentialRiskShipment";
	public static final String KPI_POI = "Poi";

	public static final String SUPPLIER_REPORTS = "SUPPLIER_REPORTS";
	public static final String PLANTS_WH_REPORTS = "PLANTS_WAREHOUSE_REPORTS";
	public static final String SHIPMENT_REPORT = "SHIPMENT_REPORTS";
	public static final String POI_REPORTS = "POI_REPORTS";
	public static final String POTENTIAL_RISK_SHIPMENT_REPORT = "POTENTIAL_RISK_SHIPMENT_REPORT";
	public static final Object ARRIVED_SHIPMENT_REPORT = "ARRIVED_SHIPMENT_REPORT";

	public static final String ERROR_RESPONSE = "Something went wrong.";

	public static final String FENCE_POLYGON = "POLYGON";
	public static final String FENCE_CIRCLE = "CIRCLE";

	public static final String EDIT_EVENT = "edit";
	public static final String ADD_EVENT = "add";

	public static final String EMAIL_BODY_MESSAGE_1 = "Here's your daily update on potential risk events that may impact your operations.";
	public static final String EMAIL_SUBJECT = "Risk Monitor Alert Summary As Of ";
	public static final String EMAIL_ATTACHMENT_NAME = "Risk_Monitor_Daily_Update_report_";

	public static final String NWS_NAME = "NWS";
	public static final String NWS_URL = "https://www.weather.gov";
	public static final String NWS_COUNTRY = "United States";
	public static final String NWS_REGION = "NORTH AMERICA";
	public static final String NWS_API_URL = "https://api.weather.gov/alerts/active";

	public static final String SWIC_NAME = "SWIC";
	public static final String FLEETMON_NAME = "FleetMon";
	public static final String FLEETMON_URL = "https://www.fleetmon.com";

	public static final String PSABDP_NAME = "PSABDP";
	public static final String PSABDP_URL = "https://navigator.bdpsmart.com/#/login";
	public static final String NA = "N/A";
	

	public static final Integer ON_TIME = Integer.valueOf(1);
	public static final Integer EARLY = Integer.valueOf(0);
	public static final Integer LATE = Integer.valueOf(-1);
	
	// Reporting Constants
	public static final String GROUP_CODES = "groupCodes";

	// USER ROLES
	public static final String ROLE_VIEWER = "hasRole('ROLE_VIEWER')";
	public static final String ROLE_APPROVER = "hasRole('ROLE_APPROVER')";
	public static final String ROLE_EVENT_MANAGER = "hasRole('ROLE_EVENT_MANAGER')";
	public static final String ROLE_ASSET_MANAGER = "hasRole('ROLE_ASSET_MANAGER')";
	public static final String ROLE_COMMODITY_MANAGER = "hasRole('ROLE_COMMODITY_MANAGER')";
	public static final String ROLE_SUPPLIER_DASHBOARD = "hasRole('ROLE_SUPPLIER_DASHBOARD')";
	public static final String ROLE_CUSTOMIZED_DASHBOARD = "hasRole('ROLE_CUSTOMIZED_DASHBOARD')";
	public static final String ROLE_QUERY_MANAGER = "hasRole('ROLE_QUERY_MANAGER')";
	public static final String ROLE_ASSET_MANAGER_AND_ROLE_VIEWER = "hasAnyRole('ROLE_ASSET_MANAGER', 'ROLE_VIEWER')";
	public static final String ROLE_ASSET_MANAGER_AND_EVENT_MANAGER_AND_ROLE_APPROVER = "hasAnyRole('ROLE_ASSET_MANAGER','ROLE_EVENT_MANAGER','ROLE_APPROVER')";
	public static final String ALL_ROLES ="hasAnyRole('ROLE_ASSET_MANAGER', 'ROLE_EVENT_MANAGER','ROLE_APPROVER','ROLE_VIEWER')";
	public static final String ALL_ROLES_AND_QUERY_MANAGER ="hasAnyRole('ROLE_ASSET_MANAGER', 'ROLE_EVENT_MANAGER','ROLE_APPROVER','ROLE_VIEWER','ROLE_QUERY_MANAGER')";
	public static final String ALL_ROLES_AND_COMMODITY_MANAGER ="hasAnyRole('ROLE_ASSET_MANAGER', 'ROLE_EVENT_MANAGER','ROLE_APPROVER','ROLE_VIEWER','ROLE_COMMODITY_MANAGER')";

	public interface EventType {
		public String EARTHQUAKES = "EQ";
		public String TROPICAL_CYCLONES = "TC";
		public String FLOODS = "FL";
		public String VOLCANOES = "VO";
		public String DROUGHTS = "DR";
		public String FOREST_FIRES = "WF";
	}

	private Constants() { }
	
	public static final String TRSH_1 ="TRS-1";
	public static final String TRSH_2 ="TRS-2";
	public static final String TRSH_3 ="TRS-3";
	public static final String ORIGIN ="ORIGIN";
	public static final String DESTINATION ="DESTINATION";
	public static final String SUPPLY_CHAIN = "Supply Chain";
	public static final String MARITIME_TRANSPORTATION = "Maritime Transportation";
	public static final String VESSEL_INCIDENTS = "Vessel Incidents";

	public static final Double DEFAULT_IMPACTED_CIRCLR_AREA = Double.valueOf(5);
	public static final String WK_WEBSTER = "WkWebster";

	public static final Integer ACTIVE = Integer.valueOf(1);
	public static final Integer INACTIVE = Integer.valueOf(0);
	
	public static final Set<String> WORDS_TO_AVOID = new HashSet<>(Arrays.asList("I", "YOU", "HE", "SHE", "IT", "WE", "THEY", "A", "AN", "THE", "AND",
			"BUT", "OR", "SO", "IN", "ON", "AT", "TO", "FROM", "BY", "WITH", "ABOUT", "FOR", "IS", "AM", "ARE", "WAS", "WERE", "BE", "BEEN", "HAVE",
			"HAS", "HAD", "DO", "DOES", "DID", "WILL", "SHALL", "SHOULD", "WOULD", "CAN", "COULD", "MAY", "MIGHT", "MUST", "GO", "COME", "SEE", "LOOK",
			"MAKE", "TAKE", "GET", "GIVE", "KNOW", "THINK", "WANT", "NEED", "FEEL", "SAY", "TELL", "ASK", "WORK", "TRY", "USE", "FIND", "CALL", "LIKE",
			"HELP", "START", "STOP", "GOOD", "BAD", "NEW", "OLD", "BIG", "SMALL", "HIGH", "LOW", "RIGHT", "WRONG", "HAPPY", "SAD", "NOW", "THEN", "HERE",
			"THERE", "ALWAYS", "NEVER", "OFTEN", "SOMETIMES", "VERY", "REALLY", "JUST", "WHERE", "WHEN", "HOW", "WHY", "THAT", "OF", "GAVE", "GIVEN",
			"THEIR", "NOT", "MY", "MINE", "SOME", "ITS", "HIM", "HER", "DONE", "DUE", "GOING"));
	
	public static final List<String> PERMITED_FILE_TYPES_PDF = Arrays.asList(".pdf", "text/pdf", "application/pdf", "application/x-pdf");

	// Commodity Manager
	public static final String NO_DATA_FOUND = "No Data Found";
	public static final String INVALID_REQUEST = "Invalid Request";
	public static final String ADD_COMMODITY_RESPONSE = "Commodities Added Successfully";
	public static final String REMOVE_COMMODITY_RESPONSE = "Commodities Removed Successfully";
	public static final String UPDATE_COMMODITY_RESPONSE = "Commodities Updated Successfully";
	
	// Asset Type
	public static final String ASSET_TYPE_POI = "POI";
	public static final String ASSET_TYPE_PLANT = "PLANT";
	public static final String ASSET_TYPE_SUPPLIER = "SUPPLIER";
	public static final String ASSET_TYPE_WAREHOUSE = "WAREHOUSE";
	public static final String OTHERS = "Others";
	public static final String COMMODITY_SPECIFIC = "Commodity Specific";
	public static final String COMMODITY_MANAGER = "ROLE_COMMODITY_MANAGER";
}