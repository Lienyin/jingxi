package com.jxxc.jingxi.ui.bindingphonenumber;

import android.widget.TextView;

import com.jxxc.jingxi.mvp.BasePresenter;
import com.jxxc.jingxi.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class BindingPhoneNumberContract {
    interface View extends BaseView {
        void gotoUserMain();
    }

    interface  Presenter extends BasePresenter<View> {
        void getThirdPartyInfo(String phonenumber,
                               String userName,
                               String avatar,
                               String wxOpenId,
                               String appleId,
                               String code);
        void getCode(String phonenumber, TextView tvAuthCode);
    }
}
