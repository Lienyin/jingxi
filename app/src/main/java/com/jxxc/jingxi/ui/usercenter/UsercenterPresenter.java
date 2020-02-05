package com.jxxc.jingxi.ui.usercenter;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.Api;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.UpdateInfoEntity;
import com.jxxc.jingxi.entity.backparameter.UserInfoEntity;
import com.jxxc.jingxi.http.EventCenter;
import com.jxxc.jingxi.http.HttpResult;
import com.jxxc.jingxi.http.JsonCallback;
import com.jxxc.jingxi.mvp.BasePresenterImpl;
import com.jxxc.jingxi.utils.AppUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.config.ISListConfig;

import java.io.File;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class UsercenterPresenter extends BasePresenterImpl<UsercenterContract.View> implements UsercenterContract.Presenter{

    ISListConfig config;

    @Override
    protected void onEventComing(EventCenter center) {

    }

    /**
     * 修改头像
     * @param avatar
     */
    @Override
    public void updateInfo(String avatar) {
        OkGo.<HttpResult>post(Api.UPDATE_INFO)
                .params("avatar",avatar)
                .execute(new JsonCallback<HttpResult>() {
                    @Override
                    public void onSuccess(Response<HttpResult> response) {
                        if (response.body().code==0){
                            mView.updateInfoCallBack();
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }

    /**
     * 选择图片
     */
    @Override
    public void initImageSelecter() {
        config = new ISListConfig.Builder()
                .multiSelect(false)
                .rememberSelected(true)
                .btnBgColor(Color.TRANSPARENT)
                .btnTextColor(Color.WHITE)
                .statusBarColor(ContextCompat.getColor(mView.getContext().getApplicationContext(), R.color.gray))
                .backResId(R.mipmap.back)
                .title("图片选择")
                .titleColor(Color.WHITE)
                .titleBgColor(ContextCompat.getColor(mView.getContext().getApplicationContext(),R.color.public_all))
                .cropSize(1, 1, 200, 200)
                .needCrop(false)
                .needCamera(true)
                .maxNum(1)
                .build();
    }

    @Override
    public void gotoImageSelect(UsercenterActivity activity, int requestCodeChoose) {
        ISNav.getInstance().toListActivity(activity, config, requestCodeChoose);
    }

    /**
     * @param s 头像路径(上传文件接口)
     */
    @Override
    public void uploadImage(String s) {
        OkGo.<HttpResult<UpdateInfoEntity>>post(Api.UPLOAD)
                .params("file",new File(s))
                .isMultipart(true)
                .execute(new JsonCallback<HttpResult<UpdateInfoEntity>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<UpdateInfoEntity>> response) {
                        StyledDialog.dismissLoading();
                        UpdateInfoEntity d = response.body().data;
                        if (AppUtils.isEmpty(mView)) {
                            return;
                        }
                        if (response.body().code == 0) {
                            updateInfo(d.fileName);
                            toast(mContext,response.body().message);
                        }else{
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
}
