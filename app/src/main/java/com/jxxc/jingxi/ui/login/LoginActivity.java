package com.jxxc.jingxi.ui.login;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.entity.backparameter.ThirdPartyLogin;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.ui.bindingphonenumber.BindingPhoneNumberActivity;
import com.jxxc.jingxi.ui.main.MainActivity;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.utils.AppUtils;
import com.jxxc.jingxi.utils.GlideImgManager;
import com.jxxc.jingxi.utils.SPUtils;
import com.jxxc.jingxi.utils.StatusBarUtil;
import com.jxxc.jingxi.wxapi.Constant;
import com.jxxc.jingxi.wxapi.WeiXin;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendAuth;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 登录
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class LoginActivity extends MVPBaseActivity<LoginContract.View, LoginPresenter> implements LoginContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_affirm)
    TextView tv_affirm;
    @BindView(R.id.btn_qiye)
    Button btn_qiye;
    @BindView(R.id.btn_geren)
    Button btn_geren;
    @BindView(R.id.btn_geren_login)
    Button btn_geren_login;
    @BindView(R.id.btn_login_code)
    Button btn_login_code;
    @BindView(R.id.btn_send_msg_code)
    Button btn_send_msg_code;
    @BindView(R.id.btn_weixin_login)
    Button btn_weixin_login;
    @BindView(R.id.btn_yzm_login)
    Button btn_yzm_login;
    @BindView(R.id.tv_msg_login)
    TextView tv_msg_login;
    @BindView(R.id.tv_pw_login)
    TextView tv_pw_login;
    @BindView(R.id.gerenView)
    View gerenView;
    @BindView(R.id.qiyeView)
    View qiyeView;
    @BindView(R.id.msgView)
    View msgView;
    @BindView(R.id.loginSelectView)
    View loginSelectView;
    @BindView(R.id.et_user_phone)
    EditText et_user_phone;
    @BindView(R.id.et_pass_word)
    EditText et_pass_word;
    @BindView(R.id.et_phone_number_code)
    EditText et_phone_number_code;
    @BindView(R.id.et_pass_word_code)
    EditText et_pass_word_code;
    @BindView(R.id.iv_open_wx_login)
    ImageView iv_open_wx_login;
    @BindView(R.id.iv_user_head)
    ImageView iv_user_head;
    @BindView(R.id.iv_user_head_code)
    ImageView iv_user_head_code;
    @BindView(R.id.rb_xieyi)
    RadioButton rb_xieyi;
    @BindView(R.id.tv_user_name_code)
    TextView tv_user_name_code;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    //微信
    public IWXAPI api;
    private String wxOpenid = "";
    private String wxHeadimgurl = "";//头像
    private String fullName = "";//昵称
    private long exitTime = 0;

    @Override
    protected int layoutId() {
        return R.layout.activity_login;
}

    @Override
    public void initData() {
        StatusBarUtil.setStatusBarMode(this, false, R.color.white);
        tv_title.setText("登录");
        tv_affirm.setText("跳过");
        tv_affirm.setVisibility(View.VISIBLE);
        api = WXAPIFactory.createWXAPI(this, Constant.APP_ID,true);
        api.registerApp(Constant.APP_ID);
        EventBus.getDefault().register(this);
        if (!AppUtils.isEmpty(SPUtils.get(SPUtils.K_SESSION_MOBILE,""))){
            et_user_phone.setText(SPUtils.get(SPUtils.K_SESSION_MOBILE,""));
        }
        if (!AppUtils.isEmpty(SPUtils.get(SPUtils.K_WX_HEAD,""))){
            GlideImgManager.loadCircleImage(this, SPUtils.get(SPUtils.K_WX_HEAD,""), iv_user_head);
            GlideImgManager.loadCircleImage(this, SPUtils.get(SPUtils.K_WX_HEAD,""), iv_user_head_code);
        }
        if (!AppUtils.isEmpty(SPUtils.get(SPUtils.K_WX_NAME,""))){
            tv_user_name_code.setText(SPUtils.get(SPUtils.K_WX_NAME,"")+"\n"+"欢迎回来！");
            tv_user_name.setText(SPUtils.get(SPUtils.K_WX_NAME,"")+"\n"+"欢迎回来！");
        }
    }

    @OnClick({R.id.tv_back,R.id.tv_affirm,R.id.btn_qiye,R.id.btn_geren,R.id.tv_msg_login,R.id.tv_pw_login,R.id.btn_geren_login,
    R.id.btn_login_code,R.id.btn_send_msg_code,R.id.iv_open_wx_login,R.id.btn_weixin_login,
    R.id.btn_yzm_login})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.btn_weixin_login://微信登录
                if (isAvilible(this,"com.tencent.mm")){
                    weiXinLogin();
                }else{
                    toast(this,"目前您安装的微信版本过低或尚未安装");
                }
                break;
            case R.id.btn_yzm_login://短信验证码登录
                loginSelectView.setVisibility(View.GONE);
                msgView.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_qiye://企业用户
                gerenView.setVisibility(View.GONE);
                qiyeView.setVisibility(View.VISIBLE);
                msgView.setVisibility(View.GONE);
                break;
            case R.id.btn_geren://个人用户
                gerenView.setVisibility(View.VISIBLE);
                qiyeView.setVisibility(View.GONE);
                msgView.setVisibility(View.GONE);
                break;
            case R.id.tv_msg_login://短信验证码
                gerenView.setVisibility(View.GONE);
                qiyeView.setVisibility(View.GONE);
                break;
            case R.id.tv_pw_login://账户密码登录
                gerenView.setVisibility(View.VISIBLE);
                qiyeView.setVisibility(View.GONE);
                msgView.setVisibility(View.GONE);
                break;
            case R.id.btn_geren_login://个人界面登录按钮
                if (AppUtils.isEmpty(et_user_phone.getText().toString())){
                    toast(this,"请输入手机号码");
                }else if (AppUtils.isEmpty(et_pass_word.getText().toString())){
                    toast(this,"请输入登录密码");
                }else{
                    mPresenter.login(et_user_phone.getText().toString(),et_pass_word.getText().toString());
                }
                break;
            case R.id.btn_login_code://短信验证码登录
                if (!rb_xieyi.isChecked()){toast(this,"请同意用户协议");return;}
                if (AppUtils.isEmpty(et_phone_number_code.getText().toString())){
                    toast(this,"请输入手机号码");
                }else if (AppUtils.isEmpty(et_pass_word_code.getText().toString())){
                    toast(this,"请输入短信验证码");
                }else{
                    StyledDialog.buildLoading("正在登录").setActivity(this).show();
                    mPresenter.loginCode(et_phone_number_code.getText().toString(),et_pass_word_code.getText().toString());
                }
                break;
            case R.id.btn_send_msg_code://发送验证码
                if (AppUtils.isEmpty(et_phone_number_code.getText().toString())){
                    toast(this,"请输入手机号码");
                }else{
                    StyledDialog.buildLoading("正在发送").setActivity(this).show();
                    mPresenter.getCode(et_phone_number_code.getText().toString(),btn_send_msg_code);
                }
                break;
            case R.id.iv_open_wx_login://微信登录
                if (isAvilible(this,"com.tencent.mm")){
                    weiXinLogin();
                }else{
                    toast(this,"目前您安装的微信版本过低或尚未安装");
                }
                break;
            case R.id.tv_back://返回
                if (msgView.getVisibility()==View.VISIBLE){
                    msgView.setVisibility(View.GONE);
                    loginSelectView.setVisibility(View.VISIBLE);
                }else{
                    finish();
                }
                break;
            case R.id.tv_affirm://跳过
                finish();
                break;
            default:
        }
    }

    @Override
    public void loginCallBack() {
        ZzRouter.gotoActivity(this, MainActivity.class);
    }

    @Override
    public void loginCodeCallBack() {
        ZzRouter.gotoActivity(this, MainActivity.class);
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            exit();
//            return false;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//    private void exit() {
//        if ((System.currentTimeMillis() - exitTime) > 2000) {
//            toast(this, "再按一次退出程序");
//            exitTime = System.currentTimeMillis();
//        } else {
//            Intent intent = new Intent(Intent.ACTION_MAIN);
//            intent.addCategory(Intent.CATEGORY_HOME);
//            startActivity(intent);
//            System.exit(0);
//        }
//    }

    /**
     * 检查手机上是否安装了指定的软件
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAvilible(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        List<String> packageNames = new ArrayList<String>();

        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        // 判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    //----------------------微信登录开始（需要重置APP_secret）--------------------------------------
    public void weiXinLogin() {
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = String.valueOf(System.currentTimeMillis());
        api.sendReq(req);
    }
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEventMainThread(WeiXin wx) {
        getAccessToken(wx.getCode());
    }
    //获取Token
    public void getAccessToken(String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=" + Constant.APP_ID + "&secret=" + Constant.WECHAT_SECRET +
                "&code=" + code + "&grant_type=authorization_code";
        OkGo.<String>post(url)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject dataJson = new JSONObject(response.body());
                            String access_token = dataJson.getString("access_token");
                            String openid = dataJson.getString("openid");
                            getWeiXinUserInfo(access_token, openid);
                        } catch (JSONException e) {
                            System.out.println("Something wrong...");
                            e.printStackTrace();
                        }
                    }
                });
    }
    //获取用户信息
    public void getWeiXinUserInfo(String access_token, String Openid) {
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + Openid;
        OkGo.<String>post(url)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        try {
                            JSONObject dataJson = new JSONObject(response.body());
                            wxOpenid = dataJson.getString("openid");
                            wxHeadimgurl = dataJson.getString("headimgurl");
                            fullName = dataJson.getString("nickname");
                            SPUtils.put(SPUtils.K_WX_HEAD,wxHeadimgurl);//保存微信
                            SPUtils.put(SPUtils.K_WX_NAME,fullName);//保存微信
                            mPresenter.thirdPartyLogin(wxOpenid);
                        } catch (JSONException e) {
                            System.out.println("Something wrong...");
                            e.printStackTrace();
                        }
                    }
                });
    }
    //---------------------------------微信登录结束----------------------------------------------

    //第三方登录返回数据
    @Override
    public void getThirdPartyLogin(ThirdPartyLogin data) {
        if ("ok".equals(data.step)) {
            //第一种状态：授权登录成功
            toast(this,"登录成功");
            ZzRouter.gotoActivity(this, MainActivity.class);
        }else if ("not_auth".equals(data.step)){
            //第一次授权登录，跳转到手机获取验证码界面(新用户)
            Intent intent = new Intent(this, BindingPhoneNumberActivity.class);
            intent.putExtra("otherAppId", wxOpenid);
            intent.putExtra("userHeadImage", wxHeadimgurl);
            intent.putExtra("fullName", fullName);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
