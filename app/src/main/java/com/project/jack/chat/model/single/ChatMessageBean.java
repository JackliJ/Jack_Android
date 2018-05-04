package com.project.jack.chat.model.single;

import java.io.Serializable;

/**
 * Create by www.lijin@foxmail.com on 2018/1/16 0016.
 * <br/>
 * 2018年1月16日：save:单聊界面的数据源(ChatMessageActivity)
 * 2018年1月16日：update:
 */

public class ChatMessageBean implements Serializable {

    private String ChatUUID;//聊天ID （环信：环信ID）
    private String ChatUserID;//(当前用户)用户ID  (与UUID 区分  一个用于聊天连接 一个用于保存自己的用户数据)
    private String ChatUserName;//(当前用户)用户昵称
    private String ChatUserNote;//(当前用户)用户备注 (可以为null 若有则传递)
    private String ChatUserAvatar;//(当前用户)用户头像
    private String ChatUserVipLevel;//(当前用户)用户会员等级
    private String ChatUserAuthStatus;//(当前用户)用户认证状态
    private String ChatUserBusinessAuthStatus;//(当前用户)用户商盟认证状态
    private String ChatUserBgImage;//(当前用户)用户个人背景

    private String ChatOtherUUID;//(对方用户)聊天ID （环信：环信ID）
    private String ChatOtherUID;//(对方用户)用户ID
    private String ChatOtherUserName;//(对方用户)用户名称
    private String ChatOtherUserNote;//(对方用户)用户备注
    private String ChatOtherUserAvatar;//(对方用户)头像v
    private String ChatOtherVipLevel;//(对方用户)用户会员等级
    private String ChatOtherAuthStatus;//(对方用户)用户认证状态
    private String ChatOtherBusinessAuthStatus;//(对方用户)用户商盟认证状态
    private String ChatOtherBgImage;//(对方用户)用户个人背景

    public String getChatUUID() {
        return ChatUUID;
    }

    public void setChatUUID(String chatUUID) {
        ChatUUID = chatUUID;
    }

    public String getChatUserID() {
        return ChatUserID;
    }

    public void setChatUserID(String chatUserID) {
        ChatUserID = chatUserID;
    }

    public String getChatUserName() {
        return ChatUserName;
    }

    public void setChatUserName(String chatUserName) {
        ChatUserName = chatUserName;
    }

    public String getChatUserNote() {
        return ChatUserNote;
    }

    public void setChatUserNote(String chatUserNote) {
        ChatUserNote = chatUserNote;
    }

    public String getChatUserAvatar() {
        return ChatUserAvatar;
    }

    public void setChatUserAvatar(String chatUserAvatar) {
        ChatUserAvatar = chatUserAvatar;
    }

    public String getChatOtherUUID() {
        return ChatOtherUUID;
    }

    public void setChatOtherUUID(String chatOtherUUID) {
        ChatOtherUUID = chatOtherUUID;
    }

    public String getChatOtherUID() {
        return ChatOtherUID;
    }

    public void setChatOtherUID(String chatOtherUID) {
        ChatOtherUID = chatOtherUID;
    }

    public String getChatOtherUserName() {
        return ChatOtherUserName;
    }

    public void setChatOtherUserName(String chatOtherUserName) {
        ChatOtherUserName = chatOtherUserName;
    }

    public String getChatOtherUserNote() {
        return ChatOtherUserNote;
    }

    public void setChatOtherUserNote(String chatOtherUserNote) {
        ChatOtherUserNote = chatOtherUserNote;
    }

    public String getChatOtherUserAvatar() {
        return ChatOtherUserAvatar;
    }

    public void setChatOtherUserAvatar(String chatOtherUserAvatar) {
        ChatOtherUserAvatar = chatOtherUserAvatar;
    }

    public String getChatUserVipLevel() {
        return ChatUserVipLevel;
    }

    public void setChatUserVipLevel(String chatUserVipLevel) {
        ChatUserVipLevel = chatUserVipLevel;
    }

    public String getChatUserAuthStatus() {
        return ChatUserAuthStatus;
    }

    public void setChatUserAuthStatus(String chatUserAuthStatus) {
        ChatUserAuthStatus = chatUserAuthStatus;
    }

    public String getChatUserBusinessAuthStatus() {
        return ChatUserBusinessAuthStatus;
    }

    public void setChatUserBusinessAuthStatus(String chatUserBusinessAuthStatus) {
        ChatUserBusinessAuthStatus = chatUserBusinessAuthStatus;
    }

    public String getChatUserBgImage() {
        return ChatUserBgImage;
    }

    public void setChatUserBgImage(String chatUserBgImage) {
        ChatUserBgImage = chatUserBgImage;
    }

    public String getChatOtherVipLevel() {
        return ChatOtherVipLevel;
    }

    public void setChatOtherVipLevel(String chatOtherVipLevel) {
        ChatOtherVipLevel = chatOtherVipLevel;
    }

    public String getChatOtherAuthStatus() {
        return ChatOtherAuthStatus;
    }

    public void setChatOtherAuthStatus(String chatOtherAuthStatus) {
        ChatOtherAuthStatus = chatOtherAuthStatus;
    }

    public String getChatOtherBusinessAuthStatus() {
        return ChatOtherBusinessAuthStatus;
    }

    public void setChatOtherBusinessAuthStatus(String chatOtherBusinessAuthStatus) {
        ChatOtherBusinessAuthStatus = chatOtherBusinessAuthStatus;
    }

    public String getChatOtherBgImage() {
        return ChatOtherBgImage;
    }

    public void setChatOtherBgImage(String chatOtherBgImage) {
        ChatOtherBgImage = chatOtherBgImage;
    }
}
