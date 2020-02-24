package com.jxxc.jingxi.ui.mywallet;

import com.jxxc.jingxi.Api;
import com.jxxc.jingxi.entity.backparameter.UserInfoEntity;
import com.jxxc.jingxi.entity.backparameter.WalletEntity;
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

public class MyWalletPresenter extends BasePresenterImpl<MyWalletContract.View> implements MyWalletContract.Presenter{

    @Override
    protected void onEventComing(EventCenter center) {

    }

    /**
     * 个人信息
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
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }

    /**
     * 我的钱包
     */
    @Override
    public void wallet() {
        OkGo.<HttpResult<WalletEntity>>post(Api.WALLET)
                .tag(this)
                .execute(new JsonCallback<HttpResult<WalletEntity>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<WalletEntity>> response) {
                        WalletEntity d = response.body().data;
                        if (response.body().code==0){
                            mView.walletCallBack(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }
}
