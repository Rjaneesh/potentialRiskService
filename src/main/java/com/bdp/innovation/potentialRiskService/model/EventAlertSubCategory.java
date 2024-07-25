package com.bdp.innovation.potentialRiskService.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "rm_alert_sub_category", indexes = {
		@Index(name = "idx_alert__id", columnList = "id"),
		@Index(name = "idx_alert_category_id", columnList = "alert_category_id") })
public class EventAlertSubCategory {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "alert_category_id")
	@Fetch(FetchMode.JOIN)
	private EventAlertCategory eventAlertCategory;

	@Column(name = "alert_sub_category_name")
	private String alertSubCategoryName;
	
}
