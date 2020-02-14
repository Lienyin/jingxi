package com.jxxc.jingxi.ui.discountcoupon.coupontype;


import com.jxxc.jingxi.entity.backparameter.MyCoupon;
import com.jxxc.jingxi.mvp.BasePresenter;
import com.jxxc.jingxi.mvp.BaseView;

import java.util.List;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class CouponTypeContract {
    interface View extends BaseView {
        void queryMyCouponCallback(List<MyCoupon> data);
        //void queryMyCouponMoreCallback(List<MyCoupon> data);
    }

    interface  Presenter extends BasePresenter<View> {
        void queryMyCoupon(int status);
        //void queryMyCouponMore(int status, int offset, int limit);
    }
}
