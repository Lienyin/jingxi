package com.jxxc.jingxi.ui.updatepassword;

import com.jxxc.jingxi.mvp.BasePresenter;
import com.jxxc.jingxi.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class UpdatePasswordContract {
    interface View extends BaseView {
        void updatePasswordCallBack();
    }

    interface  Presenter extends BasePresenter<View> {
        void updatePassword(String oldPassword, String newPassword);
    }
}
