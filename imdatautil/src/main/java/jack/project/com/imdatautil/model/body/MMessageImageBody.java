package jack.project.com.imdatautil.model.body;

import jack.project.com.imdatautil.model.MMessageBody;

/**
 * Create by www.lijin@foxmail.com on 2018/3/1 0001.
 * <br/>
 */

public class MMessageImageBody extends MMessageBody {

    private String RemoteUrl;//网络路径
    private String LocalUrl;//本地路径

    public String getLocalUrl() {
        return LocalUrl;
    }

    public void setLocalUrl(String localUrl) {
        LocalUrl = localUrl;
    }

    public String getRemoteUrl() {
        return RemoteUrl;
    }

    public void setRemoteUrl(String remoteUrl) {
        RemoteUrl = remoteUrl;
    }
}
