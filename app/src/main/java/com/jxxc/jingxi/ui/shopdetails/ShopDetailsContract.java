package com.jxxc.jingxi.ui.shopdetails;

import android.content.Context;

import com.jxxc.jingxi.entity.backparameter.AppointmentListEntity;
import com.jxxc.jingxi.entity.backparameter.CompanyDetailsEntity;
import com.jxxc.jingxi.entity.backparameter.RecommendComboInfoEntity;
import com.jxxc.jingxi.mvp.BasePresenter;
import com.jxxc.jingxi.mvp.BaseView;

import java.util.List;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class ShopDetailsContract {
    interface View extends BaseView {
        void getCompanyCallBack(CompanyDetailsEntity data);
        void appointmentListCallBack(List<AppointmentListEntity> data);

        void recommendComboInfoCallBack(List<RecommendComboInfoEntity> data);
    }

    interface  Presenter extends BasePresenter<View> {
        void getCompany(String companyId);
        void appointmentList(String companyId,String queryDate);

        void recommendComboInfo(String serviceType,String companyId);
    }
}
