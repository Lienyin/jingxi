package com.jxxc.jingxi.ui.orderdetails;

import com.jxxc.jingxi.entity.backparameter.OrderEntity;
import com.jxxc.jingxi.mvp.BaseView;
import com.jxxc.jingxi.mvp.BasePresenter;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class OrderDetailsContract {
    interface View extends BaseView {
        void getOrderCallBack(OrderEntity data);
    }

    interface  Presenter extends BasePresenter<View> {
        void getOrder(String orderId);
    }
}
