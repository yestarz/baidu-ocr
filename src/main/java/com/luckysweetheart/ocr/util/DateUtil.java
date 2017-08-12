package com.luckysweetheart.ocr.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	/**
	 * 一天的毫秒数
	 */
	private static final Long ONE_DAY_TIME = (long) 1000 * 60 * 60 * 24;

	public static Date getDate(String str, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = sdf.parse(str);
		} catch (ParseException e) {
			throw new RuntimeException("CommonUtil.getDate error|str:" + str+ ",pattern:" + pattern);
		}
		return date;
	}

	public static String formatNow(){
		return formatDate(new Date());
	}

	public static String formatDate(Date date) {
		String strDate = null;
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			strDate = sdf.format(date);
		}
		return strDate;
	}

	public static String formatDate(Date date, String pattern) {
		String strDate = null;
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			strDate = sdf.format(date);
		}
		return strDate;
	}

	/**
	 * 得到当前月的最后时刻
	 * 
	 * @return
	 */
	public static Date getLastMonthTime() {
		return getLastMonthTime(new Date());
	}

	/**
	 * 得到当前月的最后时刻
	 * 
	 * @param date
	 * @return
	 */
	public static Date getLastMonthTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH,
				cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MINUTE, 59);
		return cal.getTime();
	}

	/**
	 * 得到当前月的最后时刻
	 * 
	 * @param
	 * @return
	 */
	public static Date getNextMonthTime() {
		return getNextMonthTime(new Date());
	}

	/**
	 * 得到当前月的最后时刻
	 * 
	 * @param date
	 * @return
	 */
	public static Date getNextMonthTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		return cal.getTime();
	}

	/**
	 * 设置时间，以累加减方式
	 * 
	 * @param date
	 * @param type
	 * @param num
	 * @return
	 */
	public static Date setDateByAdd(Date date, int type, int num) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(type, cal.get(type) + num);
		return cal.getTime();
	}
	
	/**
	 * 获得一个对当前时间进行加减后的时间
	 * @param d 时间类型 如Calendar.DATE
	 * @param n 加减值
	 * @return Date
	 */
	public static Date getChangeDate(int d,int n) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(d, n);
		Date date = calendar.getTime();
		return date;
	}
	
	/**
	 * 获得一个对传入时间进行加减后的时间
	 * @param d 时间类型 如Calendar.DATE
	 * @param n 加减值
	 * @param t 加减起始时间
	 * @return Date
	 */
	public static Date getChangeDate(int d,int n,Date t) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(t);
		calendar.add(d, n);
		Date date = calendar.getTime();
		return date;
	}

	/**
	 * 获取前month个月的1号的前一天，比如：今天是2017/08/08， 6 个月前是2017/02/08 ，返回2017/01/31
	 *
	 * @param month
	 * @return
	 */
	public static Date getLastNMonthsDate(int month) {
		Date now = new Date();
		Date queryDate = DateUtil.setDateByAdd(now, Calendar.MONTH, -(month + 1));
		return getLastMonthTime(queryDate);
	}

	/**
	 * 某段时间内相差几天
	 *
	 * @param endTime
	 * @param startTime
	 * @return
	 */
	public static int getDifferDay(Date startTime,Date endTime ) {
		Long c = endTime.getTime() - startTime.getTime();
		return (int) (c / ONE_DAY_TIME);
	}

	/**
	 * 获取昨天(yyyy-MM-dd)
	 *
	 * @return
	 */
	public Date getYesterday() {
		// 当前时间的前一天，昨天
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		Date yesterday = calendar.getTime();
		String temp = DateUtil.formatDate(yesterday, "yyyy-MM-dd");

		// 设置为昨天的00:00:00
		return DateUtil.getDate(temp, "yyyy-MM-dd");
	}

	public static void main(String[] args) {
		System.out.println(getDifferDay(getDate("2017-01-01","yyyy-MM-dd"),getDate("2017-08-08","yyyy-MM-dd")));
	}
}
