package com.project.jack.chat.message.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.project.jack.R;
import com.project.jack.chat.base.ChatBaseFragment;
import com.project.jack.chat.message.adapter.ChatMessageListAdapter;
import com.project.jack.chat.model.single.ChatMessageBean;
import com.project.jack.chat.single.ChatMessageActivity;
import com.project.jack.chat.util.ChatItemClickListener;
import com.project.jack.chat.util.Constant;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jack.project.com.imdatautil.IMDataUtil;
import jack.project.com.imdatautil.model.MMSession;

/**
 * Create by www.lijin@foxmail.com on 2018/1/19 0019.
 * <br/>
 * 会话模块
 */

public class ChatMessageListFragment extends ChatBaseFragment implements ChatItemClickListener {


    @BindView(R.id.re_chat_message)
    RecyclerView vChatMessageRe;

    //全局的View
    private View vRootView;
    //会话数据源
    private List<MMSession> mMSessions;
    //上下文
    private Context mContext;
    //RecyclerView LinearLayoutManager
    LinearLayoutManager mLayoutManager;
    //适配器
    private ChatMessageListAdapter mAdapter;
    //TAG
    private static final String TAG = "ChatMessageListFragment_TAG";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取上下文
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (vRootView == null) {
            vRootView = inflater.inflate(R.layout.chat_message_list_layout, container, false);
            ButterKnife.bind(this, vRootView);
            //初始化数据
            initData();
        }
        return vRootView;
    }


    private void initData() {
        //适当清空数据
        if (mMSessions != null) {
            mMSessions.clear();
        } else {
            mMSessions = new ArrayList<>();
        }
        //查询最新的会话信息
        mMSessions = IMDataUtil.getSessionList(Constant.config);
        //去加载数据
        mAdapter = new ChatMessageListAdapter(mContext, mMSessions);
        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        vChatMessageRe.setLayoutManager(mLayoutManager);
        //设置监听事件
        mAdapter.setOnItemClickListener(this);
        vChatMessageRe.setHasFixedSize(true);
        vChatMessageRe.setAdapter(mAdapter);
        //设置侧滑回收事件
        vChatMessageRe.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    SwipeMenuLayout viewCache = SwipeMenuLayout.getViewCache();
                    if (null != viewCache) {
                        viewCache.smoothClose();
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onItemClick(View view, int postion) {
        //排除脉友圈和小秘书和搜索
        if (view.getId() != R.id.rl_frend_circle && view.getId() != R.id.rl_secretary && postion > 0) {
            int mPosition = postion - 1;
            ChatMessageBean chatMessageBean = new ChatMessageBean();
            chatMessageBean.setChatUUID("123213");
            chatMessageBean.setChatUserID("78910");
            chatMessageBean.setChatUserName("本人");
            chatMessageBean.setChatUserNote("本人");
            chatMessageBean.setChatUserAvatar("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1516103998446&di=27f76e0ea7711b38cc75db68e74edd53&imgtype=0&src=http%3A%2F%2Fa.hiphotos.baidu.com%2Fzhidao%2Fwh%253D600%252C800%2Fsign%3Da3fc2c12c9ea15ce41bbe80f863016cb%2Fa08b87d6277f9e2fa7cf5f9f1f30e924b999f3ab.jpg");
            chatMessageBean.setChatOtherUUID(mMSessions.get(mPosition).getMMSUUid());
            chatMessageBean.setChatOtherUID(mMSessions.get(mPosition).getMMSUid());
            chatMessageBean.setChatOtherUserName(mMSessions.get(mPosition).getMMSUserName());
            chatMessageBean.setChatOtherUserNote(mMSessions.get(mPosition).getMMSUserNote());
            chatMessageBean.setChatOtherUserAvatar(mMSessions.get(mPosition).getMMSUserAvatar());
            Intent intentE = new Intent(mContext, ChatMessageActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constant.CHAT_INTENT_BEAN, chatMessageBean);
            intentE.putExtra(Constant.CHAT_INTENT_BUNDLE, bundle);
            startActivity(intentE);
        }
    }

    @Override
    public void onLongClick(View view, int position) {

    }
}
