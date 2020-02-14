package com.jxxc.jingxi.ui.commissionlist;

import com.jxxc.jingxi.Api;
import com.jxxc.jingxi.entity.backparameter.CommissionListEntity;
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

public class CommissionListPresenter extends BasePresenterImpl<CommissionListContract.View> implements CommissionListContract.Presenter{

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    public void CommissionDetail(int pageNum, int pageSize) {
        OkGo.<HttpResult<List<CommissionListEntity>>>post(Api.COMMISSION_DETAIL)
                .params("pageNum",pageNum)
                .params("pageSize",pageSize)
                .execute(new JsonCallback<HttpResult<List<CommissionListEntity>>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<List<CommissionListEntity>>> response) {
                        List<CommissionListEntity> d = response.body().data;
                        if (response.body().code == 0){
                            mView.CommissionDetailCallBack(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }

    @Override
    public void CommissionDetailMore(int pageNum, int pageSize) {
        OkGo.<HttpResult<List<CommissionListEntity>>>post(Api.COMMISSION_DETAIL)
                .params("pageNum",pageNum)
                .params("pageSize",pageSize)
                .execute(new JsonCallback<HttpResult<List<CommissionListEntity>>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<List<CommissionListEntity>>> response) {
                        List<CommissionListEntity> d = response.body().data;
                        if (response.body().code == 0){
                            mView.CommissionDetailMoreCallBack(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }
}
