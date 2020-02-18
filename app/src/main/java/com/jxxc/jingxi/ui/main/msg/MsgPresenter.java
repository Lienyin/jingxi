package com.jxxc.jingxi.ui.main.msg;

import android.content.Context;

import com.jxxc.jingxi.Api;
import com.jxxc.jingxi.entity.backparameter.MessageListEntity;
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

public class MsgPresenter extends BasePresenterImpl<MsgContract.View> implements MsgContract.Presenter{

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    public void messageList(int pageNum, int pageSize) {
        OkGo.<HttpResult<List<MessageListEntity>>>post(Api.MESSAGE_LIST)
                .params("pageNum",pageNum)
                .params("pageSize",pageSize)
                .execute(new JsonCallback<HttpResult<List<MessageListEntity>>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<List<MessageListEntity>>> response) {
                        List<MessageListEntity> d = response.body().data;
                        if (response.body().code==0){
                            mView.messageList(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }

    @Override
    public void messageListMore(int pageNum, int pageSize) {
        OkGo.<HttpResult<List<MessageListEntity>>>post(Api.MESSAGE_LIST)
                .params("pageNum",pageNum)
                .params("pageSize",pageSize)
                .execute(new JsonCallback<HttpResult<List<MessageListEntity>>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<List<MessageListEntity>>> response) {
                        List<MessageListEntity> d = response.body().data;
                        if (response.body().code==0){
                            mView.messageListMore(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }
}
