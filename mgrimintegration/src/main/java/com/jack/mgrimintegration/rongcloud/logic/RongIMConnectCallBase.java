package com.jack.mgrimintegration.rongcloud.logic;

import io.rong.imlib.RongIMClient;

/**
 * Create by www.lijin@foxmail.com on 2018/1/8 0008.
 * <br/>
 * 连接状态的CallBack
 */

public interface RongIMConnectCallBase {

    void onTokenIncorrect();

    void onSuccess(String userid);

    void onError(RongIMClient.ErrorCode errorCode);

}
