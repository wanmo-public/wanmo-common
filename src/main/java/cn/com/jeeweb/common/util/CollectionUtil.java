package cn.com.jeeweb.common.util;

import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * Collection 工具类
 */
public class CollectionUtil extends CollectionUtils {

	/**
	 * 是否为空
	 * 
	 * @param list
	 * @return
	 */
	public static boolean isEmpty(List<?> list) {
		if (list == null || list.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * 是否不为空
	 * 
	 * @param list
	 * @return
	 */
	public static boolean isNotEmpty(List<?> list) {
		return !isEmpty(list);
	}

	/**
	 * 是否为空
	 * 
	 * @param set
	 * @return
	 */
	public static boolean isEmpty(Set<?> set) {
		if (set == null || set.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * 是否不为空
	 * 
	 * @param set
	 * @return
	 */
	public static boolean isNotEmpty(Set<?> set) {
		return !isEmpty(set);
	}

}
