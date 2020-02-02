package com.jxxc.jingxi.ui.login;

import com.jxxc.jingxi.mvp.BaseView;
import com.jxxc.jingxi.mvp.BasePresenter;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class LoginContract {
    interface View extends BaseView {
        void loginCallBack();
        void loginCodeCallBack();
    }

    interface  Presenter extends BasePresenter<View> {
        void login(String phonenumber,String password);
        void loginCode(String phonenumber,String code);
    }
}
