package cn.com.jeeweb.common.util;

import org.apache.commons.lang3.BooleanUtils;

/**
 * Boolean 工具类
 */
public class BooleanUtil extends BooleanUtils {

	/**
	 * <p>
	 * 将字符串 转为 boolean.
	 * </p>
	 *
	 * <p>
	 * {@code 'true'}, {@code 'on'}, {@code 'y'}, {@code 't'} or {@code 'yes'} (case
	 * insensitive) will return {@code true}. Otherwise, {@code false} is returned.
	 * </p>
	 *
	 * <p>
	 * NOTE: 如果为空，则为 false.
	 * </p>
	 *
	 * <pre>
	 *   // N.B. case is not significant
	 *   BooleanUtils.toBoolean("true")  = Boolean.TRUE
	 *   BooleanUtils.toBoolean("T")     = Boolean.TRUE // i.e. T[RUE]
	 *   BooleanUtils.toBoolean("on")    = Boolean.TRUE
	 *   BooleanUtils.toBoolean("ON")    = Boolean.TRUE
	 *   BooleanUtils.toBoolean("yes")   = Boolean.TRUE
	 *   BooleanUtils.toBoolean("Y")     = Boolean.TRUE // i.e. Y[ES]
	 * </pre>
	 *
	 * @param str the String to check; 不区分大小写
	 * @return the Boolean value of the string, {@code false} if no match or
	 *         {@code null} input
	 */
	public static boolean toBoolean(final String str) {
		Boolean booleanObject = toBooleanObject(str);
		return toBooleanDefaultIfNull(booleanObject, false);
	}

}
