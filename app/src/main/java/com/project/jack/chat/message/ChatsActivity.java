package com.project.jack.chat.message;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;

import com.project.jack.R;
import com.project.jack.chat.base.ChatBaseActivity;
import com.project.jack.chat.message.fragment.ChatsMessageFragment;

import butterknife.BindView;

/**
 * Create by www.lijin@foxmail.com on 2018/1/19 0019.
 * <br/>
 *  主页
 */

public class ChatsActivity extends ChatBaseActivity {

    @BindView(R.id.chats_fr)
    FrameLayout vFrame;

    private ChatsMessageFragment mMessageFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_chats_message_layout);
        mMessageFragment = new ChatsMessageFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.chats_fr, mMessageFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}
