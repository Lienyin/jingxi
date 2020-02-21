package com.jxxc.jingxi.ui.submitorder;

import android.content.Context;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.Api;
import com.jxxc.jingxi.entity.backparameter.CarListEntity;
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

public class SubmitOrderPresenter extends BasePresenterImpl<SubmitOrderContract.View> implements SubmitOrderContract.Presenter{

    @Override
    protected void onEventComing(EventCenter center) {

    }

    /**
     * 获取个人车辆列表
     */
    @Override
    public void getCarList() {
        OkGo.<HttpResult<List<CarListEntity>>>post(Api.GET_CAR_LIST)
                .tag(this)
                .execute(new JsonCallback<HttpResult<List<CarListEntity>>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<List<CarListEntity>>> response) {
                        StyledDialog.dismissLoading();
                        List<CarListEntity> d = response.body().data;
                        if (response.body().code==0){
                            mView.getCarListCallBack(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }

    /**
     * 查询优惠券
     * @param status
     */
    @Override
    public void queryMyCoupon(int status) {
        OkGo.<HttpResult<List<MyCoupon>>>post(Api.COUPONS)
                .params("status",status)
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
}
