package cn.com.jeeweb.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;

/**
 * AES对称加密，对明文进行加密、解密处理
 */
public class AesUtil {
	static Logger logger = LoggerFactory.getLogger(AesUtil.class);

	/** 密钥算法 AES */
	private static final String KEY_ALGORITHM_AES = "AES";
	/** 密码算法 ECB */
	private static final String CIPHER_ALGORITHM_ECB = "AES/ECB/PKCS5Padding"; // "算法/模式/补码方式"
	/** 密码算法 CBC */
	private static final String CIPHER_ALGORITHM_CBC = "AES/CBC/PKCS5Padding"; // "算法/模式/补码方式"

	/**
	 * ECB模式-加密操作
	 * 
	 * @param keyStr 进行了Base64编码的秘钥
	 * @param data   需要进行加密的原文
	 * @return String 数据密文，加密后的数据，进行了Base64的编码
	 */
	public static String encryptByECB(String keyStr, String data) throws Exception {
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB);

		Key key = new SecretKeySpec(CodecUtil.decodeBase64(keyStr), KEY_ALGORITHM_AES); // 转换密钥
		cipher.init(Cipher.ENCRYPT_MODE, key); // 加密

		byte[] result = cipher.doFinal(data.getBytes(CharsetUtil.DEFAULT_CHARSET));
		return CodecUtil.encodeBase64(result);
	}

	/**
	 * ECB模式-解密操作
	 * 
	 * @param keyStr 进行了Base64编码的秘钥
	 * @param data   需要解密的数据（数据必须是通过AES进行加密后，对加密数据Base64编码的数据）
	 * @return String 返回解密后的原文
	 */
	public static String decryptByECB(String keyStr, String data) throws Exception {
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB);

		Key key = new SecretKeySpec(CodecUtil.decodeBase64(keyStr), KEY_ALGORITHM_AES); // 转换密钥
		cipher.init(Cipher.DECRYPT_MODE, key); // 解密

		byte[] decryptBytes = cipher.doFinal(CodecUtil.decodeBase64(data));
		return StringUtil.toEncodedString(decryptBytes, CharsetUtil.DEFAULT_CHARSET);
	}

	/**
	 * CBC模式-加密操作， CBC 模式可以用IV， ECB 模式不能用 IV
	 * 
	 * @param keyStr 进行了Base64编码的秘钥
	 * @param ivStr  进行了Base64编码的偏移量
	 * @param data   需要进行加密的原文
	 * @return String 数据密文，加密后的数据，进行了Base64的编码
	 */
	public static String encryptByCBC(String keyStr, String ivStr, String data) throws Exception {
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);

		SecretKeySpec key = new SecretKeySpec(CodecUtil.decodeBase64(keyStr), KEY_ALGORITHM_AES);
		IvParameterSpec iv = new IvParameterSpec(CodecUtil.decodeBase64(ivStr));

		cipher.init(Cipher.ENCRYPT_MODE, key, iv);

		byte[] encryptBytes = cipher.doFinal(data.getBytes(CharsetUtil.DEFAULT_CHARSET));
		return CodecUtil.encodeBase64(encryptBytes);
	}

	/**
	 * CBC模式-解密操作， CBC 模式可以用IV， ECB 模式不能用 IV
	 * 
	 * @param keyStr 进行了Base64编码的秘钥
	 * @param ivStr  进行了Base64编码的偏移量
	 * @param data   需要解密的数据（数据必须是通过AES进行加密后，对加密数据Base64编码的数据）
	 * @return String 返回解密后的原文
	 */
	public static String decryptByCBC(String keyStr, String ivStr, String data) throws Exception {
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);

		SecretKeySpec key = new SecretKeySpec(CodecUtil.decodeBase64(keyStr), KEY_ALGORITHM_AES);
		IvParameterSpec iv = new IvParameterSpec(CodecUtil.decodeBase64(ivStr));

		cipher.init(Cipher.DECRYPT_MODE, key, iv);

		byte[] decryptBytes = cipher.doFinal(CodecUtil.decodeBase64(data));
		return StringUtil.toEncodedString(decryptBytes, CharsetUtil.DEFAULT_CHARSET);
	}

	/**
	 * 生成AES的秘钥，秘钥进行了Base64编码的字符串
	 * 
	 * @return String 对生成的秘钥进行了Base64编码的字符串
	 */
	public static String keyGenerate() throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_ALGORITHM_AES);
		keyGenerator.init(new SecureRandom());
		SecretKey secretKey = keyGenerator.generateKey();
		byte[] keyBytes = secretKey.getEncoded();
		return CodecUtil.encodeBase64(keyBytes);
	}

}
