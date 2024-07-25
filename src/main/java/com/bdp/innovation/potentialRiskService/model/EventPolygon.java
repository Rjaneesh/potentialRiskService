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
@Table(name = "rm_event_polygon", indexes = { 
		@Index(name = "idx_id_event_polygon", columnList = "id"),
		@Index(name = "idx_groupId_event_polygon", columnList = "id,group_Id"),
		@Index(name = "idx_groupId_sequence_polygon", columnList = "id,group_Id,sequence")})
public class EventPolygon {

	@Id
	@Column(name = "id") 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "latitude")
	private Double latitude;

	@Column(name = "longitude")
	private Double longitude;

	@Column(name = "sequence")
	private Integer sequence;

	@Builder.Default
	@Column(name = "group_Id")
	private Integer groupId = Integer.valueOf(1);

}
