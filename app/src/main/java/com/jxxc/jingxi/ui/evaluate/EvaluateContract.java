package com.jxxc.jingxi.ui.evaluate;

import android.content.Context;

import com.jxxc.jingxi.entity.backparameter.OrderEntity;
import com.jxxc.jingxi.mvp.BasePresenter;
import com.jxxc.jingxi.mvp.BaseView;
import com.jxxc.jingxi.ui.orderdetails.OrderDetailsContract;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class EvaluateContract {
    interface View extends BaseView {
        void getOrderCallBack(OrderEntity data);
        void commentBackCall();
    }

    interface  Presenter extends BasePresenter<EvaluateContract.View> {
        void getOrder(String orderId);
        void comment(String orderId,int startLevel,String content);
    }
}
