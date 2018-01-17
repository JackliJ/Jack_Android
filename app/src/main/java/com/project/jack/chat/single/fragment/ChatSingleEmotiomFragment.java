package com.project.jack.chat.single.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.jack.R;
import com.project.jack.chat.adapter.ChatHorizontalRecyclerviewAdapter;
import com.project.jack.chat.adapter.ChatNoHorizontalScrollerVPAdapter;
import com.project.jack.chat.base.ChatBaseFragment;
import com.project.jack.chat.emptionkeyboardview.ChatNoHorizontalScrollerViewPager;
import com.project.jack.chat.eventbus.ChatVoiceCloseEventBus;
import com.project.jack.chat.model.ImageModel;
import com.project.jack.chat.util.AudioRecoderUtils;
import com.project.jack.chat.util.BroadCastReceiverConstant;
import com.project.jack.chat.util.ChatGlobalOnItemClickManagerUtils;
import com.project.jack.chat.util.Constant;
import com.project.jack.chat.util.EmotionUtils;
import com.project.jack.chat.util.FileUtils;
import com.project.jack.chat.util.PopupWindowFactory;
import com.project.jack.chat.util.SoftKeyBoardListener;
import com.project.jack.chat.util.SpanStringUtils;
import com.project.jack.chat.util.TimeUtils;
import com.project.jack.chat.util.Utils;
import com.project.jack.chat.weight.MgeToast;
import com.project.jack.utils.SharedPreferencedUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by www.lijin@foxmail.com on 2018/1/12 0012.
 * <br/>
 */

public class ChatSingleEmotiomFragment extends ChatBaseFragment implements View.OnClickListener {

    private Context context;
    //是否绑定当前Bar的编辑框的flag
    public static final String BIND_TO_EDITTEXT = "bind_to_edittext";
    //是否隐藏bar上的编辑框和发生按钮
    public static final String HIDE_BAR_EDITTEXT_AND_BTN = "hide bar's editText and btn";
    public static final String EMOTION_FRAGMRNT_USERNAME = "UserName";
    public static final String EMOTION_FRAGMRNT_HXID = "HXID";
    //显示弹框的主布局
    private LinearLayout facelayout;
    //当前被选中底部tab
    private static final String CURRENT_POSITION_FLAG = "CURRENT_POSITION_FLAG";
    private int CurrentPosition = 0;
    //底部水平tab
    private RecyclerView recyclerview_horizontal;
    private ChatHorizontalRecyclerviewAdapter horizontalRecyclerviewAdapter;
    //表情面板
    private ChatEmotionKeyboard mEmotionKeyboard;
    private EditText bar_edit_text;
    private Button bar_btn_send;
    private LinearLayout rl_editbar_bg;
    //需要绑定的内容view
    private View contentView;
    private Bundle args;
    //不可横向滚动的ViewPager
    private ChatNoHorizontalScrollerViewPager viewPager;
    //是否绑定当前Bar的编辑框,默认true,即绑定。
    //false,则表示绑定contentView,此时外部提供的contentView必定也是EditText
    private boolean isBindToBarEditText = true;
    //是否隐藏bar上的编辑框和发生按钮,默认不隐藏
    private boolean isHidenBarEditTextAndBtn = false;
    List<Fragment> fragments = new ArrayList<>();
    //语音录制按钮
    private Button btn_longvoice;
    //输入选择区域
    private LinearLayout re_top;
    //底部选择框
    private LinearLayout rebotton;
    private String mHuanxinID;
    //加号按钮
    private ImageButton vImgAdd;
    // 对方用户昵称 username 对方用户头像 avatar  对方用户实名认证状态 authStatus 对方用户企业认证状态 businessAuthStatus
    private String mOtherUid, mOtherUsername, mOtherUserAvatar,
            mOtherAuthStatus, mOtherBusinessAuthStatus,
            mOtherNamgeCardBgImage;
    //对方用户VIP等级 vipLevel
    private String mOtherVipLevel;
    //平移动画
    private TranslateAnimation HiddenAmin;
    //缩放动画
    private ScaleAnimation mSShowAnim;
    //加号的旋转动画
    private RotateAnimation mRotaAnimDown;
    private RotateAnimation mRotaAnimOut;
    //是否在删除的时候不加载动画
    private boolean mIsAnim = true;
    //表情布局
    private LinearLayout ll_emotion_layout;
    //传递数据的回调
    private FragmentListener listener = null;

    private ImageView imgvoide;
    private boolean isvocie = false;
    //开放给fragment
    public static boolean isvoide = false;

    public interface FragmentListener {
        void thank(String text, String UserName);
    }

