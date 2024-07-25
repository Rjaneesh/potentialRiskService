package com.bdp.innovation.potentialRiskService.common;


import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.IsoFields;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import com.bdp.innovation.potentialRiskService.dto.WeekYear;

public interface DateUtils {

	Logger log = LoggerFactory.getLogger(DateUtils.class);

	String MMDDYYYY = "MM/dd/yyyy";
	String DD_MM_YYYY_HHMM = "dd-MM-yyyy HH:mm";
	String YYYYMMDD = "yyyy/MM/dd";
	String YYYY_MM_DD = "yyyy-MM-dd";
	String YYYY_MM_DD_HHMM = "yyyy-MM-dd HH:mm";
	String DDMMYYYY = "dd/MM/yyyy";
	String YYYYMMDDHHMM = "yyyy-MM-dd HH:mm:ss";
	String YYYY_MM_DD2 = "yyyyMMdd";
	String DD_MM_YYYY = "dd-MM-yyyy";

	static Date getDateInstance() {
		Calendar gc = Calendar.getInstance();
		return gc.getTime();
	}

	// covert date as string to date
	static Date convertStringToDate(String date) {
		DateFormat formatter = null;
		try {
			formatter = new SimpleDateFormat(Constants.MM_DD_YYYY);
			return formatter.parse(date);
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
		try {
			formatter = new SimpleDateFormat(Constants.YYYY_MM_DD);
			return formatter.parse(date);
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
		return null;
	}

	public static Date convertStringToDateForLoad(String date) throws Exception {
		Date returnDate = new Date();
		if (!ObjectUtils.isEmpty(date))
			returnDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(date);

		return returnDate;
	}

	// covert date as string to date
	static Date convertAnyStringToDate(String date) throws Exception {
		DateFormat formatter = null;
		Date returnDate = null;
		try {
			formatter = new SimpleDateFormat(YYYYMMDDHHMM);
			return formatter.parse(date);
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
		try {
			formatter = new SimpleDateFormat(DD_MM_YYYY_HHMM);
			return formatter.parse(date);
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
		try {
			formatter = new SimpleDateFormat(MMDDYYYY);
			return formatter.parse(date);
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
		try {
			formatter = new SimpleDateFormat(YYYYMMDD);
			return formatter.parse(date);
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
		try {
			formatter = new SimpleDateFormat(YYYY_MM_DD);
			return formatter.parse(date);
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
		try {
			formatter = new SimpleDateFormat(DDMMYYYY);
			return formatter.parse(date);
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
		try {
			formatter = new SimpleDateFormat(DD_MM_YYYY);
			return formatter.parse(date);
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
		try {
			formatter = new SimpleDateFormat(YYYY_MM_DD2);
			return formatter.parse(date);
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
		return returnDate;
	}

	// Format date into DD_MM_YYYY format
	static String dateFormate(String date) {
		Date converttedDate = null;
		try {
			converttedDate = new SimpleDateFormat(Constants.DD_MM_YYYY).parse(date);
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.YYYY_MM_DD);
		return dateFormat.format(converttedDate);
	}

	static String dateFormate(Date date) {
		SimpleDateFormat dateFormat = null;
		String formatedDate = null;
		try {
			if (date != null) {
				dateFormat = new SimpleDateFormat(Constants.DD_MM_YY);
				formatedDate = dateFormat.format(date);
			}
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
		return formatedDate;
	}

	static String dateFormateChange(Date date) {
		SimpleDateFormat dateFormat = null;
		String formatedDate = null;
		try {
			if (date != null) {
				dateFormat = new SimpleDateFormat(Constants.YYYY_MM_DD);
				formatedDate = dateFormat.format(date);
			}
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
		return formatedDate;
	}

	static String dateFormateChange(Date date, String format) {
		SimpleDateFormat dateFormat = null;
		String formatedDate = null;
		try {
			if (date != null) {
				dateFormat = new SimpleDateFormat(format);
				formatedDate = dateFormat.format(date);
			}
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
		return formatedDate;
	}

	static String dateFormatforCSV(Date date) {
		Format dateFormat = null;
		String formatedDate = null;
		try {
			if (date != null) {
				dateFormat = new SimpleDateFormat(Constants.MM_dd_yy);
				formatedDate = dateFormat.format(date);
			}
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
		return formatedDate;
	}

	// Format currentDate into MM_DD_YYYY format
	static Date currentDate() {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.MM_DD_YYYY);
		String currentDate = formatter.format(date);
		Date formatDate = null;
		try {
			formatDate = formatter.parse(currentDate);
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
		return formatDate;
	}

	// get before date from current date
	@SuppressWarnings("deprecation")
	static String beforeMonthDate(int month) {
		Date date = getDateInstance();
		date.setMonth(date.getMonth() - month);
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.YYYY_MM_DD);
		return dateFormat.format(date);
	}

	static String getCurrentDate() {
		return DateTimeFormatter.ofPattern("yyyyMMddHHmmss").withZone(ZoneId.of("UTC")).format(Instant.now());
	}

	static Date getRequiredDateFormatForMail(Date date) {
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(YYYYMMDDHHMM, Locale.ENGLISH);
//		LocalDateTime presentDate = LocalDateTime.now();
//		String text = presentDate.format(formatter);
		LocalDateTime localDate = dateToLocalDateTime(date);
		Date convertedDate = localDateTimeToDate(localDate);
		return convertedDate;
	}

	static String getTwoDaysBeforeDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -2);
		Date dateBefore30Days = cal.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat(YYYYMMDDHHMM);
		return dateFormat.format(dateBefore30Days);
	}

	static String getDaysBeforeDay(int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -day);
		Date dateBefore30Days = cal.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD);
		return dateFormat.format(dateBefore30Days);
	}

	static Date getDateBeforeDay(int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DATE, -day);
		Date dateBeforeDays = cal.getTime();
		return dateBeforeDays;
	}

	static Date getDateAfterGivenDay(Date date, int day) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, +day);
		Date dateBeforeDays = cal.getTime();
		return dateBeforeDays;
	}

	static Date getDateDay(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD);
		Date dt = null;
		try {
			dt = dateFormat.parse(dateFormate(date));
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
		return dt;
	}

	static Date getFirstDateOfWeek() {
		LocalDate today = LocalDate.now();
		LocalDate monday = today;
		while (monday.getDayOfWeek() != DayOfWeek.MONDAY) {
			monday = monday.minusDays(1);
		}
		return Date.from(monday.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	static Date getLastDateOfWeek() {
		LocalDate today = LocalDate.now();
		LocalDate sunday = today;
		while (sunday.getDayOfWeek() != DayOfWeek.SUNDAY) {
			sunday = sunday.plusDays(1);
		}
		return Date.from(sunday.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}

	public static long getDaysDifference(Date fromDate, Date toDate) {
		long diffrence = toDate.getTime() - fromDate.getTime();
		return (diffrence / (24 * 60 * 60 * 1000));
	}
	
	public static long getHoursDifference(Date fromDate, Date toDate) {
		long diffrence = toDate.getTime() - fromDate.getTime();
		return (diffrence / (60 * 60 * 1000));
	}

	static long getMinutesDifference(Date fromDate, Date toDate) {
		long diffrence = toDate.getTime() - fromDate.getTime();
		return (diffrence / (60 * 1000));
	}

	static long getSecondsDifference(Date fromDate, Date toDate) {
		long diffrence = toDate.getTime() - fromDate.getTime();
		return diffrence / 1000;
	}

	static long getMinutesDifferenceForMail(Date fromDate, Date toDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(fromDate);
		int fhour = cal.get(Calendar.HOUR_OF_DAY);
		int fminute = cal.get(Calendar.MINUTE);

		cal = Calendar.getInstance();
		cal.setTime(toDate);
		int thour = cal.get(Calendar.HOUR_OF_DAY);
		int tminute = cal.get(Calendar.MINUTE);

		int diffrenceh = thour - fhour;
		int diffrencem = tminute - fminute;
		return ((diffrenceh * 60) + diffrencem);
	}

	static String getDateAfterDay(int day) {
		Calendar cal = Calendar.getInstance();
		if (day == 0)
			cal.setTime(new Date());
		else
			cal.add(Calendar.DATE, +day);
		Date dateBefore30Days = cal.getTime();
		SimpleDateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD);
		return dateFormat.format(dateBefore30Days);
	}

	static Date getDateAfterHours(int hour, int minute) {
		Calendar cal = Calendar.getInstance();
		if (hour == 0)
			cal.setTime(getDateInstance());
		else {
			cal.set(Calendar.HOUR_OF_DAY, hour);
			cal.set(Calendar.MINUTE, minute);
		}
		return cal.getTime();
	}

	static Date getDateAfterHours(Date currentDate, int hour) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		if (hour == 0)
			cal.setTime(getDateInstance());
		else {
			cal.set(Calendar.HOUR_OF_DAY, hour);
		}
		return cal.getTime();
	}

	static Date getCustomDate(Date date, int hour, int minute, int second) {
		Calendar cal = Calendar.getInstance();
		if (!ObjectUtils.isEmpty(date)) {
			cal.setTime(date);
		}
		if (!ObjectUtils.isEmpty(hour)) {
			cal.set(Calendar.HOUR_OF_DAY, hour);
		}
		if (!ObjectUtils.isEmpty(minute)) {
			cal.set(Calendar.MINUTE, minute);
		}
		if (!ObjectUtils.isEmpty(second)) {
			cal.set(Calendar.SECOND, second);
		}
		return cal.getTime();
	}

	static Date getDateAfterDay(Date currentDate, int day) {
		Calendar cal = Calendar.getInstance();
		if (day == 0)
			cal.setTime(new Date());
		else {
			cal.setTime(currentDate);
			cal.add(Calendar.DATE, +day);
		}
		Date afterDate = cal.getTime();
		return afterDate;
	}

	static String getCurrentFilterDate() {
		Date dateBefore30Days = getDateInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat(YYYYMMDDHHMM);
		return dateFormat.format(dateBefore30Days);
	}

	static Date getCurrentFilterDate(String dateStr) {
		SimpleDateFormat dateFormat = null;
		Date convertedDate = null;
		try {
			if (dateStr == null)
				return getDateInstance();
			dateFormat = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
			convertedDate = dateFormat.parse(dateStr);
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
		return convertedDate;
	}

	static String convertFromDateToString(Date date) {
		SimpleDateFormat dateFormat = null;
		String convertedDateStr = null;
		if (date == null) {
			date = getDateInstance();
		}
		dateFormat = new SimpleDateFormat(YYYYMMDDHHMM);
		convertedDateStr = dateFormat.format(date);
		return convertedDateStr;
	}

	static String convertFromDateToString(Date date, TimeZone timeZone) {
		SimpleDateFormat dateFormat = null;
		String convertedDateStr = null;
		if (date == null) {
			date = getDateInstance();
		}
		dateFormat = new SimpleDateFormat(YYYYMMDDHHMM);
		dateFormat.setTimeZone(timeZone);
		convertedDateStr = dateFormat.format(date);
		return convertedDateStr;
	}

	static Date getStartDateOfDay(Date date) {
		LocalDateTime localDateTime = dateToLocalDateTime(date);
		LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
		return localDateTimeToDate(startOfDay);
	}

	static Date getEndDateOfDay(Date date) {
		LocalDateTime localDateTime = dateToLocalDateTime(date);
		LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
		return localDateTimeToDate(endOfDay);
	}

	static LocalDateTime dateToLocalDateTime(Date date) {
		return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}

	static Date localDateTimeToDate(LocalDateTime localDateTime) {
		return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	static Date getDateFromTimezoneStrDate(String timezoneDate) {
		Date date = null;
		if (timezoneDate != null) {
			OffsetDateTime odt = OffsetDateTime.parse(timezoneDate);
			Instant instant = odt.toInstant();
			date = Date.from(instant);
		}
		return date;
	}

	static Date getDateFullFormat(String date) {
		Date converttedDate = null;
		try {
			if (date == null) {
				return getDateInstance();
			}
			if (date.contains("-") && date.split("-")[0].length() == 4) {
				converttedDate = new SimpleDateFormat(YYYY_MM_DD_HHMM).parse(date);
			} else if (date.contains("-") && date.split("-")[0].length() == 2) {
				converttedDate = new SimpleDateFormat("dd-MM-yyyy HH:mm").parse(date);
			} else {
				converttedDate = new SimpleDateFormat(YYYY_MM_DD2).parse(date);
			}
		} catch (Exception e) {
		}
		return converttedDate;
	}

	static Date getDateFormat(String date) {
		Date converttedDate = null;
		try {
			if (date == null) {
				return null;
			}
			if (date.contains("-") && date.split("-")[0].length() == 4) {
				converttedDate = new SimpleDateFormat(YYYY_MM_DD).parse(date);
			} else if (date.contains("-") && date.split("-")[0].length() == 2) {
				converttedDate = new SimpleDateFormat(DD_MM_YYYY).parse(date);
			} else if (date.contains("/") && date.split("/")[0].length() == 2) {
				String newDt = null;
				TimeZone timeZone = TimeZone.getDefault();
				if (timeZone.toZoneId().getId().startsWith("Asia")) {
					newDt = date.split("/")[2] + "-" + date.split("/")[1] + "-" + date.split("/")[0];
				} else {
					newDt = date.split("/")[2] + "-" + date.split("/")[0] + "-" + date.split("/")[1];
				}
				converttedDate = new SimpleDateFormat(YYYY_MM_DD).parse(newDt);
			} else if (date.contains("/") && date.split("/")[0].length() == 4) {
				converttedDate = new SimpleDateFormat("yyyy/MM/dd").parse(date);
			} else {
				converttedDate = new SimpleDateFormat(YYYY_MM_DD2).parse(date);
			}
		} catch (Exception e) {
		}
		return converttedDate;
	}

	public static Boolean isFutureDate(String date) {
		Date todayDate = new Date();
		Date inputDate;
		try {
			inputDate = DateUtils.convertAnyStringToDate(date);
			if (todayDate.compareTo(inputDate) < 0) // today date occurs before inputDate
				return true;
		} catch (Exception e) {
			return false;
		}

		return false;
	}

	public static LocalDate addBusinessDays(LocalDate localDate, int days, Optional<List<LocalDate>> holidays) {
		if (localDate == null || days <= 0 || holidays.isPresent()) {
			throw new IllegalArgumentException("Invalid method argument(s) " + "to addBusinessDays(" + localDate + ","
					+ days + "," + holidays + ")");
		}

		Predicate<LocalDate> isHoliday = date -> holidays.isPresent() ? holidays.get().contains(date) : false;

		Predicate<LocalDate> isWeekend = date -> date.getDayOfWeek() == DayOfWeek.SATURDAY
				|| date.getDayOfWeek() == DayOfWeek.SUNDAY;

		LocalDate result = localDate;
		while (days > 0) {
			result = result.plusDays(1);
			if (isHoliday.or(isWeekend).negate().test(result)) {
				days--;
			}
		}
		return result;
	}

	public static Date getPreviousDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date now = dateFormat.parse(dateFormat.format(new Date()));
			// if the update always places the time as YYYYMMDD 00:00:00 this will be safer.
			Date oneDayBefore = new Date(now.getTime() - 2);
			// Date oneDayBefore = new Date(myDate.getTime() - (24 * 3600000));

			return dateFormat.parse(dateFormat.format(oneDayBefore));
		} catch (Exception e) {
			log.error("Error :: DateUtils :: getPreviousDate", e.getMessage());
			return new Date();
		}

	}

	public static Date getTodayDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date now = dateFormat.parse(dateFormat.format(new Date()));
			return dateFormat.parse(dateFormat.format(now));
		} catch (Exception e) {
			log.error("Error :: DateUtils :: getTodayDate", e.getMessage());
			return new Date();
		}

	}

	static Date getDateDayForMailLog(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD_HHMM);
		Date dt = null;
		try {
			dt = dateFormat.parse(dateFormate(date));
		} catch (Exception e) {
			// log.error(e.getMessage());
		}
		return dt;
	}

	static Date getDateOnWeekNo(Integer weekNo) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.WEEK_OF_YEAR, weekNo);
		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	static Integer getCurrentWeekNo() {
		return Calendar.getInstance(TimeZone.getTimeZone("UTC")).get(Calendar.WEEK_OF_YEAR);
	}

	static Date getDateFromWeek(Integer week, Integer year) {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calendar.clear();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.WEEK_OF_YEAR, week);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return calendar.getTime();
    }

	static Date getCreatedDateForPC(Date date) {
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 1);
		return cal.getTime();
	}

	static WeekYear getWeekNoAndYear() {
		LocalDate currentDate = LocalDate.now(ZoneId.of("UTC"));
		Integer week = currentDate.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
		Integer month = currentDate.getMonthValue();
		Integer year = currentDate.getYear();
		WeekYear weekYear = new WeekYear();
		weekYear.setWeek(week);
		if(month ==12 && week == 1) {
			weekYear.setYear(year + 1);
		}else {
			weekYear.setYear(year);
		}
        return weekYear;
    }

	static WeekYear getLastWeekYear(Integer week ,Integer year) {
    	if(week == 1) {
    		week = 52;
    		year--;
    	}else week--;
        return new WeekYear(week, year);
    }

	static List<WeekYear> getLast10Weeks(Integer week, Integer year) {
        List<WeekYear> result = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
        	result.add(new WeekYear(week, year));
        	if(week == 1) {
        		week = 52;
        		year--;
        	} else week--;
        }
        return result;
    }

