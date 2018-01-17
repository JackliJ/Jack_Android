package com.project.jack.chat.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;


import com.project.jack.R;
import com.project.jack.chat.util.EmotionUtils;

import java.util.List;

/**
 * Created by zejian
 * Time  16/1/7 下午4:46
 * Email shinezejian@163.com
 * Description:表情添加适配器
 */
public class ChatEmotionGridViewAdapter extends BaseAdapter {

    private Context context;
    private List<String> emotionNames;
    private int itemWidth;
    private int emotion_map_type;
    private boolean classic_extended;

    public ChatEmotionGridViewAdapter(Context context, List<String> emotionNames, int itemWidth, int emotion_map_type, boolean classic_extended) {
        this.context = context;
        this.emotionNames = emotionNames;
        this.itemWidth = itemWidth;
        this.emotion_map_type = emotion_map_type;
        this.classic_extended = classic_extended;
    }

    @Override
    public int getCount() {
        // +1 最后一个为删除按钮
        if (classic_extended) {
            return emotionNames.size();
        } else {
            return emotionNames.size() + 1;
        }
    }

    @Override
    public String getItem(int position) {
        return emotionNames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView iv_emotion = new ImageView(context);
        // 设置内边距
        iv_emotion.setPadding(itemWidth / 8, itemWidth / 8, itemWidth / 8, itemWidth / 8);
        LayoutParams params = new LayoutParams(itemWidth, itemWidth);
        iv_emotion.setLayoutParams(params);

        if (classic_extended) {
            String emotionName = emotionNames.get(position);
            iv_emotion.setImageResource(EmotionUtils.getImgByName(emotion_map_type, emotionName));
        }else{
            //判断是否为最后一个item
            if (position == getCount() - 1) {
                iv_emotion.setImageResource(R.drawable.btn_delete_nor);
            } else {
                String emotionName = emotionNames.get(position);
                iv_emotion.setImageResource(EmotionUtils.getImgByName(emotion_map_type, emotionName));
            }
        }
        return iv_emotion;
    }

}