    @Override
    public void onAttach(Activity activity) {
        listener = (FragmentListener) activity;
        super.onAttach(activity);
    }

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
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_emotions, container, false);
        context = getActivity();
        isHidenBarEditTextAndBtn = args.getBoolean(ChatSingleEmotiomFragment.HIDE_BAR_EDITTEXT_AND_BTN);
        mHuanxinID = args.getString(Constant.MEG_INTNT_CHATMESSAGE_HXID);
        mOtherUsername = args.getString(Constant.MEG_INTNT_CHATMESSAGE_OTHERUSERNAME);
        mOtherUid = args.getString(Constant.MEG_INTNT_CHATMESSAGE_OTHRTUID);
        mOtherUserAvatar = args.getString(Constant.MEG_INTNT_CHATMESSAGE_OTHERUSERAVATAR);
        mOtherAuthStatus = args.getString(Constant.MEG_INTNT_CHATMESSAGE_OTHERAURHSTATUS);
        mOtherBusinessAuthStatus = args.getString(Constant.MEG_INTNT_CHATMESSAGE_OTHERABUSINESSAU);
        mOtherVipLevel = args.getString(Constant.MEG_INTNT_CHATMESSAGE_OTHERVIPLEVEL);
        mOtherNamgeCardBgImage = args.getString(Constant.MEG_INTNT_CHATMESSAGE_OTHERNAMGECARSBGIMAGE);
        //初始化展现动画
        //控件显示的动画 缩放动画
        /* 仿造微信安卓版的效果
                参数解释：
                    第一个参数：X轴水平缩放起始位置的大小（fromX）。1代表正常大小
                    第二个参数：X轴水平缩放完了之后（toX）的大小，0代表完全消失了
                    第三个参数：Y轴垂直缩放起始时的大小（fromY）
                    第四个参数：Y轴垂直缩放结束后的大小（toY）
                    第五个参数：pivotXType为动画在X轴相对于物件位置类型
                    第六个参数：pivotXValue为动画相对于物件的X坐标的开始位置
                    第七个参数：pivotXType为动画在Y轴相对于物件位置类型
                    第八个参数：pivotYValue为动画相对于物件的Y坐标的开始位置

                   （第五个参数，第六个参数），（第七个参数,第八个参数）是用来指定缩放的中心点
                    0.5f代表从中心缩放
        */
        mSShowAnim = new ScaleAnimation(0.5f,1,0.5f,1,
                1,0.5f,1,0.5f);
        mSShowAnim.setDuration(150);


        //控件隐藏的动画 上移动画
        /* 位移动画 模拟将发送按钮发送出去的效果
                    fromXDelta：起始X坐标
                    toXDelta： 结束X坐标
                    fromYDelta：起始Y坐标
                    toYDelta： 结束Y坐标
         */
        HiddenAmin = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f,Animation.RELATIVE_TO_SELF
                ,0.0f,Animation.RELATIVE_TO_SELF,-1.0f);
        HiddenAmin.setDuration(50);

        /*以view中心点正向旋转45度
                toDegrees：旋转的结束角度。
                pivotXType：X轴的伸缩模式，可以取值为ABSOLUTE、RELATIVE_TO_SELF、RELATIVE_TO_PARENT。
                pivotXValue：X坐标的伸缩值。
                pivotYType：Y轴的伸缩模式，可以取值为ABSOLUTE、RELATIVE_TO_SELF、RELATIVE_TO_PARENT。
                pivotYValue：Y坐标的伸缩值
                 * */
        mRotaAnimDown = new RotateAnimation(0,45,
                Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        mRotaAnimDown.setDuration(100);
        //动画执行完毕后是否停在结束时的角度上
        mRotaAnimDown.setFillAfter(true);
        //还原动画
        mRotaAnimOut = new RotateAnimation(0,0,
                Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        mRotaAnimOut.setDuration(100);
        //动画执行完毕后是否停在结束时的角度上
        mRotaAnimOut.setFillAfter(true);



        //获取判断绑定对象的参数
        isBindToBarEditText = args.getBoolean(ChatSingleEmotiomFragment.BIND_TO_EDITTEXT);
        initView(rootView);
        //绑定表情按钮
        mEmotionKeyboard = ChatEmotionKeyboard.with(getActivity())
                .setEmotionView(rootView.findViewById(R.id.ll_emotion_layout))
                .bindToContent(contentView)
                .bindToEditText(!isBindToBarEditText ? ((EditText) contentView) : ((EditText) rootView.findViewById(R.id.bar_edit_text)))
                .bindToEmotionButton(rootView.findViewById(R.id.emotion_button), rebotton,re_top,btn_longvoice,vImgAdd)
                .build();
        initDatas();

        //绑定模块布局
        ChatEmotionKeyboard.with(getActivity())
                .setEmotionView(rootView.findViewById(R.id.ll_devoicefrls))
                .bindToContent(contentView)
                .bindToEditText(!isBindToBarEditText ? ((EditText) contentView) : ((EditText) rootView.findViewById(R.id.bar_edit_text)))
                .bindToViewButton(rootView.findViewById(R.id.btn_jias),ll_emotion_layout,vImgAdd)
                .build();

        //创建全局监听
        ChatGlobalOnItemClickManagerUtils globalOnItemClickManager = ChatGlobalOnItemClickManagerUtils.getInstance(getActivity());

        if (isBindToBarEditText) {
            //绑定当前Bar的编辑框
            globalOnItemClickManager.attachToEditText(bar_edit_text);

        } else {
            // false,则表示绑定contentView,此时外部提供的contentView必定也是EditText
            globalOnItemClickManager.attachToEditText((EditText) contentView);
            mEmotionKeyboard.bindToEditText((EditText) contentView);
        }
        String draft = SharedPreferencedUtils.getString(context, mHuanxinID);
        //将键盘右下角设置为发送
        bar_edit_text.setImeOptions(EditorInfo.IME_ACTION_SEND);
        bar_edit_text.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        bar_edit_text.setSingleLine(true);
        //设置草稿
        if(!TextUtils.isEmpty(draft)){
            bar_edit_text.setText(SpanStringUtils.getEmotionContent(EmotionUtils.EMOTION_TOTAL, context, bar_edit_text, draft));
        }
        bar_btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = bar_edit_text.getText().toString().trim();
                if(!TextUtils.isEmpty(content)){
                    listener.thank(content, mHuanxinID);
                    bar_edit_text.setText("");
                }else{
                    MgeToast.showErrorToast(context,getResources().getString(R.string.chat_send_editost), Toast.LENGTH_SHORT);
                }
            }
        });
        bar_edit_text.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                /*判断是否是“SEND”键*/
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    String content = bar_edit_text.getText().toString().trim();
                    if (Utils.GetStringISNull(content)) {
                        listener.thank(content, mHuanxinID);
                        bar_edit_text.setText("");
                    } else {
                        MgeToast.showErrorToast(context,getResources().getString(R.string.chat_send_editost), Toast.LENGTH_SHORT);
                    }

                    return true;
                }
                return false;
            }
        });
        bar_edit_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //监听输入 隐藏创建模块
                if (!TextUtils.isEmpty(bar_edit_text.getText().toString().trim())) {
                    bar_btn_send.setBackgroundResource(R.drawable.public_btn_submit_select_click);
                    if(mIsAnim){
                        bar_btn_send.startAnimation(mSShowAnim );
                        vImgAdd.startAnimation(HiddenAmin);
                        mIsAnim = false;
                    }
                    bar_btn_send.setVisibility(View.VISIBLE);
                    vImgAdd.setVisibility(View.GONE);
                } else {
                    if(!mIsAnim){
                        bar_btn_send.startAnimation(HiddenAmin );
                        vImgAdd.startAnimation(mSShowAnim );
                        mIsAnim = true;
                    }
                    bar_btn_send.setVisibility(View.GONE);
                    vImgAdd.setVisibility(View.VISIBLE);
                }

            }
        });
        //监听软键盘弹起的类
        SoftKeyBoardListener.setListener((Activity) context, new SoftKeyBoardListener.OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {//键盘打开
                if(rebotton != null){
                    rebotton.setVisibility(View.GONE);
                }
                if(ll_emotion_layout != null){
                    ll_emotion_layout.setVisibility(View.GONE);
                }
                if(vImgAdd != null){
                    if(bar_edit_text.getText().toString().trim().length() == 0){
                        vImgAdd.startAnimation(mRotaAnimOut);
                    }
                }
            }

            @Override
            public void keyBoardHide(int height) {//键盘收起

            }
        });
        return rootView;
    }

    /**
     * 绑定内容view
     *
     * @param contentView
     * @return
     */
    public void bindToContentView(View contentView) {
        this.contentView = contentView;
    }

    /**
     * 初始化view控件
     */
    protected void initView(View rootView) {
        viewPager = (ChatNoHorizontalScrollerViewPager) rootView.findViewById(R.id.vp_emotionview_layout);
        facelayout = (LinearLayout) rootView.findViewById(R.id.face_layouts);
        recyclerview_horizontal = (RecyclerView) rootView.findViewById(R.id.recyclerview_horizontal);
        rebotton = (LinearLayout) rootView.findViewById(R.id.ll_devoicefrls);
        rootView.findViewById(R.id.btn_voice).setOnClickListener(this);
        rootView.findViewById(R.id.hxc_tuijian).setOnClickListener(this);
        rootView.findViewById(R.id.hxc_address).setOnClickListener(this);
        rootView.findViewById(R.id.btn_redpackge).setOnClickListener(this);
        rootView.findViewById(R.id.btn_pictrue).setOnClickListener(this);
        rootView.findViewById(R.id.btn_camera).setOnClickListener(this);
        rootView.findViewById(R.id.emotion_button).setOnClickListener(this);
        rootView.findViewById(R.id.bar_edit_text).setOnClickListener(this);
        rootView.findViewById(R.id.hxc_voide).setOnClickListener(this);
        rootView.findViewById(R.id.hxc_voice).setOnClickListener(this);
        vImgAdd = (ImageButton) rootView.findViewById(R.id.btn_jias);
        ll_emotion_layout = (LinearLayout) rootView.findViewById(R.id.ll_emotion_layout);
        vImgAdd.setOnClickListener(this);
        bar_btn_send = (Button) rootView.findViewById(R.id.bar_btn_send);
        re_top = (LinearLayout) rootView.findViewById(R.id.emf_lrm);
        bar_edit_text = (EditText) rootView.findViewById(R.id.bar_edit_text);
        btn_longvoice = (Button) rootView.findViewById(R.id.btn_longvoice);
        rl_editbar_bg = (LinearLayout) rootView.findViewById(R.id.rl_editbar_bg);
        if (isHidenBarEditTextAndBtn) {//隐藏
            bar_edit_text.setVisibility(View.GONE);
            rl_editbar_bg.setBackgroundResource(R.color.b15);
        } else {
            bar_edit_text.setVisibility(View.VISIBLE);
            rl_editbar_bg.setBackgroundResource(R.drawable.shape_bg_reply_edittext);
        }
        StartListener();
    }

    private PopupWindowFactory mPop;
    private AudioRecoderUtils mAudioRecoderUtils;
    private float mPosX, mPosY, mCurPosX, mCurPosY;
    private ImageView mImageView;
    private TextView mTextView;

    /**
     * 录制语音文件
     */
    public void StartListener() {
        //PopupWindow的布局文件
        final View view = View.inflate(context, R.layout.row_layout_microphone, null);
        mPop = new PopupWindowFactory(context, view);
        //PopupWindow布局文件里面的控件
        mImageView = (ImageView) view.findViewById(R.id.iv_recording_icon);
        mTextView = (TextView) view.findViewById(R.id.tv_recording_time);
        //获取项目指定的文件夹
        String filepath  = FileUtils.createDir(context,FileUtils.hxAudioir);
        mAudioRecoderUtils = new AudioRecoderUtils(filepath);
        //Button的touch监听
        btn_longvoice.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        //发送被按下的EventBus
                        EventBus.getDefault().post(new ChatVoiceCloseEventBus());
                        mPosX = event.getX();
                        mPosY = event.getY();

                        mPop.showAtLocation(facelayout, Gravity.CENTER, 0, 0);

                        btn_longvoice.setText(getResources().getString(R.string.chat_release_send));
                        mAudioRecoderUtils.startRecord();
                        btn_longvoice.setSelected(true);

                        break;


                    case MotionEvent.ACTION_UP:
                        mCurPosY = event.getY();
                        if (mPosY - mCurPosY > 50) {//手势上滑
                            mAudioRecoderUtils.cancelRecord();    //取消录音（不保存录音文件）
                            mPop.dismiss();
                            btn_longvoice.setText(getResources().getString(R.string.chat_press_speak));
                            btn_longvoice.setSelected(false);
                            Log.d("EmotionMainFragment", "取消录制");
                            break;
                        }
                        mAudioRecoderUtils.stopRecord();        //结束录音（保存录音文件）
                        mPop.dismiss();
                        btn_longvoice.setText(getResources().getString(R.string.chat_press_speak));
                        btn_longvoice.setSelected(false);
                        break;
                }
                return true;
            }
        });

        //录音回调
        mAudioRecoderUtils.setOnAudioStatusUpdateListener(new AudioRecoderUtils.OnAudioStatusUpdateListener() {

            //录音中....db为声音分贝，time为录音时长
            @Override
            public void onUpdate(double db, long time) {
                mImageView.getDrawable().setLevel((int) (3000 + 6000 * db / 100));
                mTextView.setText(TimeUtils.long2String(time));
                mAudioRecoderUtils.stopRecord();        //结束录音（保存录音文件）
                mPop.dismiss();
                btn_longvoice.setText(getResources().getString(R.string.chat_press_speak));
                btn_longvoice.setSelected(false);
            }

            //录音结束，filePath为保存路径
            @Override
            public void onStop(String filePath, long time) {
                Log.d("FaceRelativeLayout", "录音保存在：" + filePath+"time-->"+time);
                //过滤0的情况，至少要1秒才发送
                if((time/1000) >= 1){
                    mTextView.setText(TimeUtils.long2String(0));
                    //发送录音结束的广播  通知消息发送
                    Intent in = new Intent(BroadCastReceiverConstant.BROAD_MESSAGEVOICE);
                    in.putExtra(Constant.MEG_INTNT_CHATMESSAGE_VOICEPATH, filePath);
                    in.putExtra(Constant.MEG_INTNT_CHATMESSAGE_VOICTIME, time);
                    context.sendBroadcast(in);
                }
            }
        });
    }


    /**
     * 数据操作
     */
    protected void initDatas() {
        replaceFragment();
        List<ImageModel> list = new ArrayList<>();
        for (int i = 0; i < fragments.size(); i++) {
            if (i == 0) {
                ImageModel model1 = new ImageModel();
                model1.icon = getResources().getDrawable(R.drawable.chat_icon_exp_tab_default);
                model1.flag = getResources().getString(R.string.chat_classics_emoj);
                model1.isSelected = true;
                list.add(model1);
            } else if (i == 1) {
                ImageModel model1 = new ImageModel();
                model1.icon = getResources().getDrawable(R.drawable.exp_egg_01);
                model1.flag = getResources().getString(R.string.chat_extend_emoj);
                model1.isSelected = false;
                list.add(model1);
            } else {
                ImageModel model = new ImageModel();
                model.icon = getResources().getDrawable(R.drawable.ic_plus);
                model.flag = getResources().getString(R.string.chat_other_emoj) + i;
                model.isSelected = false;
                list.add(model);
            }
        }

        //记录底部默认选中第一个
        CurrentPosition = 0;
        SharedPreferencedUtils.setInteger(getActivity(), CURRENT_POSITION_FLAG, CurrentPosition);

        //底部tab
        horizontalRecyclerviewAdapter = new ChatHorizontalRecyclerviewAdapter(getActivity(), list);
        recyclerview_horizontal.setHasFixedSize(true);//使RecyclerView保持固定的大小,这样会提高RecyclerView的性能
        recyclerview_horizontal.setAdapter(horizontalRecyclerviewAdapter);
        recyclerview_horizontal.setLayoutManager(new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false));
        //初始化recyclerview_horizontal监听器
        horizontalRecyclerviewAdapter.setOnClickItemListener(new ChatHorizontalRecyclerviewAdapter.OnClickItemListener() {
            @Override
            public void onItemClick(View view, int position, List<ImageModel> datas) {
                //获取先前被点击tab
                int oldPosition = SharedPreferencedUtils.getInteger(getActivity(), CURRENT_POSITION_FLAG, 0);
                //修改背景颜色的标记
                datas.get(oldPosition).isSelected = false;
                //记录当前被选中tab下标
                CurrentPosition = position;
                datas.get(CurrentPosition).isSelected = true;
                SharedPreferencedUtils.setInteger(getActivity(), CURRENT_POSITION_FLAG, CurrentPosition);
                //通知更新，这里我们选择性更新就行了
                horizontalRecyclerviewAdapter.notifyItemChanged(oldPosition);
                horizontalRecyclerviewAdapter.notifyItemChanged(CurrentPosition);
                //viewpager界面切换
                viewPager.setCurrentItem(position, false);
            }

            @Override
            public void onItemLongClick(View view, int position, List<ImageModel> datas) {
            }
        });


    }

    private void replaceFragment() {
        //创建fragment的工厂类
        FragmentFactory factory = FragmentFactory.getSingleFactoryInstance();
        //创建修改实例
        ChatEmotiomComplateFragment f1 = (ChatEmotiomComplateFragment) factory.getFragment(EmotionUtils.EMOTTON_AM_TYPE, false);
        fragments.add(f1);
        ChatEmotiomComplateFragment f2 = (ChatEmotiomComplateFragment) factory.getFragment(EmotionUtils.EMOTION_CLASSIC_TYPE, true);
        fragments.add(f2);
        ChatNoHorizontalScrollerVPAdapter adapter = new ChatNoHorizontalScrollerVPAdapter(getActivity().getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
    }


    /**
     * 是否拦截返回键操作，如果此时表情布局未隐藏，先隐藏表情布局
     *
     * @return true则隐藏表情布局，拦截返回键操作
     * false 则不拦截返回键操作
     */
    public boolean isInterceptBackPress() {
        return mEmotionKeyboard.interceptBackPress();
    }

    /**
     * 表情布局没隐藏的时候隐藏它
     */
    public void isShowInterceptBackPress() {
        mEmotionKeyboard.interceptback();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
//            case PictureConfig.CHOOSE_REQUEST:
//                // 图片选择结果回调
//                final List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
//                // 例如 LocalMedia 里面返回三种path
//                // 1.media.getPath(); 为原图path
//                // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
//                // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
//                // 如果裁剪并压缩了，已取压缩路径为准，因为是先裁剪后压缩的
//                if (selectList.size() > 0 && selectList != null) {
//                    boolean isOriginal = data.getBooleanExtra("isOriginal", false);//是否原图
//                    for (int i = 0; i < selectList.size(); i++) {
//                        LogUtils.d("EmotionMainFragment", "detype-->" + selectList.get(i).getPictureType());
//                        if (selectList.get(i).getPictureType().equals(Constant.PICTURE_IMG_jpeg)
//                                || selectList.get(i).getPictureType().equals(Constant.PICTURE_IMG_png)) {//如果选中的是图片
//                            final String filepath = selectList.get(i).getPath();
//                            //依次发送图片
//                            SendUtil.SendImage(context, mHuanxinID, isOriginal, filepath,mOtherUid, mOtherUsername, mOtherUserAvatar,
//                                    mOtherVipLevel, mOtherAuthStatus, mOtherBusinessAuthStatus, mOtherNamgeCardBgImage);
//                            Intent in = new Intent(BroadCastReceiverConstant.BROAD_MESSAGERECEIVED);
//                            in.putExtra(Constant.isSendRefrsh,true);
//                            context.sendBroadcast(in);
//                        } else {//选中的视频  这里不考虑音频
//                            //依次发送视频
//                            //selectList.get(i).getDuration() 视频时间  毫秒
//                            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
//                            retriever.setDataSource(selectList.get(i).getPath());
//                            Bitmap bitmap = retriever.getFrameAtTime(1 * 1000);
//                            String bitpath = FileUtil.saveBitmap("JCamera", bitmap);
//                            LogUtils.d("EmotionMainFragment", selectList.get(i).getPath() + "--" + bitpath + "--" + ((int) selectList.get(i).getDuration()));
//                            SendUtil.SendVideo(context, mHuanxinID, mOtherUid, selectList.get(i).getPath(), bitpath,
//                                    ((int) selectList.get(i).getDuration()), mOtherUsername, mOtherUserAvatar,
//                                    mOtherVipLevel, mOtherAuthStatus, mOtherBusinessAuthStatus, mOtherNamgeCardBgImage);
//                            Intent in = new Intent(BroadCastReceiverConstant.BROAD_MESSAGERECEIVED);
//                            in.putExtra(Constant.isSendRefrsh,true);
//                            context.sendBroadcast(in);
//                        }
//                    }
//                }
//                break;
        }
    }

    /**
     * 草稿
     */
    public void setdraft() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String draft = bar_edit_text.getText().toString().trim();
                if (Utils.GetStringISNull(draft)) {
                    if (context != null && Utils.GetStringISNull(mHuanxinID)) {
                        SharedPreferencedUtils.setString(context, mHuanxinID, draft);
                    }
                } else {
                    if (context != null && Utils.GetStringISNull(mHuanxinID)) {
                        SharedPreferencedUtils.setString(context, mHuanxinID, "");
                    }
                }
            }
        }).start();
    }

    @Override
    public void onResume() {
        super.onResume();
        setdraft();
    }

    @Override
    public void onPause() {
        super.onPause();
        setdraft();
    }

    @Override
    public void onStop() {
        super.onStop();
        setdraft();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        setdraft();
    }

    boolean mIsEditext = true;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_redpackge://发送红包
