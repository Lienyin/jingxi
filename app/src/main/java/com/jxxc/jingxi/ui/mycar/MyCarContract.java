package com.jxxc.jingxi.ui.mycar;

import android.content.Context;

import com.jxxc.jingxi.entity.backparameter.CarListEntity;
import com.jxxc.jingxi.mvp.BasePresenter;
import com.jxxc.jingxi.mvp.BaseView;

import java.util.List;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MyCarContract {
    interface View extends BaseView {
        void getCarListCallBack(List<CarListEntity> data);
        void removeCarCallBack();
    }

    interface  Presenter extends BasePresenter<View> {
        void getCarList();
        void removeCar(String carNum);
    }
}
