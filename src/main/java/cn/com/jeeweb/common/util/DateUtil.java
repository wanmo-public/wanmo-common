package cn.com.jeeweb.common.util;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 */
public class DateUtil extends DateUtils {
	private static String[] parsePatterns = { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
			"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM", "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss",
			"yyyy.MM.dd HH:mm", "yyyy.MM", "yyyy-MM-dd HH:mm:ss.s", "yyyy-MM-dd HH:mm:ss.ss",
			"yyyy-MM-dd HH:mm:ss.sss" };

	/**
	 * 获取当前 Calendar
	 * 
	 * @return
	 */
	public static Calendar nowCalendar() {
		return Calendar.getInstance();
	}

	/**
	 * 获取当前 Date
	 * 
	 * @return
	 */
	public static Date nowDate() {
		return nowCalendar().getTime();
	}

	/**
	 * 获取当前 Long
	 * 
	 * @return
	 */
	public static long nowLong() {
		return nowDate().getTime();
	}

	/**
	 * 获取n天之前的时间
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date getDateBefore(Date date, int day) {
		nowCalendar().setTime(date);
		nowCalendar().set(Calendar.DATE, nowCalendar().get(Calendar.DATE) - day);
		return nowCalendar().getTime();
	}

	/**
	 * 获取n天之后的时间
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date getDateAfter(Date date, int day) {
		nowCalendar().setTime(date);
		nowCalendar().set(Calendar.DATE, nowCalendar().get(Calendar.DATE) + day);
		return nowCalendar().getTime();
	}

	/**
	 * 获取n分钟前的时间
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date getDateBeforeMinute(Date date, int day) {
		nowCalendar().setTime(date);
		nowCalendar().set(Calendar.MINUTE, nowCalendar().get(Calendar.MINUTE) - day);
		return nowCalendar().getTime();
	}

	/**
	 * 获取n分钟后的时间
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date getDateAfterMinute(Date date, int day) {
		nowCalendar().setTime(date);
		nowCalendar().set(Calendar.MINUTE, nowCalendar().get(Calendar.MINUTE) + day);
		return nowCalendar().getTime();
	}

	/**
	 * 日期字符串解析
	 * 
	 * @param source
	 * @return
	 * @throws ParseException
	 */
	public static Date parse(String source) {
		if (source == null) {
			return null;
		}

		try {
			return parseDate(source, parsePatterns);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 日期格式化， 默认（yyyy-MM-dd）
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Date date, String... pattern) {
		if (date == null) {
			return null;
		}

		if (pattern == null || pattern.length == 0) {
			return DateFormatUtils.format(date, "yyyy-MM-dd");
		} else {
			return DateFormatUtils.format(date, pattern[0]);
		}

	}

	/**
	 * 得到UTC时间，类型为字符串
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatUTC(Date date, String pattern) {
		return DateFormatUtils.formatUTC(date, pattern);
	}

	/**
	 * 日期格式化
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		return format(date, pattern);
	}

	/**
	 * 格式化当前日期 （yyyy-MM-dd）
	 * 
	 * @return
	 */
	public static String formatDay() {
		return formatDay(nowDate());
	}

	/**
	 * 格式化日期 （yyyy-MM-dd）
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDay(Date date) {
		return format(date, "yyyy-MM-dd");
	}

	/**
	 * 格式化当前日期 （yyyy-MM-dd HH:mm:ss）
	 * 
	 * @return
	 */
	public static String formatDayTime() {
		return formatDayTime(nowDate());
	}

	/**
	 * 格式化日期 （yyyy-MM-dd HH:mm:ss）
	 * 
	 * @return
	 */
	public static String formatDayTime(Date date) {
		return format(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return format(nowDate(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return format(nowDate(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return format(nowDate(), "dd");
	}

	/**
	 * 得到当天字符串 格式（HH）
	 */
	public static String getHour() {
		return format(nowDate(), "HH");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return format(nowDate(), "E");
	}

	/**
	 * 获取过去的天数
	 * 
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = nowLong() - date.getTime();
		return t / (24 * 60 * 60 * 1000);
	}

	/**
	 * 获取过去的小时
	 * 
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = nowLong() - date.getTime();
		return t / (60 * 60 * 1000);
	}

	/**
	 * 获取过去的分钟
	 * 
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = nowLong() - date.getTime();
		return t / (60 * 1000);
	}
}
