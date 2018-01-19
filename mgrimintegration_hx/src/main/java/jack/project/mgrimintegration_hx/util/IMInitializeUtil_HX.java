package jack.project.mgrimintegration_hx.util;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import jack.project.mgrimintegration_hx.callbcak.LoginCallBack;

/**
 * Create by www.lijin@foxmail.com on 2018/1/19 0019.
 * <br/>
 */

public class IMInitializeUtil_HX {

    public static void Login(String userName,String password,LoginCallBack loginCallBack){
        EMClient.getInstance().login(userName,password,loginCallBack);
    }
}
