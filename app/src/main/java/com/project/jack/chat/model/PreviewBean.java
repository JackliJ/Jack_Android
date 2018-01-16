package com.project.jack.chat.model;

import java.io.Serializable;

/**
 * Created by www.lijin@foxmail.com on 2017/10/11 0011.
 * <br/>
 * 查看大图的暂存Bean
 */
public class PreviewBean implements Serializable {

    private String path;//图片的本地路径
    private String filename;//图片路径

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
