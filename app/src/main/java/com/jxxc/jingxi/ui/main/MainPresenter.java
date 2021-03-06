package com.jxxc.jingxi.ui.main;

import android.content.Context;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.dialog.UpdataHintDialog;
import com.jxxc.jingxi.dialog.UpdateProgressDialog;
import com.jxxc.jingxi.entity.backparameter.BannerEntity;
import com.jxxc.jingxi.entity.backparameter.GetStaticEntity;
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

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import zlc.season.rxdownload2.RxDownload;
import zlc.season.rxdownload2.entity.DownloadStatus;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MainPresenter extends BasePresenterImpl<MainContract.View> implements MainContract.Presenter{
    private UpdataHintDialog dialog;

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
                            SPUtils.put(SPUtils.K_SESSION_MOBILE,d.phonenumber);
                            SPUtils.put(SPUtils.K_ROLE,d.accountType);
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
                            String url = "http://"+version.url;
                            String memo = version.memo;
                            String ver = version.version;
                            if (!AppUtils.isEmpty(version)) {
                                if (ver.contains(".")) {
                                    String vOnline = ver.replace(".", "").trim();
                                    String versionName = BuildConfig.VERSION_NAME;
                                    String vLoal = versionName.replace(".", "").trim();
                                    if (Integer.parseInt(vOnline) > Integer.parseInt(vLoal)) {
                                        if (version.isForce == 1) {//是否强制更新
                                            updateAPK(url, memo, true,ver);
                                        } else {
                                            updateAPK(url, memo, false,ver);
                                        }
                                    }
                                }
                            }
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

    //获取活动状态
    @Override
    public void getStatic() {
        OkGo.<HttpResult<GetStaticEntity>>post(Api.GET_STATUS)
                .tag(this)
                .execute(new JsonCallback<HttpResult<GetStaticEntity>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<GetStaticEntity>> response) {
                        GetStaticEntity d = response.body().data;
                        if (response.body().code==0){
                            mView.getStaticCallBack(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }

    /**
     * 下载apk并安装
     */
    public void updateAPK(final String url, String memo, boolean isMust, String versions) {
        if (!AppUtils.isEmpty(mView)) {
            mView.updateCB(isMust);
        }
        if (isMust) {
            String msg = null;
            if (!AppUtils.isEmpty(memo)) {
                msg = memo+ "\n如不升级将退出应用";
            } else {
                msg = "如不升级将退出应用";
            }
            dialog = new UpdataHintDialog(mContext);
            dialog.showShareDialog(false, msg, "退出应用",versions);
            dialog.setOnFenxiangClickListener(new UpdataHintDialog.OnFenxiangClickListener() {
                @Override
                public void onFenxiangClick(int shareType) {
                    if (shareType == 1) {
                        if (!AppUtils.isEmpty(url)) {  //开启更新
                            startDownloadAPK(url);
                        }
                    }else{
                        ConfigApplication.exit(true);
                    }
                }
            });
        } else {
            String msg = null;
            if (!AppUtils.isEmpty(memo)) {
                msg = memo;
            } else {
                msg = "有更好的版本等着你，快更新吧！";
            }
            dialog = new UpdataHintDialog(mContext);
            dialog.showShareDialog(true, msg, "暂不更新",versions);
            dialog.setOnFenxiangClickListener(new UpdataHintDialog.OnFenxiangClickListener() {
                @Override
                public void onFenxiangClick(int shareType) {
                    if (shareType == 1) {
                        if (!AppUtils.isEmpty(url)) {  //开启更新
                            startDownloadAPK(url);
                        }
                    }else{
                        dialog.cleanDialog();
                    }
                }
            });
        }

    }

    /**
     * 安装apk
     *
     * @param url
     */
    private void startDownloadAPK(String url) {
        UpdateProgressDialog.show(mView.getContext());
        final RxDownload mRxDownload = RxDownload.getInstance(mView.getContext());
        mRxDownload.download(url, "jingxi.apk", ConfigApplication.CACHA_URL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DownloadStatus>() {
                    @Override
                    public void accept(DownloadStatus downloadStatus) throws Exception {

                        double v = (double) downloadStatus.getDownloadSize() / downloadStatus.getTotalSize();
                        int progress = (int) (v * 100);
                        UpdateProgressDialog.setProgress(progress,
                                downloadStatus.getFormatDownloadSize() + "/" + downloadStatus.getFormatTotalSize(),
                                downloadStatus.getDownloadSize());
                    }

                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        toast(mContext, "下载失败");
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        if (!AppUtils.isEmpty(mView)) {
                            //下载成功
                            UpdateProgressDialog.dismiss();
                            File file = mRxDownload.getRealFiles("jingxi.apk", ConfigApplication.CACHA_URL)[0];
                            Context context = mView.getContext().getApplicationContext();
                            AppUtils.installApk(context, file, BuildConfig.APPLICATION_ID + ".provider");
                            //install(mContext,file);
                        }

                    }
                });
    }
}
