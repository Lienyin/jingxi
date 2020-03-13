package com.jxxc.jingxi.ui.setmealpayinfo;

import android.content.Context;

import com.jxxc.jingxi.entity.backparameter.AppointmentListEntity;
import com.jxxc.jingxi.entity.backparameter.CarListEntity;
import com.jxxc.jingxi.entity.backparameter.CreateOrderEntity;
import com.jxxc.jingxi.entity.backparameter.MyCoupon;
import com.jxxc.jingxi.mvp.BasePresenter;
import com.jxxc.jingxi.mvp.BaseView;

import java.util.List;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class SetMealPayInfoContract {
    interface View extends BaseView {
        void getCarListCallBack(List<CarListEntity> data);
        void appointmentListCallBack(List<AppointmentListEntity> data);
        void queryMyCouponCallback(List<MyCoupon> data);
        void createOrderCallBack(CreateOrderEntity data);
    }

    interface  Presenter extends BasePresenter<View> {
        void getCarList();
        void appointmentList(String companyId,String queryDate);
        void queryMyCoupon(int status);
        void createOrder(String serviceType,String counponId,String comboId,
                         String carNum,String carNums,String phonenumber,String address,
                         String lng,String lat,String appointmentStartTime,String appointmentEndTime,
                         String remark,String companyId);
    }
}
