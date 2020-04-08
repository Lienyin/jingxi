package com.jxxc.jingxi.ui.setmealpayinfo;

import android.content.Context;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.Api;
import com.jxxc.jingxi.entity.backparameter.ActivitiesEntity;
import com.jxxc.jingxi.entity.backparameter.AppointmentListEntity;
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

public class SetMealPayInfoPresenter extends BasePresenterImpl<SetMealPayInfoContract.View> implements SetMealPayInfoContract.Presenter{

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
     * 查询预约时间
     * @param companyId
     * @param queryDate
     */
    @Override
    public void appointmentList(String companyId, String queryDate) {
        OkGo.<HttpResult<List<AppointmentListEntity>>>post(Api.APPOINTMENT_LIST)
                .params("companyId",companyId)
                .params("queryDate",queryDate)
                .execute(new JsonCallback<HttpResult<List<AppointmentListEntity>>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<List<AppointmentListEntity>>> response) {
                        StyledDialog.dismissLoading();
                        List<AppointmentListEntity> d = response.body().data;
                        if (response.body().code==0){
                            mView.appointmentListCallBack(d);
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
     * 下单
     * @param serviceType
     * @param counponId
     * @param comboId
     * @param carNum
     * @param carNums
     * @param phonenumber
     * @param address
     * @param lng
     * @param lat
     * @param appointmentStartTime
     * @param appointmentEndTime
     * @param remark
     * @param companyId
     */
    @Override
    public void createOrder(String serviceType, String counponId, String comboId,
                            String carNum, String carNums, String phonenumber, String address, String lng,
                            String lat, String appointmentStartTime, String appointmentEndTime,
                            String remark,String companyId) {
        OkGo.<HttpResult<CreateOrderEntity>>post(Api.CREATE_ORDER)
                .params("serviceType",serviceType)
                .params("counponId",counponId)
                .params("comboId",comboId)
                .params("carNum",carNum)
                .params("carNums",carNums)
                .params("phonenumber",phonenumber)
                .params("address",address)
                .params("lng",lng)
                .params("lat",lat)
                .params("appointmentStartTime",appointmentStartTime)
                .params("appointmentEndTime",appointmentEndTime)
                .params("remark",remark)
                .params("companyId",companyId)
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

    /**
     * 获取活动
     */
    @Override
    public void getActivities() {
        OkGo.<HttpResult<List<ActivitiesEntity>>>post(Api.GET_ACTIVITIES)
                .tag(this)
                .execute(new JsonCallback<HttpResult<List<ActivitiesEntity>>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<List<ActivitiesEntity>>> response) {
                        List<ActivitiesEntity> d = response.body().data;
                        if (response.body().code==0){
                            mView.getActivitiesCallBack(d);
                        }else{
                            toast(mContext,response.body().message);
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
}
