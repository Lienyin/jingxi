package com.jxxc.jingxi.ui.main.my;

import android.content.Context;

import com.jxxc.jingxi.entity.backparameter.UserInfoEntity;
import com.jxxc.jingxi.mvp.BasePresenter;
import com.jxxc.jingxi.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MyContract {
    interface View extends BaseView {
        void getUserInfoCallBack(UserInfoEntity data);
    }

    interface  Presenter extends BasePresenter<View> {
        void getUserInfo();
    }
}
