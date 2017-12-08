package com.project.jack.ui.rongcloud;

import android.app.Activity;
import android.os.Bundle;

import com.project.jack.R;
import com.project.jack.components.swupeback.app.SwipeBackActivity;

/**
 * Created by Administrator on 2017/12/8 0008.
 */

public class TestActivity extends SwipeBackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
