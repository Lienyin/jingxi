package com.jxxc.jingxi.ui.main.myCarfragment;


import com.jxxc.jingxi.entity.backparameter.ActivitiesEntity;
import com.jxxc.jingxi.entity.backparameter.AppointmentListEntity;
import com.jxxc.jingxi.entity.backparameter.CarListEntity;
import com.jxxc.jingxi.entity.backparameter.CreateOrderEntity;
import com.jxxc.jingxi.entity.backparameter.MyCoupon;
import com.jxxc.jingxi.entity.backparameter.ProductInfoEntity;
import com.jxxc.jingxi.entity.backparameter.companyListEntity;
import com.jxxc.jingxi.mvp.BasePresenter;
import com.jxxc.jingxi.mvp.BaseView;

import java.util.List;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MyCarFragmentContract {
    interface View extends BaseView {
        void getCarListCallBack(List<CarListEntity> data);
        void queryMyCouponCallback(List<MyCoupon> data);
        void comboInfoCallBack(ProductInfoEntity data);
        void createOrderCallBack(CreateOrderEntity data);
        void appointmentListCallBack(List<AppointmentListEntity> data);

        void companyListCallBack(List<companyListEntity> data);
        void companyListCallBackMore(List<companyListEntity> data);
        void getActivitiesCallBack(List<ActivitiesEntity> data);
        void BalancePayCallBack();
    }

    interface  Presenter extends BasePresenter<View> {
        void getCarList();
        void queryMyCoupon(int status);
        void comboInfo();
        void createOrder(int serviceType,
                         String counponId,
                         String carNum,
                         String carNums,
                         String phonenumber,
                         String address,
                         String lng,
                         String lat,
                         String appointmentStartTime,
                         String appointmentEndTime,
                         String remark,
                         String companyId,
                         String comboProductIds,
                         String comboTypeId,
                         String productIds,
                         String cars);
        void appointmentList(String companyId,String queryDate);

        void companyList(double lng,double lat,String queryFlag,String sort,String areaId,int pageNum,int pageSize);
        void companyListMore(double lng,double lat,String queryFlag,String sort,String areaId,int pageNum,int pageSize);
        void getActivities();
        void BalancePay(String orderId);
    }
}