	static String getDateForSnowflake(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(date);
	}

	static Date convertToUTC(String date) {
		SimpleDateFormat dateFormat = null;
		Date utcDate = null;
		if(date.contains("T"))
			dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		else
			dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
            utcDate = dateFormat.parse(date);
        } catch (Exception e) {
            log.error("Error :: DateUtils :: convertToUTC", e.getMessage());
        }
        return utcDate;
    }

	static Date getInsertedDateTime(String inputDate) {
        OffsetDateTime pdtDateTime = OffsetDateTime.parse(inputDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS Z"));
        OffsetDateTime utcDateTime = pdtDateTime.withOffsetSameInstant(ZoneOffset.UTC);
        Instant instant = utcDateTime.toInstant();
        Date utcDate = Date.from(instant);
		return utcDate;
	}

	static Date convertMilliesToDate(String milliSecond) {
		Instant instant = Instant.ofEpochMilli(Long.valueOf(milliSecond));
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		return date;
	}

	static Date convertUTCTimeFromDate(String timeZone, Date date) {
		Date utcDateTime = null;
		try {
			if (ObjectUtils.isEmpty(timeZone))
				return date;
			String time = dateFormateChange(date, "HHmmss");
			String dateStr = dateFormateChange(date);

			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(Constants.YYYY_MM_DD);
			DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmmss");

			LocalDate localDate = LocalDate.parse(dateStr, dateFormatter);
			LocalTime localTime = LocalTime.parse(time, timeFormatter);

			// System.out.println(timeZone+" : "+timeZone);
			ZonedDateTime istTime = ZonedDateTime.of(localDate, localTime, ZoneId.of(timeZone));
			// then convert it to UTC
			ZonedDateTime utcTime = istTime.withZoneSameInstant(ZoneId.of("UTC"));

			String dt = utcTime.toString().substring(0, 16);
			// System.out.println(utcTime+" : "+dt);
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			utcDateTime = sdf.parse(dt);
		} catch (Exception e) {
			log.error("Error :: DateUtils :: convertUTCTimeFromDate", e.getMessage());
		}
		return utcDateTime;
	}

	static String getTimeZone(String key) {
		Map<String, String> zone = ZoneId.SHORT_IDS;
		switch (key) {
		case "WEDT":
			key = "UTC+1";
			break;
		case "EDT":
			key = "UTC-4";
			break;
		case "GMT":
			key = "GMT";
			break;

		default:
			key = zone.get(key);
			break;
		}
		return key;
	}

	static String convertStringDateTimeToOnlyDate(String date) {
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		String pastDate = null;
		try {
			pastDate = formatter2.format(formatter1.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return pastDate;
	}

	public static List<LocalDate> getDatesBetweenTwoDates(LocalDate startDate, LocalDate endDate) {

		long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDate);
		return IntStream.iterate(0, i -> i + 1).limit(numOfDaysBetween).mapToObj(i -> startDate.plusDays(i))
				.collect(Collectors.toList());
	}

	public static LocalDate convertDateToLocalDate(Date date) {
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return localDate;
	}
}
