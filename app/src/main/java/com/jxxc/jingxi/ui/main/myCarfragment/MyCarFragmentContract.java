package com.jxxc.jingxi.ui.main.myCarfragment;


import com.jxxc.jingxi.entity.backparameter.CarListEntity;
import com.jxxc.jingxi.entity.backparameter.CreateOrderEntity;
import com.jxxc.jingxi.entity.backparameter.MyCoupon;
import com.jxxc.jingxi.entity.backparameter.ProductInfoEntity;
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
    }

    interface  Presenter extends BasePresenter<View> {
        void getCarList();
        void queryMyCoupon(int status);
        void comboInfo();
        void createOrder(String comboProductIds,int serviceType,String counponId,String comboTypeId,
                         String carNum,String carNums,String phonenumber,String address,
                         String lng,String lat,String appointmentStartTime,String appointmentEndTime,
                         String remark,String companyId);
    }
}
