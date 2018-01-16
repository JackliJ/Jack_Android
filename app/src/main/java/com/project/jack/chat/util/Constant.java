package com.project.jack.chat.util;

/**
 * Created by www.lijin@foxmail.com on 2017/10/21 0021.
 * <br/>
 * 常量管理
 */
public class Constant {

    //扩展字段类型 开始
    public static final String EXTENSION_FIELD_01 = "1";
    public static final String EXTENSION_FIELD_02 = "2";
    public static final String EXTENSION_FIELD_03 = "3";//实时视频
    public static final String EXTENSION_FIELD_04 = "4";
    public static final String EXTENSION_FIELD_05 = "5";
    public static final String EXTENSION_FIELD_06 = "6";
    public static final String EXTENSION_FIELD_07 = "7";//实时语音
    public static final String EXTENSION_FIELD_08 = "8";
    public static final String EXTENSION_FIELD_09 = "9";
    public static final String EXTENSION_FIELD_10 = "10";//名片推荐
    public static final String EXTENSION_FIELD_11 = "11";//脉友代付
    public static final String EXTENSION_FIELD_12 = "12";//邀请评价
    public static final String EXTENSION_FIELD_13 = "13";//分享动态
    public static final String EXTENSION_FIELD_14 = "14";//脉友红包
    public static final String EXTENSION_FIELD_15 = "15";//商铺主页
    public static final String EXTENSION_FIELD_16 = "16";//商品分享
    public static final String EXTENSION_FIELD_17 = "17";//咨询
    public static final String EXTENSION_FIELD_18 = "18";//群分享
    public static final String EXTENSION_FIELD_30 = "30";//大表情
    public static final String EXTENSION_FIELD_31 = "31";//领取红包提醒
    public static final String EXTENSION_FIELD_32 = "32";//脉友代付提醒

    public static final String CHAT_INTENT_BEAN = "CHAT_INTENT_BEAN";
    public static final String CHAT_INTENT_BUNDLE = "bundle";

    public static final String isSendRefrsh = "isSendRefrsh";//是否定位刷新

    public static final String isMarking = "isMarking";//是否群聊被@

    public static final String MIME_JPEG = "image/jpeg";


    public static final String SYSTEM_MESSAGE_INTERACTIVE = "SYSTEM_MESSAGE_INTERACTIVE";//交易消息未读消息数量
    public static final String SYSTEM_MESSAGE_TRADING = "SYSTEM_MESSAGE_TRADING";//互动消息未读消息数量
    public static final String SYSTEM_MESSAGE_NOTICE = "SYSTEM_MESSAGE_NOTICE";//系统消息未读消息数量

    public static final String LOGIN_CHAT_MESSAGE = "LOGIN_CHAT_MESSAGE";//环信登录标识
    public static final String LOGIN_CHAT_MESSAGE_PASSWORD = "LOGIN_CHAT_MESSAGE_PASSWORD";//环信登录标识

    //透传Action
    public static final String CMDMESSAGE_ACTION_REVOKE_FLAG = "REVOKE_FLAG";//消息撤回
    public static final String CMDMESSAGE_ACTION_REDBG_FLAG = "REDBG_FLAG";//红包状态更改
    public static final String CMDMESSAGE_ACTION_CONSULTING_FLAG = "CONSULTING_FLAG";//商家自动回复

    //阿里云透传消息数量
    public static final String CMDMESSAGE_ACTION_MYMESSAGECONTENT = "CMDMESSAGE_ACTION_MYMESSAGECONTENT";//脉友圈新消息数量
    public static final String CMDMESSAGE_ACTION_MYMESSAFEAVATAE = "CMDMESSAGE_ACTION_MYMESSAFEAVATAE";//新消息人头像

    //脉友屏蔽
    public static final String SHIELDING_CHAT = "_shielding_unblock_chat";
    public static final String SHIELDING_GROUND = "_shielding_unblock_group";

    //扩展字段类型 结束

    //视屏通话的缓存参数
    public static final String CHAT_CALL_VIDEO = "CHAT_CALL_VIDEO";
    public static final String CHAT_CALL_VIDEO_TYPE = "CHAT_CALL_VIDEO_TYPE";
    public static final String CHAT_CALL_VIDEO_STATUSA = "CHAT_CALL_VIDEO_STATUSA";
    //赋值通话被接通的状态
    public static final String CHAT_CALL_VIDEO_STATUS = "CHAT_CALL_VIDEO_STATUS";

    public static final String PICTURE_IMG_png = "image/png";
    public static final String PICTURE_IMG_jpeg = "image/jpeg";

    public static final String MEG_INTNT_CHATMESSAGE_HXID = "MEG_INTNT_CHATMESSAGE_HXID";//环信ID
    public static final String MEG_INTNT_CHATMESSAGE_CHATTYPE = "MEG_INTNT_CHATMESSAGE_CHATTYPE";//消息类型
    public static final String MEG_INTNT_CHATMESSAGE_OTHRTUID = "MEG_INTNT_CHATMESSAGE_OTHRTHXID";//对方用户ID
    public static final String MEG_INTNT_CHATMESSAGE_OTHERUSERNAME = "MEG_INTNT_CHATMESSAGE_OTHERUSERNAME";//对方用户昵称
    public static final String MEG_INTNT_CHATMESSAGE_OTHERUSERAVATAR = "MEG_INTNT_CHATMESSAGE_OTHERUSERAVATAR";//对方用户头像 avatar
    public static final String MEG_INTNT_CHATMESSAGE_OTHERVIPLEVEL= "MEG_INTNT_CHATMESSAGE_OTHERVIPLEVEL";//对方用户VIP等级
    public static final String MEG_INTNT_CHATMESSAGE_OTHERAURHSTATUS = "MEG_INTNT_CHATMESSAGE_OTHERAURHSTATUS";//对方用户实名认证状态
    public static final String MEG_INTNT_CHATMESSAGE_OTHERABUSINESSAU = "MEG_INTNT_CHATMESSAGE_OTHERABUSINESSAU";//对方用户企业认证状态
    public static final String MEG_INTNT_CHATMESSAGE_OTHERNAMGECARSBGIMAGE = "MEG_INTNT_CHATMESSAGE_OTHERNAMGECARSBGIMAGE";//对方用户的聊天背景


