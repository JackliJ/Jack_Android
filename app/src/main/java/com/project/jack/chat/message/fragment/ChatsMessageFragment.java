package com.project.jack.chat.message.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

import com.project.jack.R;
import com.project.jack.chat.base.ChatBaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by www.lijin@foxmail.com on 2018/1/19 0019.
 * <br/>
 */

public class ChatsMessageFragment extends ChatBaseFragment {

    @BindView(R.id.tabhost)
    FragmentTabHost vTabHost;
    private Context mContext;
    private View vRootView;
    // 会话消息
    private TextView vMessageList;
    // 朋友
    private TextView vMgeFrends;
    // 群组
    private TextView vGroupList;
    private int[] mMessageStr = {R.string.chats_message, R.string.chats_frends, R.string.chats_group};
    private Class[] mMessageClass = {ChatMessageListFragment.class, ChatMgeFrendFragment.class, ChatGroupListFragment.class};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (vRootView == null) {
            vRootView = inflater.inflate(R.layout.fragment_message_main, container, false);
            ButterKnife.bind(this, vRootView);
            initView();
        }
        return vRootView;
    }

    private void initView(){
        vTabHost.setup(getActivity(), getChildFragmentManager(), R.id.tab_content);
        for (int i = 0; i < mMessageStr.length; i++) {
            vTabHost.addTab(vTabHost.newTabSpec(getResources().getString(mMessageStr[i])).setIndicator(getIndicator(i)), mMessageClass[i], null);
        }
        vTabHost.getTabWidget().setDividerDrawable(android.R.color.transparent);
        vMessageList.setVisibility(View.VISIBLE);
        vMgeFrends.setVisibility(View.GONE);
        vGroupList.setVisibility(View.GONE);
        vTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId == getResources().getString(mMessageStr[0])) {
                    vMessageList.setVisibility(View.VISIBLE);
                    vMgeFrends.setVisibility(View.GONE);
                    vGroupList.setVisibility(View.GONE);
                } else if (tabId == getResources().getString(mMessageStr[1])) {
                    vMessageList.setVisibility(View.GONE);
                    vMgeFrends.setVisibility(View.VISIBLE);
                    vGroupList.setVisibility(View.GONE);
                } else if (tabId == getResources().getString(mMessageStr[2])) {
                    vMessageList.setVisibility(View.GONE);
                    vMgeFrends.setVisibility(View.GONE);
                    vGroupList.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /**
     * FragmentTabHost 按钮
     *
     * @param index
     * @return
     */
    private View getIndicator(int index) {
        if (index >= mMessageStr.length) {
            throw new IndexOutOfBoundsException();
        } else {
            View view = getActivity().getLayoutInflater().inflate(R.layout.chat_communication_indicator_tab_system, null);
            ((TextView) view.findViewById(R.id.tv_title)).setText(mMessageStr[index]);
            switch (index) {
                case 0: {
                    vMessageList = view.findViewById(R.id.tv_line);
                }
                break;
                case 1: {
                    vMgeFrends = view.findViewById(R.id.tv_line);
                }
                break;
                case 2: {
                    vGroupList = view.findViewById(R.id.tv_line);
                }
                break;
            }
            return view;
        }
    }

}
