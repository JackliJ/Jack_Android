package jack.project.com.imdatautil.model;

import com.hyphenate.chat.adapter.message.EMAMessage;

import java.io.Serializable;

/**
 * Create by www.lijin@foxmail.com on 2018/1/18 0018.
 * <br/>
 * 消息实体
 */

public class MMessage implements Serializable {

    public String MsgID;//消息ID 唯一
    public long MsgTime;//消息被创建的时间
    public String MsgUID;//创建消息的人ID
    public String MsgUName;//创建消息的人名称
    public String MsgStringAttribute;//扩展参数
    public MMessageBody MsgBody;
    public MMessage.Type MsgType;//消息类型
    public MMessage.ChatType MsgChatType;//会话类型
    public MMessage.Direct MsgDirect;//消息方向

    public String getMsgStringAttribute(String var1, String var2) {
        if(var1 != null && !var1.equals("")) {
            return var1.toString();
        } else {
            return var2;
        }
    }

    public void setMsgStringAttribute(String msgStringAttribute) {
        MsgStringAttribute = msgStringAttribute;
    }

    public MMessageBody getMsgBody() {
        return MsgBody;
    }

    public void setMsgBody(MMessageBody msgBody) {
        MsgBody = msgBody;
    }

    public Type getMsgType() {
        return MsgType;
    }

    public Direct getMsgDirect() {
        return MsgDirect;
    }

    public void setMsgDirect(Direct msgDirect) {
        MsgDirect = msgDirect;
    }

    public void setMsgType(Type msgType) {
        MsgType = msgType;
    }

    public ChatType getMsgChatType() {
        return MsgChatType;
    }

    public void setMsgChatType(ChatType msgChatType) {
        MsgChatType = msgChatType;
    }

    public String getMsgID() {
        return MsgID;
    }

    public void setMsgID(String msgID) {
        MsgID = msgID;
    }

    public long getMsgTime() {
        return MsgTime;
    }

    public void setMsgTime(long msgTime) {
        MsgTime = msgTime;
    }

    public String getMsgUID() {
        return MsgUID;
    }

    public void setMsgUID(String msgUID) {
        MsgUID = msgUID;
    }

    public String getMsgUName() {
        return MsgUName;
    }

    public void setMsgUName(String msgUName) {
        MsgUName = msgUName;
    }




    /**
     * 会话类型
     */
    public static enum ChatType {
        Chat,        //单聊
        GroupChat,   //群组
        ChatRoom;    //直播(聊天室)

        private ChatType() {
        }
    }

    /**
     * 消息类型 枚举
     */
    public static enum Type {
        TXT,         //文本消息
        IMAGE,       //图片消息
        VIDEO,       //视屏消息
        LOCATION,    //地址消息
        VOICE,       //语音消息
        FILE,        //文件
        CMD,         //透传
        TEXT_TYPE;   //文本扩展类型

        private Type() {

        }
    }

    /**
     * 消息方向
     */
    public static enum Direct {
        SEND,         //发送方
        RECEIVE;      //接收方

        private Direct() {
        }
    }

}
