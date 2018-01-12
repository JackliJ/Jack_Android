package com.jack.mgrimintegration.rongcloud.logic;

/**
 * Create by www.lijin@foxmail.com on 2018/1/8 0008.
 * <br/>
 */

public interface LoginCallBack {

    /**
     * 返回注册成功的UID
     * @param UID
     */
    void onSuccess(String UID);

    /**
     *  错误回调
     * @param var1 code 0 登录失败 1 tooken为null 2 token错误
     * @param var2
     */
    void onError(int var1, String var2);

    void onProgress(int var1, String var2);
}
