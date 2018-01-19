package com.project.jack.chat.single;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.jack.R;
import com.project.jack.chat.base.ChatBaseActivity;
import com.project.jack.chat.eventbus.ChatReceivedEventBus;
import com.project.jack.chat.model.PreviewBean;
import com.project.jack.chat.model.single.ChatMessageBean;
import com.project.jack.chat.single.fragment.ChatSingleEmotiomFragment;
import com.project.jack.chat.util.BroadCastReceiverConstant;
import com.project.jack.chat.util.Constant;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jack.project.com.imdatautil.IMDataUtil;
import jack.project.com.imdatautil.util.IMConfig;

/**
 * Create by www.lijin@foxmail.com on 2018/1/12 0012.
 * <br/>
 * 单聊 主界面
 */

public class ChatMessageActivity extends ChatBaseActivity implements SwipeRefreshLayout.OnRefreshListener,ChatSingleEmotiomFragment.FragmentListener{

    /**
     * 下拉刷新
     */
    @Nullable
    @BindView(R.id.chat_message_chat_refresh)
    SwipeRefreshLayout vSwipeRefresh;
    /**
     * RecyclerView
     */
    @BindView(R.id.chat_recyclerview)
    RecyclerView vCRecyclerview;
    /**
     * 加载fragment的frame
     */
    @BindView(R.id.chat_fr)
    FrameLayout vFrame;
    /**
     * 头部名称
     */
    @BindView(R.id.tv_title)
    TextView mTitle;
    /**
     * 粘贴 复制 转发 撤回的动态添加基健
     */
    @BindView(R.id.chat_copy_paste)
    LinearLayout vLiCP;
    /**
     * message的父级 用于获取上级ID
     */
    @BindView(R.id.activity_chat_message)
    RelativeLayout vLcp;
    @BindView(R.id.chat_ry_father)
    LinearLayout vFather;

    /**
     * 被@的提示界面
     */
    @BindView(R.id.chat_single_marking_top)
    LinearLayout vLMarking;
    /**
     * 提示的文字
     */
    @BindView(R.id.chat_single_marking_tv)
    TextView vTMarking;

