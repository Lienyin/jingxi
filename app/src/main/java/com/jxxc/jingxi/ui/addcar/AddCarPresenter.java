package com.jxxc.jingxi.ui.addcar;

import android.content.Context;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.Api;
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
        OkGo.<HttpResult<LatestVersionEntity>>post(Api.LATEST_VERSION)
                .params("type",type)
                .execute(new JsonCallback<HttpResult<LatestVersionEntity>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<LatestVersionEntity>> response) {
                        StyledDialog.dismissLoading();
                        LatestVersionEntity version = response.body().data;
                        if (response.body().code == 0){
                            SPUtils.put(SPUtils.K_STATIC_URL,version.staticUrl);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }
}
