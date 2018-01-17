package com.project.jack.chat.single.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.project.jack.R;
import com.project.jack.adapter.EmotionPagerAdapter;
import com.project.jack.chat.adapter.ChatEmotionGridViewAdapter;
import com.project.jack.chat.base.ChatBaseFragment;
import com.project.jack.chat.emptionkeyboardview.ChatEmojiIndicatorView;
import com.project.jack.chat.util.ChatGlobalOnItemClickManagerUtils;
import com.project.jack.chat.util.EmotionUtils;
import com.project.jack.utils.DisplayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijin
 * Time  17/9/22 下午4:32
 * Email www.lijin@foxmail.com
 * Description:可替换的模板表情，gridview实现
 */
public class ChatEmotiomComplateFragment extends ChatBaseFragment {
    private EmotionPagerAdapter emotionPagerGvAdapter;
    private Bundle args;
    //表情viewpager
    private ViewPager vp_complate_emotion_layout;
    //表情面板对应的点列表
    private ChatEmojiIndicatorView ll_point_group;
    private int emotion_map_type;
    //经典or扩展表情   false 为经典  true 为扩展
    private boolean classic_extended;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        args = getArguments();
    }

    /**
     * 创建与Fragment对象关联的View视图时调用
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_complate_emotions, container, false);
        initView(rootView);
        initListener();
        return rootView;
    }

    /**
     * 初始化view控件
     */
    protected void initView(View rootView) {
        vp_complate_emotion_layout = (ViewPager) rootView.findViewById(R.id.vp_complate_emotion_layout);
        ll_point_group = (ChatEmojiIndicatorView) rootView.findViewById(R.id.ll_point_group);
        //获取map的类型
        emotion_map_type = args.getInt(FragmentFactory.EMOTION_MAP_TYPE);
        classic_extended = args.getBoolean(FragmentFactory.EMOTION_CLASSIC_EXTENDED);
        initEmotion();
    }

    /**
     * 初始化监听器
     */
    protected void initListener() {

        vp_complate_emotion_layout.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            int oldPagerPos = 0;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                ll_point_group.playByStartPointToNext(oldPagerPos, position);
                oldPagerPos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 初始化表情面板
     * 思路：获取表情的总数，按每行存放7个表情，动态计算出每个表情所占的宽度大小（包含间距），
     * 而每个表情的高与宽应该是相等的，这里我们约定只存放3行
     * 每个面板最多存放7*3=21个表情，再减去一个删除键，即每个面板包含20个表情
     * 根据表情总数，循环创建多个容量为20的List，存放表情，对于大小不满20进行特殊
     * 处理即可。
     */
    private void initEmotion() {
        if (classic_extended) {
            // 获取屏幕宽度
            int screenWidth = DisplayUtils.getScreenWidthPixels(getActivity());
            // item的间距
            int spacing = DisplayUtils.dp2px(getActivity(), 4);
            // 动态计算item的宽度和高度
            int itemWidth = (screenWidth - spacing * 8) / 4;
            //动态计算gridview的总高度
            int gvHeight = itemWidth * 2 + spacing * 6;

            List<GridView> emotionViews = new ArrayList<>();
            List<String> emotionNames = new ArrayList<>();
            // 遍历所有的表情的key
            for (String emojiName : EmotionUtils.getEmojiMap(emotion_map_type).keySet()) {
                emotionNames.add(emojiName);
                // 每20个表情作为一组,同时添加到ViewPager对应的view集合中
                if (emotionNames.size() == 10) {
                    GridView gv = createEmotionGridView(emotionNames, screenWidth, spacing, itemWidth, gvHeight);
                    emotionViews.add(gv);
                    // 添加完一组表情,重新创建一个表情名字集合
                    emotionNames = new ArrayList<>();
                }
            }

            // 判断最后是否有不足15个表情的剩余情况
            if (emotionNames.size() > 0) {
                GridView gv = createEmotionGridView(emotionNames, screenWidth, spacing, itemWidth, gvHeight);
                emotionViews.add(gv);
            }

            //初始化指示器
            ll_point_group.initIndicator(emotionViews.size());
            // 将多个GridView添加显示到ViewPager中
            emotionPagerGvAdapter = new EmotionPagerAdapter(emotionViews);
            vp_complate_emotion_layout.setAdapter(emotionPagerGvAdapter);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth, gvHeight);
            vp_complate_emotion_layout.setLayoutParams(params);
        } else {
            // 获取屏幕宽度
            int screenWidth = DisplayUtils.getScreenWidthPixels(getActivity());
            // item的间距
            int spacing = DisplayUtils.dp2px(getActivity(), 12);
            // 动态计算item的宽度和高度
            int itemWidth = (screenWidth - spacing * 8) / 7;
            //动态计算gridview的总高度
            int gvHeight = itemWidth * 3 + spacing * 6;

            List<GridView> emotionViews = new ArrayList<>();
            List<String> emotionNames = new ArrayList<>();
            // 遍历所有的表情的key
            for (String emojiName : EmotionUtils.getEmojiMap(emotion_map_type).keySet()) {
                emotionNames.add(emojiName);
                // 共34个 每17个表情作为一组 加一个删除键,同时添加到ViewPager对应的view集合中
                if (emotionNames.size() == 17) {
                    GridView gv = createEmotionGridView(emotionNames, screenWidth, spacing, itemWidth, gvHeight);
                    emotionViews.add(gv);
                    // 添加完一组表情,重新创建一个表情名字集合
                    emotionNames = new ArrayList<>();
                }
            }

            // 判断最后是否有不足20个表情的剩余情况
            if (emotionNames.size() > 0) {
                GridView gv = createEmotionGridView(emotionNames, screenWidth, spacing, itemWidth, gvHeight);
                emotionViews.add(gv);
            }

            //初始化指示器
            ll_point_group.initIndicator(emotionViews.size());
            // 将多个GridView添加显示到ViewPager中
            emotionPagerGvAdapter = new EmotionPagerAdapter(emotionViews);
            vp_complate_emotion_layout.setAdapter(emotionPagerGvAdapter);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth, gvHeight);
            vp_complate_emotion_layout.setLayoutParams(params);
        }


    }

    /**
     * 创建显示表情的GridView
     */
    private GridView createEmotionGridView(List<String> emotionNames, int gvWidth, int padding, int itemWidth, int gvHeight) {
        // 创建GridView
        GridView gv = new GridView(getActivity());
        //设置点击背景透明
        gv.setSelector(android.R.color.transparent);
        //设置7列
        if (classic_extended) {
            gv.setNumColumns(5);
            gv.setGravity(Gravity.CENTER);
            gv.setHorizontalSpacing(padding);
            gv.setVerticalSpacing(padding * 2);
            //设置GridView的宽高
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(gvWidth, gvHeight);
            gv.setLayoutParams(params);
        } else {
            gv.setNumColumns(6);
            gv.setPadding(padding, padding, padding, padding);
            gv.setHorizontalSpacing(padding);
            gv.setVerticalSpacing(padding * 2);
            //设置GridView的宽高
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(gvWidth, gvHeight);
            gv.setLayoutParams(params);
        }
        // 给GridView设置表情图片
        ChatEmotionGridViewAdapter adapter = new ChatEmotionGridViewAdapter(getActivity(), emotionNames, itemWidth, emotion_map_type,classic_extended);
        gv.setAdapter(adapter);
        //设置全局点击事件
        gv.setOnItemClickListener(ChatGlobalOnItemClickManagerUtils.getInstance(getActivity()).getOnItemClickListener(emotion_map_type,classic_extended));
        return gv;
    }


}
