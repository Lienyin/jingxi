package com.jxxc.jingxi.ui.myorder;

import com.jxxc.jingxi.entity.backparameter.MyOrderEntity;
import com.jxxc.jingxi.mvp.BaseView;
import com.jxxc.jingxi.mvp.BasePresenter;

import java.util.List;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MyOrderContract {
    interface View extends BaseView {
        void myOrderCallBack(List<MyOrderEntity> data);
        void myOrderMoreCallBack(List<MyOrderEntity> data);
        void cancelOrderCallBack();
    }

    interface  Presenter extends BasePresenter<View> {
        void myOrder(String status,int pageNum,int pageSize);
        void myOrderMore(String status,int pageNum,int pageSize);
        void cancelOrder(String orderId);
    }
}
