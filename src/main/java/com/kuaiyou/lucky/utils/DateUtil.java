package com.kuaiyou.lucky.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.hutool.core.date.DateTime;

/**
 * 
 * @author hyn_ 2018年10月16日
 */
public class DateUtil {

	public static String YMD = "yyyy-MM-dd";
	public static String YM = "yyyy-MM";
	public static String Ym = "yyyy-M";
	public static String YMDHMS = "yyyy-MM-dd HH:mm:ss";

	public static boolean isValidDate(String str) {
		boolean convertSuccess = true;
		SimpleDateFormat format = new SimpleDateFormat(Ym);
		try {
			format.setLenient(false);
			format.parse(str);
		} catch (ParseException e) {
			convertSuccess = false;
		}
		return convertSuccess;
	}

	public static String parseDate(String str) {
		SimpleDateFormat format = new SimpleDateFormat(YM);
		try {
			format.setLenient(false);
			Date parse = format.parse(str);
			return format.format(parse);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return str;
	}

	public static String format(Date date, String patter) {
		SimpleDateFormat sdf = new SimpleDateFormat(patter);
		return sdf.format(date);
	}

	public static String getFirstDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat sdf = new SimpleDateFormat(YMD);
		return sdf.format(cal.getTime());
	}

	public static String getNow() {
		SimpleDateFormat sdf = new SimpleDateFormat(YM);
		return sdf.format(new Date());
	}

	public static String getMonth() {
		SimpleDateFormat sdf = new SimpleDateFormat(YM);
		return sdf.format(new Date());
	}

	public static Date add(int days) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, days);
		return cal.getTime();
	}

	public static Date add(Date date, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, days);
		return cal.getTime();
	}

	public static String getEndOfDay(String pdate, int hour, int min, int sec) {
		SimpleDateFormat sdf = new SimpleDateFormat(YMD);
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(sdf.parse(pdate));
			cal.set(Calendar.HOUR_OF_DAY, hour);
			cal.set(Calendar.MINUTE, min);
			cal.set(Calendar.SECOND, sec);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		SimpleDateFormat rsdf = new SimpleDateFormat(YMDHMS);
		return rsdf.format(cal.getTime());
	}

	public static String getEndOfDay() {
		SimpleDateFormat sdf = new SimpleDateFormat(YMDHMS);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return sdf.format(cal.getTime());
	}

	public static String getStartOfDay() {
		SimpleDateFormat sdf = new SimpleDateFormat(YMDHMS);
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return sdf.format(cal.getTime());
	}

	public static Date endOfDay() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	public static Date startOfDay() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	public static int getWeekOfDay() {
		Calendar cal = Calendar.getInstance();
		int i = cal.get(Calendar.DAY_OF_WEEK);
		if (i == 1) {
			return 7;
		}
		return i - 1;
	}

	public static int getWeekOfDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int i = cal.get(Calendar.DAY_OF_WEEK);
		if (i == 1) {
			return 7;
		}
		return i - 1;
	}

	public static Date getEndWeekOfDay() {
		Date date = new Date();
		DateTime endOfWeek = cn.hutool.core.date.DateUtil.endOfWeek(date);
		return new Date(endOfWeek.getTime());
	}

	public static Date getStartWeekOfDay() {
		Date date = new Date();
		DateTime beginOfWeek = cn.hutool.core.date.DateUtil.beginOfWeek(date);
		return new Date(beginOfWeek.getTime());
	}

	public static String endWeekOfDay() {
		Date date = new Date();
		DateTime endOfWeek = cn.hutool.core.date.DateUtil.endOfWeek(date);
		return format(new Date(endOfWeek.getTime()), YMDHMS);
	}

	public static String startWeekOfDay() {
		Date date = new Date();
		DateTime beginOfWeek = cn.hutool.core.date.DateUtil.beginOfWeek(date);
		return format(new Date(beginOfWeek.getTime()), YMDHMS);
	}

	public static void main(String[] args) {
		System.out.println(parseDate("2020-09"));
	}

}