//                rebotton.setVisibility(View.GONE);
//                isvoide = false;
//                Intent insendred = new Intent(context, SendRedActivity.class);
//                insendred.putExtra(Constant.MEG_INITENT_RED_CURTYPE,SendRedActivity.RED_SEND_TYPE_CHAT);
//                insendred.putExtra(Constant.MEG_INITENT_RED_TARGETUID,mOtherUid);
//                insendred.putExtra(Constant.MEG_INTNT_CHATMESSAGE_HXID, mHuanxinID);
//                insendred.putExtra(Constant.MEG_INTNT_CHATMESSAGE_OTHERUSERNAME, mOtherUsername);
//                insendred.putExtra(Constant.MEG_INTNT_CHATMESSAGE_OTHERUSERAVATAR, mOtherUserAvatar);
//                insendred.putExtra(Constant.MEG_INTNT_CHATMESSAGE_OTHERVIPLEVEL,mOtherVipLevel+"");
//                insendred.putExtra(Constant.MEG_INTNT_CHATMESSAGE_OTHERAURHSTATUS,mOtherAuthStatus+"");
//                insendred.putExtra(Constant.MEG_INTNT_CHATMESSAGE_OTHERABUSINESSAU,mOtherBusinessAuthStatus+"");
//                insendred.putExtra(Constant.MEG_INTNT_CHATMESSAGE_OTHERNAMGECARSBGIMAGE,mOtherNamgeCardBgImage);
//                startActivity(insendred);
                break;
            case R.id.hxc_address://发送位置
