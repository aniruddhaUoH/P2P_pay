package com.alacriti.p2p.util;

import java.math.BigDecimal;

import com.alacriti.p2p.log.impl.AppLogger;


public class StringFormats {
	/** The static logger */
	private static final AppLogger log = LogUtil.getLogger(StringFormats.class);

	/** This will be used by the pad space function */
	private String blankLine;

	/**
	 * Simple Constructor
	 */
	public StringFormats() {

	}

	/**
	 * Simple Constructor
	 */
	public StringFormats(int max) {
		log.debugPrintCurrentMethodName();
		StringBuilder buffer = new StringBuilder(max);

		for (int i = 0; i < max; i++) {
			buffer.append(" ");
		}

		blankLine = buffer.toString();
	}

	/**
	 * Simple Constructor
	 */
	public StringFormats(int max, char padChar) {
		log.debugPrintCurrentMethodName();
		StringBuilder buffer = new StringBuilder(max);

		for (int i = 0; i < max; i++) {
			buffer.append(padChar);
		}

		blankLine = buffer.toString();
	}

	/**
	 * this method eliminates the regular need to check for null or incorrect
	 * string before parsing for int
	 * 
	 * @param s
	 * @return
	 */
	public static int getInt(String s) {
		log.debugPrintCurrentMethodName();
		if (s == null) {
			return 0;
		}

		try {
			return Integer.parseInt(s);
		} catch (Exception e) {
			log.logError("Exception in getInt " + e.getMessage(), e);
			return 0;
		}
	}

	/**
	 * This method checks the string for null and then returns the trimmed
	 * string if not null and an empty string if null
	 * 
	 * @param s
	 * @return
	 */
	public static String getString(String s) {
		log.debugPrintCurrentMethodName();
		if (s == null) {
			return "";
		} else if ("null".equalsIgnoreCase(s.trim())) {
			return "";
		}

		return s.trim();
	}

	/**
	 * this method eliminates the regular need to check for null or incorrect
	 * string before parsing for long
	 * 
	 * @param s
	 * @return
	 */
	public static long getLong(String s) {
		log.debugPrintCurrentMethodName();
		if (s == null) {
			return 0;
		}

		try {
			return Long.parseLong(s);
		} catch (Exception e) {
			log.logError("Exception in getLong " + e.getMessage(), e);
			return 0;
		}
	}

	/**
	 * This method is for padding strings with characters
	 */
	public String pad(String aString, int reqLen, boolean padAtEnd) {
		log.debugPrintCurrentMethodName();
		String retVal;
		String aStr = getString(aString);
		int len = aStr.length();

		if (len > reqLen) {
			log.logDebug("The padString is truncating ", aStr, " to size ", reqLen);
			retVal = aStr.substring(0, reqLen);
		} else if ((blankLine == null) || ((reqLen - len) > blankLine.length())) {
			throw new IllegalArgumentException("The StringFormat object cannot pad as the length specified is illegal");
		} else if (padAtEnd) {
			retVal = aStr + blankLine.substring(0, reqLen - len);
		} else {
			retVal = blankLine.substring(0, reqLen - len) + aStr;
		}

		return retVal;
	}

	public static String convertBigDecimalToFxlAmount(BigDecimal bigDecimal) {
		log.debugPrintCurrentMethodName();
		String gsmAmt = "";
		if (bigDecimal == null) {
			bigDecimal = new BigDecimal("0.00");
		}
		try {
			int precision = bigDecimal.scale();
			String signBit = bigDecimal.signum() == -1 ? "-" : "+";
			String value = "";
			if (bigDecimal.signum() == -1) {
				bigDecimal = bigDecimal.negate();
			}
			value = bigDecimal.unscaledValue().toString() + "";
			value = StringUtil.pad(value, 18, false, '0');
			gsmAmt = precision + signBit + value;
		} catch (Exception e) {
			log.logError("Exception in convertBigDecimalToFxlAmount " + e.getMessage(), e);
		}

		return gsmAmt;
	}

	public static String convertBigDecimalAsFxlPercentage(BigDecimal bigDecimal) {
		log.debugPrintCurrentMethodName();
		return convertBigDecimalAsFxlPercentage(bigDecimal, null);
	}

