package jack.project.mgrimintegration_hx.callbcak;

import com.hyphenate.chat.EMClient;

/**
 * Create by www.lijin@foxmail.com on 2018/1/31 0031.
 * <br/>
 */

public abstract class EMCallBack implements com.hyphenate.EMCallBack {

    public abstract void onSuccessSend();


    public abstract void onErrorSend(int a,String b);

    public abstract void onProgressSend(int a,String b);





    @Override
    public void onSuccess() {
        onSuccessSend();
    }

    @Override
    public void onError(int code, String error) {
        onErrorSend(code,error);
    }

    @Override
    public void onProgress(int progress, String status) {
        onProgressSend(progress,status);
    }
}
