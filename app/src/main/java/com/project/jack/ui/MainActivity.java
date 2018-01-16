package com.project.jack.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.project.jack.R;
import com.project.jack.base.BaseActivity;
import com.project.jack.chat.model.single.ChatMessageBean;
import com.project.jack.chat.single.ChatMessageActivity;
import com.project.jack.chat.util.Constant;
import com.project.jack.ui.alymessage.ui.AliYLoginActivity;
import com.project.jack.ui.rongcloud.single.RYChatMessageActivity;


public class MainActivity extends BaseActivity {

    private Button btn_main_editText;
    private Button btn_main_listView;
    private Button aly_im_btnclick;
    private Button btn_main_btn_im;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListentener();
        initDatas();
    }

    /**
     * 初始化控件
     */
    private void initView()
    {
        btn_main_editText= findViewById(R.id.btn_main_editText);
        btn_main_listView= findViewById(R.id.btn_main_listView);
        aly_im_btnclick = findViewById(R.id.main_aly_im);
        btn_main_btn_im = findViewById(R.id.main_im_ui);
    }

    /**
     * 初始化监听器
     */
    public void initListentener(){
        btn_main_editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentE= new Intent(MainActivity.this, EditTextActivity.class);
                startActivity(intentE);
            }
        });

//        btn_main_listView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intentL= new Intent(MainActivity.this, ListViewBarEditActivity.class);
//                startActivity(intentL);
//            }
//        });
        btn_main_listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentL= new Intent(MainActivity.this, RYChatMessageActivity.class);
                startActivity(intentL);
            }
        });
        aly_im_btnclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentE= new Intent(MainActivity.this, AliYLoginActivity.class);
                startActivity(intentE);
            }
        });
        btn_main_btn_im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChatMessageBean chatMessageBean = new ChatMessageBean();
                chatMessageBean.setChatUUID("123456");
                chatMessageBean.setChatUserID("78910");
                chatMessageBean.setChatUserName("花儿那么红");
                chatMessageBean.setChatUserNote("阿花");
                chatMessageBean.setChatUserAvatar("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1516103998446&di=27f76e0ea7711b38cc75db68e74edd53&imgtype=0&src=http%3A%2F%2Fa.hiphotos.baidu.com%2Fzhidao%2Fwh%253D600%252C800%2Fsign%3Da3fc2c12c9ea15ce41bbe80f863016cb%2Fa08b87d6277f9e2fa7cf5f9f1f30e924b999f3ab.jpg");
                chatMessageBean.setChatOtherUUID("4165148");
                chatMessageBean.setChatOtherUID("12546");
                chatMessageBean.setChatOtherUserName("独孤阿飞");
                chatMessageBean.setChatOtherUserNote("阿飞");
                chatMessageBean.setChatOtherUserAvatar("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1516104069170&di=ec10d9a2ed764110d62c453687518df0&imgtype=0&src=http%3A%2F%2Fh.hiphotos.baidu.com%2Fzhidao%2Fwh%253D450%252C600%2Fsign%3D99d79f68b11c8701d6e3bae2124fb219%2Fc2cec3fdfc039245612cc0838594a4c27c1e25a4.jpg");
                Intent intentE= new Intent(MainActivity.this, ChatMessageActivity.class);
                Bundle b = new Bundle();
                b.putSerializable(Constant.CHAT_INTENT_BEAN,chatMessageBean);
                intentE.putExtra(Constant.CHAT_INTENT_BUNDLE,b);
                startActivity(intentE);
            }
        });
    }

    /**
     * 初始化布局数据
     */
    private void initDatas(){
    }



}
