package com.jxxc.jingxi.ui.payorder;

import android.content.Context;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.Api;
import com.jxxc.jingxi.http.EventCenter;
import com.jxxc.jingxi.http.HttpResult;
import com.jxxc.jingxi.http.JsonCallback;
import com.jxxc.jingxi.mvp.BasePresenterImpl;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class PayOrderPresenter extends BasePresenterImpl<PayOrderContract.View> implements PayOrderContract.Presenter{

    @Override
    protected void onEventComing(EventCenter center) {

    }

    /**
     * 余额支付
     * @param orderId
     */
    @Override
    public void BalancePay(String orderId) {
        OkGo.<HttpResult>post(Api.BALANCE_PAY)
                .params("orderId",orderId)
                .execute(new JsonCallback<HttpResult>() {
                    @Override
                    public void onSuccess(Response<HttpResult> response) {
                        StyledDialog.dismissLoading();
                        if (response.body().code==0){
                            mView.BalancePayCallBack();
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }
}
