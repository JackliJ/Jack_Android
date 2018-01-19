package jack.project.com.imdatautil.model;

import java.io.Serializable;

/**
 * Create by www.lijin@foxmail.com on 2018/1/18 0018.
 * <br/>
 * 会话实体
 */

public class MMSession implements Serializable {

    private String MMSUid;//用户UID(群组ID)
    private int MMSUserLeavl;//用户VIP级别
    private String MMSUserNote;//用户备注
    private String MMSUserName;//用户昵称(群组名称)
    private String MMSUserAvatar;//用户头像(群组头像)
    private String MMSUserContext;//最后一条消息文本
    private int MMSUserUnRead;//未读消息数量
    private long MMSEndTime;//会话最后一条消息的时间
    private String MMSUUid;//用户的第三方User(如环信的HxID)ID
    private String MMSAuthStatus;//用户实名认证状态
    private String MMSBusinessAuthStatus;//用户企业认证状态
    private String MMSOtherNamgeCardBgImage;//对方用户名片背景图片
    private MMessage.Type MMSType;//消息类型
    private MMessage.Direct MMSIsReceiveSend;//是发送方(true) 还是 接收方(false)
    private MMessage.ChatType MMSChatType;//聊天类型

    public String getMMSUid() {
        return MMSUid;
    }

    public void setMMSUid(String MMSUid) {
        this.MMSUid = MMSUid;
    }

    public int getMMSUserLeavl() {
        return MMSUserLeavl;
    }

    public void setMMSUserLeavl(int MMSUserLeavl) {
        this.MMSUserLeavl = MMSUserLeavl;
    }

    public String getMMSUserNote() {
        return MMSUserNote;
    }

    public void setMMSUserNote(String MMSUserNote) {
        this.MMSUserNote = MMSUserNote;
    }

    public String getMMSUserName() {
        return MMSUserName;
    }

    public void setMMSUserName(String MMSUserName) {
        this.MMSUserName = MMSUserName;
    }

    public String getMMSUserAvatar() {
        return MMSUserAvatar;
    }

    public void setMMSUserAvatar(String MMSUserAvatar) {
        this.MMSUserAvatar = MMSUserAvatar;
    }

    public String getMMSUserContext() {
        return MMSUserContext;
    }

    public void setMMSUserContext(String MMSUserContext) {
        this.MMSUserContext = MMSUserContext;
    }

    public int getMMSUserUnRead() {
        return MMSUserUnRead;
    }

    public void setMMSUserUnRead(int MMSUserUnRead) {
        this.MMSUserUnRead = MMSUserUnRead;
    }

    public long getMMSEndTime() {
        return MMSEndTime;
    }

    public void setMMSEndTime(long MMSEndTime) {
        this.MMSEndTime = MMSEndTime;
    }

    public String getMMSUUid() {
        return MMSUUid;
    }

    public void setMMSUUid(String MMSUUid) {
        this.MMSUUid = MMSUUid;
    }

    public String getMMSAuthStatus() {
        return MMSAuthStatus;
    }

    public void setMMSAuthStatus(String MMSAuthStatus) {
        this.MMSAuthStatus = MMSAuthStatus;
    }

    public String getMMSBusinessAuthStatus() {
        return MMSBusinessAuthStatus;
    }

    public void setMMSBusinessAuthStatus(String MMSBusinessAuthStatus) {
        this.MMSBusinessAuthStatus = MMSBusinessAuthStatus;
    }

    public String getMMSOtherNamgeCardBgImage() {
        return MMSOtherNamgeCardBgImage;
    }

    public void setMMSOtherNamgeCardBgImage(String MMSOtherNamgeCardBgImage) {
        this.MMSOtherNamgeCardBgImage = MMSOtherNamgeCardBgImage;
    }

    public MMessage.Type getMMSType() {
        return MMSType;
    }

    public void setMMSType(MMessage.Type MMSType) {
        this.MMSType = MMSType;
    }

    public MMessage.Direct getMMSIsReceiveSend() {
        return MMSIsReceiveSend;
    }

    public void setMMSIsReceiveSend(MMessage.Direct MMSIsReceiveSend) {
        this.MMSIsReceiveSend = MMSIsReceiveSend;
    }

    public MMessage.ChatType getMMSChatType() {
        return MMSChatType;
    }

    public void setMMSChatType(MMessage.ChatType MMSChatType) {
        this.MMSChatType = MMSChatType;
    }
}
