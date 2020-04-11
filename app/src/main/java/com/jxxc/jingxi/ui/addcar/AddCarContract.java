package com.jxxc.jingxi.ui.addcar;

import android.content.Context;

import com.jxxc.jingxi.entity.backparameter.ColorEntity;
import com.jxxc.jingxi.mvp.BasePresenter;
import com.jxxc.jingxi.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class AddCarContract {
    interface View extends BaseView {
        void queryAppVersionCallBack(ColorEntity data);
        void addCarCallBack();
    }

    interface  Presenter extends BasePresenter<View> {
        void queryAppVersion(String type);
        void addCar(String carNum,String brandId,String typeId,String color,String isNewEnergy);
        void editCar(String carNum,String brandId,String typeId,String color,String isNewEnergy,
                     String isDefault,String newCarNum);
    }
}
