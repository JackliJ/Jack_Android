package jack.project.com.imdatautil.callback;

import java.util.List;

import jack.project.com.imdatautil.model.MMessage;

/**
 * Create by www.lijin@foxmail.com on 2018/1/18 0018.
 * <br/>
 * 消息回调
 */

public interface ImMessageCallback {

    //消息回调 (新消息)
    void onMessageReceived(List<MMessage> mMessages);

    //透传消息
    void onCmdMessageReceived(List<MMessage> mMessages);

    //收到已读回执
    void onMessageRead(List<MMessage> mMessages);

    //收到已送达回执
    void onMessageDelivered(List<MMessage> mMessages);

    //消息被撤回
    void onMessageRecalled(List<MMessage> mMessages);

    void onMessageChanged(MMessage mMessage,Object change);
}
