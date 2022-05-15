package cn.com.jeeweb.common.util;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 日志工具类：track,debug,info,warn,error
 */
public class LogUtil {

	public static final String track = "track";
	public static final String debug = "debug";
	public static final String info = "info";
	public static final String warn = "warn";
	public static final String error = "error";

	public static final String CONSOLE = "appender_console";
	public static final String FILE = "appender_file";

	public static String LOG_LEVEV = debug;

	/**
	 * System.out.pringln 的方式，控制台输入日志
	 * 
	 * @param s 要输出的日志
	 */
	public static void track(String s) {
		track(CONSOLE, s);
	}

	/**
	 * 控制台输出 或者 文件输出
	 * 
	 * @param appender 控制台 或者 文件
	 * @param s        要输出的日志
	 */
	public static void track(String appender, String s) {
		trackFormat(appender, s);
	}

	/**
	 * String.format 的方式格式化 控制台输出 或者 文件输出
	 * 
	 * @param appender 控制台 或者 文件
	 * @param format   要输出的日志格式
	 * @param args     要输出的数据
	 */
	public static void trackFormat(String appender, String format, Object... args) {
		List<String> levelList = Arrays.asList(new String[] { track });
		if (levelList.contains(LOG_LEVEV)) {
			String logStr = "跟踪日志：" + printPrev("track") + String.format(format, args);

			outputLog(appender, logStr);
		}

	}

	/**
	 * System.out.pringln 的方式，控制台输入日志
	 * 
	 * @param s 要输出的日志
	 */
	public static void debug(String s) {
		debug(CONSOLE, s);
	}

	/**
	 * 控制台输出 或者 文件输出
	 * 
	 * @param appender 控制台 或者 文件
	 * @param s        要输出的日志
	 */
	public static void debug(String appender, String s) {
		debugFormat(appender, s);
	}

	/**
	 * String.format 的方式格式化 控制台输出 或者 文件输出
	 * 
	 * @param appender 控制台 或者 文件
	 * @param format   要输出的日志格式
	 * @param args     要输出的数据
	 */
	public static void debugFormat(String appender, String format, Object... args) {
		List<String> levelList = Arrays.asList(new String[] { track, debug });
		if (levelList.contains(LOG_LEVEV)) {
			String logStr = "调试日志：" + printPrev("debug") + String.format(format, args);

			outputLog(appender, logStr);
		}

	}

	/**
	 * System.out.pringln 的方式，控制台输入日志
	 * 
	 * @param s 要输出的日志
	 */
	public static void info(String s) {
		info(CONSOLE, s);
	}

	/**
	 * 控制台输出 或者 文件输出
	 * 
	 * @param appender 控制台 或者 文件
	 * @param s        要输出的日志
	 */
	public static void info(String appender, String s) {
		infoFormat(appender, s);
	}

	/**
	 * String.format 的方式格式化 控制台输出 或者 文件输出
	 * 
	 * @param appender 控制台 或者 文件
	 * @param format   要输出的日志格式
	 * @param args     要输出的数据
	 */
	public static void infoFormat(String appender, String format, Object... args) {
		List<String> levelList = Arrays.asList(new String[] { track, debug, info });
		if (levelList.contains(LOG_LEVEV)) {
			String logStr = "信息日志：" + printPrev("info") + String.format(format, args);

			outputLog(appender, logStr);
		}
	}

	/**
	 * System.out.pringln 的方式，控制台输入日志
	 * 
	 * @param s 要输出的日志
	 */
	public static void warn(String s) {
		warn(CONSOLE, s);
	}

	/**
	 * 控制台输出 或者 文件输出
	 * 
	 * @param appender 控制台 或者 文件
	 * @param s        要输出的日志
	 */
	public static void warn(String appender, String s) {
		warnFormat(appender, s);
	}

	/**
	 * String.format 的方式格式化 控制台输出 或者 文件输出
	 * 
	 * @param appender 控制台 或者 文件
	 * @param format   要输出的日志格式
	 * @param args     要输出的数据
	 */
	public static void warnFormat(String appender, String format, Object... args) {
		List<String> levelList = Arrays.asList(new String[] { track, debug, info, warn });
		if (levelList.contains(LOG_LEVEV)) {
			String logStr = "警告日志：" + printPrev("warn") + String.format(format, args);

			outputLog(appender, logStr);
		}
	}

	/**
	 * System.out.pringln 的方式，控制台输入日志
	 * 
	 * @param s 要输出的日志
	 */
	public static void error(String s) {
		error(CONSOLE, s);
	}

	/**
	 * 控制台输出 或者 文件输出
	 * 
	 * @param appender 控制台 或者 文件
	 * @param s        要输出的日志
	 */
	public static void error(String appender, String s) {
		errorFormat(appender, s);
	}

	/**
	 * String.format 的方式格式化 控制台输出 或者 文件输出
	 * 
	 * @param appender 控制台 或者 文件
	 * @param format   要输出的日志格式
	 * @param args     要输出的数据
	 */
	public static void errorFormat(String appender, String format, Object... args) {
		List<String> levelList = Arrays.asList(new String[] { track, debug, info, warn, error });
		if (levelList.contains(LOG_LEVEV)) {
			String logStr = "错误日志：" + printPrev("error") + String.format(format, args);

			outputLog(appender, logStr);
		}
	}

	/**
	 * 根据日志级别，统一给日志信息加前缀
	 * 
	 * @param level 日志级别
	 * @return
	 */
	private static String printPrev(String level) {
		Date date = new Date();
		String prev = String.format(level + ": %tF %tT %n", date, date);
		return prev;
	}

	/**
	 * 日志输出
	 * 
	 * @param appender 输出方式
	 * @param logStr   输出字符串
	 */
	public static void outputLog(String appender, String logStr) {
		if (StringUtil.equals(CONSOLE, appender)) {
			System.out.println(logStr);
			System.out.println("");
		}
		if (StringUtil.equals(FILE, appender)) {
			FileUtil.writeToFile(getLogFileName(), logStr + "\r\n", true);
			FileUtil.writeToFile(getLogFileName(), "\r\n", true);
		}
	}

	/**
	 * @return 日志文件名
	 */
	private static String getLogFileName() {
		String logFileName = StringUtil.EMPTY;
		if (SystemUtil.isWindows()) {
			logFileName = "c:/wammologs/" + DateUtil.formatDay() + ".log";
		}

		if (SystemUtil.isLinux()) {
			logFileName = "/var/wammologs/" + DateUtil.formatDay() + ".log";
		}

		if (SystemUtil.isMacOS() || SystemUtil.isMacOSX()) {
			logFileName = "/var/wammologs/" + DateUtil.formatDay() + ".log";
		}
		return logFileName;
	}
}
