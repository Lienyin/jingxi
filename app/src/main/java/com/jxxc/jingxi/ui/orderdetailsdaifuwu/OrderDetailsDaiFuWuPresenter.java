package com.jxxc.jingxi.ui.orderdetailsdaifuwu;

import android.content.Context;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.Api;
import com.jxxc.jingxi.entity.backparameter.OrderEntity;
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

public class OrderDetailsDaiFuWuPresenter extends BasePresenterImpl<OrderDetailsDaiFuWuContract.View> implements OrderDetailsDaiFuWuContract.Presenter{

    @Override
    protected void onEventComing(EventCenter center) {

    }

    /**
     * 获取订单详情
     * @param orderId
     */
    @Override
    public void getOrder(String orderId) {
        OkGo.<HttpResult<OrderEntity>>post(Api.GET_ORDER)
                .params("orderId",orderId)
                .execute(new JsonCallback<HttpResult<OrderEntity>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<OrderEntity>> response) {
                        StyledDialog.dismissLoading();
                        OrderEntity d = response.body().data;
                        if (response.body().code==0){
                            mView.getOrderCallBack(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }

    /**
     * 取消订单
     * @param orderId
     */
    @Override
    public void cancelOrder(String orderId) {
        OkGo.<HttpResult>post(Api.CANCEL_ORDER)
                .params("orderId",orderId)
                .execute(new JsonCallback<HttpResult>() {
                    @Override
                    public void onSuccess(Response<HttpResult> response) {
                        StyledDialog.dismissLoading();
                        if (response.body().code==0){
                            mView.cancelOrderCallBack();
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }

    /**
     * 催单
     * @param orderId
     */
    @Override
    public void hasten(String orderId) {
        OkGo.<HttpResult>post(Api.HASTEN_ORDER)
                .params("orderId",orderId)
                .execute(new JsonCallback<HttpResult>() {
                    @Override
                    public void onSuccess(Response<HttpResult> response) {
                        if (response.body().code==0){
                            mView.hastenCallBack();
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }

}
