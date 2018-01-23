package com.project.jack.chat.message.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.jack.R;
import com.project.jack.chat.base.ChatBaseFragment;

import butterknife.ButterKnife;

/**
 * Create by www.lijin@foxmail.com on 2018/1/19 0019.
 * <br/>
 * 会话模块
 */

public class ChatMessageListFragment extends ChatBaseFragment {

    private View vRootView;
    private static final String TAG = "ChatMessageListFragment_TAG";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (vRootView == null) {
            vRootView = inflater.inflate(R.layout.chat_message_list_layout, container, false);
            ButterKnife.bind(this, vRootView);
        }
        return vRootView;
    }
}
