package cn.com.jeeweb.common.util;

import org.apache.commons.lang3.CharUtils;

/**
 * Char 工具类
 */
public class CharUtil extends CharUtils {

	/**
	 * 数字 转 字符
	 * 
	 * @param i
	 * @return
	 */
	public static char intToChar(int i) {
		Character character = Character.valueOf((char) i);
		return character.charValue();
	}

	/**
	 * 字符 转 数字
	 * 
	 * @param s
	 * @return
	 */
	public static int charToInt(String s) {
		if (StringUtil.isBlank(s) || s.length() == 0) {
			return 0;
		}
		s = s.trim();
		if (s.length() > 1) {
			return -1;
		}

		s = s.toUpperCase();

		int i = 0;
		switch (s.charAt(0)) {
		case 'A':
			i = 65;
			break;
		case 'B':
			i = 66;
			break;
		case 'C':
			i = 67;
			break;
		case 'D':
			i = 68;
			break;
		case 'E':
			i = 69;
			break;
		case 'F':
			i = 70;
			break;
		default:
			i = 99;
			break;
		}

		return i;
	}
}
