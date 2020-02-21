package com.jxxc.jingxi.ui.submitorder;

import android.content.Context;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.Api;
import com.jxxc.jingxi.entity.backparameter.CarListEntity;
import com.jxxc.jingxi.entity.backparameter.CreateOrderEntity;
import com.jxxc.jingxi.entity.backparameter.MyCoupon;
import com.jxxc.jingxi.entity.backparameter.ProductInfoEntity;
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
    /**
     * 获取洗车组合套餐
     */
    @Override
    public void comboInfo() {
        OkGo.<HttpResult<ProductInfoEntity>>post(Api.COMBO_INFO)
                .tag(this)
                .execute(new JsonCallback<HttpResult<ProductInfoEntity>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<ProductInfoEntity>> response) {
                        ProductInfoEntity d = response.body().data;
                        if (response.body().code==0){
                            mView.comboInfoCallBack(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }

    /**
     * 下单
     * @param comboProductIds
     * @param serviceType
     * @param counponId
     * @param comboTypeId
     * @param carNum
     * @param carNums
     * @param phonenumber
     * @param address
     * @param lng
     * @param lat
     * @param appointmentStartTime
     * @param appointmentEndTime
     * @param remark
     */
    @Override
    public void createOrder(String comboProductIds, int serviceType, String counponId, String comboTypeId,
                            String carNum, String carNums, String phonenumber, String address, String lng,
                            String lat, String appointmentStartTime, String appointmentEndTime, String remark) {
        OkGo.<HttpResult<CreateOrderEntity>>post(Api.CREATE_ORDER)
                .params("comboProductIds",comboProductIds)
                .params("serviceType",serviceType)
                .params("counponId",counponId)
                .params("comboTypeId",comboTypeId)
                .params("carNum",carNum)
                .params("carNums",carNums)
                .params("phonenumber",phonenumber)
                .params("address",address)
                .params("lng",lng)
                .params("lat",lat)
                .params("appointmentStartTime",appointmentStartTime)
                .params("appointmentEndTime",appointmentEndTime)
                .params("remark",remark)
                .execute(new JsonCallback<HttpResult<CreateOrderEntity>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<CreateOrderEntity>> response) {
                        StyledDialog.dismissLoading();
                        CreateOrderEntity d = response.body().data;
                        if (response.body().code==0){
                            mView.createOrderCallBack(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }
}
