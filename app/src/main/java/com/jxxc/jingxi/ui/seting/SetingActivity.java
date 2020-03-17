package com.jxxc.jingxi.ui.seting;


import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jxxc.jingxi.R;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.ui.login.LoginActivity;
import com.jxxc.jingxi.ui.regards.RegardsActivity;
import com.jxxc.jingxi.ui.updatepassword.UpdatePasswordActivity;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.utils.SPUtils;
import com.jxxc.jingxi.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class SetingActivity extends MVPBaseActivity<SetingContract.View, SetingPresenter> implements SetingContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.ll_setting_update_password)
    LinearLayout ll_setting_update_password;
    @BindView(R.id.ll_my_guanyu)
    LinearLayout ll_my_guanyu;
    @BindView(R.id.btn_out_login)
    Button btn_out_login;

    @Override
    protected int layoutId() {
        return R.layout.seting_activity;
    }

    @Override
    public void initData() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.public_all);//状态栏颜色
        tv_title.setText("安全设置");
    }

    @OnClick({R.id.tv_back,R.id.ll_setting_update_password,R.id.ll_my_guanyu,R.id.btn_out_login})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
            case R.id.ll_setting_update_password://修改密码
                ZzRouter.gotoActivity(this, UpdatePasswordActivity.class);
                break;
            case R.id.ll_my_guanyu://关于我们
                ZzRouter.gotoActivity(this, RegardsActivity.class);
                break;
            case R.id.btn_out_login://退出登录
                SPUtils.remove(this,SPUtils.K_TOKEN);
                ZzRouter.gotoActivity(this, LoginActivity.class);
                break;
            default:
        }
    }

}
