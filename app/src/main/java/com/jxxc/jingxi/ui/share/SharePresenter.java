package com.jxxc.jingxi.ui.share;

import android.content.Context;

import com.jxxc.jingxi.Api;
import com.jxxc.jingxi.entity.backparameter.GetInfoEntity;
import com.jxxc.jingxi.entity.backparameter.QueryActivityDetailEntity;
import com.jxxc.jingxi.entity.backparameter.UserInfoEntity;
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

public class SharePresenter extends BasePresenterImpl<ShareContract.View> implements ShareContract.Presenter{

    @Override
    protected void onEventComing(EventCenter center) {

    }

    /**
     * 进度
     */
    @Override
    public void getInfo() {
        OkGo.<HttpResult<GetInfoEntity>>post(Api.GET_INFO)
                .tag(this)
                .execute(new JsonCallback<HttpResult<GetInfoEntity>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<GetInfoEntity>> response) {
                        GetInfoEntity d = response.body().data;
                        if (response.body().code==0){
                            mView.getInfoCallBack(d);
                        }else {
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }
    /**
     * 获得个人信息
     */
    @Override
    public void getUserInfo() {
        OkGo.<HttpResult<UserInfoEntity>>post(Api.INFO_USER)
                .tag(this)
                .execute(new JsonCallback<HttpResult<UserInfoEntity>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<UserInfoEntity>> response) {
                        UserInfoEntity d = response.body().data;
                        if (response.body().code==0){
                            mView.getUserInfoCallBack(d);
                        }else {
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }

    //分享数据
    @Override
    public void queryActivityDetail() {
        OkGo.<HttpResult<QueryActivityDetailEntity>>post(Api.QUERY_ACTIVITY_DETAIL)
                .tag(this)
                .execute(new JsonCallback<HttpResult<QueryActivityDetailEntity>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<QueryActivityDetailEntity>> response) {
                        QueryActivityDetailEntity d = response.body().data;
                        if (response.body().code==0){
                            mView.queryActivityDetailCallBack(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }
}
