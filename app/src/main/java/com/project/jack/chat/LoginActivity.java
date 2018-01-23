package com.project.jack.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.project.jack.R;
import com.project.jack.chat.base.ChatBaseActivity;
import com.project.jack.chat.message.ChatsActivity;
import com.project.jack.chat.util.Constant;
import com.project.jack.chat.weight.MgeToast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jack.project.com.imdatautil.IMDataUtil;
import jack.project.com.imdatautil.callback.ImLoginCallback;

/**
 * Create by www.lijin@foxmail.com on 2018/1/19 0019.
 * <br/>
 * 登录页面
 */

public class LoginActivity extends ChatBaseActivity implements View.OnClickListener {

    /**
     * 输入框
     */
    @BindView(R.id.chat_login_account)
    EditText vEAccount;
    /**
     * 密码框
     */
    @BindView(R.id.chat_login_password)
    EditText vEPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_login_layout_hx);
        //映射组件
        ButterKnife.bind(this);
    }

    @OnClick({R.id.chat_login_login})
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.chat_login_login://登录
                String mAccount = vEAccount.getText().toString();
                String mPassword = vEPassword.getText().toString();
                if(!TextUtils.isEmpty(mAccount) && !TextUtils.isEmpty(mPassword)){
                    IMDataUtil.Login(Constant.config, mAccount, mPassword, new ImLoginCallback() {
                        @Override
                        public void onSuccessTest() {
                            //登录成功跳转到信息页面
                            LoginActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    MgeToast.showSuccessToast(LoginActivity.this,"success");
                                    startActivity(new Intent(LoginActivity.this, ChatsActivity.class));
                                }
                            });
                        }

                        @Override
                        public void onFailure() {

                        }
                    });
                }else{
                    MgeToast.showErrorToast(this,getResources().getString(R.string.chat_mge_red_mima_error));
                }
                break;
            default:
                break;
        }
    }
}
