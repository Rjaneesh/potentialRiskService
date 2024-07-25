package com.bdp.innovation.potentialRiskService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bdp.innovation.potentialRiskService.model.ApplicationConfig;

public interface ApplicationConfigRepository extends JpaRepository<ApplicationConfig, Integer> {

	ApplicationConfig findByApplicationNameAndConfigTypeAndStatus(String applicationName, String configType,
			Boolean status);

	List<ApplicationConfig> findAllByApplicationNameAndStatus(String applicationName, Boolean status);

	List<ApplicationConfig> findByApplicationNameAndStatus(String applicationName, Boolean status);
}
