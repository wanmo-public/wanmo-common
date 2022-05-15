package cn.com.jeeweb.common.util_test;

import cn.com.jeeweb.common.util.Ini4jUtil;
import org.ini4j.Profile;
import org.junit.Test;

public class Ini4jUtilTest {
    @Test
    public void test() {

    }

    public void iniConfTest() {
        System.out.println(Ini4jUtil.iniDefault);
    }

    public void getValueTest() {
        Profile.Section wanmoCommon = Ini4jUtil.getSection("wanmo-common");
        System.out.println(Ini4jUtil.getValue(wanmoCommon, "test-key"));
    }

    public void getIniTest() {
        System.out.println(Ini4jUtil.getIni("wanmo-system-custom.ini"));
    }

}
