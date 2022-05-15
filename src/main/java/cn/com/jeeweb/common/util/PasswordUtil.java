package cn.com.jeeweb.common.util;

/**
 * 密码 加密解密工具类
 */
public class PasswordUtil {

    public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_INTERATIONS = 1024;
    public static final int SALT_SIZE = 8;

    /**
     * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
     */
    public static String entryptPassword(String plainPassword) {
        String plain = CodecUtil.unescapeHtml(plainPassword);
        byte[] salt = Digests.generateSalt(SALT_SIZE);
        byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, HASH_INTERATIONS);
        return CodecUtil.encodeHex(salt) + CodecUtil.encodeHex(hashPassword);
    }

    /**
     * 验证密码
     * 
     * @param plainPassword 明文密码
     * @param password      密文密码
     * @return 验证成功返回true
     */
    public static boolean validatePassword(String plainPassword, String password) {
        String plain = CodecUtil.unescapeHtml(plainPassword);
        byte[] salt = CodecUtil.decodeHex(password.substring(0, 16));
        byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, HASH_INTERATIONS);
        return password.equals(CodecUtil.encodeHex(salt) + CodecUtil.encodeHex(hashPassword));
    }

    /**
     * 密码加密
     */
    public static String enPwd(String pwd) {
        String s;
        try {
            String keyStr = StringUtil.getRandomCode(16);

            String encryptStr = AesUtil.encryptByECB(CodecUtil.encodeBase64(keyStr), pwd);
            s = keyStr + encryptStr;
        } catch (Exception e) {
            s = StringUtil.EMPTY;
        }
        return s;
    }

    /**
     * 密码解密
     */
    public static String dePwd(String pwd) {
        String s;
        try {
            if (StringUtil.isBlank(pwd)) {
                return StringUtil.EMPTY;
            }

            String keyStr = pwd.substring(0, 16);
            String encryptStr = pwd.substring(16);

            String decryptStr = AesUtil.decryptByECB(CodecUtil.encodeBase64(keyStr), encryptStr);

            return decryptStr;
        } catch (Exception e) {
            s = StringUtil.EMPTY;
        }
        return s;
    }

}
