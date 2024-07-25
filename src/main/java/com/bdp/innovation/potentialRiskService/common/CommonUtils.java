package com.bdp.innovation.potentialRiskService.common;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import com.bdp.innovation.potentialRiskService.dto.ManagementResponse;
import com.bdp.innovation.potentialRiskService.handler.GenericResponseHandler;
import com.bdp.innovation.potentialRiskService.model.EventManagement;
import com.bdp.innovation.potentialRiskService.model.EventPolygon;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.Point;
import com.mapbox.geojson.Polygon;
import com.mapbox.turf.TurfMeasurement;

import jakarta.persistence.Query;

/*
 * Class contains common methods which
 * can be used at different classes.
 */

/**
 * 
 */
/**
 * 
 */
public interface CommonUtils {
	public static int MESSAGE = 1;
	public static int ERROR = 2;
	public static int FORBIDDEN = 3;
	Logger log = LoggerFactory.getLogger(CommonUtils.class);

	public static final Boolean isActiveDecryption = null;

	public static String EXCEL_FILE_TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

	/*
	 * Method will generate 6 digit random Number from 0 to 999999
	 */
	public static String generateRandomNumber() {
		Random rnd;
		int number = 0;
		try {
			rnd = SecureRandom.getInstanceStrong();
			number = rnd.nextInt(999999);
		} catch (NoSuchAlgorithmException e) {
			log.error(e.getMessage());
		}
		return String.format("%06d", number);
	}