	public static String convertBigDecimalAsFxlPercentage(BigDecimal bigDecimal, String defaultVal) {
		log.debugPrintCurrentMethodName();
		String gsmPecentageStr = "";
		String indicator = "";
		String integerVal = "";
		String decimalVal = "";
		try {
			if (bigDecimal == null && !StringUtil.isEmpty(defaultVal)) {
				bigDecimal = new BigDecimal(defaultVal);
			}
			if (bigDecimal.signum() == -1) {
				indicator = bigDecimal.signum() == -1 ? "-" : "+";
				bigDecimal = bigDecimal.negate();
				integerVal = bigDecimal.longValue() + "";
				integerVal = StringUtil.pad(integerVal, 8, false, '0');
				BigDecimal deciBD = bigDecimal.subtract(new BigDecimal(bigDecimal.longValue()));
				String decimalStr = deciBD.stripTrailingZeros().toPlainString();
				decimalStr = decimalStr.substring(2);
				decimalVal = StringUtil.pad(decimalStr, 10, true, '0');
			} else {
				indicator = bigDecimal.signum() == -1 ? "-" : "+";
				integerVal = bigDecimal.longValue() + "";
				integerVal = StringUtil.pad(integerVal, 8, false, '0');
				BigDecimal deciBD = bigDecimal.subtract(new BigDecimal(bigDecimal.longValue()));
				String decimalStr = deciBD.stripTrailingZeros().toPlainString();
				decimalStr = decimalStr.substring(2);
				decimalVal = StringUtil.pad(decimalStr, 10, true, '0');
			}
			gsmPecentageStr = indicator + integerVal + decimalVal;
		} catch (Exception e) {
			log.logError("Exception in convertBigDecimalAsFxlPercentage " + e.getMessage(), e);
		}
		return gsmPecentageStr;
	}

	public static BigDecimal convertFxlAmountAsBigDecimal(String gsmAmt) {
		log.debugPrintCurrentMethodName();
		try {
			if (!StringUtil.isEmpty(gsmAmt)) {
				int precisionCnt = Integer.parseInt(gsmAmt.substring(0, 1));
				boolean amtFlag = gsmAmt.substring(1, 2).equals("+") ? true : false;
				long value = Long.parseLong(gsmAmt.substring(2, 20));
				return NumberUtils.getAmountBigDecimal(precisionCnt, amtFlag, value);
			} else
				return null;

		} catch (Exception e) {
			log.logError("Exception in convertFxlAmountAsBigDecimal " + e.getMessage(), e);
			return null;
		}
	}

	public static BigDecimal convertFxlPercentageAsBigDecimal(String gsmParcent) {
		log.debugPrintCurrentMethodName();
		try {
			if (!StringUtil.isEmpty(gsmParcent)) {
				boolean amtFlag = gsmParcent.substring(0, 1).equals("+") ? true : false;
				String integerVal = gsmParcent.substring(1, 9);
				String decimalVal = gsmParcent.substring(9, 18);
				return NumberUtils.getPercentageBigDecimal(amtFlag, integerVal, decimalVal);
			} else
				return null;

		} catch (Exception e) {
			log.logError("Exception in convertFxlPercentageAsBigDecimal " + e.getMessage(), e);
			return null;
		}
	}

	public static String trimString(String aString, int reqLen) {
		log.debugPrintCurrentMethodName();
		String aStr = getString(aString);
		int len = aStr.length();

		if (len > reqLen) {
			aStr = aStr.substring(0, reqLen);
		}
		return aStr;
	}

	/**
	 * this method eliminates the regular need to check for null or incorrect
	 * string before parsing for double
	 * 
	 * @param s
	 * @return
	 */
	public static double getDouble(String s) {
		log.debugPrintCurrentMethodName();
		if (s == null) {
			return 0.0;
		}

		try {
			return Double.parseDouble(s);
		} catch (Exception e) {
			log.logError("Exception in getDouble " + e.getMessage(), e);
			return 0.0;
		}
	}

	public static String convertAmtToCBAmtFormat(String amt, String sign) {
		log.debugPrintCurrentMethodName();
		String convertedAmt = null;
		String[] comp = null;
		String amtField = null;
		StringFormats sf = new StringFormats(1000, '0');
		StringFormats spaces = new StringFormats(1000);
		int inputPrec = 0;

		try {
			if (amt.contains(".")) {
				comp = amt.split("\\.");
			}

			if (comp == null)
				inputPrec = 0;
			else if (comp.length == 1)
				inputPrec = 0;
			else if (comp.length > 2)
				throw new Exception("Invalid input format for the input string amt field");

			else
				inputPrec = comp[1].length();

			String prec = inputPrec + "";
			if (comp == null)
				amtField = sf.pad(amt, 18, false);
			else if (comp.length == 2)
				amtField = sf.pad(comp[0] + comp[1], 18, false);
			else if (comp.length == 1)
				amtField = sf.pad(comp[0], 18, false);

			String space = spaces.pad("", 2, true);
			convertedAmt = prec + sign + amtField + space;

			return convertedAmt;

		} catch (Exception e) {
			log.logError("Exception in convertAmtToCBAmtFormat " + e.getMessage(), e);
			return amt;
		}
	}

