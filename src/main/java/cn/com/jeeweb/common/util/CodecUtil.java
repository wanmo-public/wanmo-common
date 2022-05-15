package cn.com.jeeweb.common.util;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 编码解码工具类<br/>
 * 说明：
 *   new sun.misc.BASE64Encoder().encode(bytes)将在未来版本中删除，
 *   由 java.util.Base64.getEncoder().encodeToString(bytes) 替代
 */
public class CodecUtil {
	/**
	 * Hex编码.
	 */
	public static String encodeHex(byte[] input) {
		return new String(Hex.encodeHex(input));
	}

	/**
	 * Hex解码.
	 */
	public static byte[] decodeHex(String input) {
		try {
			return Hex.decodeHex(input.toCharArray());
		} catch (DecoderException e) {
			throw Exceptions.unchecked(e);
		}
	}

	/**
	 * Base64编码.
	 */
	public static String encodeBase64(byte[] bytes) {
		byte[] encodeBase64 = Base64.encodeBase64(bytes);
		return StringUtil.toEncodedString(encodeBase64, CharsetUtil.DEFAULT_CHARSET);
	}

	/**
	 * Base64编码.
	 */
	public static String encodeBase64(String input) {
		byte[] bytes = input.getBytes(CharsetUtil.DEFAULT_CHARSET);
		return encodeBase64(bytes);
	}

	/**
	 * Base64解码.
	 */
	public static byte[] decodeBase64(String input) {
		byte[] bytes = input.getBytes(CharsetUtil.DEFAULT_CHARSET);
		return Base64.decodeBase64(bytes);
	}

	/**
	 * Base64解码.
	 */
	public static String decodeBase64String(String input) {
		byte[] decodeBase64 = decodeBase64(input);
		return StringUtil.toEncodedString(decodeBase64, CharsetUtil.DEFAULT_CHARSET);
	}

	/**
	 * HTML 转码.
	 */
	public static String escapeHtml(String html) {
		return StringEscapeUtils.escapeHtml4(html);
	}

	/**
	 * HTML 解码.
	 */
	public static String unescapeHtml(String htmlEscaped) {
		return StringEscapeUtils.unescapeHtml4(htmlEscaped);
	}

	/**
	 * XML 转码.
	 */
	public static String escapeXml(String xml) {
		return StringEscapeUtils.escapeXml10(xml);
	}

	/**
	 * XML 解码.
	 */
	public static String unescapeXml(String xmlEscaped) {
		return StringEscapeUtils.unescapeXml(xmlEscaped);
	}

	/**
	 * URL 编码, Encode默认为UTF-8.
	 */
	public static String urlEncode(String part) {
		try {
			return URLEncoder.encode(part, CharsetUtil.DEFAULT_CHARSET_NAME);
		} catch (UnsupportedEncodingException e) {
			throw Exceptions.unchecked(e);
		}
	}

	/**
	 * URL 解码, Encode默认为UTF-8.
	 */
	public static String urlDecode(String part) {

		try {
			return URLDecoder.decode(part, CharsetUtil.DEFAULT_CHARSET_NAME);
		} catch (UnsupportedEncodingException e) {
			throw Exceptions.unchecked(e);
		}
	}

}
