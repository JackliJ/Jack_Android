package com.project.jack.chat.emotionkeyboardview;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.project.jack.chat.single.fragment.ChatSingleEmotiomFragment;


/**
 * author : lijin
 * time : 2017年9月22日 上午11:14:27
 * email : www.lijin@foxmail.com
 * description :源码来自开源项目https://github.com/dss886/Android-EmotionInputDetector
 * 本人仅做细微修改以及代码解析
 */
public class EmotionKeyboard {

    private static final String SHARE_PREFERENCE_NAME = "EmotionKeyboard";
    private static final String SHARE_PREFERENCE_SOFT_INPUT_HEIGHT = "soft_input_height";

    private Activity mActivity;
    private InputMethodManager mInputManager;//软键盘管理类
    private SharedPreferences sp;
    private View mEmotionLayout;//表情布局
    private EditText mEditText;//
    private View mContentView;//内容布局view,即除了表情布局或者软键盘布局以外的布局，用于固定bar的高度，防止跳闪

    private EmotionKeyboard() {

    }

    /**
     * 外部静态调用
     *
     * @param activity
     * @return
     */
    public static EmotionKeyboard with(Activity activity) {
        EmotionKeyboard emotionInputDetector = new EmotionKeyboard();
        emotionInputDetector.mActivity = activity;
        emotionInputDetector.mInputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        emotionInputDetector.sp = activity.getSharedPreferences(SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return emotionInputDetector;
    }

    /**
     * 绑定内容view，此view用于固定bar的高度，防止跳闪
     *
     * @param contentView
     * @return
     */
    public EmotionKeyboard bindToContent(View contentView) {
        mContentView = contentView;
        return this;
    }

    /**
     * 绑定编辑框
     *
     * @param editText
     * @return
     */
    public EmotionKeyboard bindToEditText(EditText editText) {
        mEditText = editText;
        mEditText.requestFocus();
        mEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP && mEmotionLayout.isShown()) {
                    lockContentHeight();//显示软件盘时，锁定内容高度，防止跳闪。
                    hideEmotionLayout(true);//隐藏表情布局，显示软件盘

                    //软件盘显示后，释放内容高度
                    mEditText.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            unlockContentHeightDelayed();
                        }
                    }, 200L);
                }
                return false;
            }
        });
        return this;
    }

    /**
     * 绑定表情按钮
     * @param emotionButton 表情按钮
     * @param rebotton 表情展示区域
     * @param re_top 输入区域
     * @param btn_longvoice 语音发送区域
     * @return
     */
    public EmotionKeyboard bindToEmotionButton(View emotionButton, final LinearLayout rebotton,
                                               final LinearLayout re_top, final Button btn_longvoice
                                                ,final ImageButton vImgAdd) {
        emotionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //还原动画
                RotateAnimation mRotaAnimOut = new RotateAnimation(0,0,
                        Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                mRotaAnimOut.setDuration(100);
                //动画执行完毕后是否停在结束时的角度上
                mRotaAnimOut.setFillAfter(true);
                //去初始化加号按钮动画 防止来回点击动画没有初始化
                if(vImgAdd != null){
                    if(mEditText.getText().toString().trim().length() == 0){
                        vImgAdd.startAnimation(mRotaAnimOut);
                    }

                }
                if(re_top != null && btn_longvoice != null){
                    re_top.setVisibility(View.VISIBLE);
                    btn_longvoice.setVisibility(View.GONE);
                }
                if(rebotton != null){
                    rebotton.setVisibility(View.GONE);
                }
                ChatSingleEmotiomFragment.isvoide = false;
                if (mEmotionLayout.isShown()) {
                    //显示软件盘时，锁定内容高度，防止跳闪。
                    lockContentHeight();
                    //隐藏表情布局，显示软件盘
                    hideEmotionLayout(true);
                    //软件盘显示后，释放内容高度
                    unlockContentHeightDelayed();
                } else {
                    if (isSoftInputShown()) {//同上
                        lockContentHeight();
                        showEmotionLayout();
                        unlockContentHeightDelayed();
                    } else {
                        showEmotionLayout();//两者都没显示，直接显示表情布局
                    }
                }
            }
        });
        return this;
    }


    /**
     * 绑定加号
     * @param emotionButton 表情按钮
     * @return
     */
    public EmotionKeyboard bindToViewButton(View emotionButton, final LinearLayout ll_emotion_layout, final ImageButton vImgAdd) {
        emotionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*以view中心点正向旋转45度
                toDegrees：旋转的结束角度。
                pivotXType：X轴的伸缩模式，可以取值为ABSOLUTE、RELATIVE_TO_SELF、RELATIVE_TO_PARENT。
                pivotXValue：X坐标的伸缩值。
                pivotYType：Y轴的伸缩模式，可以取值为ABSOLUTE、RELATIVE_TO_SELF、RELATIVE_TO_PARENT。
                pivotYValue：Y坐标的伸缩值
                 * */
                RotateAnimation mRotaAnimDown = new RotateAnimation(0,45,
                        Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                mRotaAnimDown.setDuration(100);
                //动画执行完毕后是否停在结束时的角度上
                mRotaAnimDown.setFillAfter(true);
                //以view中心点反向旋转45度
                RotateAnimation mRotaAnimOut = new RotateAnimation(45,0,
                        Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                mRotaAnimOut.setDuration(100);
                //动画执行完毕后是否停在结束时的角度上
                mRotaAnimOut.setFillAfter(true);

                if(ll_emotion_layout != null){
                    ll_emotion_layout.setVisibility(View.GONE);
                }
                if (mEmotionLayout.isShown()) {
                    vImgAdd.startAnimation(mRotaAnimOut);
                    lockContentHeight();//显示软件盘时，锁定内容高度，防止跳闪。
                    hideEmotionLayout(true);//隐藏表情布局，显示软件盘
                    unlockContentHeightDelayed();//软件盘显示后，释放内容高度
                } else {
                    vImgAdd.startAnimation(mRotaAnimDown);
                    if (isSoftInputShown()) {//同上
                        lockContentHeight();
                        showEmotionLayout();
                        unlockContentHeightDelayed();
                    } else {
                        showEmotionLayout();//两者都没显示，直接显示表情布局
                    }
                }
            }
        });
        return this;
    }

    /**
     * 设置表情内容布局
     *
     * @param emotionView
     * @return
     */
    public EmotionKeyboard setEmotionView(View emotionView) {
        mEmotionLayout = emotionView;
        return this;
    }


    public EmotionKeyboard build() {
        //设置软件盘的模式：SOFT_INPUT_ADJUST_RESIZE  这个属性表示Activity的主窗口总是会被调整大小，从而保证软键盘显示空间。
        //从而方便我们计算软件盘的高度
        mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN |
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //隐藏软件盘
        hideSoftInput();
        return this;
    }

    /**
     * 点击返回键时先隐藏表情布局
     *
     * @return
     */
    public boolean interceptBackPress() {
        Log.d("EmotionKeyboard", "mEmotionLayout.isShown():" + mEmotionLayout.isShown());
        if (mEmotionLayout.isShown()) {
//            hideEmotionLayout(false);
            mEmotionLayout.setVisibility(View.GONE);
            return false;
        } else {
            return true;
        }
    }

    public void interceptback() {
        if (mEmotionLayout.isShown()) {
            mEmotionLayout.setVisibility(View.GONE);
        } else {
            mActivity.finish();
        }
    }

    public void intercept() {
        ChatSingleEmotiomFragment.isvoide = false;
        if (mEmotionLayout.isShown()) {
            lockContentHeight();//显示软件盘时，锁定内容高度，防止跳闪。
            hideEmotionLayout(true);//隐藏表情布局，显示软件盘
            unlockContentHeightDelayed();//软件盘显示后，释放内容高度
        }
    }

    private void showEmotionLayout() {
        int softInputHeight = getSupportSoftInputHeight();
        if (softInputHeight == 0) {
            softInputHeight = sp.getInt(SHARE_PREFERENCE_SOFT_INPUT_HEIGHT, 578);
        }
        hideSoftInput();
        mEmotionLayout.getLayoutParams().height = softInputHeight;
        mEmotionLayout.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏表情布局
     *
     * @param showSoftInput 是否显示软件盘
     */
    public void hideEmotionLayout(boolean showSoftInput) {
        if (mEmotionLayout.isShown()) {
            mEmotionLayout.setVisibility(View.GONE);
            if (showSoftInput) {
                showSoftInput();
            }
        }
    }

    /**
     * 锁定内容高度，防止跳闪
     */
    private void lockContentHeight() {
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mContentView.getLayoutParams();
        params.height = mContentView.getHeight();
        params.weight = 0.0F;
    }

    /**
     * 释放被锁定的内容高度
     */
    private void unlockContentHeightDelayed() {
        mEditText.postDelayed(new Runnable() {
            @Override
            public void run() {
                ((LinearLayout.LayoutParams) mContentView.getLayoutParams()).weight = 1.0F;
            }
        }, 200L);
    }

    /**
     * 编辑框获取焦点，并显示软件盘
     */
    private void showSoftInput() {
        mEditText.requestFocus();
        mEditText.post(new Runnable() {
            @Override
            public void run() {
                mInputManager.showSoftInput(mEditText, 0);
            }
        });
    }

    /**
     * 隐藏软件盘
     */
    private void hideSoftInput() {
        mInputManager.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }

    /**
     * 是否显示软件盘
     *
     * @return
     */
    private boolean isSoftInputShown() {
        return getSupportSoftInputHeight() != 0;
    }

    /**
     * 获取软件盘的高度
     *
     * @return
     */
    private int getSupportSoftInputHeight() {
        Rect r = new Rect();
        /**
         * decorView是window中的最顶层view，可以从window中通过getDecorView获取到decorView。
         * 通过decorView获取到程序显示的区域，包括标题栏，但不包括状态栏。
         */
        mActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
        //获取屏幕的高度
        int screenHeight = mActivity.getWindow().getDecorView().getRootView().getHeight();
        //计算软件盘的高度
        int softInputHeight = screenHeight - r.bottom;

        /**
         * 某些Android版本下，没有显示软键盘时减出来的高度总是144，而不是零，
         * 这是因为高度是包括了虚拟按键栏的(例如华为系列)，所以在API Level高于20时，
         * 我们需要减去底部虚拟按键栏的高度（如果有的话）
         */
        if (Build.VERSION.SDK_INT >= 20) {
            // When SDK Level >= 20 (Android L), the softInputHeight will contain the height of softButtonsBar (if has)
            softInputHeight = softInputHeight - getSoftButtonsBarHeight();
        }

        if (softInputHeight < 0) {
            Log.d("EmotionKeyboard", "EmotionKeyboard--Warning: value of softInputHeight is below zero!");
        }
        //存一份到本地
        if (softInputHeight > 0) {
            sp.edit().putInt(SHARE_PREFERENCE_SOFT_INPUT_HEIGHT, softInputHeight).apply();
        }
        return softInputHeight;
    }


    /**
     * 底部虚拟按键栏的高度
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private int getSoftButtonsBarHeight() {
        DisplayMetrics metrics = new DisplayMetrics();
        //这个方法获取可能不是真实屏幕的高度
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;
        //获取当前屏幕的真实高度
        mActivity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight) {
            return realHeight - usableHeight;
        } else {
            return 0;
        }
    }

    /**
     * 获取软键盘高度
     *
     * @return
     */
    public int getKeyBoardHeight() {
        return sp.getInt(SHARE_PREFERENCE_SOFT_INPUT_HEIGHT, 400);

    }

}
