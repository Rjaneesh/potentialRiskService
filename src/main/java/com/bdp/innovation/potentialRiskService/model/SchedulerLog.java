package com.bdp.innovation.potentialRiskService.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "rm_scheduler_logs")
public class SchedulerLog {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "scheduler_name")
	private String schedulerName;

	@Column(name = "duration")
	private Long duration;

	@Column(name = "start_time")
	private Date startTime;

	@Column(name = "end_time")
	private Date endTime;

	@Column(name = "status")
	private Integer status;
}
