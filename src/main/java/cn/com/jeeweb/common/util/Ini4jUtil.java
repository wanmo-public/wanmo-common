package cn.com.jeeweb.common.util;

import com.google.common.io.Resources;
import org.ini4j.Config;
import org.ini4j.Ini;
import org.ini4j.Profile.Section;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

/**
 * INI 格式文件工具类
 */
public class Ini4jUtil {
	private static Logger logger = LoggerFactory.getLogger(Ini4jUtil.class);

	/**
	 * 获取配置文件的 Section
	 * 
	 * @param ini
	 * @param sectionName
	 * @return 返回配置文件的 Section
	 */
	public static Section getSection(Ini ini, String sectionName) {
		if (ini == null || StringUtil.isBlank(sectionName)) {
			throw new RuntimeException("参数 ini 或  sectionName 不能为空");
		}

		return ini.get(sectionName);
	}

	/**
	 * 获取默认配置文件的 Section <br>
	 * 获取 class path 路径下的配置文件， 默认 fileName=wanmo-system-ini.conf
	 * 
	 * @param sectionName
	 * @return 返回默认配置文件的 Section
	 */
	public static Section getSection(String sectionName) {
		return getSection(iniDefault, sectionName);
	}

	/**
	 * 获取配置文件的 value
	 * 
	 * @param section
	 * @param key
	 * @return
	 */
	public static String getValue(Section section, String key) {
		if (section == null || StringUtil.isBlank(key)) {
			throw new RuntimeException("参数 section 或  key 不能为空");
		}

		return section.get(key);
	}

	/**
	 * 获取 class path 路径下的配置文件
	 * 
	 * @return
	 */
	public static Ini getIni(String fileName) {
		Ini ini = null;
		try {
			if (ini == null) {
				Config cfg = new Config();
				// 设置Section允许出现重复
				cfg.setMultiSection(true);

				// 生成配置文件的URL
				URL url = Resources.getResource(fileName);

				ini = new Ini();
				ini.setConfig(cfg);
				ini.load(url);
			}
		} catch (Exception e) {
			throw new RuntimeException(String.format("加载配置文件异常： %s", fileName), e);
		}

		return ini;
	}

	/**
	 * 获取 class path 路径下的配置文件， 默认 fileName=wanmo-system-ini.conf
	 * 
	 * @return
	 */
	public static Ini iniDefault = null;
	static {
		iniDefault = getIni("wanmo-config.ini");
	}
}