	public static void createDirectory(String filePath) {
		try {
			Path path = Paths.get(filePath);
			if (!Files.exists(path)) {
				Files.createDirectories(path);
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	public static ResponseEntity<ManagementResponse> responseMessages(String message, int type) {
		ManagementResponse response = new ManagementResponse();
		if (type == 1 || type == 0) {
			response.setReturnCode(Constants.SUCCESS_CODE);
			response.setReturnMessage(Constants.SUCCESS_MESSAGE);
			response.setResult(message);
			return new ResponseEntity<ManagementResponse>(response, HttpStatus.OK);
		} else if (type == 2) {
			response.setReturnCode(Constants.ERROR_CODE);
			response.setReturnMessage(Constants.ERROR_MESSAGE);
			response.setResult(message);
			return new ResponseEntity<ManagementResponse>(response, HttpStatus.EXPECTATION_FAILED);
		} else {
			response.setReturnCode(Constants.FORBIDDEN);
			response.setReturnMessage(Constants.FORBIDDEN_MESSAGE);
			response.setResult(message);
			return new ResponseEntity<ManagementResponse>(response, HttpStatus.FORBIDDEN);
		}
	}

	public static ResponseEntity<Map<String, Object>> buildResponse(Object response) {
		return new GenericResponseHandler.Builder().setStatus(CommonUtils.getStatusCode(response))
				.setMessage(Constants.SUCCESS_MESSAGE).setData(response).create();
	}

	public static ResponseEntity<Map<String, Object>> buildErrorResponse(String response, Object data) {
		return new GenericResponseHandler.Builder().setStatus(HttpStatus.EXPECTATION_FAILED).setMessage(response)
				.setData(data).create();
	}

	public static ResponseEntity<Map<String, Object>> buildResponseMessage(String responseMessage, Object data) {
		return new GenericResponseHandler.Builder().setStatus(HttpStatus.OK).setMessage(responseMessage).setData(data)
				.create();
	}

	public static Map<String, Object> createResponse(Object data, String message, HttpStatus status) {
		Map<String, Object> response = new HashMap<>();
		response.put("status", status);
		response.put("message", message);
		response.put("data", data);
		return response;
	}

	public static Map<String, Object> handleException(Exception e) {
		Map<String, Object> response = new HashMap<>();
		response.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
		response.put("message", e.getMessage());
		return response;
	}

	public static HttpStatus getStatusCode(Object object) {
		HttpStatus httpStatus = null;
		if (!ObjectUtils.isEmpty(object)) {
			httpStatus = HttpStatus.OK;
		} else {
			httpStatus = HttpStatus.NO_CONTENT;
		}
		return httpStatus;
	}

	public static String getText(String key) {
		Properties props = new Properties();
		try {
			String resourceName = "application.yml";
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			try (InputStream resourceStream = loader.getResourceAsStream(resourceName)) {
				props.load(resourceStream);
			}
			return props.get(key).toString();
		} catch (Exception e) {
			System.out.println("getText : " + e.getMessage());
		}
		return null;
	}

	public static boolean IsNumber(String drayId) {
		if (drayId != null && !drayId.matches("[0-9]+")) {
			return false;
		}
		return true;
	}

	public static boolean isValidString(String draySCAC) {
		if (draySCAC != null) {
			if (draySCAC.matches("^[a-zA-Z]*$")) {
				return true;
			}
		}
		return false;
	}

	public static boolean isValidName(String drayName) {
		String regx = "^[\\p{L} .'-]+$";
		if (drayName != null) {
			if (drayName.matches(regx)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isValidMail(String mailid) {
		if (mailid.contains("@") && mailid.contains(".")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNullOrEmpty(String string) {
		if (string != null && string.length() > 0) {
			return false;
		} else {
			return true;
		}
	}

	public static boolean hasExcelFormat(MultipartFile file) {
		if (file == null)
			return false;
		return EXCEL_FILE_TYPE.equals(file.getContentType());
	}

	public static Boolean isValidPostalCode(String postalCode) {
		if (ObjectUtils.isEmpty(postalCode))
			return false;
		return Pattern.matches("(?i)^[a-z0-9][a-z0-9\\- ]{0,10}[a-z0-9]$", postalCode);
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
	public static double getDistanceBetweenCordinate(Double lat1, Double lat2, Double lon1, Double lon2) {
		if (lat1 == null || lat2 == null || lon1 == null || lon2 == null)
			return 0;
		double radius = 6371;
		lon1 = Math.toRadians(lon1);
		lon2 = Math.toRadians(lon2);
		lat1 = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);
		double dlon = lon2 - lon1;
		double dlat = lat2 - lat1;

		double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);

		return radius * (2 * Math.asin(Math.sqrt(a)));
	}

	public static String getLocation(double latitude, double longitude) {
		String lati = convertToDegreeMinuteSecond(latitude);
		String latitudeCardinal = latitude >= 0 ? "N" : "S";

		String longi = convertToDegreeMinuteSecond(longitude);
		String longitudeCardinal = longitude >= 0 ? "E" : "W";

		return lati + " " + latitudeCardinal + "  " + longi + " " + longitudeCardinal;

	}

	public static String convertToDegreeMinuteSecond(double coordinate) {
		double absolute = Math.abs(coordinate);
		double degrees = Math.floor(coordinate);
		double minutesNotTruncated = (absolute - degrees) * 60;
		double minutes = Math.floor(minutesNotTruncated);
		double seconds = Math.floor((minutesNotTruncated - minutes) * 60);

		return degrees + "\u00B0 " + minutes + "' " + seconds + "''";
	}

	public static Double convertAreaToRadius(Double area) {
		return Math.sqrt((area * 0.01) / Math.PI);
	}

	public static String getNameFromEamil(String email) {
		StringTokenizer st = new StringTokenizer(email, "@");
		String name = st.nextToken();
		return name;
	}

	public static float convertAreaToRadius(Double value, String unit) {
		float radious = 5f;
		if (!ObjectUtils.isEmpty(unit) && unit != null && unit.length() != 0) {
			if (unit.equalsIgnoreCase("ha")) {
				double sqkm = value / 100;
				radious = Math.round(Math.sqrt(sqkm));
			} else if (unit.equalsIgnoreCase("m")) {
				radious = Math.round(value.floatValue() / 1000);
			} else if (unit.equalsIgnoreCase("km/h")) {
				radious = Math.round(value.floatValue());
			} else if (unit.equalsIgnoreCase("km2")) {
				radious = Math.round(Math.sqrt(value));
			} else {
				radious = Math.round(value.floatValue());
			}
		}
		return radious;
	}

	public static Double convertAreaToKmsq(Double value, String unit, String type) {
		Double area = 5.0;
		if (!ObjectUtils.isEmpty(unit) && unit != null && unit.length() != 0) {
			if (unit.equalsIgnoreCase("ha"))
				area = value / 100;
			else if (unit.equalsIgnoreCase("m")) {
				area = Math.PI * Math.pow(Math.round(value.floatValue()), 2);
			} else if (unit.equalsIgnoreCase("km/h")) {
				area = Math.PI * Math.pow(Math.round(value.floatValue()), 2);
				// area = (double) Math.round(value);
			} else if (unit.equalsIgnoreCase("km2")) {
				area = value;
			}
		}
		if (type.equalsIgnoreCase("vo")) {
			area = Math.PI * Math.pow(Math.round(30.0), 2);
		}
		return area;
	}

	public static List<String> convertStringToList(String str) {
		if (str == null)
			return new ArrayList<String>();
		return Arrays.asList(str.split(","));
	}

	public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Map<Object, Boolean> seen = new ConcurrentHashMap<>();
		return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}

	/**
	 * Method to calculate multi polygon area based on co-odinates
	 * 
	 * @param multiEventPolygon
	 * @return total Area
	 */
	public static double calculatePolygonArea(List<EventPolygon> multiEventPolygon) throws ArithmeticException {
		double totalArea = 0;
		if (!ObjectUtils.isEmpty(multiEventPolygon)) {

			Map<Integer, List<EventPolygon>> eventPolygon = multiEventPolygon.stream()
					.collect(Collectors.groupingBy(EventPolygon::getGroupId));
			for (Entry<Integer, List<EventPolygon>> entry : eventPolygon.entrySet()) {
				if (!ObjectUtils.isEmpty(entry)) {

					List<Double> latitudes = entry.getValue().stream().map(EventPolygon::getLatitude)
							.collect(Collectors.toList());

					List<Double> longitudes = entry.getValue().stream().map(EventPolygon::getLongitude)
							.collect(Collectors.toList());

					List<Point> points = new ArrayList<>();
					for (int i = 0; i < latitudes.size(); i++) {
						points.add(Point.fromLngLat(longitudes.get(i), latitudes.get(i)));
					}

					// Create a Polygon geometry from the points
					Polygon polygon = Polygon.fromLngLats(List.of(points));

					// Convert the Polygon geometry to a Feature
					Feature feature = Feature.fromGeometry(polygon);

					// Use Turf to calculate the area of the polygon
					double areaInSquareMeters = TurfMeasurement.area(feature);

					// Convert square meters to square kilometers
					double areaInSquareKilometers = areaInSquareMeters / 1000000.0;

					totalArea += areaInSquareKilometers;
					log.info("total area of the polygon :{}", totalArea);
				}
			}
		}
		return totalArea;
	}

	public static Query executeParam(Query queryObj, Map<String, Object> queryParam) {
		for (Entry<String, Object> param : queryParam.entrySet()) {
			queryObj.setParameter(param.getKey(), param.getValue());
		}
		return queryObj;
	}

	public static boolean isGlobleEvent(EventManagement eventManagement) {
		boolean flag = Boolean.FALSE;
		if (!ObjectUtils.isEmpty(eventManagement.getIsGlobalEvent())
				&& eventManagement.getIsGlobalEvent().equals(Constants.ACTIVE)) {
			flag = Boolean.TRUE;
		}
		return flag;
	}

}
