package cn.com.jeeweb.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 字符串工具类
 */
public class StringUtil extends StringUtils {

	/**
	 * 如果对象为空，则使用defaultVal值 see: Objects.toString(o, defaultVal)
	 * 
	 * @param obj
	 * @param defaultVal
	 * @return
	 */
	public static String toString(final Object obj, final String defaultVal) {
		return Objects.toString(obj, defaultVal);
	}

	/**
	 * 是否包含字符串
	 * 
	 * @param str  验证字符串
	 * @param strs 字符串组
	 * @return 包含返回true
	 */
	public static boolean inString(String str, String... strs) {
		if (isBlank(str) || strs == null) {
			return false;
		}

		List<String> list = Arrays.asList(strs);
		if (list.contains(str)) {
			return true;
		}

		return false;
	}

	/**
	 * 数字前补零
	 * 
	 * @param num    数字
	 * @param length 长度
	 * @return
	 */
	public static String preZeros(int num, int length) {
		String s = String.valueOf(num);
		int differ = length - s.length();

		if (differ > 0) {
			for (int i = 0; i <= differ - 1; i++) {
				s = "0" + s;
			}
		}
		return s;
	}

	/**
	 * 生成一个可选长度的随机数列
	 * 
	 * @param
	 */
	public static String getRandomCode(int length) {
		String[] arr = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F",
				"G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "a",
				"b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v",
				"w", "x", "y", "z" };
		String str = "";
		for (int i = 0; i < length; i++) {
			int r = (int) (Math.random() * 61);
			str += arr[r];
		}
		return str;
	}

}
