package com.vmeet.netsocket.tool;

import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTool {
	public static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static String TIME_FORMAT1 = "yyyy-MM-dd";
	private static String TIME_FORMAT2 = "MM/dd";
	public static long convertString2long(String date) {
		try {
			SimpleDateFormat sf = new SimpleDateFormat(TIME_FORMAT);
			return sf.parse(date).getTime();
		} catch (ParseException e) {
		}
		return 0l;
	}
	public static String convertLong2String(long time) {
		Date date = new Date(time);
		SimpleDateFormat format = new SimpleDateFormat(TIME_FORMAT);
		String tempDate = format.format(date);
		return tempDate;
	}
	public static String convertLong2String1(long time) {
		Date date = new Date(time);
		SimpleDateFormat format = new SimpleDateFormat(TIME_FORMAT1);
		String tempDate = format.format(date);
		return tempDate;
	}
	public static String getTimeStr() {
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);
		return sdf.format(d);
	}
	public static String timeLong2Str(long time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		return sdf.format(new Date(time));
	}
	public static Date string2Date(String time) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT);
		Date date = null;
		try {
			date = dateFormat.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String string2Format(String time){
		String formatTime = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat(TIME_FORMAT2);
		formatTime = dateFormat.format(string2Date(time));
		return formatTime;
	}

	public static String date2String(Date date){
		 SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT);  
		 String s  = sdf.format(date);  
		 return s;
	}
	
	/**
	 * 取得当前日期所在周的第一天
	 */
	public static long getFirstDayOfWeek() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTimeInMillis();
	}

	/**
	 * 取得当前日期所在周的最后一天
	 */
	public static long getLastDayOfWeek() {
		Calendar c=Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		c.add(Calendar.WEEK_OF_MONTH, 1);
		return c.getTimeInMillis()-1;
	}

	/**
	 * 获得当前日期所在月的第一天
	 */
	public static long getFirstDayOfMonth() {
		Calendar c=Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTimeInMillis();
	}

	/**
	 * 获得当前日期所在月的最后一天
	 */
	public static long getLastDayOfMonth() {
		Calendar c=Calendar.getInstance();
		c.add(Calendar.MONTH, 1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTimeInMillis()-1;
	}
	
	public static long dateDiff(String startTime, String endTime) {
	  	SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
		long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
		long nh = 1000 * 60 * 60;// 一小时的毫秒数
		long nm = 1000 * 60;// 一分钟的毫秒数
		long ns = 1000;// 一秒钟的毫秒数
		long diff,min = 0;
		try {
			// 获得两个时间的毫秒时间差异
			diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
			min = diff % nd % nh / nm;// 计算差多少分钟
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return min;
	}

	/**
	 * 
	 * 判断当前日期是星期几
	 * @param pTime 修要判断的时间
	 * @return dayForWeek 判断结果
	 * @throws Exception 
	 * @Exception 发生异常
	 */
	
	public static String dayForWeek(String pTime) throws Exception{
		String[] weeks = new String[]{"星期日","星期一", "星期二", "星期三", "星期四", "星期五", "星期六"}; 
		SimpleDateFormat format = new SimpleDateFormat(TIME_FORMAT1);
		Calendar c = Calendar.getInstance();
		c.setTime(format.parse(pTime));
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return weeks[dayForWeek];
	}
	
	/**
	 * 获得昨天日期
	 * @return
	 */
	public static String getYesterDay(){
		SimpleDateFormat format = new SimpleDateFormat(TIME_FORMAT1);
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -1);
		String yesterday = format.format(c.getTime());
		return yesterday;
	}
}
