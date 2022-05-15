package cn.com.jeeweb.common.util_test;

import cn.com.jeeweb.common.util.StringUtil;
import cn.com.jeeweb.common.util.SystemUtil;
import cn.com.jeeweb.common.util.AesUtil;
import cn.com.jeeweb.common.util.CodecUtil;
import org.junit.Test;

public class AesUtilTest {
	@Test
	public void test() {}

	/** 数据原文 */
	String data = "测试abc#￥$123+456";
	/** KEY */
	String keyStr = null;
	/** IV */
	String ivStr = null;
	/** 加密以后的数据 */
	String encryptStr = null;
	/** 解密以后的数据 */
	String decryptStr = null;

	/**
	 * 测试 AES ECB 加解密数据
	 */
	public void encryptByECBTest1() {
		try {
			SystemUtil.outPrintln("加密前的数据 :" + data);
			keyStr = StringUtil.getRandomCode(16);
			SystemUtil.outPrintln("BASE64 编码前的 KEY :" + keyStr);
			keyStr = CodecUtil.encodeBase64(keyStr);
			SystemUtil.outPrintln("BASE64 编码后的 KEY :" + keyStr);
			encryptStr = AesUtil.encryptByECB(keyStr, data);
			SystemUtil.outPrintln("加密后的数据 :" + encryptStr);
			decryptStr = AesUtil.decryptByECB(keyStr, encryptStr);
			SystemUtil.outPrintln("解密后的数据 :" + decryptStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 测试 AES ECB 加解密数据
	 */
	public void encryptByECBTest2() {
		try {
			SystemUtil.outPrintln("加密前的数据 :" + data);
			keyStr = AesUtil.keyGenerate();
			SystemUtil.outPrintln("编码后的 KEY :" + keyStr);
			encryptStr = AesUtil.encryptByECB(keyStr, data);
			SystemUtil.outPrintln("加密后的数据 :" + encryptStr);
			decryptStr = AesUtil.decryptByECB(keyStr, encryptStr);
			SystemUtil.outPrintln("解密后的数据 :" + decryptStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 测试 AES CBC 加解密数据
	 */
	public void encryptByCBCTest1() {
		try {
			SystemUtil.outPrintln("加密前的数据 :" + data);
			keyStr = StringUtil.getRandomCode(16); // 获取 KEY
			keyStr = CodecUtil.encodeBase64(keyStr); // BASE64编码 KEY
			ivStr = StringUtil.getRandomCode(16); // 获取 IV
			ivStr = CodecUtil.encodeBase64(ivStr); // BASE64编码 IV
			encryptStr = AesUtil.encryptByCBC(keyStr, ivStr, data);
			SystemUtil.outPrintln("加密后的数据 :" + encryptStr);
			decryptStr = AesUtil.decryptByCBC(keyStr, ivStr, encryptStr);
			SystemUtil.outPrintln("解密后的数据 :" + decryptStr);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 测试 AES CBC 加解密数据
	 */
	public void encryptByCBCTest2() {
		try {
			SystemUtil.outPrintln("加密前的数据 :" + data);
			keyStr = AesUtil.keyGenerate(); // 获取 BASE64编码 KEY
			ivStr = AesUtil.keyGenerate(); // 获取 BASE64编码 IV
			encryptStr = AesUtil.encryptByCBC(keyStr, ivStr, data);
			SystemUtil.outPrintln("加密后的数据 :" + encryptStr);
			decryptStr = AesUtil.decryptByCBC(keyStr, ivStr, encryptStr);
			SystemUtil.outPrintln("解密后的数据 :" + decryptStr);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
