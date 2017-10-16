package com.alacriti.p2p.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.alacriti.p2p.constants.Constants;
import com.alacriti.p2p.log.impl.AppLogger;

public class StringUtil {
	private static final AppLogger log = LogUtil.getLogger(StringUtil.class);

	private static DateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");

	private static Random random = new Random();

	public static String pad(String aString, int reqLen, boolean padAtEnd, char padWith) {
		log.debugPrintCurrentMethodName();
		StringFormats format = new StringFormats(reqLen, padWith);

		String retVal = format.pad(aString, reqLen, padAtEnd);
		return retVal;
	}
	public static String concatWithTokens(String token, boolean noTrim, String... stringsToConcat) {
		log.debugPrintCurrentMethodName();
		String finalString = "";

		for (String string : stringsToConcat) {
			finalString += (isEmpty(finalString) ? "" : token) + (noTrim ? string : noNullTrim(string));
		}

		return finalString;
	}

	public static String getStringFromBytes(byte[] byteData) {
		log.debugPrintCurrentMethodName();
		if (byteData == null)
			return null;
		else
			return new String(byteData);
	}

	public static String concatWithTokens(String token, String... stringsToConcat) {
		log.debugPrintCurrentMethodName();
		return concatWithTokens(token, true, stringsToConcat);
	}

	public static String getInUpperCase(String str) {
		log.debugPrintCurrentMethodName();
		return noNullTrim(str).toUpperCase();
	}

	public static boolean isEmpty(String str) {
		log.debugPrintCurrentMethodName();
		return (str == null) || (str.trim().length() == 0);
	}

	public static String noNullTrim(String str) {
		log.debugPrintCurrentMethodName();
		return isEmpty(str) ? Constants.EMPTY_STRING : str.trim();
	}

	public static String noNATrim(String str) {
		return isEmpty(str) ? Constants.NA_STRING : str.trim();
	}

	public static String noNull(String str) {
		log.debugPrintCurrentMethodName();
		return str == null ? Constants.EMPTY_STRING : str;
	}

	public static String toString(Object obj) {
		log.debugPrintCurrentMethodName();
		return obj == null ? Constants.EMPTY_STRING : obj.toString();
	}

	public static String getStringRepeated(String str, int repeatCount) {
		log.debugPrintCurrentMethodName();
		String strRep = Constants.EMPTY_STRING;

		if (isEmpty(str)) {
			return strRep;
		}

		strRep = str;

		for (int i = 0; i <= repeatCount; i++) {
			strRep = concatWithTokens(strRep, false, str);
		}

		return strRep;
	}

	private static String replaceAll(String input, String stringToReplace, String replacement) {
		log.debugPrintCurrentMethodName();

		if (input != null && input.indexOf(stringToReplace) > -1)
			return input.replaceAll(stringToReplace, replacement);
		return input;
	}

	public static String xmlEncode(String inputString) {
		log.debugPrintCurrentMethodName();
		String localInputString = inputString;
		localInputString = replaceAll(localInputString, "&", "&amp;");
		localInputString = replaceAll(localInputString, "<", "&lt;");
		localInputString = replaceAll(localInputString, ">", "&gt;");
		localInputString = replaceAll(localInputString, "\"", "&quot;");
		localInputString = replaceAll(localInputString, "\'", "&#39;");
		return localInputString;
	}

	public static String xmlEncodeForSidebuyDeals(String inputString) {
		log.debugPrintCurrentMethodName();
		String localInputString = inputString;
		localInputString = replaceAll(localInputString, "&lt;span&gt;", "");
		localInputString = replaceAll(localInputString, "&lt;/span&gt;", "");
		return localInputString;
	}

	public static boolean areEqual(String strA, String strB) {
		log.debugPrintCurrentMethodName();
		return areEqual(strA, strB, false);
	}

	public static boolean areEqual(String strA, String strB, boolean isCaseSensitive) {
		log.debugPrintCurrentMethodName();
		String str1 = "";
		String str2 = "";
		str1 = noNullTrim(strA);
		str2 = noNullTrim(strB);
		return isCaseSensitive ? str1.equals(str2) : str1.equalsIgnoreCase(str2);
	}

	/**
	 * Compares <code>strA</code> with <code>strB</code>.
	 * 
	 * @param isRequired
	 *            Decides whether the method is required to be performed or not
	 * @param strA
	 * @param strB
	 * @return
	 */
	public static boolean areEqual(boolean isRequired, String strA, String strB) {

		log.debugPrintCurrentMethodName();
		return isRequired ? noNullTrim(strA).equalsIgnoreCase(noNullTrim(strB)) : false;
	}

	public static String[] split(String element) {
		log.debugPrintCurrentMethodName();
		log.logInfo("element : " + element);

		if (element != null && !element.isEmpty() && element.contains("/")) {
			return element.split("/");
		} else {
			return null;
		}
	}

	public static String[] split(String src, String token) {
		log.debugPrintCurrentMethodName();
		// log.logInfo("element : " + src);

		if (src != null && !src.isEmpty()) {
			return src.split(token);
		} else {
			return null;
		}
	}

	public static String trimOrSetDefault(String str, String defailtVal) {
		log.debugPrintCurrentMethodName();
		return isEmpty(str) ? defailtVal : str.trim();
	}

	// method to return string for the given limit
	public static String truncateString(String str, int val) {
		log.debugPrintCurrentMethodName();
		if (str != null && str.length() > val) {
			return str.substring(0, val);
		}
		return str;
	}

	// this method sanitizes and makes the filename unique with timestamp
	public static String createFileName(String rawFileName) {
		log.debugPrintCurrentMethodName();
		if (isEmpty(rawFileName)) {
			return null;
		}
		String cleanedName = "";
		try {
			// filter ? \ / : | < > *
			rawFileName = rawFileName.replaceAll("[\\?\\\\/:|<>\\*]", "_");
			cleanedName = URLEncoder.encode(rawFileName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.logError("UnsupportedEncodingException in createFileName" + e.getMessage(), e);
			return null;
		}
		String randomName = format.format(new Date()) + "_" + random.nextInt(1000);

		return (randomName + cleanedName);
	}

	public static String appendStrings(Object[] messages) {
		log.debugPrintCurrentMethodName();
		String str = "";
		for (Object msg : messages) {
			str += toString(msg);
		}
		return str;
	}

	public static String joinStrings(String str1, String str2) {
		log.debugPrintCurrentMethodName();
		String str = "";
		str = str1 + str2;
		return str;
	}

	public static String convertStringListToStringWithCommaSeparation(List<String> stringList, char separator) {
		log.debugPrintCurrentMethodName();
		StringBuilder rString = new StringBuilder();
		for (String each : stringList) {
			if (each.equals(stringList.get(0)))
				rString.append(each);
			else
				rString.append(separator).append(each);
		}
		return rString.toString();
	}

	public static String removeSpaces(String item) {
		log.debugPrintCurrentMethodName();
		return item.replace(" ", "");
	}

	public static String trimTrailingZerosToTwoDecimalPlaces(String item) {
		log.debugPrintCurrentMethodName();
		double value = Double.parseDouble(item);
		DecimalFormat df = new DecimalFormat("0.00");

		return df.format(value);
	}

	public static String retrieveTextBetweenAngularBraces(String text) {
		log.debugPrintCurrentMethodName();
		int startingIndex = text.indexOf("<");
		if (startingIndex != -1) {
			int endingIndex = text.indexOf(">", startingIndex);
			if (endingIndex != -1)
				return text.substring(startingIndex + 1, endingIndex);
		}
		return null;
	}

}
