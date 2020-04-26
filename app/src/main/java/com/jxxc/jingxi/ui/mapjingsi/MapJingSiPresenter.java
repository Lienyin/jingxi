package com.jxxc.jingxi.ui.mapjingsi;

import android.content.Context;

import com.jxxc.jingxi.Api;
import com.jxxc.jingxi.entity.backparameter.NearbyConpanyEntity;
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

public class MapJingSiPresenter extends BasePresenterImpl<MapJingSiContract.View> implements MapJingSiContract.Presenter{

    @Override
    protected void onEventComing(EventCenter center) {

    }

    /**
     * 查询附近洗车点
     * @param distance
     * @param lng
     * @param lat
     */
    @Override
    public void nearbyConpany(String distance, double lng, double lat) {
        OkGo.<HttpResult<List<NearbyConpanyEntity>>>post(Api.NEARBY_CONPANY)
                .params("distance",distance)
                .params("lng",lng)
                .params("lat",lat)
                .execute(new JsonCallback<HttpResult<List<NearbyConpanyEntity>>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<List<NearbyConpanyEntity>>> response) {
                        List<NearbyConpanyEntity> d = response.body().data;
                        if (response.body().code==0){
                            mView.nearbyConpanyCallBack(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }
}
