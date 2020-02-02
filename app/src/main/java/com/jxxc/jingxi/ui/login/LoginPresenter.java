package com.jxxc.jingxi.ui.login;
import android.util.Log;

import com.google.gson.Gson;
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

    @Override
    protected void onEventComing(EventCenter center) {

    }
}
