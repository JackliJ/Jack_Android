package jack.project.com.imdatautil;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import jack.project.com.imdatautil.util.IMConfig;
import jack.project.mgrimintegration_hx.util.IMInitializeUtil_HX;

/**
 * Create by www.lijin@foxmail.com on 2018/1/31 0031.
 * <br/>
 * 发送方法的接口
 */

public class IMSendUtil {

    /**
     * 重发消息
     * @param config    标识
     * @param emMessage 消息实体
     */
    public static void resendMessage(IMConfig.ChatEPackageType config, EMMessage emMessage){
        switch (config){
            case IM_RongYun:
                break;
            case IM_HuanXin:
                emMessage.setStatus(EMMessage.Status.CREATE);
                EMClient.getInstance().chatManager().sendMessage(emMessage);
                break;
        }
    }

    /**
     * 发送文本消息
     */
    public static void SendText(IMConfig.ChatEPackageType config,String Content,String HXID,
                                String uid,
                                String username,
                                String avatar,
                                String vipLevel,
                                String authStatus,
                                String businessAuthStatus,
                                String bgImage,
                                String otherUid,
                                String otherUsername,
                                String otherUserAvatar,
                                String otherVipLevel,
                                String otherAuthStatus,
                                String otherBusinessAuthStatus,
                                String otherNamgeCardBgImage,
                                String text_type,
                                jack.project.mgrimintegration_hx.callbcak.EMCallBack callBack){
        switch (config){
            case IM_HuanXin:
                IMInitializeUtil_HX.hx_SendText(Content,HXID,uid,
                        username,avatar,vipLevel,authStatus,businessAuthStatus,
                        bgImage,otherUid,otherUsername,otherUserAvatar,otherVipLevel,
                        otherAuthStatus,otherBusinessAuthStatus,otherNamgeCardBgImage,text_type,callBack);
                break;
            case IM_RongYun:
                break;
        }
    }
}
