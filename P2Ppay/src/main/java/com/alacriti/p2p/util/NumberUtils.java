package com.alacriti.p2p.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Random;
import java.util.regex.Pattern;

import com.alacriti.p2p.constants.Constants;
import com.alacriti.p2p.log.impl.AppLogger;

public class NumberUtils {
	private static final AppLogger log = LogUtil.getLogger(NumberUtils.class);

	public static double getDouble(String numStr) {
		log.debugPrintCurrentMethodName();
		try {
			return Double.valueOf(StringUtil.noNullTrim(numStr));
		} catch (NumberFormatException e) {
			log.logError("NumberFormatException in getDouble " + e.getMessage(), e);
			return Constants.DEFAULT_DOUBLE;
		}
	}

	public static int getInt(String numStr) {
		log.debugPrintCurrentMethodName();
		try {
			return Integer.valueOf(StringUtil.noNullTrim(numStr));
		} catch (NumberFormatException e) {
			log.logError("NumberFormatException in getInt " + e.getMessage(), e);
			return Constants.DEFAULT_INT;
		}
	}

	public static long getLong(String numStr) {
		log.debugPrintCurrentMethodName();
		try {
			return Long.valueOf(StringUtil.noNullTrim(numStr));
		} catch (NumberFormatException e) {
			log.logError("NumberFormatException in getLong " + e.getMessage(), e);
			return Constants.DEFAULT_LONG;
		}
	}

	public static short getShort(String numStr) {
		log.debugPrintCurrentMethodName();
		try {
			return Short.valueOf(StringUtil.noNullTrim(numStr));
		} catch (NumberFormatException e) {
			log.logError("NumberFormatException in getShort " + e.getMessage(), e);
			return Constants.DEFAULT_SHORT;
		}
	}

	public static long getLong(Object object) {
		log.debugPrintCurrentMethodName();

		if (object == null) {
			return Constants.DEFAULT_LONG;
		}

		if (object instanceof String) {
			return getLong((String) object);
		}

		if (object instanceof Long) {
			return (Long) object;
		}

		return Constants.DEFAULT_LONG;
	}

	public static int getInt(Object object) {
		log.debugPrintCurrentMethodName();

		if (object == null) {
			return Constants.DEFAULT_INT;
		}

		if (object instanceof String) {
			return getInt((String) object);
		}

		if (object instanceof Integer) {
			return (Integer) object;
		}

		return Constants.DEFAULT_INT;
	}

	public static BigDecimal getAmountBigDecimal(int precision, boolean signBit, long l) {
		log.debugPrintCurrentMethodName();
		BigDecimal bigDecimal = new BigDecimal(l);
		bigDecimal = bigDecimal.movePointLeft(precision);
		if (!signBit)
			bigDecimal = bigDecimal.negate();
		return bigDecimal;
	}

	public static BigDecimal getPercentageBigDecimal(boolean indicator, long integerVal, long decimalVal) {
		log.debugPrintCurrentMethodName();
		BigDecimal integerValDecimal = new BigDecimal(integerVal);
		BigDecimal decimalValDecimal = new BigDecimal(decimalVal);
		decimalValDecimal = decimalValDecimal.movePointLeft(decimalValDecimal.precision());
		BigDecimal returnVal = integerValDecimal.add(decimalValDecimal);
		if (!indicator) {
			returnVal = returnVal.negate();
		}

		return returnVal;
	}

	public static BigDecimal getPercentageBigDecimal(boolean indicator, String integerVal, String decimalVal) {
		log.debugPrintCurrentMethodName();
		BigDecimal bigDecimal = new BigDecimal((indicator ? "+" : "-") + integerVal + "." + decimalVal);
		return bigDecimal;
	}

	public static boolean isNumeric(String s) {
		log.debugPrintCurrentMethodName();
		return Pattern.matches("[0-9]+", s);
	}

	public static boolean isDecimal(String str) {
		log.debugPrintCurrentMethodName();
		try {
			//double d = Double.parseDouble(str);
		} catch (NumberFormatException e) {
			log.logError("NumberFormatException in isDecimal " + e.getMessage(), e);
			return false;
		}
		return true;
	}

	public static BigDecimal getBigdecimal(String stringNumber) {
		log.debugPrintCurrentMethodName();
		try {
			stringNumber = stringNumber.trim();
			BigDecimal money = new BigDecimal(stringNumber.replaceAll(",", ""));
			return money;
		} catch (NumberFormatException e) {
			log.logError("NumberFormatException in getBigdecimal " + e.getMessage(), e);
			return new BigDecimal("0.00");
		}

	}

	public static boolean isLong(String str) {
		log.debugPrintCurrentMethodName();
		if (str == null) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if (Character.isDigit(str.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	public static BigDecimal getBigDecimalUptoTwoDecimalPlaces(BigDecimal num) {
		log.debugPrintCurrentMethodName();

		return num.setScale(2, BigDecimal.ROUND_HALF_EVEN);

	}

	public static int randInt(int min, int max) {
		log.debugPrintCurrentMethodName();
		Random rand = new Random();

		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	public static String amountFormatUSD(String d) {
		log.debugPrintCurrentMethodName();

		NumberFormat formatter = NumberFormat.getCurrencyInstance();
		return formatter.format(d) + "";
	}

	public static String amountFormatUSDForMail(BigDecimal amt) {
		log.debugPrintCurrentMethodName();
		return NumberFormat.getCurrencyInstance().format(amt);
	}

}
