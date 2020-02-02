package com.jxxc.jingxi.ui.regards;


import com.jxxc.jingxi.mvp.BasePresenter;
import com.jxxc.jingxi.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class RegardsContract {
    interface View extends BaseView {
        void updateCB(boolean must);
    }

    interface  Presenter extends BasePresenter<View> {
        //查询app版本
        void queryAppVersion(String type);
    }
}
