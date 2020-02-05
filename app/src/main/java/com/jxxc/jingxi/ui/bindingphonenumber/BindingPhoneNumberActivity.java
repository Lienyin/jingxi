package com.jxxc.jingxi.ui.bindingphonenumber;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.ui.main.MainActivity;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.utils.AppUtils;
import com.jxxc.jingxi.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class BindingPhoneNumberActivity extends MVPBaseActivity<BindingPhoneNumberContract.View, BindingPhoneNumberPresenter> implements BindingPhoneNumberContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.btn_binding)
    Button btn_binding;
    @BindView(R.id.btn_send_msg_code)
    Button btn_send_msg_code;
    @BindView(R.id.et_account)
    EditText et_account;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_affirm_password)
    EditText et_affirm_password;
    private String otherAppId;
    private String photoPath;
    private String nickName;

    @Override
    protected int layoutId() {
        return R.layout.binding_phone_number_activity;
    }

    @Override
    public void initData() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);//状态栏颜色
        tv_title.setText("绑定手机号");
        otherAppId = getIntent().getStringExtra("otherAppId");
        photoPath = getIntent().getStringExtra("userHeadImage");
        nickName = getIntent().getStringExtra("fullName");
    }

    @OnClick({R.id.tv_back,R.id.btn_binding,R.id.btn_send_msg_code})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
            case R.id.btn_binding://绑定
                if (AppUtils.isEmpty(et_account.getText().toString())){
                    toast(this,"请输入您的手机号码");
                }else if (AppUtils.isEmpty(et_password.getText().toString())){
                    toast(this,"请输入您的账户密码");
                }else if (AppUtils.isEmpty(et_affirm_password.getText().toString())){
                    toast(this,"请确认短信验证码");
                }else{
                    StyledDialog.buildLoading("正在绑定").setActivity(this).show();
                    mPresenter.getThirdPartyInfo(et_account.getText().toString().trim(),et_password.getText().toString().trim(),otherAppId,et_affirm_password.getText().toString());
                }
                break;
            case R.id.btn_send_msg_code://发送验证码
                if (AppUtils.isEmpty(et_account.getText().toString())){
                    toast(this,"请输入您的手机号码");
                }else{
                    StyledDialog.buildLoading("正在登录").setActivity(this).show();
                    mPresenter.getCode(et_account.getText().toString(),btn_send_msg_code);
                }
                break;
            default:
        }
    }

    @Override
    public void gotoUserMain() {
        ZzRouter.gotoActivity(this, MainActivity.class);
        finish();
    }
}
