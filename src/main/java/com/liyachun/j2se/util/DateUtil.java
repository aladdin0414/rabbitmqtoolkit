package com.liyachun.j2se.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author liyc
 * @time 2012-1-9 下午11:13:45
 * @annotation
 */
public class DateUtil {
	public static String DateFormat_1 = "yyyy-MM-dd hh:mm:ss";
	public static String DateFormat_24 = "yyyy-MM-dd HH:mm:ss";

	public static String switchDay(int day) {
		String daystr = day + "";
		if (daystr.length() == 2) {
			return daystr;
		} else {
			return "0" + daystr;
		}
	}

	public static String convertDate(Date date, String format) {
		if (date != null) {
			DateFormat format1 = new SimpleDateFormat(format);
			String s = format1.format(date);
			return s;
		}
		return "";
	}

	public static String current() {
		return convertDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}
	
}
