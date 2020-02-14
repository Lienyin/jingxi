package com.jxxc.jingxi.ui.discountcoupon.coupontype;

import android.graphics.Color;
import android.view.View;

import com.jxxc.jingxi.Api;
import com.jxxc.jingxi.entity.backparameter.MyCoupon;
import com.jxxc.jingxi.http.EventCenter;
import com.jxxc.jingxi.http.HttpResult;
import com.jxxc.jingxi.http.JsonCallback;
import com.jxxc.jingxi.mvp.BasePresenterImpl;
import com.jxxc.jingxi.utils.AppUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.List;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class CouponTypePresenter extends BasePresenterImpl<CouponTypeContract.View> implements CouponTypeContract.Presenter{
    @Override
    protected void onEventComing(EventCenter center) {

    }

    public void initFragmentBG(View view) {
        int color = Color.rgb((int) Math.floor(Math.random() * 128) + 64,
                (int) Math.floor(Math.random() * 128) + 64,
                (int) Math.floor(Math.random() * 128) + 64);
        view.setBackgroundColor(color);
    }
    @Override
    public void queryMyCoupon() {
        OkGo.<HttpResult<List<MyCoupon>>>post(Api.COUPONS)
                .tag(this)
                .execute(new JsonCallback<HttpResult<List<MyCoupon>>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<List<MyCoupon>>> response) {
                        HttpResult<List<MyCoupon>> body = response.body();
                        if (!AppUtils.isEmpty(body)) {
                            List<MyCoupon> data = body.data;
                            if (!AppUtils.isEmpty(mView)) {
                                mView.queryMyCouponCallback(data);
                            }
                        }
                    }
                });
    }

//    @Override
//    public void queryMyCouponMore(int status,int offset,int limit) {
//        OkGo.<HttpResult<List<MyCoupon>>>post(Api.COUPONS)
//                .params("status",status)
//                .params("offset",offset)
//                .params("limit",limit)
//                .execute(new JsonCallback<HttpResult<List<MyCoupon>>>() {
//                    @Override
//                    public void onSuccess(Response<HttpResult<List<MyCoupon>>> response) {
//                        HttpResult<List<MyCoupon>> body = response.body();
//                        if (!AppUtils.isEmpty(body)) {
//                            List<MyCoupon> data = body.data;
//                            if (!AppUtils.isEmpty(mView)) {
//                                mView.queryMyCouponMoreCallback(data);
//                            }
//                        }
//                    }
//                });
//    }
}
