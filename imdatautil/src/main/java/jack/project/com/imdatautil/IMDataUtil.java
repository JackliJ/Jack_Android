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
                for (int i = 0;i<3;i++){
                    mmSession = new MMSession();
                    mmSession.setMMSUid("12345"+i);
                    mmSession.setMMSUserLeavl(i);
                    if(i == 0){
                        mmSession.setMMSUserNote("王思聪");
                        mmSession.setMMSUserName("王思聪");
                    }else if(i == 1){
                        mmSession.setMMSUserNote("范冰冰");
                        mmSession.setMMSUserName("范冰冰");
                    }else if(i == 2){
                        mmSession.setMMSUserNote("胡歌");
                        mmSession.setMMSUserName("琅琊榜");
                    }
                    if(i == 0){
                        mmSession.setMMSUserAvatar("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=3718858439,1664323015&fm=27&gp=0.jpg");
                    }else if(i == 1){
                        mmSession.setMMSUserAvatar("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1516713218372&di=99fc65e4532a2b8c76c8b8a3c7a11360&imgtype=jpg&src=http%3A%2F%2Fimg1.imgtn.bdimg.com%2Fit%2Fu%3D72407761%2C308001660%26fm%3D214%26gp%3D0.jpg");
                    }else if(i == 2){
                        mmSession.setMMSUserAvatar("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1516713251415&di=da5930ebf7f37928af0c20cb8092008f&imgtype=0&src=http%3A%2F%2Fimg2.touxiang.cn%2Ffile%2F20171116%2F6c115e9dedbaddda2a92e14da3a8706a.jpg");
                    }
                    if(i == 0){
                        mmSession.setMMSUserContext("晚上一起吃鸡，陈赫也来");
                    }else if(i == 1){
                        mmSession.setMMSUserContext("晚上李晨不在家哦");
                    }else if(i == 2){
                        mmSession.setMMSUserContext("李导，凡人修仙传什么时候开拍啊！");
                    }

                    mmSession.setMMSUserUnRead(i+1);
                    mmSession.setMMSEndTime(1516700246);
                    mmSession.setMMSUUid("5416548"+i);
                    mmSession.setMMSAuthStatus("3" + i);
                    mmSession.setMMSBusinessAuthStatus("2");
                    mmSession.setMMSOtherNamgeCardBgImage("http://e.hiphotos.baidu.com/zhidao/wh%3D450%2C600/sign=22330fbff4246b607b5bba70dec8367a/8326cffc1e178a82253b2e4cf403738da877e8d3.jpg");
                    mmSession.setMMSType(MMessage.Type.TXT);
                    mmSession.setMMSChatType(MMessage.ChatType.Chat);
                    mmSession.setMMSIsReceiveSend(MMessage.Direct.SEND);
                    mMessages.add(mmSession);
                }
                break;
            case IM_RongYun:
                break;
        }
        return mMessages;
    }

    /**
     * 根据第三方ID删除某个会话
     * @param UUID
     */
    public static void DeleteSession(String UUID){

    }



}
