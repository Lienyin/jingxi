package com.jxxc.jingxi.ui.main.secondfragment;

import com.jxxc.jingxi.Api;
import com.jxxc.jingxi.entity.backparameter.FindEntity;
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

public class SecondFramentPresenter extends BasePresenterImpl<SecondFramentContract.View> implements SecondFramentContract.Presenter{
    @Override
    protected void onEventComing(EventCenter center) {

    }

    /**
     * 发现列表
     * @param type
     */
    @Override
    public void find(String type, int pageNum, int pageSize) {
        OkGo.<HttpResult<List<FindEntity>>>post(Api.FIND_LIST)
                .params("type",type)
                .params("pageNum",pageNum)
                .params("pageSize",pageSize)
                .execute(new JsonCallback<HttpResult<List<FindEntity>>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<List<FindEntity>>> response) {
                        List<FindEntity> d = response.body().data;
                        if (response.body().code==0){
                            mView.findCallBackCall(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }

    @Override
    public void findMore(String type, int pageNum, int pageSize) {
        OkGo.<HttpResult<List<FindEntity>>>post(Api.FIND_LIST)
                .params("type",type)
                .params("pageNum",pageNum)
                .params("pageSize",pageSize)
                .execute(new JsonCallback<HttpResult<List<FindEntity>>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<List<FindEntity>>> response) {
                        List<FindEntity> d = response.body().data;
                        if (response.body().code==0){
                            mView.findMoreCallBackCall(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }
}
