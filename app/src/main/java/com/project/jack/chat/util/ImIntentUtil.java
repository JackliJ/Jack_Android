package com.project.jack.chat.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.project.jack.chat.model.single.ChatMessageBean;
import com.project.jack.chat.single.ChatMessageActivity;

/**
 * Create by www.lijin@foxmail.com on 2018/1/31 0031.
 * <br/>
 * 跳转类
 */

public class ImIntentUtil {


    /**
     * 跳转到聊天窗口
     * @param mChatMessageBean
     * @param mContext
     */
    public static void StartIntentSession(ChatMessageBean mChatMessageBean, Context mContext) {
        Intent intentE = new Intent(mContext, ChatMessageActivity.class);
        Bundle b = new Bundle();
        b.putSerializable(Constant.CHAT_INTENT_BEAN, mChatMessageBean);
        intentE.putExtra(Constant.CHAT_INTENT_BUNDLE, b);
        mContext.startActivity(intentE);
    }

}
