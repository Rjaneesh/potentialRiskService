package com.bdp.innovation.potentialRiskService.dto;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreAlertShipmentsDto {
	private Integer shipmentId;
	private String bdpReferenceNumber;
	private Integer groupCode;
	private String blNumber;
	private String bookingNumber;
	private Integer geId;
	private String carrierName;
	private String carrierScacCode;
	private String orderNumber;
	private String purchaseOrderNumber;
	private String containerNumbers;
	private Double latitude;
	private Double longitude;
	private Double latitudeArr;
	private Double longitudeArr;
	private Date etd;
	private Date atd;
	private Date eta;
	private String vesselName;
	private String imoNumber;
	private String voyage;
	private String originRegion;
	private String destinationRegion;
	private Integer sbuId;
	private String sbuDescription;
	private Date lastScheduledOn;
	private Double deviations;
	private Double bookingDeviations;
	private Integer statusCode;
	private String portUnlocCode;
	private String trshp1ArrvlLoc;
	private Date trshp1ArrvlEstDt;
	private Date trshp1DepEstDt;
	private String trshp2ArrvlLoc;
	private Date trshp2ArrvlEstDt;
	private Date trshp2DepEstDt;
	private String trshp3ArrvlLoc;
	private Date trshp3ArrvlEstDt;
	private Date trshp3DepEstDt;
	private Date predictiveEta;	
	private Integer isReadyToServe;
	private String bdpServiceCode;
	private String saleTermCode;	
}
