package com.jxxc.jingxi.ui.cartypeselect;

import android.content.Context;

import com.jxxc.jingxi.entity.backparameter.BandAndTypeEntity;
import com.jxxc.jingxi.mvp.BasePresenter;
import com.jxxc.jingxi.mvp.BaseView;

import java.util.List;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class CarTypeSelectContract {
    interface View extends BaseView {
        void getBandAndTypeCallBack(BandAndTypeEntity data);
    }

    interface  Presenter extends BasePresenter<View> {
        void getBandAndType();
    }
}
