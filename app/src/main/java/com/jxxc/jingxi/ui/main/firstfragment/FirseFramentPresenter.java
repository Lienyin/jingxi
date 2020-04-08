package com.jxxc.jingxi.ui.main.firstfragment;


import com.jxxc.jingxi.Api;
import com.jxxc.jingxi.entity.backparameter.BannerEntity;
import com.jxxc.jingxi.entity.backparameter.GetStateEntity;
import com.jxxc.jingxi.entity.backparameter.ProductIdListEntity;
import com.jxxc.jingxi.entity.backparameter.ProductInfoEntity;
import com.jxxc.jingxi.entity.backparameter.RecommendComboInfoEntity;
import com.jxxc.jingxi.entity.backparameter.RecommendCompanyListEntity;
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
     * 获取推荐洗车套餐
     */
    @Override
    public void recommendComboInfo(String serviceType,String companyId) {
        OkGo.<HttpResult<List<RecommendComboInfoEntity>>>post(Api.RECOMMEND_COMBO_INFO)
                .params("serviceType",serviceType)
                .params("companyId",companyId)
                .execute(new JsonCallback<HttpResult<List<RecommendComboInfoEntity>>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<List<RecommendComboInfoEntity>>> response) {
                        List<RecommendComboInfoEntity> d = response.body().data;
                        if (response.body().code==0){
                            mView.recommendComboInfoCallBack(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }

    /**
     * 推荐加盟商列表
     * @param lat
     * @param lng
     */
    @Override
    public void recommendCompanyList(double lat, double lng) {
        OkGo.<HttpResult<List<RecommendCompanyListEntity>>>post(Api.RECOMMEND_COMPANY_LIST)
                .params("lat",lat)
                .params("lng",lng)
                .execute(new JsonCallback<HttpResult<List<RecommendCompanyListEntity>>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<List<RecommendCompanyListEntity>>> response) {
                        List<RecommendCompanyListEntity> d = response.body().data;
                        if (response.body().code==0){
                            mView.recommendCompanyListCallBack(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }

    /**
     * 获取用户状态
     */
    @Override
    public void getState() {
        OkGo.<HttpResult<GetStateEntity>>post(Api.GET_STATE)
                .tag(this)
                .execute(new JsonCallback<HttpResult<GetStateEntity>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<GetStateEntity>> response) {
                        GetStateEntity d = response.body().data;
                        if (response.body().code==0){
                            mView.getStateCallBack(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }

    /**
     * 菁喜产品
     */
    @Override
    public void productIdList() {
        OkGo.<HttpResult<List<ProductIdListEntity>>>post(Api.PRODUCT_ID_LIST)
                .tag(this)
                .execute(new JsonCallback<HttpResult<List<ProductIdListEntity>>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<List<ProductIdListEntity>>> response) {
                        List<ProductIdListEntity> d = response.body().data;
                        if (response.body().code==0){
                            mView.productIdListCallBack(d);
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
