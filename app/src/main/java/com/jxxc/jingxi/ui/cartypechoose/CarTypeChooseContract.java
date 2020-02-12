package com.jxxc.jingxi.ui.cartypechoose;

import android.content.Context;

import com.jxxc.jingxi.entity.backparameter.BandAndTypeEntity;
import com.jxxc.jingxi.mvp.BasePresenter;
import com.jxxc.jingxi.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class CarTypeChooseContract {
    interface View extends BaseView {
        void getBandAndTypeCallBack(BandAndTypeEntity data);
    }

    interface  Presenter extends BasePresenter<View> {
        void getBandAndType();
    }
}
