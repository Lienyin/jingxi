package com.jxxc.jingxi.ui.shopdetails;

import android.content.Context;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.Api;
import com.jxxc.jingxi.entity.backparameter.CompanyDetailsEntity;
import com.jxxc.jingxi.http.EventCenter;
import com.jxxc.jingxi.http.HttpResult;
import com.jxxc.jingxi.http.JsonCallback;
import com.jxxc.jingxi.mvp.BasePresenterImpl;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

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
}
