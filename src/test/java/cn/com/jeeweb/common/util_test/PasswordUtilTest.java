package cn.com.jeeweb.common.util_test;

import cn.com.jeeweb.common.util.PasswordUtil;
import cn.com.jeeweb.common.util.SystemUtil;
import org.junit.Test;

public class PasswordUtilTest {
    @Test
    public void test() {}

    /**
     * 测试加密的验证密码
     */
    public void testPassword() {
        String entryptPassword = PasswordUtil.entryptPassword("sxca123456");
        SystemUtil.outPrintln(entryptPassword);

        boolean validatePassword = PasswordUtil.validatePassword("123456",
                "fc8b8cff070c3379de0fa2c94b33f5d44c14269c673985552eddc86f");
        SystemUtil.outPrintln(validatePassword);

    }

    /**
     * 测试加密和解密
     */
    public void dePwdTest() {
        String p = "abc123";
        String enPwd = PasswordUtil.enPwd(p);
        System.out.println(enPwd);
        String pwd = PasswordUtil.dePwd(enPwd);
        System.out.println(pwd);
    }

}
