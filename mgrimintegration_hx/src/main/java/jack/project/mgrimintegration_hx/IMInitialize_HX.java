package jack.project.mgrimintegration_hx;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

import java.util.Iterator;
import java.util.List;

/**
 * Create by www.lijin@foxmail.com on 2018/1/19 0019.
 * <br/>
 * 初始化环信
 */

public class IMInitialize_HX {

    private static final String TAG = "IMInitialize_hx";

    public static void init(Context appContext){
        //初始化单例
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(appContext,pid);
        // 如果APP启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回
        if (processAppName == null || !processAppName.equalsIgnoreCase(appContext.getPackageName())) {
            Log.e(TAG, "enter the service process!");

            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }
        EMOptions options = new EMOptions();
        // 设置Appkey，如果配置文件已经配置，这里可以不用设置
        // options.setAppKey("lzan13#hxsdkdemo");
        // 设置自动登录
        options.setAutoLogin(true);
        // 设置是否需要发送已读回执
        options.setRequireAck(true);
        // 设置小米推送 appID 和 appKey
//        options.setMipushConfig("2882303761517481214", "5221748126214");
        // 设置是否需要发送回执，
        options.setRequireDeliveryAck(true);
        // 设置是否需要服务器收到消息确认
        //options.setRequireServerAck(true);
        // 设置是否根据服务器时间排序，默认是true
        options.setSortMessageByServerTime(false);
        // 收到好友申请是否自动同意，如果是自动同意就不会收到好友请求的回调，因为sdk会自动处理，默认为true
        options.setAcceptInvitationAlways(false);
        // 设置是否自动接收加群邀请，如果设置了当收到群邀请会自动同意加入
        options.setAutoAcceptGroupInvitation(false);
        // 设置（主动或被动）退出群组时，是否删除群聊聊天记录
        options.setDeleteMessagesAsExitGroup(false);
        // 设置是否允许聊天室的Owner 离开并删除聊天室的会话
        options.allowChatroomOwnerLeave(false);

        //初始化环信
        EMClient.getInstance().init(appContext, options);

//        //注册电话监听
//        initCallMonitor();
//
//        //注册环信网络和离线监听
//        initnoetowork();
//        // 设置通话过程中对方如果离线是否发送离线推送通知，默认 false
//        EMClient.getInstance().callManager().getCallOptions().setIsSendPushIfOffline(true);
//        // 设置音频通话推送提供者，在 onRemoteOffline()回调中给对方发送通知
//        EMClient.getInstance().callManager().setPushProvider(new EMCallManager.EMCallPushProvider() {
//            @Override
//            public void onRemoteOffline(String username) {
//                User user = User.GetLoginedUser(getApplicationContext());
//                EMMessage message = EMMessage.createTxtSendMessage(getResources().getString(R.string.chat_offline_voice_voides), username);
//                // 设置强制推送
//                message.setAttribute("em_force_notification", "true");
//                message.setAttribute("uid", user.uid);
//                message.setAttribute("username", user.username);
//                message.setAttribute("userAvatar", user.avatar);
//                message.setAttribute("vipLevel", String.valueOf(user.vipLevel));
//                message.setAttribute("authStatus", String.valueOf(user.authStatus));
//                message.setAttribute("businessAuthStatus", String.valueOf(user.businessAuthStatus));
//                message.setAttribute("namgeCardBgImage", user.bgImage);
//                message.setAttribute("otherUid", user.uid);
//                message.setAttribute("otherUsername", user.username);
//                message.setAttribute("otherUserAvatar", user.avatar);
//                message.setAttribute("otherVipLevel", String.valueOf(user.vipLevel));
//                message.setAttribute("otherAuthStatus", String.valueOf(user.authStatus));
//                message.setAttribute("otherBusinessAuthStatus", String.valueOf(user.businessAuthStatus));
//                message.setAttribute("otherNamgeCardBgImage", user.bgImage);
//                message.setMessageStatusCallback(new EMCallBack() {
//                    @Override
//                    public void onSuccess() {
//                        // 在这里可以删除消息
//                    }
//
//                    @Override
//                    public void onError(int i, String s) {
//                        // 在这里可以删除消息
//                    }
//
//                    @Override
//                    public void onProgress(int i, String s) {
//                    }
//                });
//                EMClient.getInstance().chatManager().sendMessage(message);
//            }
//        });
//
//        EMAGroupManager sd = new EMAGroupManager();
//        //注册获取消息的监听
//        initaddListener();
//        //初始化群组状态变化监听
//        initgroupListener();
//        //注册接受电话消息的广播
//        IntentFilter callFilter = new IntentFilter(EMClient.getInstance().callManager().getIncomingCallBroadcastAction());
//        registerReceiver(new CallReceiver(), callFilter);
    }

    private static String getAppName(Context mContext,int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) mContext.getSystemService(mContext.ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = mContext.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }
}
