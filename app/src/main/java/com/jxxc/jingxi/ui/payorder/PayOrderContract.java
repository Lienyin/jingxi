package com.jxxc.jingxi.ui.payorder;

import android.content.Context;

import com.jxxc.jingxi.mvp.BasePresenter;
import com.jxxc.jingxi.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class PayOrderContract {
    interface View extends BaseView {
        void BalancePayCallBack();
    }

    interface  Presenter extends BasePresenter<View> {
        void BalancePay(String orderId);
    }
}
