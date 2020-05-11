package com.jxxc.jingxi.ui.login;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.ThirdPartyLogin;
import com.jxxc.jingxi.utils.SPUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.jxxc.jingxi.Api;
import com.jxxc.jingxi.entity.backparameter.back_Login;
import com.jxxc.jingxi.entity.requestparameter.req_Login;
import com.jxxc.jingxi.http.EventCenter;
import com.jxxc.jingxi.http.HttpResult;
import com.jxxc.jingxi.http.JsonCallback;
import com.jxxc.jingxi.utils.AppUtils;
import com.jxxc.jingxi.mvp.BasePresenterImpl;
import com.jxxc.jingxi.utils.MD5Utils;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class LoginPresenter extends BasePresenterImpl<LoginContract.View> implements LoginContract.Presenter{

    private CountDownTimer timer;
    /**
     * 账户密码登录
     * @param phonenumber
     * @param password
     */
    @Override
    public void login(final String phonenumber, String password) {
        OkGo.<HttpResult<back_Login>>post(Api.LOGIN)
                .params("phonenumber",phonenumber)
                .params("password", MD5Utils.shaPassword(password).trim().toUpperCase())
                .execute(new JsonCallback<HttpResult<back_Login>>(){
                    @Override
                    public void onSuccess(Response<HttpResult<back_Login>> response) {
                        hideLoading();
                        back_Login d = response.body().data;
                        if (response.body().code == 0){
                            mView.loginCallBack();
                            SPUtils.put(SPUtils.K_SESSION_MOBILE,phonenumber);
                            SPUtils.put(SPUtils.K_TOKEN,d.token);
                        }else {
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }

    /**
     * 验证码登录
     * @param phonenumber
     * @param code
     */
    @Override
    public void loginCode(final String phonenumber, String code) {
        OkGo.<HttpResult<back_Login>>post(Api.LOGIN_BY_CODE)
                .params("phonenumber",phonenumber)
                .params("code", code)
                .execute(new JsonCallback<HttpResult<back_Login>>(){
                    @Override
                    public void onSuccess(Response<HttpResult<back_Login>> response) {
                        hideLoading();
                        back_Login d = response.body().data;
                        if (response.body().code == 0){
                            mView.loginCallBack();
                            SPUtils.put(SPUtils.K_SESSION_MOBILE,phonenumber);
                            SPUtils.put(SPUtils.K_TOKEN,d.token);
                        }else {
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }

    /**
     * 微信登录
     * @param wxOpenId
     */
    @Override
    public void thirdPartyLogin(String wxOpenId,String userName,String avatar) {
        OkGo.<HttpResult<ThirdPartyLogin>>post(Api.LOGIN_BY_WECHAT)
                .params("wxOpenId",wxOpenId)
                .params("userName",userName)
                .params("avatar",avatar)
                .execute(new JsonCallback<HttpResult<ThirdPartyLogin>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<ThirdPartyLogin>> response) {
                        ThirdPartyLogin d = response.body().data;
                        mView.getThirdPartyLogin(d);
                        SPUtils.put(SPUtils.K_TOKEN,d.token);
                    }
                });
    }

    /**
     * 获取验证码
     * @param phonenumber
     */
    @Override
    public void getCode(String phonenumber, final TextView tvAuthCode) {
        OkGo.<HttpResult>post(Api.GET_CODE)
                .params("phonenumber",phonenumber)
                .execute(new JsonCallback<HttpResult>() {
                    @Override
                    public void onSuccess(Response<HttpResult> response) {
                        StyledDialog.dismissLoading();
                        HttpResult data = response.body();
                        if (data.code == 0) {
                            timer = initCountDownTimer(tvAuthCode);
                            timer.start();
                            toast(mContext,"验证码已发送");
                        } else {
                            toast(mContext,data.message);
                        }
                    }
                });
    }

    @NonNull
    private CountDownTimer initCountDownTimer(final TextView tvAuthCode) {
        return new CountDownTimer(60000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (AppUtils.isEmpty(mView)) {
                    return;
                }
                if (tvAuthCode != null) {
                    tvAuthCode.setEnabled(false);
                    tvAuthCode.setTextColor(ContextCompat.getColor(mView.getContext(), R.color.blackcf));
                    tvAuthCode.setText("重新发送(" + (millisUntilFinished / 1000) + ")");
                }
            }

            @Override
            public void onFinish() {
                if (AppUtils.isEmpty(mView)) {
                    return;
                }
                if (tvAuthCode != null) {
                    tvAuthCode.setEnabled(true);
                    tvAuthCode.setTextColor(ContextCompat.getColor(mView.getContext(), R.color.public_all));
                    tvAuthCode.setText("获取验证码");
                }
            }
        };
    }

    @Override
    protected void onEventComing(EventCenter center) {

    }
}
