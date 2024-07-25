package com.bdp.innovation.potentialRiskService.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BDPRefDto {
	private String bdpReferenceNumber;
	private Date logDate;
}
