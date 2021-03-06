package com.jxxc.jingxi.ui.welcome;


import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.jxxc.jingxi.R;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.ui.start.StartActivity;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.utils.AppUtils;
import com.jxxc.jingxi.utils.SPUtils;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 欢迎界面
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class WelcomeActivity extends MVPBaseActivity<WelcomeContract.View, WelcomePresenter> implements WelcomeContract.View {

    @BindView(R.id.btn_enter)
    Button button;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    @Override
    protected int layoutId() {
        //设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_welcome;
    }

    @Override
    public void initData() {
        //控制是否显示系统状态栏，默认显示(可不写)
        mPresenter.querySetting();
        boolean isfirstlogin = SPUtils.get(this, "ISFIRSTLOGIN", true);
        if (isfirstlogin) {
            SPUtils.put(this, "ISFIRSTLOGIN", false);
            mPresenter.isShowViewPager(viewPager, isfirstlogin);
        } else {
            Intent intent = new Intent(this, StartActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @OnClick(R.id.btn_enter)
    public void onViewClicked() {
        AnimUtils.clickAnimator(button);
        gotoMainNow();
    }

    @Override
    public void gotoMainNow() {
        SPUtils.put(this, "ISFIRSTLOGIN", false);
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        if (!AppUtils.isEmpty(viewPager)) {
            viewPager.clearOnPageChangeListeners();
            viewPager.clearDisappearingChildren();
            viewPager.removeAllViewsInLayout();
        }
        super.onDestroy();

    }
}
