package com.project.jack.chat.message.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.jack.R;
import com.project.jack.ui.fragment.BaseFragment;

import butterknife.ButterKnife;

/**
 * Create by www.lijin@foxmail.com on 2018/1/19 0019.
 * <br/>
 * 朋友界面
 */

public class ChatMgeFrendFragment extends BaseFragment {

    private View vRootView;
    private static final String TAG = "ChatMgeFrendFragment_TAG";

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
