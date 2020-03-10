package com.jxxc.jingxi.ui.main.firstfragment;


import com.jxxc.jingxi.entity.backparameter.BannerEntity;
import com.jxxc.jingxi.entity.backparameter.GetStateEntity;
import com.jxxc.jingxi.entity.backparameter.ProductInfoEntity;
import com.jxxc.jingxi.entity.backparameter.RecommendComboInfoEntity;
import com.jxxc.jingxi.entity.backparameter.RecommendCompanyListEntity;
import com.jxxc.jingxi.mvp.BasePresenter;
import com.jxxc.jingxi.mvp.BaseView;

import java.util.List;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class FirseFramentContract {
    interface View extends BaseView {
        void bannerCallBack(List<BannerEntity> data);
        void recommendComboInfoCallBack(List<RecommendComboInfoEntity> data);
        void recommendCompanyListCallBack(List<RecommendCompanyListEntity> data);
        void getStateCallBack(GetStateEntity data);
    }

    interface  Presenter extends BasePresenter<View> {
        void banner();
        void recommendComboInfo();
        void recommendCompanyList(double lat,double lng);
        void getState();
    }
}
