package jack.project.com.imdatautil.callback;

/**
 * Create by www.lijin@foxmail.com on 2018/1/31 0031.
 * <br/>
 */

public interface ImSendCallBack {

    //成功
    void onSuccess();

    /**
     * 失败
     * @param i   code
     * @param s   msg
     */
    void onError(int i, String s);

    /**
     *
     * @param i   code
     * @param s   msg
     */
    void onProgress(int i, String s);

}
