package com.jxxc.jingxi.ui.shopdetails;

import android.content.Context;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.Api;
import com.jxxc.jingxi.entity.backparameter.AppointmentListEntity;
import com.jxxc.jingxi.entity.backparameter.CompanyDetailsEntity;
import com.jxxc.jingxi.entity.backparameter.RecommendComboInfoEntity;
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

public class ShopDetailsPresenter extends BasePresenterImpl<ShopDetailsContract.View> implements ShopDetailsContract.Presenter{

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    public void getCompany(String companyId) {
        OkGo.<HttpResult<CompanyDetailsEntity>>post(Api.GET_COMPANY)
                .params("companyId",companyId)
                .execute(new JsonCallback<HttpResult<CompanyDetailsEntity>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<CompanyDetailsEntity>> response) {
                        StyledDialog.dismissLoading();
                        CompanyDetailsEntity d = response.body().data;
                        if (response.body().code==0){
                            mView.getCompanyCallBack(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }

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
     * 获取洗车套餐
     */
    @Override
    public void recommendComboInfo(String serviceType,String companyId) {
        OkGo.<HttpResult<List<RecommendComboInfoEntity>>>post(Api.RECOMMEND_COMBO_INFO)
                .params("serviceType",serviceType)
                .params("companyId",companyId)
                .execute(new JsonCallback<HttpResult<List<RecommendComboInfoEntity>>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<List<RecommendComboInfoEntity>>> response) {
                        List<RecommendComboInfoEntity> d = response.body().data;
                        if (response.body().code==0){
                            mView.recommendComboInfoCallBack(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }
}
