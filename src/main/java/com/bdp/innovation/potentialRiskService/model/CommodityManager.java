package com.bdp.innovation.potentialRiskService.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "commodity_manager",
indexes = { 
		@Index(name = "idx_id", columnList = "id"),
		@Index(name = "idx_hs_code", columnList = "hs_code"),
		@Index(name = "idx_product_code", columnList = "product_code"),
})
public class CommodityManager {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "hs_code")
	private String hsCode;
	
	@Column(name = "product_code")
	private String productCode;
	
	@Column(name = "product_group")
	private String productGroup;
	
}
