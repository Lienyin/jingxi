package com.jxxc.jingxi.ui.evaluate;

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

public class EvaluatePresenter extends BasePresenterImpl<EvaluateContract.View> implements EvaluateContract.Presenter{


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
     * 评价
     * @param orderId
     * @param startLevel
     * @param content
     */
    @Override
    public void comment(String orderId, int startLevel, String content) {
        OkGo.<HttpResult>post(Api.COMMENT)
                .params("orderId",orderId)
                .params("startLevel",startLevel)
                .params("content",content)
                .execute(new JsonCallback<HttpResult>() {
                    @Override
                    public void onSuccess(Response<HttpResult> response) {
                        StyledDialog.dismissLoading();
                        if (response.body().code==0){
                            mView.commentBackCall();
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }
}
