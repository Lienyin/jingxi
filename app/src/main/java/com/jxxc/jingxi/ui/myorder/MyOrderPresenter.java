package com.jxxc.jingxi.ui.myorder;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.Api;
import com.jxxc.jingxi.entity.backparameter.MyOrderEntity;
import com.jxxc.jingxi.entity.backparameter.OrderNumEntity;
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

public class MyOrderPresenter extends BasePresenterImpl<MyOrderContract.View> implements MyOrderContract.Presenter{

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    public void myOrder(String status, int pageNum, int pageSize) {
        OkGo.<HttpResult<List<MyOrderEntity>>>post(Api.MY_ORDER)
                .params("status",status)
                .params("pageNum",pageNum)
                .params("pageSize",pageSize)
                .execute(new JsonCallback<HttpResult<List<MyOrderEntity>>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<List<MyOrderEntity>>> response) {
                        List<MyOrderEntity> d = response.body().data;
                        if (response.body().code == 0){
                            mView.myOrderCallBack(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }

    @Override
    public void myOrderMore(String status, int pageNum, int pageSize) {
        OkGo.<HttpResult<List<MyOrderEntity>>>post(Api.MY_ORDER)
                .params("status",status)
                .params("pageNum",pageNum)
                .params("pageSize",pageSize)
                .execute(new JsonCallback<HttpResult<List<MyOrderEntity>>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<List<MyOrderEntity>>> response) {
                        List<MyOrderEntity> d = response.body().data;
                        if (response.body().code == 0){
                            mView.myOrderMoreCallBack(d);
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
     * 查询订单数量
     */
    @Override
    public void orderNum() {
        OkGo.<HttpResult<OrderNumEntity>>post(Api.ORDER_NUM)
                .tag(this)
                .execute(new JsonCallback<HttpResult<OrderNumEntity>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<OrderNumEntity>> response) {
                        OrderNumEntity d = response.body().data;
                        if (response.body().code==0){
                            mView.orderNumCallBack(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }
}