    public static final String MEG_INTNT_CHATMESSAGE_VOICEPATH = "MEG_INTNT_CHATMESSAGE_VOICEPATH";
    public static final String MEG_INTNT_CHATMESSAGE_VOICTIME = "MEG_INTNT_CHATMESSAGE_VOICTIME";



    public static final String EMOGI_CLASSIC_EXTENDED = "EMOGI_CLASSIC_EXTENDED";

    public static final String CHATSETUP_INTENT_OTHERHXID = "CHATSETUP_INTENT_OTHERHXID";
    public static final String CHATSETUP_INTENT_OTHERUID = "CHATSETUP_INTENT_OTHERUID";

    public static final String MEG_INTNT_GROUP_GROUPNAME = "MEG_INTNT_GROUP_GROUPNAME";//群名称
    public static final String MEG_INTNT_GROUP_GROUPMGRID = "MEG_INTNT_GROUP_GROUPMGRID";//脉果儿群ID
    public static final String MEG_INTNT_GROUP_GROUPHXID = "MEG_INTNT_GROUP_GROUPHXID";//环信群ID
    public static final String MEG_INTNT_GROUP_GROUPAVATAR = "MEG_INTNT_GROUP_GROUPAVATAR";//群头像
    public static final String MEG_INTNT_GROUP_GROUPTYPE = "MEG_INTNT_GROUP_GROUPTYPE";//群分类
    public static final String MEG_INTNT_GROUP_GROUPTYPEID = "MEG_INTNT_GROUP_GROUPTYPEID";//群分类ID
    public static final String MEG_INTNT_GROUP_GROUPJIES = "MEG_INTNT_GROUP_GROUPJIES";//群介绍

    public static final String SHARDPERFACE_NAME_SYSTEMSETUP = "SHARDPERFACE_NAME_SYSTEMSETUP";

    public static final String SORT_INTENT_TYPENAME = "SORT_INTENT_TYPENAME";
    public static final String SORT_INTENT_TYPEID = "SORT_INTENT_TYPEID";
    public static final int SORT_INTENT_TAG = 200;

    public static final String STRING_ATTRIBUTE_GROUP_UID = "uid";
    public static final String STRING_ATTRIBUTE_GROUP_USERNAME = "username";
    public static final String STRING_ATTRIBUTE_GROUP_USERAVATAR = "userAvatar";
    public static final String STRING_ATTRIBUTE_GROUP_VIPLEVEL = "vipLevel";
    public static final String STRING_ATTRIBUTE_GROUP_MARKING = "em_at_list";


    public static final String MEG_INTENT_LOGIN_REMOELOGIN = "MEG_INTENT_LOGIN_REMOELOGIN";

    public static final String MEG_INTENT_FREND_TYPE = "MEG_INTENT_FREND_TYPE";
    public static final String MEG_INTENT_FREND_ADDRESS = "MEG_INTENT_FREND_ADDRESS";

    public static final String MEG_INITENT_RED_CURTYPE = "MEG_INITENT_RED_CURTYPE";
    public static final String MEG_INITENT_RED_TARGETUID = "MEG_INITENT_RED_TARGETUID";
    public static final String MEG_INITENT_RED_GROUPID = "MEG_INITENT_RED_GROUPID";
    public static final String MEG_INITENT_RED_REDBAGID = "MEG_INITENT_RED_REDBAGID";//红包ID
    public static final String MEG_INITENT_RED_ROBTYPE = "MEG_INITENT_RED_ROBTYPE";//红包ID

    public static final String MEG_INTENT_CHATTYPE = "MEG_INTENT_CHATTYPE";
    public static final String MEG_INTENT_TEXTTYPE = "MEG_INTENT_TEXTTYPE";
    public static final String MEG_INTENT_FFilePath = "MEG_INTENT_FFilePath";
    public static final String MEG_INTENT_FLatitude = "MEG_INTENT_FLatitude";
    public static final String MEG_INTENT_Flongitude = "MEG_INTENT_Flongitude";
    public static final String MEG_INTENT_FDetailedAddress = "MEG_INTENT_FDetailedAddress";

    public static final String MEG_INTENT_TEXTCONTENT = "MEG_INTENT_TEXTCONTENT";
    public static final String MEG_INTENT_CHATTYPE_TXT = "MEG_INTENT_CHATTYPE_TXT";
    public static final String MEG_INTENT_CHATTYPE_IMAGE = "MEG_INTENT_CHATTYPE_IMAGE";
    public static final String MEG_INTENT_CHATTYPE_LOCATION = "MEG_INTENT_CHATTYPE_LOCATION";

    public static final String MEG_INTENT_PAY_ORDERNO = "MEG_INTENT_PAY_ORDERNO";
    public static final String MEG_INTENT_PAY_PAYFORMONEY = "MEG_INTENT_PAY_PAYFORMONEY";

    public static final String MEG_INTENT_GROUP_ISSWITCH = "MEG_INTENT_GROUP_ISSWITCH";


}