//                rebotton.setVisibility(View.GONE);
//                isvoide = false;
//                Intent inaddress = new Intent(context, LocationActivity.class);
//                inaddress.putExtra(IConfig.EXTRA_ACTION_TYPE_0,false);
//                startActivity(inaddress);
                break;
            case R.id.hxc_tuijian://推荐好友（名片分享）
//                rebotton.setVisibility(View.GONE);
//                isvoide = false;
//                Intent infrend = new Intent(context, MgeFrendListActivity.class);
//                infrend.putExtra(Constant.MEG_INTENT_FREND_TYPE,1);
//                infrend.putExtra(Constant.MEG_INTENT_FREND_ADDRESS,2);//跳转的路径
//                infrend.putExtra(Constant.MEG_INTNT_CHATMESSAGE_OTHRTUID,mOtherUid+"");
//                infrend.putExtra(Constant.MEG_INTNT_CHATMESSAGE_HXID, mHuanxinID);
//                infrend.putExtra(Constant.MEG_INTNT_CHATMESSAGE_OTHERUSERNAME, mOtherUsername);
//                infrend.putExtra(Constant.MEG_INTNT_CHATMESSAGE_OTHERUSERAVATAR, mOtherUserAvatar);
//                infrend.putExtra(Constant.MEG_INTNT_CHATMESSAGE_OTHERVIPLEVEL,mOtherVipLevel+"");
//                infrend.putExtra(Constant.MEG_INTNT_CHATMESSAGE_OTHERAURHSTATUS,mOtherAuthStatus+"");
//                infrend.putExtra(Constant.MEG_INTNT_CHATMESSAGE_OTHERABUSINESSAU,mOtherBusinessAuthStatus+"");
//                infrend.putExtra(Constant.MEG_INTNT_CHATMESSAGE_OTHERNAMGECARSBGIMAGE,mOtherNamgeCardBgImage);
//                startActivity(infrend);
                break;
            case R.id.btn_voice://语音
