package jack.project.com.imdatautil;

import java.util.ArrayList;
import java.util.List;

import jack.project.com.imdatautil.callback.ImLoginCallback;
import jack.project.com.imdatautil.callback.ImMessageCallback;
import jack.project.com.imdatautil.model.MMSession;
import jack.project.com.imdatautil.model.MMessage;
import jack.project.com.imdatautil.model.body.MMessageTxTBody;
import jack.project.com.imdatautil.util.IMConfig;
import jack.project.mgrimintegration_hx.util.IMInitializeUtil_HX;

/**
 * Create by www.lijin@foxmail.com on 2018/1/18 0018.
 * <br/>
 * 开放出来的外部接口
 */

public class IMDataUtil {

    /**
     * 用户登录
     *
     * 需要将用户基本信息进行存储
     * @param config
     * @param account    账户
     * @param password   密码
     */
    public static void Login(IMConfig.ChatEPackageType config, String account, String password, ImLoginCallback imLoginCallback){
        switch (config){
            case IM_HuanXin:
                IMInitializeUtil_HX.Login(account,password,imLoginCallback);
                break;
            case IM_RongYun:
                break;
        }
    }

    /**
     * 新消息回调接口
     * @param callback
     */
    public static void MessageListener(IMConfig.ChatEPackageType config,ImMessageCallback callback){
        switch (config){
            case IM_HuanXin:
                //这里实现环信的回调方法
                MMessage mMessage = new MMessage();
                MMessageTxTBody mMessageTxTBody = (MMessageTxTBody)mMessage.MsgBody;
                mMessageTxTBody.setContent("sss");
                mMessage.setMsgBody(mMessageTxTBody);
                break;
            case IM_RongYun:
                //这里实现融云的回调方法
                break;
        }
    }

    /**
     * 获取会话列表
     * @param config   数据来源
     * @return
     */
    public static List<MMSession> getSessionList(IMConfig.ChatEPackageType config){
        List<MMSession> mMessages = new ArrayList<>();
        switch (config){
            case IM_HuanXin:
                MMSession mmSession;
                for (int i = 0;i<7;i++){
                    mmSession = new MMSession();
                    mmSession.setMMSUid("12345"+i);
                    mmSession.setMMSUserLeavl(i);
                    mmSession.setMMSUserNote("阿飞"+i);
                    mmSession.setMMSUserName("祁王"+i);
                    mMessages.add(mmSession);
                }
                break;
            case IM_RongYun:
                break;
        }
        return mMessages;
    }


}
