package jack.project.com.imdatautil.util;

import com.hyphenate.chat.EMMessage;

import jack.project.com.imdatautil.model.MMessage;

/**
 * Create by www.lijin@foxmail.com on 2018/1/18 0018.
 * <br/>
 */

public class IMConfig {

    /**
     * 外部数据来源分类
     */
    public enum ChatEPackageType{
        IM_HuanXin,
        IM_RongYun
    }

    public static MMessage.Type getType(EMMessage.Type type){
        switch (type){
            case TXT:
                return MMessage.Type.TXT;
            case VOICE:
                return MMessage.Type.VOICE;
            case VIDEO:
                return MMessage.Type.VIDEO;
            case LOCATION:
                return MMessage.Type.LOCATION;
            case IMAGE:
                return MMessage.Type.IMAGE;
            case CMD:
                return MMessage.Type.CMD;
            case FILE:
                return MMessage.Type.FILE;
                default:
                    return MMessage.Type.TXT;
        }
    }
}
