package cn.com.jeeweb.common.util;

/**
 * 当前类信息工具类
 */
public class CurrentLineInfo {
	private static int originStackIndex = 2;

	/**
	 * java文件名
	 */
	public static String getFileName() {
		return Thread.currentThread().getStackTrace()[originStackIndex].getFileName();
	}

	/**
	 * 类名
	 */
	public static String getClassName() {
		return Thread.currentThread().getStackTrace()[originStackIndex].getClassName();
	}

	/**
	 * 当前方法名字
	 */
	public static String getMethodName() {
		return Thread.currentThread().getStackTrace()[originStackIndex].getMethodName();
	}

	/**
	 * 当前代码是第几行
	 */
	public static int getLineNumber() {
		return Thread.currentThread().getStackTrace()[originStackIndex].getLineNumber();
	}
}
