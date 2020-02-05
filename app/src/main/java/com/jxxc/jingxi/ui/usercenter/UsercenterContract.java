package com.jxxc.jingxi.ui.usercenter;

import com.jxxc.jingxi.entity.backparameter.UserInfoEntity;
import com.jxxc.jingxi.mvp.BaseView;
import com.jxxc.jingxi.mvp.BasePresenter;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class UsercenterContract {
    interface View extends BaseView {
        void updateInfoCallBack();
        void getUserInfoCallBack(UserInfoEntity data);
    }

    interface  Presenter extends BasePresenter<View> {
        void initImageSelecter();
        void gotoImageSelect(UsercenterActivity activity, int requestCodeChoose);
        void uploadImage(String s);
        void updateInfo(String avatar);
        void getUserInfo();
    }
}
