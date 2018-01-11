package com.project.jack.ui.alymessage.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.auth.ClientType;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.auth.OnlineClient;
import com.project.jack.R;
import com.project.jack.aly.preferences.AlyLoginPreferences;
import com.project.jack.base.BaseActivity;
import com.project.jack.base.BaseSwipeActivity;
import com.project.jack.ui.alymessage.ui.message.AlyMessageActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Create by www.lijin@foxmail.com on 2017/12/28 0028.
 * <br/>
 * 网易云IM 登录页
 */

public class AliYLoginActivity extends BaseSwipeActivity {

    /**
     * account ed
     */
    @BindView(R.id.aly_login_account)
    EditText vAccount;
    /**
     * password ed
     */
    @BindView(R.id.aly_login_password)
    EditText vPassword;
    /**
     * login button
     */
    @BindView(R.id.aly_login_btnClick)
    Button vBtnClick;

    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aliy_login_layout);
        ButterKnife.bind(this);
        mContext = this;
        setToolbar();
        vBtnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initLogin();
            }
        });
    }

    private void initLogin(){
        String account = vAccount.getText().toString();
        String password = vPassword.getText().toString();

        if(!TextUtils.isEmpty(account) && !TextUtils.isEmpty(password)){
            //do login
            doLogin(account,password);
        }else{
            Toast.makeText(this, getResources().getString(R.string.aly_login_login_null), Toast.LENGTH_SHORT).show();
        }
    }

    public void doLogin(final String account, final String password) {
        LoginInfo info = new LoginInfo(account,password);
        RequestCallback<LoginInfo> callback = new RequestCallback<LoginInfo>() {
            //login success
            @Override
            public void onSuccess(LoginInfo param) {
                //Save login information
                AlyLoginPreferences.saveUserAccount(mContext,account);
                AlyLoginPreferences.saveUserToken(mContext,password);
                //register observer
                initObserver();
                //start message main
                startActivity(new Intent(mContext, AlyMessageActivity.class));
            }

            @Override
            public void onFailed(int code) {
                switch (code){
                    /*
                        token 错误或者账号不存在都会导致 302 错误码。
                        这种情况一般发生于用户在其他设备上修改了密码。/
                     */
                    case 302:

                        break;
                    /*
                     1、连接建立成功，SDK 发出登录请求后网易云通信服务器一直
                        没有响应，那么 30s 后将导致登录超时。
                        2、登录成功之前，调用服务器相关请求接口
                        （由于与网易云通信服务器连接尚未建立成功，会导致发包超时）/
                         */
                    case 408:

                        break;
                        /*
                        网络断开或者与网易云通信服务器建立连接失败/
                         */
                    case 415:

                        break;
                        /*
                        请求过频错误，
                        为了防止开发者错误的调用导致服务器压力过大， SDK 做了频控限制，
                        并有一分钟的惩罚时间，过了惩罚时间后接口可以再次正常调用。/
                         */
                    case 416:

                        break;
                        /*
                        一般由一端登录导致自动登录失败导致。
                        这种情况发生于非强制登录模式下已有一端在线而当前设备进行自动登录(设置为只允许一端同时登录时)，
                        出于安全方面的考虑，云信服务器判定当前端需要重新手动输入用户密码进行登录，故拒绝登录。/
                         */
                    case 417:
                        break;
                        //登录成功之前，调用本地数据库相关接口（手动登录的情况下数据库未打开）
                    case 1000:
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onException(Throwable exception) {

            }
        };
        NIMClient.getService(AuthService.class).login(info)
                .setCallback(callback);
    }

    /*
    注册多端登录的观察者/
     */
    private void initObserver(){
        Observer<List<OnlineClient>> clientsObserver = new Observer<List<OnlineClient>>() {
            @Override
            public void onEvent(List<OnlineClient> onlineClients) {
                if (onlineClients == null || onlineClients.size() == 0) {
                    return;
                }
                OnlineClient client = onlineClients.get(0);
                switch (client.getClientType()) {
                    case ClientType.Windows:
                        // PC端
                        break;
                    case ClientType.MAC:
                        // MAC端
                        break;
                    case ClientType.Web:
                        // Web端
                        break;
                    case ClientType.iOS:
                        // IOS端
                        break;
                    case ClientType.Android:
                        // Android端
                        break;
                    default:
                        break;
                }
            }
        };
        NIMClient.getService(AuthServiceObserver.class).observeOtherClients(clientsObserver, true);
    }
}
