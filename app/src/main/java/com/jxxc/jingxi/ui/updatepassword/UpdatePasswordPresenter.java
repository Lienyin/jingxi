package com.jxxc.jingxi.ui.updatepassword;

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

public class UpdatePasswordPresenter extends BasePresenterImpl<UpdatePasswordContract.View> implements UpdatePasswordContract.Presenter{

    @Override
    protected void onEventComing(EventCenter center) {

    }

    /**
     * 修改密码
     * @param oldPassword
     * @param newPassword
     */
    @Override
    public void updatePassword(String oldPassword, String newPassword) {
        OkGo.<HttpResult>post(Api.UPDATE_PASSWORD)
                .params("oldPassword",oldPassword)
                .params("newPassword",newPassword)
                .execute(new JsonCallback<HttpResult>() {
                    @Override
                    public void onSuccess(Response<HttpResult> response) {
                        if (response.body().code == 0){
                            mView.updatePasswordCallBack();
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }
}
