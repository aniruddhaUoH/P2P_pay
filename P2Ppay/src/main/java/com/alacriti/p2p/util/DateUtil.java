package com.alacriti.p2p.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import com.alacriti.p2p.log.impl.AppLogger;


public class DateUtil {
	private static final AppLogger log = LogUtil.getLogger(DateUtil.class);
	// The various formats used for the date and times are
	private static final String format1 = "yyyy-MM-dd";
	private static final String format2 = "yyyyMMdd";
	public static final String format3 = "MM/dd/yyyy";
	private static final String format4 = "yyyyMMddHHmmss";
	private static final String format5 = "hhmmss";
	private static final String format6 = "MM/dd/yyyy HH:mm:ss:SSS ";
	private static final String format7 = "yyyy-MM-dd HH:mm:ss Z";
	private static final String format8 = "HH:mm:ss";
	private static final String format9 = "MMddyy";
	private static final String format10 = "yyMMdd";
	//private static final String format11 = "ccyyMMdd";
	private static final String format12 = "yy/MM/dd";
	private static final String format13 = "ddMMyy";
	private static final String format14 = "DDD";
	private static final String format15 = "yyyyMMddHHmmssSSSSSS";
	public static final String format16 = "dd/MM/yyyy";
	private static final String format17 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	private static final String format18 = "MM-dd-yyyy";
	private static final String format19 = "yyyy/MM/dd";
	private static final long millisPerDay = (24 * 60 * 60 * 1000);

	public DateUtil() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * @method: Converts the String[MM/dd/yyy] format to a Date
	 */
	public static Date getStringToDate(String date, String dateFormatStr) {
		log.debugPrintCurrentMethodName();
		Date toDate = null;
		SimpleDateFormat toDateFormat = new SimpleDateFormat(dateFormatStr);

		try {
			toDate = toDateFormat.parse(date);
		} catch (ParseException e) {
			log.logError("ParseException in  getStringToDate" + e.getMessage(), e);
		}

		return toDate;
	}

	public static String getCurrentDateText() {
		log.debugPrintCurrentMethodName();
		return new SimpleDateFormat("MM/dd/yyyy").format(new GregorianCalendar().getTime());
	}

