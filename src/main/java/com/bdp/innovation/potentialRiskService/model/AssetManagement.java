package com.bdp.innovation.potentialRiskService.model;

import java.util.List;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "rm_asset_management",
indexes = {
		@Index(name = "idx_id", columnList = "id"),
		@Index(name = "idx_asset_type", columnList = "asset_type"),
		@Index(name = "idx_company_id", columnList = "company_id"),
//		@Index(name = "idx_sbu_id", columnList = "sbu_id")
	})
public class AssetManagement extends BaseModel{
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "asset_name")
	private String assetName;

	@Enumerated(EnumType.STRING)
	@Column(name = "asset_type")
	private AssetType assetType;	
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "country")
	private String country;
	
	@Column(name = "region")
	private String region;
	
	@Column(name = "state")
	private String state;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "postal_code")
	private String postalCode;

	@Column(name = "latitude")
	private Double latitude;
	
	@Column(name = "longitude")
	private Double longitude;
	
	@Column(name = "is_deleted")
	private Boolean isDeleted;
	
	@Column(name = "is_favorite")
	private Integer isFavorite;
	
	@Column(name = "is_pin_on_map")
	private Boolean isPinOnMap;
	
//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "sbu_id")
//	private SBUMaster sbuMaster;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "company_id")
	private CompanyMaster companyMaster;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "rm_asset_sbu_map", joinColumns = {
			@JoinColumn(name = "asset_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "sbu_id", referencedColumnName = "id") },
					indexes = {
					        @Index(name = "idx_sbu_id", columnList = "sbu_id"),
					        @Index(name = "idx_asset_id", columnList = "asset_id")
					    })
	@Fetch(FetchMode.SUBSELECT)
	private List<SBUMaster> mappedSbu;
	
	@Transient
	private Long totalCount;
	
	public AssetManagement(Long totalCount, CompanyMaster companyMaster,  AssetType assetType) {
		this.totalCount = totalCount;
		this.companyMaster = companyMaster;
		this.assetType = assetType;
	}
}
