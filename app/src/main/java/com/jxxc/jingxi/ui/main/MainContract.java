package com.jxxc.jingxi.ui.main;

import com.jxxc.jingxi.entity.backparameter.UserInfoEntity;
import com.jxxc.jingxi.mvp.BaseView;
import com.jxxc.jingxi.mvp.BasePresenter;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MainContract {
    interface View extends BaseView {
        void getUserInfoCallBack(UserInfoEntity data);
    }

    interface  Presenter extends BasePresenter<View> {
        void getUserInfo();
        //查询app版本
        void queryAppVersion(String type);
    }
}
