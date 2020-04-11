package com.jxxc.jingxi.ui.addcar;

import android.content.Context;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.Api;
import com.jxxc.jingxi.entity.backparameter.ColorEntity;
import com.jxxc.jingxi.entity.backparameter.LatestVersionEntity;
import com.jxxc.jingxi.http.EventCenter;
import com.jxxc.jingxi.http.HttpResult;
import com.jxxc.jingxi.http.JsonCallback;
import com.jxxc.jingxi.mvp.BasePresenterImpl;
import com.jxxc.jingxi.utils.SPUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class AddCarPresenter extends BasePresenterImpl<AddCarContract.View> implements AddCarContract.Presenter{

    @Override
    protected void onEventComing(EventCenter center) {

    }

    /**
     * 查询app版本
     */
    @Override
    public void queryAppVersion(String type) {
        OkGo.<HttpResult<ColorEntity>>post(Api.LATEST_VERSION)
                .params("type",type)
                .execute(new JsonCallback<HttpResult<ColorEntity>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<ColorEntity>> response) {
                        StyledDialog.dismissLoading();
                        ColorEntity version = response.body().data;
                        if (response.body().code == 0){
                            mView.queryAppVersionCallBack(version);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }

    /**
     * 添加车型
     * @param carNum
     * @param brandId
     * @param typeId
     * @param color
     * @param isNewEnergy
     */
    @Override
    public void addCar(String carNum, String brandId, String typeId, String color, String isNewEnergy) {
        OkGo.<HttpResult>post(Api.ADD_CAR)
                .params("carNum",carNum)
                .params("brandId",brandId)
                .params("typeId",typeId)
                .params("color",color)
                .params("isNewEnergy",isNewEnergy)
                .execute(new JsonCallback<HttpResult>() {
                    @Override
                    public void onSuccess(Response<HttpResult> response) {
                        if (response.body().code==0){
                            mView.addCarCallBack();
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }

    @Override
    public void editCar(String carNum, String brandId, String typeId, String color, String isNewEnergy,
                        String isDefault,String newCarNum) {
        OkGo.<HttpResult>post(Api.EDIT_CAR)
                .params("carNum",carNum)
                .params("brandId",brandId)
                .params("typeId",typeId)
                .params("color",color)
                .params("isNewEnergy",isNewEnergy)
                .params("isDefault",isDefault)
                .params("newCarNum",newCarNum)
                .execute(new JsonCallback<HttpResult>() {
                    @Override
                    public void onSuccess(Response<HttpResult> response) {
                        if (response.body().code==0){
                            mView.addCarCallBack();
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }
}