	public static String convertAmtToString(BigDecimal bigDecimal, int decimalPart) {
		log.debugPrintCurrentMethodName();
		String gsmAmt = "";
		if (bigDecimal != null) {
			try {
				int precision = bigDecimal.scale();
				String signBit = bigDecimal.signum() == -1 ? "-" : "+";
				String value = "";
				if (bigDecimal.signum() == -1) {
					bigDecimal = bigDecimal.negate();
				}
				value = bigDecimal.unscaledValue().toString() + "";
				value = StringUtil.pad(value, decimalPart, false, '0');
				gsmAmt = precision + signBit + value;
			} catch (Exception e) {
				log.logError("Exception in  convertAmtToString " + e.getMessage(), e);
			}

		}
		return gsmAmt;
	}

	public static String convertAmtToString(BigDecimal bigDecimal) {
		log.debugPrintCurrentMethodName();
		// FE message the amount field length is 20 (excluding sign &
		// precision),
		// for BE message amount field
		// length is 18 (excluding sign & precision)
		// NOTE: Below the default decimalPart is specified for FE messages
		return convertAmtToString(bigDecimal, 20);
	}

	public static String convertCBAmtToAmtFormat(String amt) {
		log.debugPrintCurrentMethodName();
		try {
			log.logInfo("Converting CB Amt to AmtFormat.....");
			String lamt = amt.trim();
			int prec = Integer.parseInt(lamt.substring(0, 1));
			String sign = lamt.substring(1, 2);
			String amtField = lamt.substring(2).trim();
			boolean signFlag = sign.equals("+") ? true : false;
			BigDecimal bdAmt = NumberUtils.getAmountBigDecimal(prec, signFlag, Long.parseLong(amtField));
			// Double amount = Double.parseDouble(amtField) / Math.pow(10,
			// prec);
			return bdAmt.toPlainString();
		} catch (Exception e) {
			log.logError("Exception in convertCBAmtToAmtFormat " + e.getMessage(), e);
			return null;
		}
	}

	public static String convertCBRateToRateFormat(String rate) {
		log.debugPrintCurrentMethodName();
		try {
			log.logInfo("Converting CB Rate to RateFormat.....");
			String bd = rate.substring(1, 9).trim();
			String ad = rate.substring(9).trim();
			return Double.parseDouble(bd + "." + ad) + "";

		} catch (Exception e) {
			log.logError("Exception in convertCBRateToRateFormat " + e.getMessage(), e);
			return null;
		}
	}

	public static String convertInttypeToSignedIntegerFormat(int fieldLengthIncludeSignBit, int intValue) {
		log.debugPrintCurrentMethodName();
		try {
			String signedIntFormat = intValue > -1 ? "+" : "-";
			signedIntFormat = signedIntFormat
					.concat(StringUtil.pad(String.valueOf(intValue), fieldLengthIncludeSignBit - 1, false, '0'));
			log.logDebug("converted :", intValue, ": value to ", signedIntFormat);
			return signedIntFormat;

		} catch (Exception e) {
			log.logError("Exception in convertInttypeToSignedIntegerFormat " + e.getMessage(), e);
			return null;
		}
	}

	public static int convertSignedIntegerFormatToIntType(int fieldLengthIncludeSignBit, String intStr) {
		log.debugPrintCurrentMethodName();
		try {
			String endIndexRequest = intStr;
			endIndexRequest = endIndexRequest.startsWith("+") ? endIndexRequest.substring(1) : endIndexRequest;
			int intValue = Integer.parseInt(endIndexRequest);
			log.logDebug("converted :", intStr, ": value to :", intValue);
			return intValue;
		} catch (Exception e) {
			log.logError("Exception in convertSignedIntegerFormatToIntType " + e.getMessage(), e);
			return 0;
		}
	}

	public static String zeroPad(long number, int width) {
		log.debugPrintCurrentMethodName();
		StringBuilder result = new StringBuilder("");
		for (int i = 0; i < width - Long.toString(number).length(); i++)
			result.append("0");
		result.append(Long.toString(number));
		return result.toString();
	}

}
