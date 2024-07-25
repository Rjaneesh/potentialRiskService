package com.bdp.innovation.potentialRiskService.model;

import java.util.List;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "company",
indexes = { 
		@Index(name = "idx_id_event_polygon", columnList = "id"),
		@Index(name = "idx_group_code", columnList = "group_code"),
		})
public class CompanyMaster {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "group_code")
	private Integer groupCode;
	
	@Column(name = "group_name")
	private String groupName;

	@Column(name = "is_rm_company")
	private Boolean isRmCompany;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "rm_company_commodity_map", joinColumns = {
			@JoinColumn(name = "company_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "commodity_id", referencedColumnName = "id") },
					indexes = {
					        @Index(name = "idx_company_id", columnList = "company_id"),
					        @Index(name = "idx_commodity_id", columnList = "commodity_id")
					    })
	@Fetch(FetchMode.SUBSELECT)
	private List<CommodityManager> associatedCommodities;
}
