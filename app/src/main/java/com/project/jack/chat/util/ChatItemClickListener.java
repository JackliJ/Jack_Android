package com.project.jack.chat.util;

import android.view.View;

/**
 * Created by www.lijin@foxmail.com on 2017/10/21 0021.
 * <br/>
 * item 点击事件
 */
public interface ChatItemClickListener {

    public void onItemClick(View view, int postion);

    public void onLongClick(View view, int position);
}