//                if(Utils.isVoicePermission()){
//                    isInterceptBackPress();
//                    //收起软键盘
//                    Utils.HideMethod(context, bar_edit_text);
//                    rebotton.setVisibility(View.GONE);
//                    isvoide = false;
//                    if (isvocie) {
//                        btn_longvoice.setVisibility(View.GONE);
//                        isvocie = false;
//                    } else {
//                        //展示语音输出按钮
//                        btn_longvoice.setVisibility(View.VISIBLE);
//                        isvocie = true;
//                    }
//                    if(vImgAdd != null){
//                        if(bar_edit_text.getText().toString().trim().length() == 0){
//                            vImgAdd.startAnimation(mRotaAnimOut);
//                        }
//                    }
//                }else{
//                    MgeToast.showErrorToast(context,context.getResources().getString(R.string.permission_message));
//                }
                break;
            case R.id.btn_jias://加号
                ll_emotion_layout.setVisibility(View.GONE);
                if (isvoide) {
                    rebotton.setVisibility(View.GONE);
                    isvoide = false;
                } else {
                    rebotton.setVisibility(View.VISIBLE);
                    isvoide = true;
                }
                break;
            case R.id.hxc_voide:
//                /**
//                 * 拨打视频通话
//                 * @param to
//                 * @throws EMServiceNotReadyException
//                 */
//                ChatCallTimerUtil.getBinstance().startConversation(context,mHuanxinID,false,true,
//                        mOtherUid,mOtherUsername,mOtherUserAvatar,mOtherVipLevel,mOtherAuthStatus,mOtherBusinessAuthStatus,
//                        mOtherNamgeCardBgImage);
//                break;
            case R.id.hxc_voice:
