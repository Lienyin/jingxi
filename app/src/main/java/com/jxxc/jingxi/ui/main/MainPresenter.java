package com.jxxc.jingxi.ui.main;

import android.content.Context;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.entity.backparameter.BannerEntity;
import com.jxxc.jingxi.entity.backparameter.LatestVersionEntity;
import com.jxxc.jingxi.entity.backparameter.UserInfoEntity;
import com.jxxc.jingxi.utils.SPUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.jxxc.jingxi.Api;
import com.jxxc.jingxi.BuildConfig;
import com.jxxc.jingxi.ConfigApplication;
import com.jxxc.jingxi.http.EventCenter;
import com.jxxc.jingxi.http.HttpResult;
import com.jxxc.jingxi.http.JsonCallback;
import com.jxxc.jingxi.mvp.BasePresenterImpl;
import com.jxxc.jingxi.utils.AppUtils;
import java.io.File;
import java.util.List;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MainPresenter extends BasePresenterImpl<MainContract.View> implements MainContract.Presenter{

    @Override
    protected void onEventComing(EventCenter center) {

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
                            String url = version.url;
                            String memo = version.memo;
                            String ver = version.version;
//                            if (!AppUtils.isEmpty(version)) {
//                                if (ver.contains(".")) {
//                                    String vOnline = ver.replace(".", "").trim();
//                                    String versionName = BuildConfig.VERSION_NAME;
//                                    String vLoal = versionName.replace(".", "").trim();
//                                    if (Integer.parseInt(vOnline) > Integer.parseInt(vLoal)) {
//                                        if (version.isForce == 1) {//是否强制更新
//                                            updateAPK(url, memo, true,ver);
//                                        } else {
//                                            updateAPK(url, memo, false,ver);
//                                        }
//                                    }
//                                }
//                            }
                            //mView.latestVersionCallBack();
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
