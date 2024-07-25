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
@Table(name = "rm_global_ocean_port")
public class GlobalOceanportMaster {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "port_name")
	private String portName;

	@Column(name = "unl_code")
	private String unlCode;

	@Column(name = "unl_country")
	private String unlCountry;

	@Column(name = "unl_city")
	private String unlCity;

	@Column(name = "country_name")
	private String countryName;

	@Column(name = "country_unloc_code")
	private String countryUnlocCode;

	@Column(name = "port_address")
	private String portAddress;

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