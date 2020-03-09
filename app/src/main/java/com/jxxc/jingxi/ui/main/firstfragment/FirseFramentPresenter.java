package com.jxxc.jingxi.ui.main.firstfragment;


import com.jxxc.jingxi.Api;
import com.jxxc.jingxi.entity.backparameter.BannerEntity;
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

    /**
     * 获取洗车组合套餐
     */
    @Override
    public void comboInfo() {
        OkGo.<HttpResult<ProductInfoEntity>>post(Api.COMBO_INFO)
                .tag(this)
                .execute(new JsonCallback<HttpResult<ProductInfoEntity>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<ProductInfoEntity>> response) {
                        ProductInfoEntity d = response.body().data;
                        if (response.body().code==0){
                            mView.comboInfoCallBack(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }

    /**
     * 广告
     */
    @Override
    public void banner() {
        OkGo.<HttpResult<List<BannerEntity>>>post(Api.BANNER_LIST)
                .tag(this)
                .execute(new JsonCallback<HttpResult<List<BannerEntity>>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<List<BannerEntity>>> response) {
                        List<BannerEntity> d = response.body().data;
                        if (response.body().code==0){
                            mView.bannerCallBack(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }
}
