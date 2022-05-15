package cn.com.jeeweb.common.util_test;

import cn.com.jeeweb.common.util.http.HttpURLConnectionUtil;
import org.junit.Test;

public class HttpUtilTest {
	@Test
	public void test() {}

	public void testGet() {
		HttpURLConnectionUtil.sendGet("https://www.baidu.com/");
	}

}
