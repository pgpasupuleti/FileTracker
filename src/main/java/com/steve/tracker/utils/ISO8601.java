package com.steve.tracker.utils;

import java.util.Date;

import org.codehaus.jackson.map.util.ISO8601Utils;

/**
 * @author Praveen Kumar
 *
 */
public class ISO8601 {
	public static Date parse(String value) {
		// Truncate fractions to normalize timestamp resolution across platforms
		if (value.length() > 20) {
			value = value.substring(0, 19) + "Z";
		}
		return ISO8601Utils.parse(value);
	}

	public static String format(Date value) {
		return ISO8601Utils.format(value, false);
	}

	public static String format(long value) {
		return ISO8601Utils.format(new Date(value), false);
	}

	public static String now() {
		return ISO8601Utils.format(new Date(), false);
	}
}