//                /**
//                 * 拨打语音通话
//                 * @param to
//                 * @throws EMServiceNotReadyException
//                 */
//                try {//多参数
//                    User user = User.GetLoginedUser(context);
//                    String mExt = String.valueOf(user.uid)+"|"+user.username + "|"+String.valueOf(user.vipLevel)+"|" + user.avatar + "|" + user.bgImage+"|"+String.valueOf(user.authStatus)+"|"+String.valueOf(user.businessAuthStatus);
//                    EMClient.getInstance().callManager().makeVoiceCall(mHuanxinID,mExt);
//                    Intent ins = new Intent(context, CallVoiceActivity.class);
//                    ins.putExtra("username", mHuanxinID);
//                    ins.putExtra("isComingCall", false);
//                    ins.putExtra("status", true);
//                    ins.putExtra(Constant.MEG_INTNT_CHATMESSAGE_OTHRTUID, mOtherUid);
//                    ins.putExtra(Constant.MEG_INTNT_CHATMESSAGE_OTHERUSERNAME,mOtherUsername);
//                    ins.putExtra(Constant.MEG_INTNT_CHATMESSAGE_OTHERUSERAVATAR, mOtherUserAvatar);
//                    ins.putExtra(Constant.MEG_INTNT_CHATMESSAGE_OTHERVIPLEVEL, mOtherVipLevel);
//                    ins.putExtra(Constant.MEG_INTNT_CHATMESSAGE_OTHERAURHSTATUS, mOtherAuthStatus);
//                    ins.putExtra(Constant.MEG_INTNT_CHATMESSAGE_OTHERABUSINESSAU, mOtherBusinessAuthStatus);
//                    ins.putExtra(Constant.MEG_INTNT_CHATMESSAGE_OTHERNAMGECARSBGIMAGE, mOtherNamgeCardBgImage);
//                    context.startActivity(ins);
//                } catch (EMServiceNotReadyException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
                break;
            case R.id.bar_edit_text:
