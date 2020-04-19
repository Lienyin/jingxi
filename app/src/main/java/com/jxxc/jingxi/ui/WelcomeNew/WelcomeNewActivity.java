package com.jxxc.jingxi.ui.WelcomeNew;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.jxxc.jingxi.R;
import com.jxxc.jingxi.ui.main.MainActivity;
import com.jxxc.jingxi.ui.start.StartActivity;

public class WelcomeNewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /*首先启动该Activity，并判断是否是第一次启动,注意，需要添加默认值,
         * 如果是第一次启动，则先进入功能引导页*/
        boolean isFirstOpen = SharedPreferencesUtil.getBoolean(this, SharedPreferencesUtil.FIRST_OPEN, true);
        if (isFirstOpen) {
            Intent intent = new Intent(this, WelcomeGuideActivity.class);
            startActivity(intent);
            /*注意，需要使用finish将该activity进行销毁，否则，在按下手机返回键时，会返回至启动页*/
            finish();
            return;
        }
        /*如果不是第一次启动app，则启动页*/
        setContentView(R.layout.activity_welcome_new);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /*2秒后进入主页*/
                enterHomeActivity();
            }
        }, 2000);
    }

    private void enterHomeActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

