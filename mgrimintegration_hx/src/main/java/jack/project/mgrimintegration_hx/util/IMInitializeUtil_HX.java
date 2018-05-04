package jack.project.mgrimintegration_hx.util;

import android.content.Context;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import jack.project.mgrimintegration_hx.callbcak.LoginCallBack;

/**
 * Create by www.lijin@foxmail.com on 2018/1/19 0019.
 * <br/>
 *
 */

public class IMInitializeUtil_HX {

    /**
     * 登录
     * @param userName         账户名称
     * @param password         账户密码
     * @param loginCallBack    回调
     */
    public static void Login(String userName,String password,LoginCallBack loginCallBack){
        EMClient.getInstance().login(userName,password,loginCallBack);
    }

    /**
     * 发送文本消息
     * @param Content               文本内容
     * @param HXID                  环信ID
     * @param uid                   用户ID
     * @param username              用户名称
     * @param avatar                用户头像
     * @param vipLevel              用户VIP
     * @param authStatus            用户认证状态
     * @param businessAuthStatus    用户商户认证状态
     * @param bgImage               用户背景头像
     * @param otherUid              对方用户id
     * @param otherUsername         对方用户名称
     * @param otherUserAvatar       对方用户头像
     * @param otherVipLevel         对方用户VIP
     * @param otherAuthStatus       对方用户认证状态
     * @param otherBusinessAuthStatus 对方用户商盟认证状态
     * @param otherNamgeCardBgImage  对方用户背景头像
     * @param text_type             文本扩展类型
     * @param callBack              消息回调
     */
    public static void hx_SendText(String Content,
                                   String HXID,
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
                                   String text_type, jack.project.mgrimintegration_hx.callbcak.EMCallBack callBack){
        //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
        EMMessage message = EMMessage.createTxtSendMessage(Content, HXID);
        message.setTo(HXID);
        message.setChatType(EMMessage.ChatType.Chat);
        if (text_type != null) {
            message.setAttribute("text_type", text_type);
        }
        //设置离线推送 (仅针对小米，华为机型)
        message.setAttribute("em_force_notification", true);
        message.setAttribute("uid", uid);
        message.setAttribute("username", username);
        message.setAttribute("userAvatar", avatar);
        message.setAttribute("vipLevel", String.valueOf(vipLevel));
        message.setAttribute("authStatus", String.valueOf(authStatus));
        message.setAttribute("businessAuthStatus", String.valueOf(businessAuthStatus));
        message.setAttribute("namgeCardBgImage", bgImage);
        message.setAttribute("otherUid", otherUid);
        message.setAttribute("otherUsername", otherUsername);
        message.setAttribute("otherUserAvatar", otherUserAvatar);
        message.setAttribute("otherVipLevel", otherVipLevel);
        message.setAttribute("otherAuthStatus", otherAuthStatus);
        message.setAttribute("otherBusinessAuthStatus", otherBusinessAuthStatus);
        message.setAttribute("otherNamgeCardBgImage", otherNamgeCardBgImage);
        message.setMessageStatusCallback(callBack);
        //发送消息
        EMClient.getInstance().chatManager().sendMessage(message);

    }
}