//                if(re_top != null && btn_longvoice != null){
//                    re_top.setVisibility(View.VISIBLE);
//                    btn_longvoice.setVisibility(View.GONE);
//                }
//                if(rebotton != null){
//                    rebotton.setVisibility(View.GONE);
//                }
//                mEmotionKeyboard.intercept();
//                mIsEditext = false;
//                isvoide = false;
//                break;
            case R.id.btn_pictrue://发送图片视频
//                rebotton.setVisibility(View.GONE);
//                isvoide = false;
//                PictureSelector.create(EmotionMainFragment.this)
//                        .openGallery(PictureMimeType.ofAll())// 全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
//                        .theme(R.style.picture_MGR_style)// 主题样式设置 具体参考 values/styles   用法：R.style.picture.white.style
//                        .maxSelectNum(9)// 最大图片选择数量
//                        .maxVideoSelectNum(1)//最大的视屏选择数量
//                        .videoQuality(0)// 视频录制质量 0 or 1
//                        .videoMaxSecond(10)//加载视频的最大秒数
//                        .videoMinSecond(1)//选择视频的最小秒数
//                        .recordVideoSecond(10)//录制视频秒数 默认60s
//                        .minSelectNum(1)// 最小图片选择数量
//                        .imageSpanCount(4)// 每行显示个数
////                        .setOutputCameraPath()//相机摄像摄影文件的保存的路径 (需要在相册中开启摄像摄影功能)
//                        .selectionMode(PictureConfig.MULTIPLE)// 多选 or 单选  PictureConfig.MULTIPLE : PictureConfig.SINGLE
//                        .previewImage(true)// 是否可预览图片
//                        .previewVideo(true)// 是否可预览视频
//                        .enablePreviewAudio(false) // 是否可播放音频
//                        .compressGrade(Luban.THIRD_GEAR)// luban压缩档次，默认3档 Luban.FIRST_GEAR、Luban.CUSTOM_GEAR
//                        .isCamera(false)// 是否显示拍照按钮
//                        .isZoomAnim(true)// 图片列表点击 缩放效果 默认true
//                        //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径
//                        .enableCrop(false)// 是否裁剪
//                        .compress(false)// 是否压缩
//                        .compressMode(PictureConfig.LUBAN_COMPRESS_MODE)//系统自带 or 鲁班压缩 PictureConfig.SYSTEM_COMPRESS_MODE or LUBAN_COMPRESS_MODE
//                        //.sizeMultiplier(0.5f)// glide 加载图片大小 0~1之间 如设置 .glideOverride()无效
//                        .glideOverride(160, 160)// glide 加载宽高，越小图片列表越流畅，但会影响列表图片浏览的清晰度
////                        .withAspectRatio(aspect_ratio_x, aspect_ratio_y)// 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
////                        .hideBottomControls(cb_hide.isChecked() ? false : true)// 是否显示uCrop工具栏，默认不显示
////                        .isGif(cb_isGif.isChecked())// 是否显示gif图片
////                        .freeStyleCropEnabled(cb_styleCrop.isChecked())// 裁剪框是否可拖拽
////                        .circleDimmedLayer(cb_crop_circular.isChecked())// 是否圆形裁剪
////                        .showCropFrame(cb_showCropFrame.isChecked())// 是否显示裁剪矩形边框 圆形裁剪时建议设为false
////                        .showCropGrid(cb_showCropGrid.isChecked())// 是否显示裁剪矩形网格 圆形裁剪时建议设为false
//                        .openClickSound(false)// 是否开启点击声音
////                        .selectionMedia(selectList)// 是否传入已选图片
//                        .previewEggs(true)// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中)
//                        //.cropCompressQuality(90)// 裁剪压缩质量 默认100
//                        //.compressMaxKB()//压缩最大值kb compressGrade()为Luban.CUSTOM_GEAR有效
//                        //.compressWH() // 压缩宽高比 compressGrade()为Luban.CUSTOM_GEAR有效
//                        //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效
//                        //.rotateEnabled() // 裁剪是否可旋转图片
//                        //.scaleEnabled()// 裁剪是否可放大缩小图片
//                        .forResult(PictureConfig.CHOOSE_REQUEST);//结果回调onActivityResult code
//                break;
            case R.id.btn_camera://摄像  照片  小视频
//                rebotton.setVisibility(View.GONE);
//                isvoide = false;
//                startActivityForResult(new Intent(context, CameraActivity.class), 100);
                break;
        }
    }
}
