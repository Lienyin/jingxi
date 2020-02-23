package com.jxxc.jingxi.ui.recharge;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.Api;
import com.jxxc.jingxi.entity.backparameter.AliPayInfo;
import com.jxxc.jingxi.entity.backparameter.PayByWeChat;
import com.jxxc.jingxi.entity.backparameter.RechargeSet;
import com.jxxc.jingxi.http.EventCenter;
import com.jxxc.jingxi.http.HttpResult;
import com.jxxc.jingxi.http.JsonCallback;
import com.jxxc.jingxi.mvp.BasePresenterImpl;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.List;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class RechargePresenter extends BasePresenterImpl<RechargeContract.View> implements RechargeContract.Presenter{

    @Override
    protected void onEventComing(EventCenter center) {

    }

    /**
     * 充值活动
     */
    @Override
    public void getRechargeConfiguration() {
        OkGo.<HttpResult<List<RechargeSet>>>post(Api.RECHARGE_CONFIGURATION)
                .tag(this)
                .execute(new JsonCallback<HttpResult<List<RechargeSet>>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<List<RechargeSet>>> response) {
                            List<RechargeSet> list = response.body().data;
                            if (response.body().code == 0){
                                mView.getRechargeConfigurationCallback(list);
                            }else{
                                toast(mContext,response.body().message);
                            }
                    }
                });
    }


    /**
     * 支付宝支付
     */
    @Override
    public void payByAliPay(double money,int payType) {
        OkGo.<HttpResult<AliPayInfo>>post(Api.RECHARGE)
                .params("money",money)
                .params("payType",payType)
                .execute(new JsonCallback<HttpResult<AliPayInfo>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<AliPayInfo>> response) {
                        StyledDialog.dismissLoading();
                        AliPayInfo a = response.body().data;
                        if (response.body().code == 0){
                            mView.payByAliPayCallBack(a);
                        }else{
                            toast(mContext,response.body().message);
                            //payFail(a.orderId,"1");
                        }
                    }
                });
    }

    /**
     * 微信支付
     */
    @Override
    public void payByWeChat(double money,int payType) {
        OkGo.<HttpResult<PayByWeChat>>post(Api.RECHARGE)
                .params("money",money)
                .params("payType",payType)
                .execute(new JsonCallback<HttpResult<PayByWeChat>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<PayByWeChat>> response) {
                        StyledDialog.dismissLoading();
                        PayByWeChat a = response.body().data;
                        if (response.body().code == 0){
                            mView.payByWeChatCallBack(a);
                        }else{
                            toast(mContext,response.body().message);
                            //payFail(a.orderId,"1");
                        }
                    }
                });
    }
}
