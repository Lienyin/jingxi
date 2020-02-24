package com.jxxc.jingxi.ui.orderdetails;

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

public class OrderDetailsPresenter extends BasePresenterImpl<OrderDetailsContract.View> implements OrderDetailsContract.Presenter{

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
     * 删除评论
     */
    @Override
    public void clearComment(String orderId) {
        OkGo.<HttpResult>post(Api.CLEAR_COMMENT)
                .params("orderId",orderId)
                .execute(new JsonCallback<HttpResult>() {
                    @Override
                    public void onSuccess(Response<HttpResult> response) {
                        StyledDialog.dismissLoading();
                        if (response.body().code==0){
                            mView.clearCommentCallBack();
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }
}
