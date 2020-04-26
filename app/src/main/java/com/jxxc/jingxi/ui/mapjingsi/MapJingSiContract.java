package com.jxxc.jingxi.ui.mapjingsi;

import android.content.Context;

import com.jxxc.jingxi.entity.backparameter.NearbyConpanyEntity;
import com.jxxc.jingxi.mvp.BasePresenter;
import com.jxxc.jingxi.mvp.BaseView;

import java.util.List;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MapJingSiContract {
    interface View extends BaseView {
        void nearbyConpanyCallBack(List<NearbyConpanyEntity> data);
    }

    interface  Presenter extends BasePresenter<View> {
        void nearbyConpany(String distance,double lng,double lat);
    }
}
