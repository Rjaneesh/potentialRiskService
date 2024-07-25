package com.bdp.innovation.potentialRiskService.model;

import java.util.Arrays;
import java.util.List;

public enum RiskScore {

	HIGH("High"),
	MEDIUM("Medium"),
	LOW("Low"),
	NO_RISK("No_Risk");

	private String value;

	public String getValue() {
		return value;
	}

	RiskScore(String value) {
		this.value = value;
	}

	public static List<String> getAllRiskScore() {
		return Arrays.asList(HIGH.name(), MEDIUM.name(), LOW.name(), NO_RISK.name());
	}

	public static RiskScore getRiskScore(String risk) {
		return RiskScore.valueOf(risk.toUpperCase());
	}

}
