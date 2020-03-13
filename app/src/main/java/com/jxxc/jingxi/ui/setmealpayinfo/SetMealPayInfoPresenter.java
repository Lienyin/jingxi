package com.jxxc.jingxi.ui.setmealpayinfo;

import android.content.Context;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.Api;
import com.jxxc.jingxi.entity.backparameter.AppointmentListEntity;
import com.jxxc.jingxi.http.EventCenter;
import com.jxxc.jingxi.http.HttpResult;
import com.jxxc.jingxi.http.JsonCallback;
import com.jxxc.jingxi.mvp.BasePresenterImpl;
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
}
