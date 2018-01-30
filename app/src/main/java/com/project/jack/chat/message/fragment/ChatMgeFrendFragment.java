package com.project.jack.chat.message.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hyphenate.chat.EMMessage;
import com.project.jack.R;
import com.project.jack.chat.message.adapter.ChatMgeFrendListAdapter;
import com.project.jack.chat.util.Constant;
import com.project.jack.chat.weight.MgeToast;
import com.project.jack.chat.weight.SideBar;
import com.project.jack.ui.fragment.BaseFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jack.project.com.imdatautil.IMDataUtil;
import jack.project.com.imdatautil.model.MMFrend;

import static android.content.Context.WINDOW_SERVICE;

/**
 * Create by www.lijin@foxmail.com on 2018/1/19 0019.
 * <br/>
 * 朋友界面
 */

public class ChatMgeFrendFragment extends BaseFragment {

    @BindView(R.id.frend_list)
    ListView vFrendList;

    @BindView(R.id.sideBar)
    SideBar vSideBar;

    @BindView(R.id.frend_listEmptyView)
    LinearLayout vEmpty;

    @BindView(R.id.frend_edsearch)
    EditText vESearch;

    @BindView(R.id.rl_search)
    RelativeLayout vRsearch;
    /**
     * 好友数据
     */
    List<MMFrend> mFrends;
    String editString;
    Context mContext;
    ChatMgeFrendListAdapter mFrendAdapter;
    private Handler handler = new Handler();

    /**
     * 延迟线程，看是否还有下一个字符输入
     */
    private Runnable delayRun = new Runnable() {

        @Override
        public void run() {
            //在这里调用服务器的接口，获取数据
            getSearchResult(editString);
        }
    };

    private View vRootView;
    private TextView mDialogText;
    private WindowManager mWindowManager;
    private static final String TAG = "ChatMgeFrendFragment_TAG";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (vRootView == null) {
            vRootView = inflater.inflate(R.layout.fragment_mge_frend_list, container, false);
            ButterKnife.bind(this, vRootView);
            init();
        }
        return vRootView;
    }

    private void init() {
        mContext = getActivity();
        vFrendList.setEmptyView(vEmpty);
        mWindowManager = (WindowManager) getActivity().getSystemService(WINDOW_SERVICE);
        mFrends = getDBAllFrends();
        mFrendAdapter = new ChatMgeFrendListAdapter(getActivity(), mFrends);
        vFrendList.setAdapter(mFrendAdapter);
        vSideBar.setListView(vFrendList);
        vFrendList.setOnItemClickListener(listOnitem);

        mDialogText = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.sidebar_list_position, null);
        mDialogText.setVisibility(View.INVISIBLE);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        mWindowManager.addView(mDialogText, lp);
        vSideBar.setTextView(mDialogText);
        vRsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vESearch.setFocusable(true);
                vESearch.setFocusableInTouchMode(true);
                vESearch.requestFocus();
                vESearch.requestFocusFromTouch();
                //弹出挼键盘
                InputMethodManager inputManager =
                        (InputMethodManager)vESearch.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(vESearch, 0);
            }
        });
        vESearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                if(delayRun!=null){
                    //每次editText有变化的时候，则移除上次发出的延迟线程
                    handler.removeCallbacks(delayRun);
                }
                editString = s.toString();

                //延迟800ms，如果不再输入字符，则执行该线程的run方法
                handler.postDelayed(delayRun, 300);
            }
        });

        //将键盘右下角设置为发送
        vESearch.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        vESearch.setInputType(EditorInfo.TYPE_CLASS_TEXT);
        vESearch.setSingleLine(true);
        vESearch.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                /*判断是否是“SEND”键*/
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String content = vESearch.getText().toString().trim();
                    if (!TextUtils.isEmpty(content)) {
                        getSearchResult(content);
                    } else {
                        MgeToast.showErrorToast(mContext, getResources().getString(R.string.chat_send_editost));
                    }

                    return true;
                }
                return false;
            }
        });

    }

    /**
     * 获取脉友信息
     *
     * @return 脉友
     */
    private List<MMFrend> getDBAllFrends() {
        List<MMFrend> frends = IMDataUtil.GetAllByPYAsc(Constant.config,getActivity());
        return frends;
    }

    /**
     * 搜索方法
     * @param editString
     */
    private void getSearchResult(String editString){
        if(mFrends != null){
            mFrends.clear();
        }else{
            mFrends = new ArrayList<>();
        }
        if(TextUtils.isEmpty(editString)){
            mFrends.addAll(IMDataUtil.GetAllByPYAsc(Constant.config,getActivity()));
//            mFrendAdapter.notifyDataSetChanged();
        }else{
            mFrends.addAll(IMDataUtil.GetVagueQueryFrend(Constant.config,mContext,editString));
//            mFrendAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 脉友点击事件
     */
    private AdapterView.OnItemClickListener listOnitem = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    };

}
