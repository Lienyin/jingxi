package com.jxxc.jingxi.ui.login;

import android.widget.TextView;

import com.jxxc.jingxi.entity.backparameter.ThirdPartyLogin;
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
        void getThirdPartyLogin(ThirdPartyLogin data);
    }

    interface  Presenter extends BasePresenter<View> {
        void login(String phonenumber,String password);
        void loginCode(String phonenumber,String code);
        void thirdPartyLogin(String wxOpenid);

        void getCode(String phonenumber, TextView tvAuthCode);
    }
}
