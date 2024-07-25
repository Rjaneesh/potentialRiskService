package com.bdp.innovation.potentialRiskService.model;

import java.util.Arrays;
import java.util.List;

import org.springframework.util.ObjectUtils;

import com.bdp.innovation.potentialRiskService.exceptionHandler.BaseException;

public enum AssetType {

	WAREHOUSE("Warehouse"), 
	PLANT("Plant"), 
	SUPPLIER("Supplier"),
	POI("Poi");
	
	private String value;

	public String getValue() {
		return value;
	}

	AssetType(String value) {
		this.value = value;
	}

	public static List<String> getAllAssetType() {
		return Arrays.asList(WAREHOUSE.getValue(), PLANT.getValue(), SUPPLIER.getValue(), POI.getValue());
	}
	
	public static List<String> getAllAssetTypeName() {
		return Arrays.asList(WAREHOUSE.name(), PLANT.name(), SUPPLIER.name(), POI.name());
	}
	
	public static AssetType getAssetType(String type) {
		try {
			if (ObjectUtils.isEmpty(type)) {
				throw new BaseException("Asset Type Should not be null");
			}
//			return AssetType.valueOf(type.toUpperCase());
			return AssetType.valueOf(type.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new BaseException(type+" is Not a Valid Asset Type");
		}
		
	}
}
