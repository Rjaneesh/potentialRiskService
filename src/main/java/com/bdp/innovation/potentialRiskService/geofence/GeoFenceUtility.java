package com.bdp.innovation.potentialRiskService.geofence;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Service
public interface GeoFenceUtility {
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public class Coordinate {
		Double longitude;
		Double latitude;
	}

	boolean isInsidePolygon(List<Coordinate> Coordinate, Coordinate point);

	boolean isInsideCircle(Coordinate center, Double radius, Coordinate point);
}
