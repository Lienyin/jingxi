package com.jxxc.jingxi.ui.shoplist;

import android.content.Context;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.Api;
import com.jxxc.jingxi.entity.backparameter.AreaListEntity;
import com.jxxc.jingxi.entity.backparameter.companyListEntity;
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

public class ShopListPresenter extends BasePresenterImpl<ShopListContract.View> implements ShopListContract.Presenter{

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    public void companyList(double lng, double lat, String queryFlag, String sort, String areaId, int pageNum, int pageSize) {
        OkGo.<HttpResult<List<companyListEntity>>>post(Api.COMPANY_LIST)
                .params("lng",lng)
                .params("lat",lat)
                .params("queryFlag",queryFlag)
                .params("sort",sort)
                .params("areaId",areaId)
                .params("pageNum",pageNum)
                .params("pageSize",pageSize)
                .execute(new JsonCallback<HttpResult<List<companyListEntity>>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<List<companyListEntity>>> response) {
                        StyledDialog.dismissLoading();
                        List<companyListEntity> d = response.body().data;
                        if (response.body().code==0){
                            mView.companyListCallBack(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }

    @Override
    public void companyListMore(double lng, double lat, String queryFlag, String sort, String areaId, int pageNum, int pageSize) {
        OkGo.<HttpResult<List<companyListEntity>>>post(Api.COMPANY_LIST)
                .params("lng",lng)
                .params("lat",lat)
                .params("queryFlag",queryFlag)
                .params("sort",sort)
                .params("areaId",areaId)
                .params("pageNum",pageNum)
                .params("pageSize",pageSize)
                .execute(new JsonCallback<HttpResult<List<companyListEntity>>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<List<companyListEntity>>> response) {
                        List<companyListEntity> d = response.body().data;
                        if (response.body().code==0){
                            mView.companyListCallBackMore(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }

    @Override
    public void areaList() {
        OkGo.<HttpResult<List<AreaListEntity>>>post(Api.AREA_LIST)
                .tag(this)
                .execute(new JsonCallback<HttpResult<List<AreaListEntity>>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<List<AreaListEntity>>> response) {
                        List<AreaListEntity> d = response.body().data;
                        if (response.body().code==0){
                            mView.areaListCallBack(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }
}
