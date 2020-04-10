package com.jxxc.jingxi.ui.bindingphonenumber;

import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.Api;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.back_Login;
import com.jxxc.jingxi.http.EventCenter;
import com.jxxc.jingxi.http.HttpResult;
import com.jxxc.jingxi.http.JsonCallback;
import com.jxxc.jingxi.mvp.BasePresenterImpl;
import com.jxxc.jingxi.utils.AppUtils;
import com.jxxc.jingxi.utils.MD5Utils;
import com.jxxc.jingxi.utils.SPUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class BindingPhoneNumberPresenter extends BasePresenterImpl<BindingPhoneNumberContract.View> implements BindingPhoneNumberContract.Presenter{

    private CountDownTimer timer;
    @Override
    protected void onEventComing(EventCenter center) {

    }

    /**
     * 绑定微信
     * @param phonenumber
     * @param userName
     * @param wxOpenId
     */
    @Override
    public void getThirdPartyInfo(String phonenumber,
                                  String userName,
                                  String avatar,
                                  String wxOpenId,
                                  String appleId,
                                  String code) {
        OkGo.<HttpResult<back_Login>>post(Api.AUTH_WECHAT)
                .params("phonenumber",phonenumber)
                .params("userName", userName)
                .params("avatar", avatar)
                .params("wxOpenId",wxOpenId)
                .params("appleId",appleId)
                .params("code",code)
                .execute(new JsonCallback<HttpResult<back_Login>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<back_Login>> response) {
                        StyledDialog.dismissLoading();
                        back_Login d = response.body().data;
                        if (response.body().code == 0) {
                            SPUtils.put(mView.getContext(), SPUtils.K_TOKEN,d.token);
                            toast(mContext,"绑定成功");
                            mView.gotoUserMain();
                        }else {
                            toast(mContext,response.body().message);
                        }
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
                    tvAuthCode.setTextColor(ContextCompat.getColor(mView.getContext(), R.color.public_all));
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
                    tvAuthCode.setTextColor(ContextCompat.getColor(mView.getContext(), R.color.black));
                    tvAuthCode.setText("获取验证码");
                }
            }
        };
    }
}
