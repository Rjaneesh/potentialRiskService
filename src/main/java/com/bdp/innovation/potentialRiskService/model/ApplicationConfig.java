package com.bdp.innovation.potentialRiskService.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="rm_application_configuration")
public class ApplicationConfig {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "application_name")
	private String applicationName;
	
	@Column(name = "config_type")
	private String configType;
	
	@Column(name = "config_value")
	private String configValue;
	
	@Column(name = "status")
	private Boolean status;
	
	@Column(name = "is_running")
	private Boolean isRunning;
}
