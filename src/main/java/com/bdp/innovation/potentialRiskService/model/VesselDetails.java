package com.bdp.innovation.potentialRiskService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "vessel_details")
public class VesselDetails {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "vessel_imo")
    private Integer vesselIMO;

    @Column(name = "vessel_name")
    private String vesselName;

    @Column(name = "voyage")
    private String voyage;

    @Column(name = "is_active")
    private Integer isActive;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "updated_on")
    private Date updatedOn;

    public VesselDetails(Integer vesselIMO) {
        this.vesselIMO = vesselIMO;
    }
}
