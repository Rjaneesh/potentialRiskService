package com.bdp.innovation.potentialRiskService.model;

import java.util.Date;

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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "master_sbu",
indexes = {
		@Index(name = "idx_id", columnList = "id"),
		@Index(name = "idx_sbu", columnList = "sbu"),
	})
public class SBUMaster {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "sbu")
	private String sbu;
	
	@Column(name = "sbu_description")
	private String sbuDescription;
	
	@Column(name = "created_on")
	private Date createdOn;
	
	@Column(name = "modified_on")
	private Date modifiedOn;
	
}
