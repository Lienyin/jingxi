package com.jxxc.jingxi.ui.main.firstfragment;


import com.jxxc.jingxi.Api;
import com.jxxc.jingxi.entity.backparameter.ProductInfoEntity;
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

public class FirseFramentPresenter extends BasePresenterImpl<FirseFramentContract.View> implements FirseFramentContract.Presenter{
    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    public void productInfo() {
        OkGo.<HttpResult<List<ProductInfoEntity>>>post(Api.PRODUCT_INFO)
                .tag(this)
                .execute(new JsonCallback<HttpResult<List<ProductInfoEntity>>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<List<ProductInfoEntity>>> response) {
                        List<ProductInfoEntity> d = response.body().data;
                        if (response.body().code==0){
                            mView.productInfoCallBack(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }
}
