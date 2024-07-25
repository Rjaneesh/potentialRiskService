package com.bdp.innovation.potentialRiskService.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "rm_global_air_port")
public class GlobalAirportMaster {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "airport_name")
	private String airportName;

	@Column(name = "airport_city")
	private String airportCity;

	@Column(name = "airport_country")
	private String airportCountry;

	@Column(name = "icao_code")
	private String icaoCode;

	@Column(name = "iata_code")
	private String iataCode;

	@Column(name = "latitude")
	private Double latitude;

	@Column(name = "longitude")
	private Double longitude;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "created_date")
	private Date createdDate;

	@Column(name = "updated_date")
	private Date updatedDate;

	@Builder.Default
	@Column(name = "versionno")
	private Integer versionNo = Integer.valueOf(0);

}