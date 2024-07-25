package com.bdp.innovation.potentialRiskService.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shipment_raw_data")
public class ShipmentRawData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "batch_id")
    private Integer batchId;

    @Column(name = "shipment_data")
    private String shipmentData;
}
