package com.jack.mgrimintegration.rongcloud.interfaceshare;

import android.content.Context;
import android.text.TextUtils;
import com.jack.mgrimintegration.rongcloud.io.rong.RongCloud;
import com.jack.mgrimintegration.rongcloud.io.rong.models.TokenResult;
import com.jack.mgrimintegration.rongcloud.logic.LoginCallBack;
import com.jack.mgrimintegration.rongcloud.logic.RongIMConnectCallBase;

import io.rong.imlib.OnReceiveMessageListener;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;

/**
 * Create by www.lijin@foxmail.com on 2018/1/8 0008.
 * <br/>
 * 融云API
 */

public class RongCloudIM {

    private static Context mContext;
    private static RongCloudIM instance = null;
    static RongCloud rongCloud;

    private static String ry_app_key = "0vnjpoad0gfuz";
    private static String ry_app_secret = "WVAy092iShCbR";

    /**
     * 加载单例模式
     * @return
     */
    public static RongCloudIM getInstance() {
        if(instance == null) {
            loadLibrary();
            instance = new RongCloudIM();
        }

        return instance;
    }

    public static void setRoogContext(Context context){
        mContext = context;
    }

    /**
     * 初始化调用
     */
    private static void loadLibrary() {
        rongCloud = RongCloud.getInstance(ry_app_key, ry_app_secret);
    }


    /**
     * 初始化融云SDK
     * @param mContext
     */
    public void init(Context mContext){
        //赋值上下文
        setRoogContext(mContext);
        //初始化SDK
        RongIMClient.init(mContext,ry_app_key);
        RongIMClient.getInstance().setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
            @Override
            public boolean onReceived(Message message, int i) {
                return false;
            }
        });
    }

    /**
     * 获取融云Token
     * 在连接融云的服务器前 必须先获取到Token UID为自己定义
     * 可以理解为两个UID 进行聊天
     * @param UID      聊天的ID 自己创建
     * @param UserName 用户的昵称 自己创建
     * @param Avatar   用户的头像 自己创建
     */
    public String getToken(String UID,String UserName,String Avatar) throws Exception {
        TokenResult userGetTokenResult = rongCloud.user.getToken(UID,UserName,Avatar);
        return userGetTokenResult.toString();
    }

    /**
     * 连接融云服务器 前提是获取到token
     * @param token
     * @param callback
     */
    public void connect(String token, final RongIMConnectCallBase callback){
            RongIMClient.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
                 *                            2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
                 */
                @Override
                public void onTokenIncorrect() {
                    callback.onTokenIncorrect();
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token 对应的用户 id
                 */
                @Override
                public void onSuccess(String userid) {
                    callback.onSuccess(userid);
                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    callback.onError(errorCode);
                }
            });
    }


    /**
     * 登录方法
     * @param account  账户名
     * @param password 密码
     * @param callBack 回调
     */
    public void Login(String account, String password, String avatar, final LoginCallBack callBack) throws Exception {
        //获取Token
        String token = getToken(account,password,avatar);
        if(!TextUtils.isEmpty(token)){
            connect(token, new RongIMConnectCallBase() {
                @Override
                public void onTokenIncorrect() {
                    callBack.onError(2,"Token有误，连接失败");
                }

                @Override
                public void onSuccess(String userid) {
                    callBack.onSuccess(userid);
                }

                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    //可以在这里进行Code码的判断 http://www.rongcloud.cn/docs/status_code.html
                    //根据自己的业务逻辑需求
                    callBack.onError(0,errorCode.getMessage());
                }
            });
        }else{
            callBack.onError(1,"Token为null");
        }
    }
}


