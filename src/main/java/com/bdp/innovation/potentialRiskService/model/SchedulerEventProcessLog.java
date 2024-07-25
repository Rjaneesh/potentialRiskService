package com.bdp.innovation.potentialRiskService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "scheduler_event_process_log")
public class SchedulerEventProcessLog {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_id")
    private Integer eventId;

    @Column(name = "shipment_count")
    private Integer shipmentCount;

    @Column(name = "scheduler_type")
    private Integer schedulerType;

    @Column(name = "record_date")
    private Date recordDate;
}
