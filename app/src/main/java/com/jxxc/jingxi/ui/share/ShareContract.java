package com.jxxc.jingxi.ui.share;

import android.content.Context;

import com.jxxc.jingxi.entity.backparameter.GetInfoEntity;
import com.jxxc.jingxi.entity.backparameter.UserInfoEntity;
import com.jxxc.jingxi.mvp.BasePresenter;
import com.jxxc.jingxi.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class ShareContract {
    interface View extends BaseView {
        void getInfoCallBack(GetInfoEntity data);
        void getUserInfoCallBack(UserInfoEntity data);
    }

    interface  Presenter extends BasePresenter<View> {
        void getInfo();
        void getUserInfo();
    }
}
