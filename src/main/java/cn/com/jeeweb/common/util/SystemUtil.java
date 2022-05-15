package cn.com.jeeweb.common.util;

/**
 * 系统工具类
 */
public class SystemUtil {
	/**
	 * 控制台打印输入
	 * 
	 * @param obj
	 */
	public static void outPrintln(Object obj) {
		System.out.println(obj);
	}

	/**
	 * 控制台打印输入
	 * 
	 * @param obj
	 */
	public static void errPrintln(Object obj) {
		System.err.println(obj);
	}

	/** 操作系统 名称 */
	public static String osName = System.getProperty("os.name").toLowerCase();

	/**
	 * 是否是 Linux 系统
	 * 
	 * @return
	 */
	public static boolean isLinux() {
		return osName.indexOf("linux") >= 0;
	}

	/**
	 * 是否是 MacOS 系统
	 * 
	 * @return
	 */
	public static boolean isMacOS() {
		return osName.indexOf("mac") >= 0 && osName.indexOf("os") > 0 && osName.indexOf("x") < 0;
	}

	/**
	 * 是否是 MacOSX 系统
	 * 
	 * @return
	 */
	public static boolean isMacOSX() {
		return osName.indexOf("mac") >= 0 && osName.indexOf("os") > 0 && osName.indexOf("x") > 0;
	}

	/**
	 * 是否是 Windows 系统
	 * 
	 * @return
	 */
	public static boolean isWindows() {
		return osName.indexOf("windows") >= 0;
	}

}
