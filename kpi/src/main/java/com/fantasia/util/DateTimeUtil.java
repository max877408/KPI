package com.fantasia.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeUtil {

	private static final int CODE_LOSE_TIME = 60 * 30;

	public static Date getLocalTime() {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		Calendar rightNow = Calendar.getInstance();
		return rightNow.getTime();
	}

	public static Date getLoseTime() {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		Calendar rightNow = Calendar.getInstance();
		rightNow.add(Calendar.SECOND, +CODE_LOSE_TIME);
		return rightNow.getTime();
	}

	public static Date getLoseTime(int loseData) {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
		Calendar rightNow = Calendar.getInstance();
		rightNow.add(Calendar.DATE, +loseData);
		return rightNow.getTime();
	}

	public static boolean compare_date(Date date1, Date date2) {
		long result = date1.getTime() - date2.getTime();
		return result > 0 ? true : false;
	}

	public static void main(String args[]) {
		System.out.println(compare_date(getLoseTime(), getLocalTime()));

	}

	public static String shortDateString() {
		Date currentTime = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String dateString = sdf.format(currentTime);
		return dateString;
	}

	/**
	 * 字符串转换成日期
	 * 
	 * @param str
	 * @return date
	 */
	public static Date StrToDate(String str) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = format.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
}
