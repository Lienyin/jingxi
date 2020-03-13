package com.jxxc.jingxi.ui.main.msg;

import android.content.Context;

import com.jxxc.jingxi.Api;
import com.jxxc.jingxi.entity.backparameter.MessageListEntity;
import com.jxxc.jingxi.entity.backparameter.RecommendComboInfoEntity;
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

    /**
     * 获取洗车套餐
     */
    @Override
    public void recommendComboInfo(String serviceType,String companyId) {
        OkGo.<HttpResult<RecommendComboInfoEntity>>post(Api.RECOMMEND_COMBO_INFO)
                .params("serviceType",serviceType)
                .params("companyId",companyId)
                .execute(new JsonCallback<HttpResult<RecommendComboInfoEntity>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<RecommendComboInfoEntity>> response) {
                        RecommendComboInfoEntity d = response.body().data;
                        if (response.body().code==0){
                            mView.recommendComboInfoCallBack(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }
}
