package com.jxxc.jingxi.ui.finddetails;

import android.content.Context;

import com.jxxc.jingxi.Api;
import com.jxxc.jingxi.entity.backparameter.AppreciateEntity;
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

public class FindDetailsPresenter extends BasePresenterImpl<FindDetailsContract.View> implements FindDetailsContract.Presenter{

    @Override
    protected void onEventComing(EventCenter center) {

    }

    /**
     * 点赞接口
     * @param noticeId
     */
    @Override
    public void appreciate(String noticeId) {
        OkGo.<HttpResult<AppreciateEntity>>post(Api.APPRECIATE)
                .params("noticeId",noticeId)
                .execute(new JsonCallback<HttpResult<AppreciateEntity>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<AppreciateEntity>> response) {
                        AppreciateEntity d = response.body().data;
                        if (response.body().code==0){
                            mView.appreciateCallBack(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }
}
