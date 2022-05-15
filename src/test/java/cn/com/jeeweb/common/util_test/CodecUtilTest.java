package cn.com.jeeweb.common.util_test;

import cn.com.jeeweb.common.util.SystemUtil;
import cn.com.jeeweb.common.util.CodecUtil;
import org.junit.Test;

public class CodecUtilTest {
	@Test
	public void test() {}

	/** 数据原文 */
	String data = "测试abc#￥+$12+-3456";

	/**
	 * CodecUtil 编码解码测试
	 */
	public void encodeAndDecodeBase64Test() {
		try {
			SystemUtil.outPrintln("编码前的数据 :" + data);
			String encodeBase64 = CodecUtil.encodeBase64(data);
			SystemUtil.outPrintln("编码后的数据 :" + encodeBase64);
			String decryptStr = CodecUtil.decodeBase64String(encodeBase64);
			SystemUtil.outPrintln("解码后的数据 :" + decryptStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
