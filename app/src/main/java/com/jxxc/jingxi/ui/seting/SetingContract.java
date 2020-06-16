package com.jxxc.jingxi.ui.seting;

import com.jxxc.jingxi.mvp.BasePresenter;
import com.jxxc.jingxi.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class SetingContract {
    interface View extends BaseView {
        
    }

    interface  Presenter extends BasePresenter<View> {
        void outLogin();
    }
}
