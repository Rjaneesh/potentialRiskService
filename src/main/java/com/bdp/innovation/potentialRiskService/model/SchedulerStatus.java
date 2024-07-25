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
@Table(name = "rm_scheduler_status")
public class SchedulerStatus {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "scheduler_Name")
	private String schedulerName;
	
	@Column(name = "cron_Expression")
	private String cronExpression;
	
	@Column(name = "duretion")
	private Long duretion;
	
	@Column(name = "last_Scheduled_Date")
	private Date lastScheduledDate;
	
	@Column(name = "next_Scheduled_Date")
	private Date nextScheduledDate;
	
	@Column(name = "scheduler_Status")
	private Boolean schedulerStatus;
	
	@Column(name = "remarks")
	private String remarks;
}