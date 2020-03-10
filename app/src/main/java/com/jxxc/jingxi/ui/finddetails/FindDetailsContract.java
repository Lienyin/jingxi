package com.jxxc.jingxi.ui.finddetails;

import android.content.Context;

import com.jxxc.jingxi.entity.backparameter.AppreciateEntity;
import com.jxxc.jingxi.mvp.BasePresenter;
import com.jxxc.jingxi.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class FindDetailsContract {
    interface View extends BaseView {
        void appreciateCallBack(AppreciateEntity data);
    }

    interface  Presenter extends BasePresenter<View> {
        void appreciate(String noticeId);
    }
}