    // 对方用户昵称 username 对方用户头像 avatar  对方用户实名认证状态 authStatus 对方用户企业认证状态 businessAuthStatus
    private String mOtherAuthStatus,mOtherBusinessAuthStatus, mOtherNamgeCardBgImage;
    //对方用户VIP等级 vipLevel
    private String mOtherVipLevel;
    //全局上下文
    private Context mContext;
    //底部框 包含输入区域和表情区域
    private ChatSingleEmotiomFragment mEmotionMainFragment;
    //用于注册关闭的Filter
    private IntentFilter mFilter;
    //用于全局接收的Intent
    private Intent mIn;
    //语音播放器
    MediaPlayer player = new MediaPlayer();
    //存储播放大图的零时集合
    ArrayList<PreviewBean> mDList;
    //存储大图路径的几个
    ArrayList<String> mPathList;
    //大图的实体
    PreviewBean previewBean;
    //用于保存需要传递到大图的下标
    int mPicPosition = 0;
    //RecyclerView 的 LinearLayoutManager
    LinearLayoutManager linearLayoutManager;
    //用于保存未读语音的动画View
    List<View> mVoiceView = new ArrayList<>();
    //自增的播放下标
    int mVoicePosition = 0;
    //用于全局保存语音是否在播放的状态
    boolean mIsVoiceStart = false;
    //保存当前播放到的view
    View mFastView;
    //有多少条未读
    private int mMarkingContent;
    //当前页面的数据bean
    private ChatMessageBean mChatMessageBean;
    String SYSTEM_REASON = "reason";
    String SYSTEM_HOME_KEY = "homekey";
    String SYSTEM_HOME_KEY_LONG = "recentapps";
    //TAG
    private final static String TAG = "ChatMessageActivity_TAG";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_message_single_main);
        //映射组件
        ButterKnife.bind(this);
        //赋值上下文
        mContext = this;
        //注册EventBus
        EventBus.getDefault().register(this);//订阅
        //组件赋值
        initView();
        //数据查询与赋值
        initData();
        //注册语音消息发送的广播
        mFilter = new IntentFilter(BroadCastReceiverConstant.BROAD_MESSAGEVOICE);
        registerReceiver(mReceiver, mFilter);
        //注册收到消息的广播
        mFilter = new IntentFilter(BroadCastReceiverConstant.BROAD_MESSAGERECEIVED);
        mContext.registerReceiver(mReceiver, mFilter);
        //注册语音通话状态的广播
        mFilter = new IntentFilter(BroadCastReceiverConstant.BROAD_INITCALLMOITOR);
        mContext.registerReceiver(mReceiver, mFilter);
        //注册扩展类表情发送的广播
        mFilter = new IntentFilter(BroadCastReceiverConstant.BROAD_CLASSIC_EXTENDED);
        mContext.registerReceiver(mReceiver, mFilter);
        //注册home键
        mFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        registerReceiver(mReceiver, mFilter);
    }

    /**
     * 用于多通道刷新的EventBus
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onReceivedEvent(ChatReceivedEventBus event) {

    }

    /**
     * 注册的广播接收器
     */
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(final Context mContext, Intent intent) {

        }
    };

    /**
     * 组件赋值
     */
    private void initView() {
        mIn = getIntent();
        if (mIn != null) {
            //赋值基础数据Bean
            mChatMessageBean = (ChatMessageBean) getIntent()
                    .getBundleExtra(Constant.CHAT_INTENT_BUNDLE)
                    .getSerializable(Constant.CHAT_INTENT_BEAN);
            //获取跳转到聊天页面需要的传值接收
            mOtherAuthStatus = mIn.getStringExtra(Constant.MEG_INTNT_CHATMESSAGE_OTHERAURHSTATUS);
            mOtherBusinessAuthStatus = mIn.getStringExtra(Constant.MEG_INTNT_CHATMESSAGE_OTHERABUSINESSAU);
            mOtherVipLevel = mIn.getStringExtra(Constant.MEG_INTNT_CHATMESSAGE_OTHERVIPLEVEL);
            mOtherNamgeCardBgImage = mIn.getStringExtra(Constant.MEG_INTNT_CHATMESSAGE_OTHERNAMGECARSBGIMAGE);
            //赋值聊天头部昵称  规则：备注-->昵称-->HXID
            if (!TextUtils.isEmpty(mChatMessageBean.getChatOtherUID())) {
                //查询当前用户的备注
                    if (!TextUtils.isEmpty(mChatMessageBean.getChatOtherUserNote())) {//昵称
                        mTitle.setText(mChatMessageBean.getChatOtherUserNote());
                    } else {
                        mTitle.setText(mChatMessageBean.getChatOtherUserName());
                    }
            }
        }
        //初始化刷新
        //为SwipeRefreshLayout设置监听事件
        vSwipeRefresh.setOnRefreshListener(this);
        //为SwipeRefreshLayout设置刷新时的颜色变化，最多可以设置4种
        vSwipeRefresh.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }


    /**
     * 初始化布局数据
     */
    private void initData() {
        //初始化表情列表
        initEmotionMainFragment();
    }

    /**
     * 初始化表情面板
     */
    public void initEmotionMainFragment() {
        //构建传递参数
        Bundle bundle = new Bundle();
        //绑定主内容编辑框
        bundle.putBoolean(ChatSingleEmotiomFragment.BIND_TO_EDITTEXT, true);
        //隐藏控件
        bundle.putBoolean(ChatSingleEmotiomFragment.HIDE_BAR_EDITTEXT_AND_BTN, false);
        bundle.putString(Constant.MEG_INTNT_CHATMESSAGE_HXID, mChatMessageBean.getChatUUID());
        bundle.putString(Constant.MEG_INTNT_CHATMESSAGE_OTHRTUID, mChatMessageBean.getChatOtherUID());
        bundle.putString(Constant.MEG_INTNT_CHATMESSAGE_OTHERUSERNAME, mChatMessageBean.getChatOtherUserName());
        bundle.putString(Constant.MEG_INTNT_CHATMESSAGE_OTHERUSERAVATAR, mChatMessageBean.getChatOtherUserAvatar());
        bundle.putString(Constant.MEG_INTNT_CHATMESSAGE_OTHERAURHSTATUS, mOtherAuthStatus);
        bundle.putString(Constant.MEG_INTNT_CHATMESSAGE_OTHERABUSINESSAU, mOtherBusinessAuthStatus);
        bundle.putString(Constant.MEG_INTNT_CHATMESSAGE_OTHERNAMGECARSBGIMAGE, mOtherNamgeCardBgImage);
        bundle.putString(Constant.MEG_INTNT_CHATMESSAGE_OTHERVIPLEVEL, mOtherVipLevel);
        //替换fragment
        //创建修改实例
        mEmotionMainFragment = ChatSingleEmotiomFragment.newInstance(ChatSingleEmotiomFragment.class, bundle);
        mEmotionMainFragment.bindToContentView(vFather);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.chat_fr, mEmotionMainFragment);
        transaction.addToBackStack(null);
        //提交修改
        transaction.commit();
    }

    /**
     * 拦截返回键
     */
    @Override
    public void onBackPressed() {
        mEmotionMainFragment.isShowInterceptBackPress();
    }

    /**
     * 下拉刷新(google)
     */
    @Override
    public void onRefresh() {

    }

    /**
     * 发送方法
     * @param text       发送文本
     * @param UserName   发送名称
     */
    @Override
    public void thank(String text, String UserName) {

    }
}