	public static String getCurrentDateWithTime() {
		log.debugPrintCurrentMethodName();
		return new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new GregorianCalendar().getTime());
	}

	public static String changeDateFormat(String strDate, final String currentDateFormat, final String newDateFormat) {
		log.debugPrintCurrentMethodName();
		String modifiedDateStr = "";

		try {
			Date date = new Date();
			if (strDate != null && !strDate.trim().equals("")) {
				DateFormat df1 = new SimpleDateFormat(currentDateFormat);
				date = df1.parse(strDate);
			}
			DateFormat df2 = new SimpleDateFormat(newDateFormat);
			modifiedDateStr = df2.format(date);
		} catch (Exception e) {
			log.logError(" Exception in changeDateFormat " + e.getMessage(), e);
		}
		return modifiedDateStr;
	}

	/*
	 *
	 * returns the week start date of the given date
	 */
	public static Date getWeekStartDate(Date date) {
		log.debugPrintCurrentMethodName();

		Calendar c = Calendar.getInstance();

		c.setTime(date);

		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());

		return c.getTime();

	}

	/*
	 *
	 * returns the week end date of the given date
	 */
	public static Date getWeekEndDate(Date date) {
		log.debugPrintCurrentMethodName();

		Calendar c = Calendar.getInstance();

		c.setTime(date);

		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());

		c.add(Calendar.DAY_OF_WEEK, 6);

		return c.getTime();

	}

	/*
	 * @param date Date to set
	 *
	 * @param noOfDays number of days to add or subtract
	 *
	 * @param isAdd if true, adds number of days to given Calendar Date. Else
	 * subtracts from given Calendar date.
	 *
	 * @return *
	 */
	public static Date addDates(Date date, int noOfDays, boolean isAdd) {
		log.debugPrintCurrentMethodName();
		Calendar c = Calendar.getInstance();
		c.setTime(date);

		if (isAdd) {
			c.add(Calendar.DAY_OF_MONTH, noOfDays);
		} else {
			c.add(Calendar.DAY_OF_MONTH, -noOfDays);
		}

		return c.getTime();
	}

	public static int getDayOfWeek(Date date) {
		log.debugPrintCurrentMethodName();

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK);

	}

	public static java.sql.Date getSQLDate(java.util.Date date) {
		log.debugPrintCurrentMethodName();
		return new java.sql.Date(date.getTime());
	}

	/**
	 * @return true if application timezone is GMT false otherwise
	 */
	public static final boolean isGMT() {
		log.debugPrintCurrentMethodName();
		TimeZone t = Calendar.getInstance().getTimeZone();
		return TimeZone.getTimeZone("GMT").equals(t) || TimeZone.getTimeZone("UTC").equals(t);
	}

	public static boolean isValidDate(String s, int i) {
		log.debugPrintCurrentMethodName();
		if (s == null) {
			return false;
		}

		if (i == 1) {
			if (!Pattern.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}", s)) {
				return false;
			}
		} else if (i == 2) {
			if (!Pattern.matches("[0-9]{8}", s)) {
				return false;
			}
		} else if (i == 3) {
			if (!Pattern.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}", s)) {
				return false;
			} else {
				return true;
			}
		} else if (i == 4) {
			if (!Pattern.matches("[0-9]{14}", s)) {
				return false;
			}

			int hh = Integer.parseInt(s.substring(8, 10));
			int mm = Integer.parseInt(s.substring(10, 12));
			int ss = Integer.parseInt(s.substring(12, 14));

			if (hh < 0 || hh > 23) {
				return false;
			}

			if (mm < 0 || mm > 59) {
				return false;
			}

			if (ss < 0 || ss > 59) {
				return false;
			}

			s = s.substring(0, 8);
			i = 2;
		} else if (i == 5) {
			if (!Pattern.matches("[0-9]{2}-[0-9]{2}-[0-9]{4}", s)) {
				return false;
			}
		} else if (i == 6) {
			if (!Pattern.matches("[0-9]{6}", s)) {
				return false;
			}
		} else if (i == 7) {
			if (!Pattern.matches("[0-9]{4}", s)) {
				return false;
			}
		} else if (i == 8) {
			if (!Pattern.matches("[0-9]{2}/[0-9]{2}", s)) {
				return false;
			}
		} else if (i == 9) {
			if (!Pattern.matches("[0-9]{2}/[0-9]{4}", s)) {
				return false;
			}
		} else {
			return false;
		}

		String myDate = changeDateFormat(s, i);

		if (myDate == null) {
			return false;
		}

		GregorianCalendar gc = new GregorianCalendar(Integer.parseInt(myDate.substring(0, 4)),
				Integer.parseInt(myDate.substring(5, 7)) - 1, Integer.parseInt(myDate.substring(8, 10)));
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

		if (df.format(gc.getTime()).equals(myDate)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 1 :- yyyy-mm-dd<br>
	 * 2 :- yyyymmdd<br>
	 * 3 :- mm/dd/yyyy<br>
	 * 10 :- YYYY-MM-dd hh24:mm:ss<br>
	 * returns -1 for d1 < d2, 1 for d1 > d2 and 0 for d1 = d2<br>
	 * Note: Check the dates to be of valid format before passing in this method
	 * Note: If d1 or d2 is passed empty it will be taken as current date
	 */
	public static int compareDates(String d1, String d2, int i) {
		log.debugPrintCurrentMethodName();
		GregorianCalendar gc1;
		GregorianCalendar gc2;
		if (!StringUtil.isEmpty(d1)) {
			String date1 = changeDateFormat(d1, i);
			if (StringUtil.isEmpty(date1)) {
				return 0;
			}
			gc1 = new GregorianCalendar(Integer.parseInt(date1.substring(0, 4)),
					Integer.parseInt(date1.substring(5, 7)) - 1, Integer.parseInt(date1.substring(8, 10)),
					Integer.parseInt(date1.substring(11, 13)), Integer.parseInt(date1.substring(14, 16)),
					Integer.parseInt(date1.substring(17, 19)));
		} else {
			gc1 = new GregorianCalendar();
		}

		if (!StringUtil.isEmpty(d2)) {
			String date2 = changeDateFormat(d2, i);
			if (StringUtil.isEmpty(date2)) {
				return 0;
			}
			gc2 = new GregorianCalendar(Integer.parseInt(date2.substring(0, 4)),
					Integer.parseInt(date2.substring(5, 7)) - 1, Integer.parseInt(date2.substring(8, 10)),
					Integer.parseInt(date2.substring(11, 13)), Integer.parseInt(date2.substring(14, 16)),
					Integer.parseInt(date2.substring(17, 19)));
		} else {
			gc2 = new GregorianCalendar();
		}

		if (gc1.after(gc2)) {
			return 1;
		} else if (gc1.before(gc2)) {
			return -1;
		} else {
			return 0;
		}
	}

	/**
	 * 1 :- yyyy-mm-dd<br>
	 * 2 :- yyyymmdd<br>
	 * 3 :- mm/dd/yyyy<br>
	 * 5 :- mm-dd-yyyy<br>
	 * 6 :- mmyyyy<br>
	 * 7 :- mmyy<br>
	 * 8 :- mm/yy<br>
	 * 9 :- mm/yyyy<br>
	 * 10 :- YYYY-MM-dd hh24:mm:ss<br>
	 * changes to yyyy-mm-dd
	 */
	public static String changeDateFormat(String s, int i) {
		log.debugPrintCurrentMethodName();
		int dayI = 0;
		int monthI = 0;
		int yearI = 0;
		int hourI = 0;
		int minuteI = 0;
		int secondI = 0;

		if (i == 1) {
			String[] dates = s.split("-");
			if (dates.length != 3) {
				return null;
			}
			try {
				dayI = Integer.parseInt(dates[2]);
				monthI = Integer.parseInt(dates[1]);
				yearI = Integer.parseInt(dates[0]);
			} catch (Exception e) {
				log.logError("Exception in changeDateFormat " + e.getMessage(), e);
				return null;
			}
		} else if (i == 2) {
			try {
				dayI = Integer.parseInt(s.substring(6));
				monthI = Integer.parseInt(s.substring(4, 6));
				yearI = Integer.parseInt(s.substring(0, 4));
			} catch (Exception e) {
				log.logError("Exception in changeDateFormat " + e.getMessage(), e);
				return null;
			}
		} else if (i == 3) {
			String[] dates = s.split("/");
			if (dates.length != 3) {
				return null;
			}
			try {
				dayI = Integer.parseInt(dates[1]);
				monthI = Integer.parseInt(dates[0]);
				yearI = Integer.parseInt(dates[2]);
			} catch (Exception e) {
				log.logError("Exception in changeDateFormat  " + e.getMessage(), e);
				return null;
			}
		} else if (i == 5) {
			String[] dates = s.split("-");
			if (dates.length != 3) {
				return null;
			}
			try {
				dayI = Integer.parseInt(dates[1]);
				monthI = Integer.parseInt(dates[0]);
				yearI = Integer.parseInt(dates[2]);
			} catch (Exception e) {
				log.logError("Exception in changeDateFormat " + e.getMessage(), e);
				return null;
			}
		} else if (i == 6) {
			try {
				dayI = 1;
				monthI = Integer.parseInt(s.substring(0, 2));
				yearI = Integer.parseInt(s.substring(2, 6));
			} catch (Exception e) {
				log.logError("Exception in changeDateFormat " + e.getMessage(), e);
				return null;
			}
		} else if (i == 7) {
			try {
				dayI = 1;
				monthI = Integer.parseInt(s.substring(0, 2));
				// just adding 2000 because year doesnot matter while checking
				// for only
				// months
				yearI = 2000 + Integer.parseInt(s.substring(2, 4));
			} catch (Exception e) {
				log.logError("Exception in changeDateFormat " + e.getMessage(), e);
				return null;
			}
		} else if (i == 8) {
			try {
				dayI = 1;
				monthI = Integer.parseInt(s.substring(0, 2));
				// just adding 2000 because year doesnot matter while checking
				// for only
				// months
				yearI = 2000 + Integer.parseInt(s.substring(3, 5));
			} catch (Exception e) {
				log.logError("Exception in changeDateFormat " + e.getMessage(), e);
				return null;
			}
		} else if (i == 9) {
			try {
				dayI = 1;
				monthI = Integer.parseInt(s.substring(0, 2));
				yearI = Integer.parseInt(s.substring(3, 7));
			} catch (Exception e) {
				log.logError("Exception in changeDateFormat " + e.getMessage(), e);
				return null;
			}
		} else if (i == 10) {
			String[] dates = s.substring(0, s.indexOf(" ")).split("-");
			if (dates.length != 3) {
				return null;
			}
			try {
				dayI = Integer.parseInt(dates[2]);
				monthI = Integer.parseInt(dates[1]);
				yearI = Integer.parseInt(dates[0]);
			} catch (Exception e) {
				log.logError("Exception in changeDateFormat " + e.getMessage(), e);
				return null;
			}
			String[] times = s.substring(s.indexOf(" ") + 1, s.length()).split(":");
			if (times.length != 3) {
				return null;
			}
			try {
				hourI = Integer.parseInt(times[0]);
				minuteI = Integer.parseInt(times[1]);
				secondI = Integer.parseInt(times[2]);
			} catch (Exception e) {
				log.logError("Exception in changeDateFormat " + e.getMessage(), e);
				return null;
			}
		} else {
			return null;
		}

		StringBuffer sb = new StringBuffer("");

		if (yearI < 10) {
			sb.append("000" + yearI + "-");
		} else if (yearI < 100) {
			sb.append("00" + yearI + "-");
		} else if (yearI < 1000) {
			sb.append("0" + yearI + "-");
		} else {
			sb.append(yearI + "-");
		}

		if (monthI < 10) {
			sb.append("0" + monthI + "-");
		} else {
			sb.append(monthI + "-");
		}

		if (dayI < 10) {
			sb.append("0" + dayI + " ");
		} else {
			sb.append("" + dayI + " ");
		}

		if (hourI < 10) {
			sb.append("0" + hourI + ":");
		} else {
			sb.append("" + hourI + ":");
		}
		if (minuteI < 10) {
			sb.append("0" + minuteI + ":");
		} else {
			sb.append("" + minuteI + ":");
		}
		if (secondI < 10) {
			sb.append("0" + secondI);
		} else {
			sb.append("" + secondI);
		}
		return sb.toString();
	}

	/**
	 * Adds a time to a date.
	 */
	public static Date addTime(Date date, String time) {
		log.debugPrintCurrentMethodName();
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		String[] timeHmS = StringUtil.split(time, ":");
		if (timeHmS != null && timeHmS.length > 0) {
			int strlength = timeHmS.length;
			if (strlength > 0)
				cal.add(Calendar.HOUR_OF_DAY, NumberUtils.getInt(timeHmS[0]));

			if (strlength > 1)
				cal.add(Calendar.MINUTE, NumberUtils.getInt(timeHmS[1]));

			if (strlength > 2)
				cal.add(Calendar.SECOND, NumberUtils.getInt(timeHmS[2]));
		}
		return cal.getTime();
	}

	/**
	 * Adds a number of days to a date.
	 */
	public static Date addDays(Date date, int days) {
		log.debugPrintCurrentMethodName();
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}

	/**
	 * Adds a number of days to a Calendar and it is returns new object.
	 */
	public static Calendar addHours(Calendar cal, int hours) {
		log.debugPrintCurrentMethodName();
		Calendar newCal = (Calendar) cal.clone();
		newCal.add(Calendar.HOUR, hours);
		return newCal;
	}

	/**
	 * Adds a number of days to a Calendar and it is returns new object.
	 */
	public static Calendar addDays(Calendar cal, int days) {
		log.debugPrintCurrentMethodName();
		Calendar newCal = (Calendar) cal.clone();
		newCal.add(Calendar.DATE, days);
		return newCal;
	}

	/**
	 * Adds a number of months to a Calendar and it is returns new object.
	 */
	public static Calendar addMonths(Calendar cal, int months) {
		log.debugPrintCurrentMethodName();
		Calendar newCal = (Calendar) cal.clone();
		newCal.add(Calendar.MONTH, months);
		return newCal;
	}

	/**
	 * Adds a number of years to a Calendar and it is returns new object.
	 */
	public static Calendar addYears(Calendar cal, int years) {
		log.debugPrintCurrentMethodName();
		Calendar newCal = (Calendar) cal.clone();
		newCal.add(Calendar.YEAR, years);
		return newCal;
	}

	/**
	 * Adds a number of days to a Calendar and it is returns new object.
	 */
	public static Calendar getNextSemiMonth(Calendar cal) {
		log.debugPrintCurrentMethodName();
		Calendar newCal = (Calendar) cal.clone();
		if (newCal.get(Calendar.DAY_OF_MONTH) < 16) {
			newCal.set(Calendar.DAY_OF_MONTH, 16);
		} else {
			newCal = DateUtil.addMonths(newCal, 1);
			newCal.set(Calendar.DAY_OF_MONTH, newCal.getActualMinimum(GregorianCalendar.DAY_OF_MONTH));
		}
		return newCal;
	}

	/**
	 * Adds a number of days to a Calendar and it is returns new object.
	 */
	// public static Calendar addHalfMonths(Calendar cal, int months)
	// {
	// Calendar newCal = (Calendar) cal.clone();
	// newCal.add(Calendar.MONTH, months);
	// return newCal;
	// }
	/**
	 * returns the date in different format string
	 *
	 * Keep on adding the formats at the top and give constant to each
	 *
	 * For date YYYYMMDD -> 2,mm/dd/yyyy -> 3
	 *
	 * @param mon
	 * @return
	 * @author venkat
	 * @throws DateException
	 */
	public static final String formatDate(Date date, int formatCode) {
		log.debugPrintCurrentMethodName();
		String strdt = null;
		if (formatCode == 1) {
			// yyyy-mm-dd
			strdt = new SimpleDateFormat(format1).format(date);
		} else if (formatCode == 2) {
			// yyyymmdd
			strdt = new SimpleDateFormat(format2).format(date);
		} else if (formatCode == 3) {
			// mm/dd/yyyy
			strdt = new SimpleDateFormat(format3).format(date);
		}
		if (formatCode == 4) {
			// yyyymmddHHmmss
			strdt = new SimpleDateFormat(format4).format(date);
		}
		if (formatCode == 5) {
			// hhmmss
			strdt = new SimpleDateFormat(format5).format(date);
		}
		if (formatCode == 6) {
			// MM/dd/yyyy hh:mm:ss a
			strdt = new SimpleDateFormat(format6).format(date);
		}
		if (formatCode == 7) {
			// MM/dd/yyyy hh:mm:ss a
			strdt = new SimpleDateFormat(format7).format(date);
		}

		if (formatCode == 8) {
			// MM/dd/yyyy hh:mm:ss a
			strdt = new SimpleDateFormat(format8).format(date);
		}
		if (formatCode == 9) {
			// MMddyy
			strdt = new SimpleDateFormat(format9).format(date);
		}
		if (formatCode == 10) {
			// yyMMdd
			strdt = new SimpleDateFormat(format10).format(date);
		}
		if (formatCode == 11) {
			// yyMMdd
			strdt = getCurrentCentury() + new SimpleDateFormat(format12).format(date);
		}

		if (formatCode == 13) {
			// ddMMyy
			strdt = new SimpleDateFormat(format13).format(date);
		}
		if (formatCode == 14) {
			// DDD
			strdt = new SimpleDateFormat(format14).format(date);
		}
		if (formatCode == 15) {
			// DDD
			strdt = new SimpleDateFormat(format15).format(date);
		}
		if (formatCode == 16) {
			// dd/MM/yyyy
			strdt = new SimpleDateFormat(format16).format(date);
		}
		if (formatCode == 18) {
			// MM-dd-yyyy
			strdt = new SimpleDateFormat(format18).format(date);
		}
		if (formatCode == 19) {
			// MM-dd-yyyy
			strdt = new SimpleDateFormat(format19).format(date);
		}
		return strdt;
	}

	/**
	 * returns the date in different format string
	 *
	 * Keep on adding the formats at the top and give constant to each
	 *
	 * For date YYYYMMDD -> 2,mm/dd/yyyy -> 3
	 *
	 * @param mon
	 * @return
	 * @author venkat
	 * @throws DateException
	 */
	public static final String formatDate(Date date, int formatCode, TimeZone timeZone) {
		log.debugPrintCurrentMethodName();
		String strdt = null;
		if (formatCode == 1) {
			// yyyy-mm-dd
			SimpleDateFormat dateFormat = new SimpleDateFormat(format1);
			dateFormat.setTimeZone(timeZone);
			strdt = dateFormat.format(date);
		} else if (formatCode == 2) {
			// yyyymmdd
			SimpleDateFormat dateFormat = new SimpleDateFormat(format2);
			dateFormat.setTimeZone(timeZone);
			strdt = dateFormat.format(date);
		} else if (formatCode == 3) {
			// mm/dd/yyyy
			SimpleDateFormat dateFormat = new SimpleDateFormat(format3);
			dateFormat.setTimeZone(timeZone);
			strdt = dateFormat.format(date);
		}
		if (formatCode == 4) {
			// yyyymmddhhmmss
			SimpleDateFormat dateFormat = new SimpleDateFormat(format4);
			dateFormat.setTimeZone(timeZone);
			strdt = dateFormat.format(date);
		}

		if (formatCode == 5) {
			// hhmmss
			SimpleDateFormat dateFormat = new SimpleDateFormat(format5);
			dateFormat.setTimeZone(timeZone);
			strdt = dateFormat.format(date);
		}

		if (formatCode == 6) {
			// MM/dd/yyyy HH:mm:ss:SSS
			SimpleDateFormat dateFormat = new SimpleDateFormat(format6);
			dateFormat.setTimeZone(timeZone);
			strdt = dateFormat.format(date);
		}
		return strdt;
	}

	/**
	 * returns the date in different format string
	 *
	 * Keep on adding the formats at the top and give constant to each
	 *
	 * For date YYYYMMDD -> 2,mm/dd/yyyy -> 3
	 *
	 * @param mon
	 * @return
	 * @author venkat
	 * @throws ParseException
	 * @throws DateException
	 */
	public static final Date formatString(String dateInStr, int formatCode) {
		log.debugPrintCurrentMethodName();
		Date strdt = null;
		try {
			if (formatCode == 1) {
				// yyyy-mm-dd
				strdt = new SimpleDateFormat(format1).parse(dateInStr);
			} else if (formatCode == 2) {
				// yyyymmdd
				strdt = new SimpleDateFormat(format2).parse(dateInStr);
			} else if (formatCode == 3) {
				// mm/dd/yyyy
				strdt = new SimpleDateFormat(format3).parse(dateInStr);
			}
			if (formatCode == 4) {
				// yyyymmddhhmmss
				strdt = new SimpleDateFormat(format4).parse(dateInStr);
			}
			if (formatCode == 7) {
				// yyyy-MM-dd HH:mm:ss Z
				strdt = new SimpleDateFormat(format7).parse(dateInStr);
			}

			if (formatCode == 9) {
				// mmddyy
				strdt = new SimpleDateFormat(format9).parse(dateInStr);
			}
			if (formatCode == 10) {
				// mmddyy
				strdt = new SimpleDateFormat(format10).parse(dateInStr);
			}
			if (formatCode == 17) {
				// yyyy-MM-dd'T'HH:mm:ss'Z'
				strdt = new SimpleDateFormat(format17).parse(dateInStr);
			}
			if (formatCode == 18) {
				// mm-dd-yyyy
				strdt = new SimpleDateFormat(format18).parse(dateInStr);
			}
		} catch (Exception e) {
			log.logError("Exception in formatString" + e.getMessage(), e);
		}
		return strdt;
	}

	public static final Date formatAppointmentDate(String dateInStr) {
		log.debugPrintCurrentMethodName();
		Date strdt = null;
		try {
			dateInStr = dateInStr.replaceAll("st |nd |rd |th ", " ");
			SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE,dd MMMM,yyyy");
			strdt = dateFormat.parse(dateInStr);
		} catch (Exception e) {
			log.logError("Exception in formatAppointmentDate" + e.getMessage(), e);
		}
		return strdt;
	}

	/**
	 * returns the date in different format string
	 *
	 * Keep on adding the formats at the top and give constant to each
	 *
	 * For date YYYYMMDD -> 2,mm/dd/yyyy -> 3
	 *
	 * @param mon
	 * @return
	 * @author venkat
	 * @throws ParseException
	 * @throws DateException
	 */
	public static final Date formatString(String dateInStr, int formatCode, TimeZone timeZone) {
		log.debugPrintCurrentMethodName();
		Date strdt = null;
		try {
			if (formatCode == 1) {
				// yyyy-mm-dd
				SimpleDateFormat dateFormat = new SimpleDateFormat(format1);
				dateFormat.setTimeZone(timeZone);
				strdt = dateFormat.parse(dateInStr);
			} else if (formatCode == 2) {
				// yyyymmdd

				SimpleDateFormat dateFormat = new SimpleDateFormat(format2);
				dateFormat.setTimeZone(timeZone);
				strdt = dateFormat.parse(dateInStr);
			} else if (formatCode == 3) {
				// mm/dd/yyyy
				SimpleDateFormat dateFormat = new SimpleDateFormat(format3);
				dateFormat.setTimeZone(timeZone);
				strdt = dateFormat.parse(dateInStr);
			}
			if (formatCode == 4) {
				// yyyymmddhhmmss
				SimpleDateFormat dateFormat = new SimpleDateFormat(format4);
				dateFormat.setTimeZone(timeZone);
				strdt = dateFormat.parse(dateInStr);
			}
		} catch (Exception e) {
			log.logError("Exception in formatString" + e.getMessage(), e);
		}
		return strdt;
	}

	public static Date getStartDate(Date date) {
		log.debugPrintCurrentMethodName();
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(GregorianCalendar.HOUR_OF_DAY, calendar.getActualMinimum(GregorianCalendar.HOUR_OF_DAY));
		calendar.set(GregorianCalendar.MINUTE, calendar.getActualMinimum(GregorianCalendar.MINUTE));
		calendar.set(GregorianCalendar.SECOND, calendar.getActualMinimum(GregorianCalendar.SECOND));
		calendar.set(GregorianCalendar.MILLISECOND, calendar.getActualMinimum(GregorianCalendar.MILLISECOND));
		return (Date) calendar.getTime().clone();
	}

	public static Calendar getStartCalendarDate(final Calendar inputCal) {
		log.debugPrintCurrentMethodName();
		Calendar dayStart = (Calendar) inputCal.clone();
		dayStart.set(GregorianCalendar.HOUR_OF_DAY, dayStart.getActualMinimum(GregorianCalendar.HOUR_OF_DAY));
		dayStart.set(GregorianCalendar.MINUTE, dayStart.getActualMinimum(GregorianCalendar.MINUTE));
		dayStart.set(GregorianCalendar.SECOND, dayStart.getActualMinimum(GregorianCalendar.SECOND));
		dayStart.set(GregorianCalendar.MILLISECOND, dayStart.getActualMinimum(GregorianCalendar.MILLISECOND));
		return dayStart;
	}

	public static Calendar getEndCalendarDate(final Calendar inputCal) {
		log.debugPrintCurrentMethodName();
		Calendar dayEnd = (Calendar) inputCal.clone();
		dayEnd.set(GregorianCalendar.HOUR_OF_DAY, dayEnd.getActualMaximum(GregorianCalendar.HOUR_OF_DAY));
		dayEnd.set(GregorianCalendar.MINUTE, dayEnd.getActualMaximum(GregorianCalendar.MINUTE));
		dayEnd.set(GregorianCalendar.SECOND, dayEnd.getActualMaximum(GregorianCalendar.SECOND));
		dayEnd.set(GregorianCalendar.MILLISECOND, dayEnd.getActualMaximum(GregorianCalendar.MILLISECOND));
		return dayEnd;
	}

	public static Date getEndDate(Date date) {
		log.debugPrintCurrentMethodName();
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.set(GregorianCalendar.HOUR_OF_DAY, calendar.getActualMaximum(GregorianCalendar.HOUR_OF_DAY));
		calendar.set(GregorianCalendar.MINUTE, calendar.getActualMaximum(GregorianCalendar.MINUTE));
		calendar.set(GregorianCalendar.SECOND, calendar.getActualMaximum(GregorianCalendar.SECOND));
		calendar.set(GregorianCalendar.MILLISECOND, calendar.getActualMaximum(GregorianCalendar.MILLISECOND));
		return (Date) calendar.getTime().clone();
	}

	public static Date getEndDate(Date date, TimeZone timeZone) {
		log.debugPrintCurrentMethodName();
		GregorianCalendar calendar = new GregorianCalendar(timeZone);
		calendar.setTime(date);
		calendar.set(GregorianCalendar.HOUR_OF_DAY, calendar.getActualMaximum(GregorianCalendar.HOUR_OF_DAY));
		calendar.set(GregorianCalendar.MINUTE, calendar.getActualMaximum(GregorianCalendar.MINUTE));
		calendar.set(GregorianCalendar.SECOND, calendar.getActualMaximum(GregorianCalendar.SECOND));
		calendar.set(GregorianCalendar.MILLISECOND, calendar.getActualMaximum(GregorianCalendar.MILLISECOND));
		return (Date) calendar.getTime().clone();
	}

	// Change a date to GMT from a given timezone
	public static Date toGmtFromZone(Date date, String entityTimeZone) {
		log.debugPrintCurrentMethodName();
		String fromZone = "GMT" + entityTimeZone;
		TimeZone tz = TimeZone.getTimeZone(fromZone);
		return new Date(date.getTime() - tz.getRawOffset());
	}

	// Change agmt date in another timezone
	public static Date toZoneFromGmt(Date date, String zone) {
		log.debugPrintCurrentMethodName();
		TimeZone tz = TimeZone.getTimeZone(zone);
		Calendar first = Calendar.getInstance(tz);
		first.setTimeInMillis(date.getTime());

		Calendar output = Calendar.getInstance(TimeZone.getTimeZone("GMT+00:00"));
		output.set(Calendar.YEAR, first.get(Calendar.YEAR));
		output.set(Calendar.MONTH, first.get(Calendar.MONTH));
		output.set(Calendar.DAY_OF_MONTH, first.get(Calendar.DAY_OF_MONTH));
		output.set(Calendar.HOUR_OF_DAY, first.get(Calendar.HOUR_OF_DAY));
		output.set(Calendar.MINUTE, first.get(Calendar.MINUTE));
		output.set(Calendar.SECOND, first.get(Calendar.SECOND));
		output.set(Calendar.MILLISECOND, first.get(Calendar.MILLISECOND));

		return output.getTime();
	}

	public static String calendarToReadableString(Calendar cal) {
		log.debugPrintCurrentMethodName();
		if (cal != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z");
			sdf.setTimeZone(cal.getTimeZone());
			return sdf.format(cal.getTime());
		} else {
			return "NULL";
		}
	}

	public static java.util.Date convertTimeZone(java.util.Date date, TimeZone timeZone) throws ParseException {
		log.debugPrintCurrentMethodName();
		final SimpleDateFormat format = new SimpleDateFormat(format4);
		format.setTimeZone(timeZone);
		String dateTimestampFormat = format.format(date);
		SimpleDateFormat reverseFormat = new SimpleDateFormat(format4);
		return reverseFormat.parse(dateTimestampFormat);
		// return new java.util.Date(
		// date.getTime()
		// + ((timeZone.getRawOffset() + timeZone.getDSTSavings()) -
		// (TimeZone.getDefault().getRawOffset() + TimeZone
		// .getDefault().getDSTSavings())));
	}

	public static java.util.Date convertFromTimeZone(java.util.Date date, TimeZone timeZone) throws ParseException {
		log.debugPrintCurrentMethodName();
		final SimpleDateFormat format = new SimpleDateFormat(format4);
		String dateTimestampFormat = format.format(date);
		SimpleDateFormat reverseFormat = new SimpleDateFormat(format4);
		reverseFormat.setTimeZone(timeZone);
		return reverseFormat.parse(dateTimestampFormat);
		// return new java.util.Date(
		// date.getTime()
		// + ((timeZone.getRawOffset() + timeZone.getDSTSavings()) -
		// (TimeZone.getDefault().getRawOffset() + TimeZone
		// .getDefault().getDSTSavings())));
	}

	// Using when we getting timestamp from database
	public static Calendar getCalendarFromSQLTimestamp(java.sql.Timestamp timestamp) {
		log.debugPrintCurrentMethodName();
		if (timestamp == null) {
			return null;
		}
		java.util.Date utilDate = new java.util.Date(timestamp.getTime());
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(utilDate);
		return calendar;
	}

	// Using when we getting days difference between two dates yyyymmdd
	public static long getDateDiffInDays(String fromDateStr, String toDateStr) {
		log.debugPrintCurrentMethodName();
		Date fromDate = null;
		Date toDate = null;
		long daysDiff = 0;
		try {
			fromDate = new SimpleDateFormat(format16).parse(fromDateStr);
			toDate = new SimpleDateFormat(format16).parse(toDateStr);
			daysDiff = (toDate.getTime() - fromDate.getTime()) / millisPerDay;
		} catch (Exception e) {
			log.logError("Exception in getDateDiffInDays" + e.getMessage(), e);
		}
		return daysDiff;
	}

	public static final String calcDateFromJulian(int daynumber) {
		log.debugPrintCurrentMethodName();
		// *******Needs some logic to handle when year changes********
		String schdlDate = null;
		try {
			Calendar cal = new GregorianCalendar();
			cal.set(Calendar.DAY_OF_YEAR, daynumber);
			SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyy");
			schdlDate = dateFormat.format(cal.getTime());
		} catch (IllegalArgumentException e) {
			log.logError("IllegalArgumentException in calcDateFromJulian" + e.getMessage(), e);
		} catch (Exception e) {
			log.logError("Exception in calcDateFromJulian" + e.getMessage(), e);
		}
		// log.logInfo("The schdlDate calculated is :" + schdlDate);
		return schdlDate;

	}

	public static int getCurrentCentury() {
		log.debugPrintCurrentMethodName();
		Calendar cal = new GregorianCalendar();
		int year = cal.get(java.util.Calendar.YEAR);
		int century = 0;

		century = year % 100 == 0 ? (year / 100) : (year / 100) + 1;

		return century;

	}

	public static final Date getDateFromJulian(String julianDate) {
		log.debugPrintCurrentMethodName();
		Calendar cal = Calendar.getInstance();

		String changeOfCentury = julianDate.substring(0, 1);

		int currentYear = cal.get(Calendar.YEAR);
		String year = "" + currentYear;

		if (changeOfCentury.equals("0")) {
			year = year.substring(0, 2);
		} else {
			year = "" + (Integer.parseInt(year.substring(0, 2)) + 1);
		}

		year = year + julianDate.substring(1, 3);
		cal.set(Calendar.YEAR, Integer.parseInt(year));

		cal.set(Calendar.DAY_OF_YEAR, Integer.parseInt(julianDate.substring(3)));

		Date date = cal.getTime();

		return date;
	}

	public static String getCurrentDateInRequiredFormatwithoutTime(String formatString) {
		log.debugPrintCurrentMethodName();
		Date date = new Date();
		String modifiedDate = new SimpleDateFormat(formatString).format(date);

		return modifiedDate;
	}

	public static Boolean isTodayLastDayOfMonth() {
		log.debugPrintCurrentMethodName();
		Date today = new Date();

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);

		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DATE, -1);

		Date lastDayOfMonth = calendar.getTime();

		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (compareDates(sdf.format(today), sdf.format(lastDayOfMonth), 1) == 0)
			return true;
		else
			return false;

	}

	public static Boolean isTodayLastDayOfMonth(Date today) {
		log.debugPrintCurrentMethodName();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(today);

		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DATE, -1);

		Date lastDayOfMonth = calendar.getTime();

		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (compareDates(sdf.format(today), sdf.format(lastDayOfMonth), 1) == 0)
			return true;
		else
			return false;

	}

	// dateFormat: mm/dd/yyyy etc.
	public static Long getDaysBetweenDates(String dateFormat, String inputDate1, String inputDate2) {
		log.debugPrintCurrentMethodName();
		SimpleDateFormat myFormat = new SimpleDateFormat(dateFormat);
		Long numDays = 0l;

		try {
			Date date1 = myFormat.parse(inputDate1);
			Date date2 = myFormat.parse(inputDate2);
			Long diff = date2.getTime() - date1.getTime();
			numDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			log.logError("Exception in getDaysBetweenDates " + e.getMessage(), e);
		}
		return numDays;
	}

	// Months shortcut names in a year
	private static final List<String> listOfMonthNames = Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul",
			"Aug", "Sep", "Oct", "Nov", "Dec");

	public static String getMonthName(int monthSequenceNumber) {
		log.debugPrintCurrentMethodName();
		if (monthSequenceNumber != 0)
			return listOfMonthNames.get(monthSequenceNumber - 1);
		else
			return "";
	}

	public static String getLastDayOfTheMonth(String date) {
		log.debugPrintCurrentMethodName();
		String[] dates = date.split("-");
		String mon = dates[0];
		String day = dates[1];
		String year = dates[2];

		log.logInfo("i am in");

		if (mon.equals("01") || mon.equals("03") || mon.equals("05") || mon.equals("07") || mon.equals("08")
				|| mon.equals("10") || mon.equals("12"))

		{
			log.logInfo("in last date as 31 ");

			day = "31";

		}
		if (mon.equals("02")) {
			log.logInfo("in last date as 28 or 29 ");

			if (Integer.parseInt(year) % 4 == 0) {
				day = "29";
			} else
				day = "28";
		}
		if (mon.equals("04") || mon.equals("06") || mon.equals("09") || mon.equals("11")) {
			day = "30";
			log.logInfo("in last date as 30 ");

		}
		log.logInfo("MONTH" + mon);
		log.logInfo("DAY" + day);
		log.logInfo("DAY" + year);

		date = mon + "-" + day + "-" + year;

		return date;

	}

	public static Long getHoursDiff(String d1, String d2, String dateFormate) {
		log.debugPrintCurrentMethodName();
		SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormate);
		long second = 1000l;
		long minute = 60l * second;
		long hour = 60l * minute;
		Long diff = 0l;
		try {
			// parsing input
			Date date1 = dateFormat.parse(d1);
			Date date2 = dateFormat.parse(d2);
			diff = date2.getTime() - date1.getTime();
		} catch (Exception e) {
			log.logError("Exception in getHoursDiff " + e.getMessage(), e);
		}

		return (diff / hour);
	}

	public static String otherdayDate(String format, int difference) {
		log.debugPrintCurrentMethodName();
		java.util.Date dt = new java.util.Date();
		if (dt.getDate() > 27) {
			difference += 5;
		}
		Calendar c = Calendar.getInstance();

		c.setTime(dt);
		c.add(Calendar.DATE, difference);

		dt = c.getTime();
		SimpleDateFormat ft = new SimpleDateFormat(format);
		return ft.format(dt);
	}

	public static boolean isFutureDate(String date) {
		log.debugPrintCurrentMethodName();
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy");
		try {
			java.util.Date date1 = sdf1.parse(date);
			log.logInfo("date formatted" + date1);
			if (DateUtil.compareDates(DateUtil.formatDate(date1, 3), DateUtil.formatDate(new java.util.Date(), 3),
					3) == 1) {
				return true;
			} else {
				return false;
			}
		} catch (ParseException e) {
			log.logError("ParseException in isFutureDate" + e.getMessage(), e);
			return false;
		}

	}

	public static java.sql.Date getSQLDateFromStringDateMMDDYYYY(String Date) throws ParseException {
		log.debugPrintCurrentMethodName();
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd-yyyy");
		java.util.Date tmpDate = sdf1.parse(Date);
		java.sql.Date sqlDate = new java.sql.Date(tmpDate.getTime());
		return sqlDate;
	}

}
