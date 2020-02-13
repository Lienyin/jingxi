package com.jxxc.jingxi.ui.mycar;

import android.content.Context;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.Api;
import com.jxxc.jingxi.entity.backparameter.CarListEntity;
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

public class MyCarPresenter extends BasePresenterImpl<MyCarContract.View> implements MyCarContract.Presenter{

    @Override
    protected void onEventComing(EventCenter center) {

    }

    /**
     * 获取个人车辆列表
     */
    @Override
    public void getCarList() {
        OkGo.<HttpResult<List<CarListEntity>>>post(Api.GET_CAR_LIST)
                .tag(this)
                .execute(new JsonCallback<HttpResult<List<CarListEntity>>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<List<CarListEntity>>> response) {
                        List<CarListEntity> d = response.body().data;
                        if (response.body().code==0){
                            mView.getCarListCallBack(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }

    /**
     * 删除车辆
     */
    @Override
    public void removeCar(String carNum) {
        OkGo.<HttpResult>post(Api.REMOVE_CAR)
                .params("carNum",carNum)
                .execute(new JsonCallback<HttpResult>() {
                    @Override
                    public void onSuccess(Response<HttpResult> response) {
                        StyledDialog.dismissLoading();
                        if (response.body().code==0){
                            mView.removeCarCallBack();
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }
}
