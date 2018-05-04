package jack.project.com.imdatautil;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMMessageBody;
import com.hyphenate.chat.EMTextMessageBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jack.project.com.imdatautil.callback.ImLoginCallback;
import jack.project.com.imdatautil.callback.ImMessageCallback;
import jack.project.com.imdatautil.model.MMFrend;
import jack.project.com.imdatautil.model.MMSession;
import jack.project.com.imdatautil.model.MMessage;
import jack.project.com.imdatautil.model.MMessageBody;
import jack.project.com.imdatautil.model.body.MMessageTxTBody;
import jack.project.com.imdatautil.model.body.MMessageVoiceBody;
import jack.project.com.imdatautil.util.Constent;
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
     * <p>
     * 需要将用户基本信息进行存储
     *
     * @param config
     * @param account  账户
     * @param password 密码
     */
    public static void Login(IMConfig.ChatEPackageType config, String account, String password, ImLoginCallback imLoginCallback) {
        switch (config) {
            case IM_HuanXin:
                IMInitializeUtil_HX.Login(account, password, imLoginCallback);
                break;
            case IM_RongYun:

                break;
        }
    }

    /**
     * 新消息回调接口
     *
     * @param callback
     */
    public static void MessageListener(IMConfig.ChatEPackageType config, ImMessageCallback callback) {
        switch (config) {
            case IM_HuanXin:
                //这里实现环信的回调方法
                MMessage mMessage = new MMessage();
                MMessageTxTBody mMessageTxTBody = (MMessageTxTBody) mMessage.MsgBody;
                mMessageTxTBody.setContent("sss");
                mMessage.setMsgBody(mMessageTxTBody);
                break;
            case IM_RongYun:
                //这里实现融云的回调方法
                break;
        }
    }


    /**
     * 获取当前用户与某人的会话记录
     *
     * @param config  数据来源方向
     * @param TagIMID 会话ID
     * @return
     */
    public static List<MMessage> getSessionPersonal(IMConfig.ChatEPackageType config, String TagIMID) {
        final List<MMessage> mEssageList = new ArrayList<>();
        switch (config) {
            case IM_HuanXin:
                //获取当前用户的会话数据
                EMConversation conversation = EMClient.getInstance().chatManager().getConversation(TagIMID);
                if (conversation != null) {
                    List<EMMessage> message = conversation.getAllMessages();
                    MMessage mMessage = new MMessage();
                    for (int i = 0; i < message.size(); i++) {
                        mMessage.setMsgUID(message.get(i).getMsgId());
                        mMessage.setMsgUName(message.get(i).getUserName());
                        mMessage.setMsgType(getMsgType(message.get(i).getType()));
                        mMessage.setMsgID(message.get(i).getMsgId());
                        mMessage.setMsgChatType(getMsgChatType(message.get(i).getChatType()));
                        mMessage.setMsgDirect(getMsgDirect(message.get(i).direct()));
                        mMessage.setMsgBody(getMsgBody(message.get(i).getBody(),message.get(i)));
                        mMessage.setMsgTime(message.get(i).getMsgTime());
                    }

                }
                return mEssageList;
            case IM_RongYun:
                return mEssageList;
        }
        return mEssageList;
    }

    public static MMessageBody getMsgBody(EMMessageBody body,EMMessage message){
        switch (message.getType()){
            case TXT:
                MMessageTxTBody mMessageTxTBody = new MMessageTxTBody();
                mMessageTxTBody.setContent(body.toString());
                return mMessageTxTBody;
            case IMAGE:
                break;
            case VIDEO:
                break;
            case VOICE:
                break;
        }
        return null;
    }

    /**
     * 将环信的聊天数据类型转换为通用数据类型
     * @param type
     * @return
     */
    public static MMessage.Type getMsgType(EMMessage.Type type){
        switch (type){
            case FILE:
                return MMessage.Type.FILE;
            case CMD:
                return MMessage.Type.CMD;
            case IMAGE:
                return MMessage.Type.IMAGE;
            case LOCATION:
                return MMessage.Type.LOCATION;
            case VIDEO:
                return MMessage.Type.VIDEO;
            case VOICE:
                return MMessage.Type.VOICE;
            case TXT:
                return MMessage.Type.TXT;
        }
        return null;
    }

    /**
     * 将环信的会话数据类型转换为通用数据类型
     * @param type
     * @return
     */
    public static MMessage.ChatType getMsgChatType(EMMessage.ChatType type){
        switch (type){
            case ChatRoom:
                return MMessage.ChatType.ChatRoom;
            case Chat:
                return MMessage.ChatType.Chat;
            case GroupChat:
                return MMessage.ChatType.GroupChat;
        }
        return null;
    }

    /**
     * 根据环信的接收方类型 返回通用的接收方类型
     * @param direct
     * @return
     */
    public static MMessage.Direct getMsgDirect(EMMessage.Direct direct){
        switch (direct){
            case SEND:
                return MMessage.Direct.SEND;
            case RECEIVE:
                return MMessage.Direct.RECEIVE;
        }
        return  null;
    }


    /**
     * 获取会话列表
     *
     * @param config 数据来源
     * @return
     */
    public static List<MMSession> getSessionList(IMConfig.ChatEPackageType config) {
        List<MMSession> mMessages = new ArrayList<>();
        MMSession mmSession;
        switch (config) {
            case IM_HuanXin:
//                MMSession mmSession;
//                for (int i = 0;i<3;i++){
//                    mmSession = new MMSession();
//                    mmSession.setMMSUid("12345"+i);
//                    mmSession.setMMSUserLeavl(i);
//                    if(i == 0){
//                        mmSession.setMMSUserNote("王思聪");
//                        mmSession.setMMSUserName("王思聪");
//                    }else if(i == 1){
//                        mmSession.setMMSUserNote("范冰冰");
//                        mmSession.setMMSUserName("范冰冰");
//                    }else if(i == 2){
//                        mmSession.setMMSUserNote("胡歌");
//                        mmSession.setMMSUserName("琅琊榜");
//                    }
//                    if(i == 0){
//                        mmSession.setMMSUserAvatar("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3718858439,1664323015&fm=27&gp=0.jpg");
//                    }else if(i == 1){
//                        mmSession.setMMSUserAvatar("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1516713218372&di=99fc65e4532a2b8c76c8b8a3c7a11360&imgtype=jpg&src=http%3A%2F%2Fimg1.imgtn.bdimg.com%2Fit%2Fu%3D72407761%2C308001660%26fm%3D214%26gp%3D0.jpg");
//                    }else if(i == 2){
//                        mmSession.setMMSUserAvatar("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1516713251415&di=da5930ebf7f37928af0c20cb8092008f&imgtype=0&src=http%3A%2F%2Fimg2.touxiang.cn%2Ffile%2F20171116%2F6c115e9dedbaddda2a92e14da3a8706a.jpg");
//                    }
//                    if(i == 0){
//                        mmSession.setMMSUserContext("晚上一起吃鸡，陈赫也来");
//                    }else if(i == 1){
//                        mmSession.setMMSUserContext("晚上李晨不在家哦");
//                    }else if(i == 2){
//                        mmSession.setMMSUserContext("李导，凡人修仙传什么时候开拍啊！");
//                    }
//
//                    mmSession.setMMSUserUnRead(i+1);
//                    mmSession.setMMSEndTime(1516700246);
//                    mmSession.setMMSUUid("5416548"+i);
//                    mmSession.setMMSAuthStatus("3" + i);
//                    mmSession.setMMSBusinessAuthStatus("2");
//                    mmSession.setMMSOtherNamgeCardBgImage("http://e.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=22330fbff4246b607b5bba70dec8367a/8326cffc1e178a82253b2e4cf403738da877e8d3.jpg");
//                    mmSession.setMMSType(MMessage.Type.TXT);
//                    mmSession.setMMSChatType(MMessage.ChatType.Chat);
//                    mmSession.setMMSIsReceiveSend(MMessage.Direct.SEND);
//                    mMessages.add(mmSession);
//                }
                int MesgCount = 0;
                Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
                if (conversations.size() > 0 && conversations != null) {
                    MesgCount = 0;
                    for (String i : conversations.keySet()) {
                        MesgCount = MesgCount + conversations.get(i).getUnreadMsgCount();
                        List<EMMessage> messages = conversations.get(i).getAllMessages();
                        int mMesPositon = messages.size() - 1;
                        if (messages != null && messages.size() > 0) {
                            if (!messages.get(mMesPositon).getUserName().equals(Constent.customer_service)) {//去掉小秘书
                                mmSession = new MMSession();
                                if (messages.get(mMesPositon).getChatType().toString().equals(EMMessage.ChatType.Chat.toString())) {//单聊
                                    if (messages.get(mMesPositon).direct() == EMMessage.Direct.RECEIVE) {//最后一条消息是接受方
                                        mmSession.setMMSUid(messages.get(mMesPositon).getStringAttribute(Constent.uid, null));
                                        mmSession.setMMSUserLeavl(messages.get(mMesPositon).getIntAttribute(Constent.vipLevel, 0));
                                        String uid = messages.get(mMesPositon).getStringAttribute(Constent.uid, null);
                                        mmSession.setMMSUserName(getusernote(uid) != null ?
                                                getusernote(uid) : messages.get(mMesPositon).getStringAttribute(Constent.username, null));
                                        mmSession.setMMSUserAvatar(messages.get(mMesPositon).getStringAttribute(Constent.userAvatar, null));
                                        mmSession.setMMSAuthStatus(messages.get(mMesPositon).getStringAttribute(Constent.authStatus, null));
                                        mmSession.setMMSBusinessAuthStatus(messages.get(mMesPositon).getStringAttribute(Constent.businessAuthStatus, null));
                                        mmSession.setMMSOtherNamgeCardBgImage(messages.get(mMesPositon).getStringAttribute(Constent.namgeCardBgImage, null));
                                    } else { //最后一条消息是发送方
                                        mmSession.setMMSUid(messages.get(mMesPositon).getStringAttribute(Constent.otherUid, null));
                                        mmSession.setMMSUserLeavl(messages.get(mMesPositon).getIntAttribute(Constent.otherVipLevel, 0));
                                        String uid = messages.get(mMesPositon).getStringAttribute(Constent.otherUid, null);
                                        mmSession.setMMSUserName(getusernote(uid) != null ?
                                                getusernote(uid) : messages.get(mMesPositon).getStringAttribute(Constent.otherUsername, null));
                                        mmSession.setMMSUserAvatar(messages.get(mMesPositon).getStringAttribute(Constent.otherUserAvatar, null));
                                        mmSession.setMMSAuthStatus(messages.get(mMesPositon).getStringAttribute(Constent.otherAuthStatus, null));
                                        mmSession.setMMSBusinessAuthStatus(messages.get(mMesPositon).getStringAttribute(Constent.otherBusinessAuthStatus, null));
                                        mmSession.setMMSOtherNamgeCardBgImage(messages.get(mMesPositon).getStringAttribute(Constent.otherNamgeCardBgImage, null));
                                    }
                                } else {//群聊
                                    mmSession.setMMSUid(messages.get(mMesPositon).getStringAttribute(Constent.uid, null));
                                    mmSession.setMMSUserLeavl(messages.get(mMesPositon).getIntAttribute(Constent.vipLevel, 0));
                                    mmSession.setMMSUserName(messages.get(mMesPositon).getStringAttribute(Constent.username, null));
                                    mmSession.setMMSUserAvatar(messages.get(mMesPositon).getStringAttribute(Constent.userAvatar, null));
                                    mmSession.setMMSAuthStatus(messages.get(mMesPositon).getStringAttribute(Constent.authStatus, null));
                                    mmSession.setMMSBusinessAuthStatus(messages.get(mMesPositon).getStringAttribute(Constent.businessAuthStatus, null));
                                    mmSession.setMMSOtherNamgeCardBgImage(messages.get(mMesPositon).getStringAttribute(Constent.namgeCardBgImage, null));
                                }

                                try {
                                    mmSession.setMMSUserUnRead(conversations.get(i).getUnreadMsgCount());//获取未读消息的数量
                                } catch (Exception e) {
                                    mmSession.setMMSUserUnRead(0);
                                }
                                mmSession.setMMSUUid(conversations.get(i).conversationId());//赋值环信用户ID
                                mmSession.setMMSEndTime(messages.get(mMesPositon).getMsgTime());//赋值最后一条消息的时间
                                mmSession.setMMSUserContext(messages.get(mMesPositon).getBody().toString());//赋值最后一条消息
                                mmSession.setMMSType(IMConfig.getType(messages.get(mMesPositon).getType()));//赋值消息类型
                                mmSession.setMMSChatType(getChatType(messages.get(mMesPositon)));
                                mmSession.setMsgTypeString(messages.get(mMesPositon).getStringAttribute(Constent.text_type, null));//扩展类型
                                mMessages.add(mmSession);
                                //按照时间进行排序
                                ListSort(mMessages);
                            }
                        }
                    }
                } else {
                    Log.d("ChatInterfaceActivity", " is call list null ");
                }
                break;
            case IM_RongYun:
                break;
        }
        return mMessages;
    }

    /**
     * 根据ID获取字段
     *
     * @param uid
     * @return
     */
    private static String getusernote(String uid) {
        if (!TextUtils.isEmpty(uid)) {
            int userid = Integer.parseInt(uid);
//            Frend frend = FrendDao.getFrend(mContext, userid);
//            if (frend != null) {
//                if (!TextUtils.isEmpty(frend.userNote)) {//昵称
//                    return frend.userNote;
//                } else {
//                    return frend.username;
//                }
//            }
        }
        return null;
    }


    /**
     * 将消息数据按照时间排序  最新的消息在最上面
     *
     * @param list
     */
    private static void ListSort(List<MMSession> list) {
        Collections.sort(list, new Comparator<MMSession>() {
            @Override
            public int compare(MMSession o1, MMSession o2) {
                SimpleDateFormat formatd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date dt1 = formatd.parse(formatd.format(o1.getMMSEndTime()));
                    Date dt2 = formatd.parse(formatd.format(o2.getMMSEndTime()));
                    if (dt1.getTime() < dt2.getTime()) {
                        return 1;
                    } else if (dt1.getTime() > dt2.getTime()) {
                        return -1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }


    public static MMessage.ChatType getChatType(EMMessage message) {
        if (message.getChatType().toString().equals(EMMessage.ChatType.Chat.toString())) {
            return MMessage.ChatType.Chat;
        } else if (message.getChatType().toString().equals(EMMessage.ChatType.GroupChat.toString())) {
            return MMessage.ChatType.GroupChat;
        }
        return MMessage.ChatType.Chat;
    }

    /**
     * 根据第三方ID删除某个会话
     *
     * @param UUID
     */
    public static void DeleteSession(IMConfig.ChatEPackageType config, String UUID) {

    }

    /**
     * 获取所有好友数据，根据字母进行排序，PinYin字段
     */
    public static List<MMFrend> GetAllByPYAsc(IMConfig.ChatEPackageType config, Context context) {
        List<MMFrend> mmFrends = new ArrayList<>();
        switch (config) {
            case IM_HuanXin:
                MMFrend mmFrend;
                for (int i = 0; i < 2; i++) {
                    mmFrend = new MMFrend();
                    if (i == 0) {
                        mmFrend.setMFlogin_uid(1);
                        mmFrend.setMFUid(1);
                        mmFrend.setMFUUID("ces1");
                        mmFrend.setMFStoreId(1);
                        mmFrend.setMFUUName("ces1");
                        mmFrend.setMFAvatar("http://d.hiphotos.baidu.com/baike/pic/item/b8389b504fc2d56236183555e41190ef76c66c72.jpg");
                        mmFrend.setMFUserName("测试1");
                        mmFrend.setMFUnionLevel(1);
                        mmFrend.setMFVipLevel(1);
                        mmFrend.setMFAuthStatus(0);
                        mmFrend.setMFBusinessAuthStatus(1);
                        mmFrend.setMFGender(1);
                        mmFrend.setMFOccupation("太空事业");
                        mmFrend.setMFUserNote("测试：Jack");
                        mmFrend.setMFBirthPeriod("90");
                        mmFrend.setMFConstellation("白羊座");
                        mmFrend.setMFCompany("美联邦共和国密歇根州特斯拉太空有限公司");
                        mmFrend.setMFPosition("外星人研究部");
                        mmFrend.setMFBgImage("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1517218562103&di=28a6fb6a5e3bfb74ea1f53da3863e5f4&imgtype=jpg&src=http%3A%2F%2Fimg.mp.itc.cn%2Fupload%2F20161009%2Ff3ae30b120dd42eaa22e96196f2d1f29_th.jpg");
                        mmFrend.setMFIsSingle(1);
                        mmFrend.setPinYin("J");

                    } else {
                        mmFrend.setMFlogin_uid(1);
                        mmFrend.setMFUid(1);
                        mmFrend.setMFStoreId(1);
                        mmFrend.setMFUUID("ces2");
                        mmFrend.setMFUUName("ces2");
                        mmFrend.setMFAvatar("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1517221123215&di=51eb8058e2eddb94bbc9b87f0a310010&imgtype=0&src=http%3A%2F%2Fwww.chinafranchiseexpo.com%2Fphp%2Ffiles%2Flogo-1-01.jpg");
                        mmFrend.setMFUserName("测试2");
                        mmFrend.setMFUnionLevel(1);
                        mmFrend.setMFVipLevel(2);
                        mmFrend.setMFAuthStatus(0);
                        mmFrend.setMFBusinessAuthStatus(1);
                        mmFrend.setMFGender(2);
                        mmFrend.setMFOccupation("快餐行业");
                        mmFrend.setMFUserNote("测试：Mr·L");
                        mmFrend.setMFBirthPeriod("90");
                        mmFrend.setMFConstellation("白羊座");
                        mmFrend.setMFCompany("英国巴黎德克士有限公司");
                        mmFrend.setMFPosition("快餐开发部");
                        mmFrend.setMFBgImage("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1517218562103&di=28a6fb6a5e3bfb74ea1f53da3863e5f4&imgtype=jpg&src=http%3A%2F%2Fimg.mp.itc.cn%2Fupload%2F20161009%2Ff3ae30b120dd42eaa22e96196f2d1f29_th.jpg");
                        mmFrend.setMFIsSingle(2);
                        mmFrend.setPinYin("M");
                    }
                    mmFrends.add(mmFrend);
                }
                break;
            case IM_RongYun:
                break;
        }
        return mmFrends;
    }

    /**
     * 根据条件模糊搜索好友
     *
     * @param context    上下文
     * @param keyContent 搜索条件
     * @return
     */
    public static List<MMFrend> GetVagueQueryFrend(IMConfig.ChatEPackageType config, Context context, String keyContent) {
        List<MMFrend> mmFrends = new ArrayList<>();
        return mmFrends;
    }


}
