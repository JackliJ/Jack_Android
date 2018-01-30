package com.project.jack.chat.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.project.jack.R;
import com.project.jack.chat.weight.MgeToast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by www.lijin@foxmail.com on 2017/9/18 0018.
 * <br/>
 * 帮助类
 */
public class Utils {

    private final static String TAG = "Utils_TAG";

    /**
     * 将sp转换为px
     *
     * @param context
     * @param sp
     * @return
     */
    public static float TranslateSpiToPx(Context context, float sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp,
                context.getResources().getDisplayMetrics());
    }

    /**
     * 检测当前网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetWorkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable() && mNetworkInfo.isConnected();
            }
        }

        return false;
    }

    /**
     * 判断输入法是否显示  如果显示 则隐藏
     *
     * @param context
     * @param editText
     */
    public static void HideMethod(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();// isOpen若返回true，则表示输入法打开
        if (isOpen) {
            // 将输入法隐藏
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }


    /**
     * 读取表情配置文件
     *
     * @param context
     * @return
     */
    public static List<String> getEmojiFile(Context context) {
        try {
            List<String> list = new ArrayList<String>();
            InputStream in = context.getResources().getAssets().open("emoji");
            BufferedReader br = new BufferedReader(new InputStreamReader(in,
                    "UTF-8"));
            String str = null;
            while ((str = br.readLine()) != null) {
                list.add(str);
            }

            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断字符串是否为null
     *
     * @param text true 不为null
     * @return
     */
    public static boolean GetStringISNull(String text) {
        boolean isstring = false;
        if (text != null && !"".equals(text) && text.length() > 0) {
            isstring = true;
        }
        return isstring;
    }

    /**
     * 根据Uri获取图片路径
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getFilePathByContentResolver(Context context, Uri uri) {
        if (null == uri) {
            return null;
        }
        Cursor c = context.getContentResolver().query(uri, null, null, null, null);
        String filePath = null;
        if (null == c) {
            throw new IllegalArgumentException(
                    "Query on " + uri + " returns null result.");
        }
        try {
            if ((c.getCount() != 1) || !c.moveToFirst()) {
            } else {
                filePath = c.getString(
                        c.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA));
            }
        } finally {
            c.close();
        }
        return filePath;
    }

//    public static void ShowNotification(Context mContext, String title, String Content, EMMessage emMessage, ConversationBean mData) {
//        NotificationManager manager = (NotificationManager) mContext.getSystemService(mContext.NOTIFICATION_SERVICE);
//        Notification.Builder builder = new Notification.Builder(mContext);
////        builder.setContentInfo("补充内容");
//        builder.setContentText(Content);
//        builder.setContentTitle(title);
//        builder.setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher));
//        builder.setSmallIcon(R.drawable.icon_42_lucency);
////        builder.setTicker("新消息sss");
//        builder.setAutoCancel(true);
//
//        builder.setWhen(System.currentTimeMillis());//设置时间，设置为系统当前的时间
//        Intent in = null;
//        if(mData != null){
//            if (emMessage.getChatType() == EMMessage.ChatType.GroupChat) {//群聊
//                in = new Intent(mContext, ChatGroupMessageActivity.class);
//                in.putExtra(Constant.MEG_INTNT_CHATMESSAGE_HXID, mData.getUuid());
//                in.putExtra(Constant.MEG_INTNT_CHATMESSAGE_CHATTYPE, EMMessage.ChatType.GroupChat.ordinal());
//                in.putExtra(Constant.MEG_INTNT_CHATMESSAGE_OTHRTUID, mData.getUid());
//                in.putExtra(Constant.MEG_INTNT_CHATMESSAGE_OTHERUSERNAME, mData.getUsername());
//                in.putExtra(Constant.MEG_INTNT_CHATMESSAGE_OTHERUSERAVATAR, mData.getUseravatar());
//                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            } else if (emMessage.getChatType() == EMMessage.ChatType.Chat) {//单聊
//                in = new Intent(mContext, ChatMessageActivity.class);
//                in.putExtra(Constant.MEG_INTNT_CHATMESSAGE_OTHRTUID, mData.getUid());
//                in.putExtra(Constant.MEG_INTNT_CHATMESSAGE_HXID, mData.getUuid());
//                in.putExtra(Constant.MEG_INTNT_CHATMESSAGE_CHATTYPE, EMMessage.ChatType.Chat.ordinal());
//                in.putExtra(Constant.MEG_INTNT_CHATMESSAGE_OTHERUSERNAME, mData.getUsername());
//                in.putExtra(Constant.MEG_INTNT_CHATMESSAGE_OTHERUSERAVATAR, mData.getUseravatar());
//                in.putExtra(Constant.MEG_INTNT_CHATMESSAGE_OTHERVIPLEVEL, String.valueOf(mData.getUserLeavl()));
//                in.putExtra(Constant.MEG_INTNT_CHATMESSAGE_OTHERAURHSTATUS, mData.getAuthStatus());
//                in.putExtra(Constant.MEG_INTNT_CHATMESSAGE_OTHERABUSINESSAU, mData.getBusinessAuthStatus());
//                in.putExtra(Constant.MEG_INTNT_CHATMESSAGE_OTHERNAMGECARSBGIMAGE, mData.getOtherNamgeCardBgImage());
//                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            }
//            in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent
//                    .FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        }else{
//            in = new Intent(mContext,MainActivity.class);
//        }
//
//        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0, in, PendingIntent.FLAG_CANCEL_CURRENT);
//        builder.setContentIntent(pendingIntent);
//        Notification notification = builder.build();
//        /**
//         * vibrate属性是一个长整型的数组，用于设置手机静止和振动的时长，以毫秒为单位。
//         * 参数中下标为0的值表示手机静止的时长，下标为1的值表示手机振动的时长， 下标为2的值又表示手机静止的时长，以此类推。
//         */
//        long[] vibrates = {0, 1000, 1000, 1000};
//        notification.vibrate = vibrates;
//
////                /**
////                 * 手机处于锁屏状态时， LED灯就会不停地闪烁， 提醒用户去查看手机,下面是绿色的灯光一 闪一闪的效果
////                 */
////                notification.ledARGB = Color.GREEN;// 控制 LED 灯的颜色，一般有红绿蓝三种颜色可选
////                notification.ledOnMS = 1000;// 指定 LED 灯亮起的时长，以毫秒为单位
////                notification.ledOffMS = 1000;// 指定 LED 灯暗去的时长，也是以毫秒为单位
////                notification.flags = Notification.FLAG_SHOW_LIGHTS;// 指定通知的一些行为，其中就包括显示
//        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
//        /**
//         * sound属性是一个 Uri 对象。 可以在通知发出的时候播放一段音频，这样就能够更好地告知用户有通知到来.
//         * 如：手机的/system/media/audio/ringtones 目录下有一个 Basic_tone.ogg音频文件，
//         * 可以写成： Uri soundUri = Uri.fromFile(new
//         * File("/system/media/audio/ringtones/Basic_tone.ogg"));
//         * notification.sound = soundUri; 我这里为了省事，就去了手机默认设置的铃声
//         */
//        notification.sound = uri;
//        notification.defaults = Notification.DEFAULT_ALL;
//        manager.notify(1, notification);
//    }

//    /**
//     * 根据用户ID获取扩展字段里面的实体
//     *
//     * @return
//     */
//    public static ConversationBean getConversationBean(Context mContext, String mHxname) {
//        ConversationBean mConversation = null;
//        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
//        if (conversations.size() > 0 && conversations != null) {
//            for (String i : conversations.keySet()) {
//                List<EMMessage> messages = conversations.get(i).getAllMessages();
//                if (messages != null && messages.size() > 0) {
//                    if (!messages.get(messages.size() - 1).getUserName().equals("customer_service")) {//去掉小秘书
//                        String hxname;
//                        if(messages.get(messages.size() - 1).getChatType().toString().equals(EMMessage.ChatType.Chat.toString())){
//                            hxname = messages.get(messages.size() - 1).getFrom();
//                        }else{
//                            hxname = messages.get(messages.size() - 1).getTo();
//                        }
//                        if(hxname.equals(mHxname)){
//                            mConversation = new ConversationBean();
//                            if (messages.get(messages.size() - 1).getChatType().toString().equals(EMMessage.ChatType.Chat.toString())) {//单聊
//                                if (messages.get(messages.size() - 1).direct() == EMMessage.Direct.RECEIVE) {//最后一条消息是接受方
//                                    mConversation.setUid(messages.get(messages.size() - 1).getStringAttribute("uid", ""));
//                                    mConversation.setUserLeavl(messages.get(messages.size() - 1).getIntAttribute("vipLevel", 0));
//                                    String uid = messages.get(messages.size() - 1).getStringAttribute("uid", "");
//                                    mConversation.setUsername(getusernote(mContext,uid) != null ?
//                                            getusernote(mContext,uid) : messages.get(messages.size() - 1).getStringAttribute("username", ""));
//                                    mConversation.setUseravatar(messages.get(messages.size() - 1).getStringAttribute("userAvatar", ""));
//                                    mConversation.setAuthStatus(messages.get(messages.size() - 1).getStringAttribute("authStatus", ""));
//                                    mConversation.setBusinessAuthStatus(messages.get(messages.size() - 1).getStringAttribute("businessAuthStatus", ""));
//                                    mConversation.setOtherNamgeCardBgImage(messages.get(messages.size() - 1).getStringAttribute("namgeCardBgImage", ""));
//                                } else { //最后一条消息是发送方
//                                    mConversation.setUid(messages.get(messages.size() - 1).getStringAttribute("otherUid", ""));
//                                    mConversation.setUserLeavl(messages.get(messages.size() - 1).getIntAttribute("otherVipLevel", 0));
//                                    String uid = messages.get(messages.size() - 1).getStringAttribute("otherUid", "");
//                                    mConversation.setUsername(getusernote(mContext,uid) != null ?
//                                            getusernote(mContext,uid) : messages.get(messages.size() - 1).getStringAttribute("otherUsername", ""));
//                                    mConversation.setUseravatar(messages.get(messages.size() - 1).getStringAttribute("otherUserAvatar", ""));
//                                    LogUtils.tag("www").d("vip--4->" + messages.get(messages.size() - 1).getStringAttribute("otherUserAvatar", ""));
//                                    mConversation.setAuthStatus(messages.get(messages.size() - 1).getStringAttribute("otherAuthStatus", ""));
//                                    mConversation.setBusinessAuthStatus(messages.get(messages.size() - 1).getStringAttribute("otherBusinessAuthStatus", ""));
//                                    mConversation.setOtherNamgeCardBgImage(messages.get(messages.size() - 1).getStringAttribute("otherNamgeCardBgImage", ""));
//                                }
//                            } else {//群聊
//                                mConversation.setUid(messages.get(messages.size() - 1).getStringAttribute("uid", ""));
//                                mConversation.setUserLeavl(messages.get(messages.size() - 1).getIntAttribute("vipLevel", 0));
//                                mConversation.setUsername(messages.get(messages.size() - 1).getStringAttribute("username", ""));
//                                mConversation.setUseravatar(messages.get(messages.size() - 1).getStringAttribute("userAvatar", ""));
//                                mConversation.setAuthStatus(messages.get(messages.size() - 1).getStringAttribute("authStatus", ""));
//                                mConversation.setBusinessAuthStatus(messages.get(messages.size() - 1).getStringAttribute("businessAuthStatus", ""));
//                                mConversation.setOtherNamgeCardBgImage(messages.get(messages.size() - 1).getStringAttribute("namgeCardBgImage", ""));
//                            }
//
//                            try {
//                                mConversation.setUserunread(conversations.get(i).getUnreadMsgCount());//获取未读消息的数量
//                            } catch (Exception e) {
//                                mConversation.setUserunread(0);
//                            }
//                            mConversation.setUuid(conversations.get(i).conversationId());//赋值环信用户ID
//                            mConversation.setEndtime(messages.get(messages.size() - 1).getMsgTime());//赋值最后一条消息的时间
//                            mConversation.setUsercontext(messages.get(messages.size() - 1).getBody().toString());//赋值最后一条消息
//                            mConversation.setType(messages.get(messages.size() - 1).getType().toString());//赋值消息类型
//                            mConversation.setChatType(getChatType(messages.get(messages.size() - 1)));
//                            mConversation.setTextType(messages.get(messages.size() - 1).getStringAttribute("text_type", ""));//扩展类型
//                        }
//
//                    }
//                }
//            }
//        } else {
//            LogUtils.d("ChatInterfaceActivity", " is call list null ");
//        }
//        return mConversation;
//    }
//
//    public static EMMessage.ChatType getChatType(EMMessage message) {
//        if (message.getChatType() == EMMessage.ChatType.Chat) {
//            return EMMessage.ChatType.Chat;
//        } else if (message.getChatType() == EMMessage.ChatType.GroupChat) {
//            return EMMessage.ChatType.GroupChat;
//        }
//        return EMMessage.ChatType.Chat;
//    }

    public static void isPlayringtones(Context mContext, boolean isoffon) {
        Vibrator vibrator = null;
        MediaPlayer mMediaPlayer = null;
        if (isoffon) {
            //-开始播放手机铃声及震动
            try {
                Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
                mMediaPlayer = new MediaPlayer();
                mMediaPlayer.setDataSource(mContext, alert);
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_RING);
                mMediaPlayer.setLooping(true);
                mMediaPlayer.prepare();
                mMediaPlayer.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
                long[] pattern = {800, 150, 400, 130}; // OFF/ON/OFF/ON...
                vibrator.vibrate(pattern, 2);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //关闭
            try {
                if (mMediaPlayer != null) {
                    mMediaPlayer.stop();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (null != vibrator) {
                    vibrator.cancel();
                    vibrator = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    //保存文件到指定路径
    public static boolean saveImageToGallery(final Context context, Bitmap bmp) {
        try {
            // 首先保存图片 保存二维码图片名
            String fileName = "Mge_" + System.currentTimeMillis() + ".jpg";
            String imageDir = FileUtils.createDir(context, FileUtils.images);
            String imagePath = imageDir + fileName;
            if (!FileUtils.checkFile(imagePath)) {
                new File(imagePath).delete();
            }

            File mFile = new File(imagePath);
            try {
                FileOutputStream fos = new FileOutputStream(mFile);
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 其次把文件插入到系统图库
            try {
                MediaStore.Images.Media.insertImage(context.getContentResolver(), mFile.getAbsolutePath(), fileName, null);
                // 最后通知图库更新
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + mFile)));
                MgeToast.showSuccessToast(context, context.getText(R.string.qrcode_text3) + mFile.getAbsolutePath(), MgeToast.LENGTH_LONG);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


//    /**
//     * 根据ID获取字段
//     *
//     * @param uid
//     * @return
//     */
//    public static final String getusernote(Context mContext, String uid) {
//        try {
//            if (!TextUtils.isEmpty(uid) && !"null".equals(uid)) {
//                //判断传递过来的是否都为数字
//                if(Utils.isDigit(uid)){
//                    int userid = Integer.parseInt(uid);
//                    Frend frend = FrendDao.getFrend(mContext, userid);
//                    if (frend != null) {
//                        if (!TextUtils.isEmpty(frend.userNote)) {//昵称
//                            return frend.userNote;
//                        } else {
//                            return frend.username;
//                        }
//                    }
//                }
//
//            }
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }



    // 判断一个字符串是否都为数字
    public static boolean isDigit(String strNum) {
        Pattern pattern = Pattern.compile("[0-9]{1,}");
        Matcher matcher = pattern.matcher((CharSequence) strNum);
        return matcher.matches();
    }

//    /**
//     * 添加应用角标
//     * @param mContext
//     */
//    public static final void AddAngleMark(Context mContext){
//        //累计未读消息数量
//        //系统消息
//        //获取系统未读消息
//        int mINTERACTIVE = SharedPreferencedUtils.getInteger(mContext,User.GetLoginedUser(mContext).uid+"_"+Constant.SYSTEM_MESSAGE_INTERACTIVE,0);
//        int mNOTICE = SharedPreferencedUtils.getInteger(mContext,User.GetLoginedUser(mContext).uid+"_"+Constant.SYSTEM_MESSAGE_NOTICE,0);
//        int mTRADING = SharedPreferencedUtils.getInteger(mContext,User.GetLoginedUser(mContext).uid+"_"+Constant.SYSTEM_MESSAGE_TRADING,0);
//        int mSystemConversation = mINTERACTIVE + mNOTICE + mTRADING;
//        //脉友圈
//        int mMYQContent = SharedPreferencedUtils.getInteger(MaiGuoErApplication.getInstance().getApplicationContext(),User.GetLoginedUser(mContext).uid+"_"+Constant.CMDMESSAGE_ACTION_MYMESSAGECONTENT,0);
//        //聊天消息
//        int mHXContent = 0;
//        Map<String, EMConversation> conversations = EMClient.getInstance().chatManager().getAllConversations();
//        if(conversations.size() > 0 && conversations != null){
//            for (String i : conversations.keySet()) {
//                mHXContent = mHXContent + conversations.get(i).getUnreadMsgCount();
//            }
//        }
//        int mAngleMark = mSystemConversation + mMYQContent + mHXContent;
//        ShortcutBadger.applyCount(mContext, mAngleMark); //for 1.1.4+
//    }

    /**
     * 用户是否同意录音权限
     *
     * @return true 同意 false 拒绝
     */
    public static boolean isVoicePermission() {

        try {
            AudioRecord record = new AudioRecord(MediaRecorder.AudioSource.MIC, 22050, AudioFormat.CHANNEL_CONFIGURATION_MONO,
                    AudioFormat.ENCODING_PCM_16BIT, AudioRecord.getMinBufferSize(22050, AudioFormat.CHANNEL_CONFIGURATION_MONO,
                    AudioFormat.ENCODING_PCM_16BIT));
            record.startRecording();
            int recordingState = record.getRecordingState();
            if(recordingState == AudioRecord.RECORDSTATE_STOPPED){
                return false;
            }
            record.release();
            return true;
        } catch (Exception e) {
            return false;
        }

    }
}
