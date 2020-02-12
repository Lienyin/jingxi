package com.jxxc.jingxi.ui.cartypechoose;

import android.content.Context;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.Api;
import com.jxxc.jingxi.entity.backparameter.BandAndTypeEntity;
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

public class CarTypeChoosePresenter extends BasePresenterImpl<CarTypeChooseContract.View> implements CarTypeChooseContract.Presenter{

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    public void getBandAndType() {
        OkGo.<HttpResult<BandAndTypeEntity>>post(Api.GET_BAND_AND_TYPE)
                .tag(this)
                .execute(new JsonCallback<HttpResult<BandAndTypeEntity>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<BandAndTypeEntity>> response) {
                        StyledDialog.dismissLoading();
                        BandAndTypeEntity d = response.body().data;
                        if (response.body().code == 0){
                            mView.getBandAndTypeCallBack(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }
}
