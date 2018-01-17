package com.project.jack.chat.util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;

import com.project.jack.chat.adapter.ChatEmotionGridViewAdapter;


/**
 * Created by zejian
 * Time  16/1/8 下午5:05
 * Email shinezejian@163.com
 * Description:点击表情的全局监听管理类
 */
public class ChatGlobalOnItemClickManagerUtils {

    private static ChatGlobalOnItemClickManagerUtils instance;
    private EditText mEditText;//输入框
    private static Context mContext;

    public static ChatGlobalOnItemClickManagerUtils getInstance(Context context) {
        mContext = context;
        if (instance == null) {
            synchronized (ChatGlobalOnItemClickManagerUtils.class) {
                if (instance == null) {
                    instance = new ChatGlobalOnItemClickManagerUtils();
                }
            }
        }
        return instance;
    }

    public void attachToEditText(EditText editText) {
        mEditText = editText;
    }

    public AdapterView.OnItemClickListener getOnItemClickListener(final int emotion_map_type,final boolean classic_extended) {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object itemAdapter = parent.getAdapter();

                if (itemAdapter instanceof ChatEmotionGridViewAdapter) {


                    // 点击的是表情
                    ChatEmotionGridViewAdapter emotionGvAdapter = (ChatEmotionGridViewAdapter) itemAdapter;

                    if(classic_extended){
                        //当前点击的那个表情
                        String emotionName = emotionGvAdapter.getItem(position);
                        //扩展类的表情这直接发送  这里发送广播通知
                        Intent in = new Intent(BroadCastReceiverConstant.BROAD_CLASSIC_EXTENDED);
                        in.putExtra(Constant.EMOGI_CLASSIC_EXTENDED,emotionName);
                        mContext.sendBroadcast(in);
                    }else{
                        if (position == emotionGvAdapter.getCount() - 1) {
                            // 如果点击了最后一个回退按钮,则调用删除键事件
                            mEditText.dispatchKeyEvent(new KeyEvent(
                                    KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
                        } else {
                            // 如果点击了表情,则添加到输入框中
                            String emotionName = emotionGvAdapter.getItem(position);
                            // 获取当前光标位置,在指定位置上添加表情图片文本
                            int curPosition = mEditText.getSelectionStart();
                            StringBuilder sb = new StringBuilder(mEditText.getText().toString());
                            sb.insert(curPosition, emotionName);
                            Log.d("GlobalOnItemClickManage", "su----->" + sb.toString() + "---->" + emotion_map_type);
                            // 特殊文字处理,将表情等转换一下
                            mEditText.setText(SpanStringUtils.getEmotionContent(EmotionUtils.EMOTION_TOTAL,
                                    mContext, mEditText, sb.toString()));
                            // 将光标设置到新增完表情的右侧
                            mEditText.setSelection(curPosition + emotionName.length());
                        }
                    }



                }
            }
        };
    }

}
