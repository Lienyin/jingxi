package com.jxxc.jingxi.ui.orderdetailsdaifuwu;

import android.content.Context;

import com.jxxc.jingxi.entity.backparameter.OrderEntity;
import com.jxxc.jingxi.mvp.BasePresenter;
import com.jxxc.jingxi.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class OrderDetailsDaiFuWuContract {
    interface View extends BaseView {
        void getOrderCallBack(OrderEntity data);
        void cancelOrderCallBack();
        void hastenCallBack();
    }

    interface  Presenter extends BasePresenter<View> {
        void getOrder(String orderId);
        void cancelOrder(String orderId);
        void hasten(String orderId);
    }
}
