package com.bdp.innovation.potentialRiskService.geofence;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class GeoFencingImpl implements GeoFenceUtility {
	@Override
	public boolean isInsidePolygon(List<Coordinate> coordinates, Coordinate point) {
		boolean isInsidePoygon = false;

		Coordinate polygon[] = new Coordinate[coordinates.size()];
		polygon = coordinates.toArray(polygon);

		int vertices = polygon.length - 1;
		if (isInside(polygon, vertices, point))
			isInsidePoygon = true;

		return isInsidePoygon;
	}

	@Override
	public boolean isInsideCircle(Coordinate center, Double radius, Coordinate point) {
		boolean isInside = false;

		if (getDistanceBetween(center, point) <= radius)
			isInside = true;

		return isInside;
	}

	/**
	 * Method to find the distance between 2 given points on earth. Uses Haversine
	 * formula, refer : https://en.wikipedia.org/wiki/Haversine_formula
	 * 
	 * @param lat1
	 * @param lat2
	 * @param lon1
	 * @param lon2
	 * @return
	 */
	private double getDistanceBetween(Coordinate point1, Coordinate point2) {
		double radius = 6371;
		Double lon1 = Math.toRadians(point1.getLongitude());
		Double lat1 = Math.toRadians(point1.getLatitude());
		Double lon2 = Math.toRadians(point2.getLongitude());
		Double lat2 = Math.toRadians(point2.getLatitude());
		// Haversine formula
		double dlon = lon2 - lon1;
		double dlat = lat2 - lat1;
		double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);

		return radius * (2 * Math.asin(Math.sqrt(a)));
	}

	// Define Infinite (Using INT_MAX caused overflow problems)
	static Double INF = 10000.0;

	/**
	 * Method to validate if the point is inside the polygon
	 * 
	 * @param polygon
	 * @param n
	 * @param point
	 * @return Returns true if the point lies inside the polygon[] with n vertices
	 */
	private boolean isInside(Coordinate[] polygon, int n, Coordinate point) {

		// There must be at least 3 vertices in polygon
		if (n < 3)
			return false;

		// Create a point for line segment from p to infinite
		Coordinate extreme = new Coordinate(INF, point.latitude);

		// Count intersections of the above line
		// with sides of polygon
		int count = 0, i = 0;
		do {
			int next = (i + 1) % n;

			// Check if the line segment from 'p' to 'extreme' intersects
			// with the line segment from 'polygon[i]' to 'polygon[next]'
			if (doIntersect(polygon[i], polygon[next], point, extreme)) {

				// If the point 'p' is collinear with line segment 'i-next',
				// then check if it lies on segment. If it lies, return true, otherwise false
				if (orientation(polygon[i], point, polygon[next]) == 0) {
					return onSegment(polygon[i], point, polygon[next]);
				}

				count++;
			}

			i = next;

		} while (i != 0);

		// Return true if count is odd, false otherwise
		return (count % 2 == 1);
	}

	/**
	 * The function that returns true if line segment 'p1q1' and 'p2q2' intersect.
	 * 
	 * @param p1
	 * @param q1
	 * @param p2
	 * @param q2
	 * @return
	 */
	private boolean doIntersect(Coordinate p1, Coordinate q1, Coordinate p2, Coordinate q2) {
		// Find the four orientations needed for
		// general and special cases
		int o1 = orientation(p1, q1, p2);
		int o2 = orientation(p1, q1, q2);
		int o3 = orientation(p2, q2, p1);
		int o4 = orientation(p2, q2, q1);

		// General case
		if (o1 != o2 && o3 != o4)
			return true;

		// Special Cases
		// p1, q1 and p2 are collinear and
		// p2 lies on segment p1q1
		if (o1 == 0 && onSegment(p1, p2, q1))
			return true;

		// p1, q1 and p2 are collinear and
		// q2 lies on segment p1q1
		if (o2 == 0 && onSegment(p1, q2, q1))
			return true;

		// p2, q2 and p1 are collinear and
		// p1 lies on segment p2q2
		if (o3 == 0 && onSegment(p2, p1, q2))
			return true;

		// p2, q2 and q1 are collinear and
		// q1 lies on segment p2q2
		if (o4 == 0 && onSegment(p2, q1, q2))
			return true;

		// Doesn't fall in any of the above cases
		return false;
	}

	/**
	 * To find orientation of ordered triplet (p, q, r). The function returns
	 * following values 0 --> p, q and r are collinear 1 --> Clockwise 2 -->
	 * Counterclockwise
	 * 
	 * @param p
	 * @param q
	 * @param r
	 * @return
	 */
	private int orientation(Coordinate p, Coordinate q, Coordinate r) {
		Double val = (q.latitude - p.latitude) * (r.longitude - q.longitude)
				- (q.longitude - p.longitude) * (r.latitude - q.latitude);

		if (val == 0)
			return 0; // collinear

		return (val > 0) ? 1 : 2; // clock or counterclock wise
	}

	/**
	 * Given three collinear points p, q, r, the function checks if point q lies on
	 * line segment 'pr'
	 * 
	 * @param p
	 * @param q
	 * @param r
	 * @return true or false accordingly.
	 */
	private boolean onSegment(Coordinate p, Coordinate q, Coordinate r) {

		if (q.longitude <= Math.max(p.longitude, r.longitude) && q.longitude >= Math.min(p.longitude, r.longitude)
				&& q.latitude <= Math.max(p.latitude, r.latitude) && q.latitude >= Math.min(p.latitude, r.latitude)) {
			return true;
		}

		return false;
	}
}
