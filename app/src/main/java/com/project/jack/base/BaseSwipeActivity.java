package com.project.jack.base;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import com.project.jack.components.swupeback.app.SwipeBackActivity;

/**
 * Created by Administrator on 2017/12/7 0007.
 */

public class BaseSwipeActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 禁止修改APP字体大小
     * @return
     */
    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config,res.getDisplayMetrics() );
        return res;
    }
}
