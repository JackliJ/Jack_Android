package com.project.jack.ui.rongcloud.single;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import com.project.jack.R;
import com.project.jack.base.BaseSwipeActivity;
import com.project.jack.ui.fragment.EmotionMainFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 融云单聊界面
 * Created by Administrator on 2017/12/7 0007.
 */

public class RYChatMessageActivity extends BaseSwipeActivity implements SwipeRefreshLayout.OnRefreshListener {

    //加载数据的RecyclerView
    @BindView(R.id.chat_recyclerview)
    RecyclerView vRecyclerView;
    //下拉聊天记录
    @BindView(R.id.chat_message_refresh)
    SwipeRefreshLayout vSRefresh;
    @BindView(R.id.chat_ry_father)
    LinearLayout vFather;

    private EmotionMainFragment emotionMainFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.ry_activity_chat_message);
        ButterKnife.bind(this);
        init();
    }

    /**
     * 初始化数据
     */
    private void init(){
        initEmotionMainFragment();
    }


    /**
     * 初始化表情面板
     */
    public void initEmotionMainFragment(){
        //构建传递参数
        Bundle bundle = new Bundle();
        //绑定主内容编辑框
        bundle.putBoolean(EmotionMainFragment.BIND_TO_EDITTEXT,true);
        //隐藏控件
        bundle.putBoolean(EmotionMainFragment.HIDE_BAR_EDITTEXT_AND_BTN,false);
        //替换fragment
        //创建修改实例
        emotionMainFragment =EmotionMainFragment.newInstance(EmotionMainFragment.class,bundle);
        emotionMainFragment.bindToContentView(vFather);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        // Replace whatever is in thefragment_container view with this fragment,
        // and add the transaction to the backstack
        transaction.replace(R.id.chat_fr,emotionMainFragment);
        transaction.addToBackStack(null);
        //提交修改
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        /**
         * 判断是否拦截返回键操作
         */
        if (!emotionMainFragment.isInterceptBackPress()) {
            super.onBackPressed();
        }
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {

    }
}
