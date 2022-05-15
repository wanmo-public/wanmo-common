package cn.com.jeeweb.common.util;

import org.apache.commons.lang3.Validate;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;

/**
 * 支持SHA-1/MD5消息摘要的工具类.<br>
 * 返回ByteSource，可进一步被编码为Hex, Base64或UrlSafeBase64
 */
public class Digests {

	private static final String SHA1 = "SHA-1";
	private static final String MD5 = "MD5";

	private static SecureRandom random = new SecureRandom();

	/**
	 * 对输入字符串进行md5散列.
	 * 
	 * @param input 字符串数据
	 * @return 返回 md5 后的字节数值
	 */
	public static byte[] md5(byte[] input) {
		return digest(input, MD5, null, 1);
	}

	/**
	 * 对输入字符串进行md5散列.
	 * 
	 * @param input      字符串数据
	 * @param iterations 迭代次数
	 * @return 返回 md5 后的字节数值
	 */
	public static byte[] md5(byte[] input, int iterations) {
		return digest(input, MD5, null, iterations);
	}

	/**
	 * 对输入字符串进行sha1散列.
	 * 
	 * @param input 字符串数据
	 * @return 返回 sha1 后的字节数值
	 */
	public static byte[] sha1(byte[] input) {
		return digest(input, SHA1, null, 1);
	}

	/**
	 * 对输入字符串进行sha1散列.
	 * 
	 * @param input 字符串数据
	 * @param salt  盐值
	 * @return 返回 sha1 后的字节数值
	 */
	public static byte[] sha1(byte[] input, byte[] salt) {
		return digest(input, SHA1, salt, 1);
	}

	/**
	 * 对输入字符串进行sha1散列.
	 * 
	 * @param input      字符串数据
	 * @param salt       盐值
	 * @param iterations 迭代次数
	 * @return 返回 sha1 后的字节数值
	 */
	public static byte[] sha1(byte[] input, byte[] salt, int iterations) {
		return digest(input, SHA1, salt, iterations);
	}

	/**
	 * 对文件进行md5散列.
	 * 
	 * @param input 文件数据
	 * @return 返回 md5 后的字节数值
	 */
	public static byte[] md5(InputStream input) {
		return digest(input, MD5);
	}

	/**
	 * 对文件进行sha1散列.
	 * 
	 * @param input 文件数据
	 * @return 返回 sha1 后的字节数值
	 */
	public static byte[] sha1(InputStream input) {
		return digest(input, SHA1);
	}

	/**
	 * 对字符串进行散列, 支持 md5 与 sha1 算法.
	 * 
	 * @param input      需要摘要的数据
	 * @param algorithm  摘要算法
	 * @param salt       盐值
	 * @param iterations 迭代次数
	 * @return
	 */
	private static byte[] digest(byte[] input, String algorithm, byte[] salt, int iterations) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);

			if (salt != null) {
				messageDigest.update(salt);
			}

			byte[] result = messageDigest.digest(input);

			for (int i = 1; i < iterations; i++) {
				messageDigest.reset();
				result = messageDigest.digest(result);
			}
			return result;
		} catch (GeneralSecurityException e) {
			throw Exceptions.unchecked(e);
		}
	}

	/**
	 * 对文件进行散列, 支持 md5 与 sha1 算法.
	 * 
	 * @param input     需要摘要的数据
	 * @param algorithm 摘要算法
	 * @return
	 * @throws IOException
	 */
	private static byte[] digest(InputStream input, String algorithm) {
		try {
			MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
			int bufferLength = 8 * 1024;
			byte[] buffer = new byte[bufferLength];
			int read = input.read(buffer, 0, bufferLength);

			while (read > -1) {
				messageDigest.update(buffer, 0, read);
				read = input.read(buffer, 0, bufferLength);
			}

			return messageDigest.digest();
		} catch (Exception e) {
			throw Exceptions.unchecked(e);
		}
	}

	/**
	 * 生成随机的 byte[] 作为salt.
	 * 
	 * @param numBytes byte 数组的大小
	 */
	public static byte[] generateSalt(int numBytes) {
		Validate.isTrue(numBytes > 0, "numBytes argument must be a positive integer (1 or larger)", numBytes);

		byte[] bytes = new byte[numBytes];
		random.nextBytes(bytes);
		return bytes;
	}
}
