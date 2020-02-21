package com.jxxc.jingxi.ui.submitorder;

import android.content.Context;

import com.jxxc.jingxi.entity.backparameter.CarListEntity;
import com.jxxc.jingxi.entity.backparameter.MyCoupon;
import com.jxxc.jingxi.mvp.BasePresenter;
import com.jxxc.jingxi.mvp.BaseView;

import java.util.List;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class SubmitOrderContract {
    interface View extends BaseView {
        void getCarListCallBack(List<CarListEntity> data);
        void queryMyCouponCallback(List<MyCoupon> data);
    }

    interface  Presenter extends BasePresenter<View> {
        void getCarList();
        void queryMyCoupon(int status);
    }
}
