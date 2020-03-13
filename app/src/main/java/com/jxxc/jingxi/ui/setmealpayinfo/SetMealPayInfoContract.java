package com.jxxc.jingxi.ui.setmealpayinfo;

import android.content.Context;

import com.jxxc.jingxi.entity.backparameter.AppointmentListEntity;
import com.jxxc.jingxi.mvp.BasePresenter;
import com.jxxc.jingxi.mvp.BaseView;

import java.util.List;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class SetMealPayInfoContract {
    interface View extends BaseView {
        void appointmentListCallBack(List<AppointmentListEntity> data);
    }

    interface  Presenter extends BasePresenter<View> {
        void appointmentList(String companyId,String queryDate);
    }
}
