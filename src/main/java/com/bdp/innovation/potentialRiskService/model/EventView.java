package com.bdp.innovation.potentialRiskService.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rm_event_management", indexes = { @Index(name = "idx_IdIndex", columnList = "id"),
		@Index(name = "idx_eventIdIndex", columnList = "event_id"),
		@Index(name = "idx_approvalStatusIndex", columnList = "approved_status"),
		@Index(name = "idx_statusIndex", columnList = "status") })
public class EventView {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "event_id")
	private String eventId;

	@Column(name = "status")
	private Integer status;

	@Column(name = "approved_status")
	private Integer approvedStatus;

	@Column(name = "is_deleted")
	private Boolean isDeleted;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "start_date")
	private Date startDate;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "end_date")
	private Date endDate;
	
	@Column(name = "is_global_event")
	private Integer isGlobalEvent;

}
