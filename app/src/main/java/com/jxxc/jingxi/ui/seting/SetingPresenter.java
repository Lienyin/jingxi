package com.jxxc.jingxi.ui.seting;

import com.jxxc.jingxi.Api;
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

public class SetingPresenter extends BasePresenterImpl<SetingContract.View> implements SetingContract.Presenter{

    @Override
    protected void onEventComing(EventCenter center) {

    }

    /**
     * 登出
     */
    @Override
    public void outLogin() {
        OkGo.<HttpResult>post(Api.LOGOUT)
                .tag(this)
                .execute(new JsonCallback<HttpResult>() {
                    @Override
                    public void onSuccess(Response<HttpResult> response) {

                    }
                });
    }
}
