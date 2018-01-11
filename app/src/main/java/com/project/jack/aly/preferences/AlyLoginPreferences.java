package com.project.jack.aly.preferences;

import android.content.Context;

import com.project.jack.utils.SharedPreferencedUtils;

/**
 * Create by www.lijin@foxmail.com on 2017/12/28 0028.
 * <br/>
 * 用于保存登录的账户密码
 */

public class AlyLoginPreferences {

    //账户
    private static final String KEY_USER_ACCOUNT = "account";
    //密码
    private static final String KEY_USER_TOKEN = "token";

    //将账户保存到本地
    public static void saveUserAccount(Context mContext,String account) {
        saveLoginString(mContext,KEY_USER_ACCOUNT, account);
    }

    public static String getUserAccount(Context mContext) {
        return getLoginString(mContext,KEY_USER_ACCOUNT);
    }

    //将密码保存到本地
    public static void saveUserToken(Context mContext,String token) {
        saveLoginString(mContext,KEY_USER_TOKEN, token);
    }

    public static String getUserToken(Context mContext) {
        return getLoginString(mContext,KEY_USER_TOKEN);
    }

    private static void saveLoginString(Context mContext,String key, String value) {
        SharedPreferencedUtils.setString(mContext,key,value);
    }

    private static String getLoginString(Context mContext,String key) {
        return SharedPreferencedUtils.getString(mContext, key);
    }

}
