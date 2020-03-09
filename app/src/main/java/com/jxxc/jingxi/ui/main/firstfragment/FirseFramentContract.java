package com.jxxc.jingxi.ui.main.firstfragment;


import com.jxxc.jingxi.entity.backparameter.BannerEntity;
import com.jxxc.jingxi.entity.backparameter.ProductInfoEntity;
import com.jxxc.jingxi.mvp.BasePresenter;
import com.jxxc.jingxi.mvp.BaseView;

import java.util.List;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class FirseFramentContract {
    interface View extends BaseView {
        void comboInfoCallBack(ProductInfoEntity data);
        void bannerCallBack(List<BannerEntity> data);
    }

    interface  Presenter extends BasePresenter<View> {
        void comboInfo();
        void banner();
    }
}
