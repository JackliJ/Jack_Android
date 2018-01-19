package jack.project.mgrimintegration_hx.callbcak;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

/**
 * Create by www.lijin@foxmail.com on 2018/1/19 0019.
 * <br/>
 */

public abstract class LoginCallBack implements EMCallBack {

    public abstract void onSuccessTest();


    public abstract void onFailure();



    @Override
    public void onSuccess() {
        //登录聊天服务器成功！
        EMClient.getInstance().groupManager().loadAllGroups();
        //load数据到内存
        EMClient.getInstance().chatManager().loadAllConversations();
        onSuccessTest();
    }

    @Override
    public void onError(int code, String error) {
        onFailure();
    }

    @Override
    public void onProgress(int progress, String status) {

    }
}
