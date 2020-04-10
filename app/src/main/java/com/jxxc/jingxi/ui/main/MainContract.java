package com.jxxc.jingxi.ui.main;

import com.jxxc.jingxi.entity.backparameter.BannerEntity;
import com.jxxc.jingxi.entity.backparameter.GetStaticEntity;
import com.jxxc.jingxi.entity.backparameter.UserInfoEntity;
import com.jxxc.jingxi.mvp.BaseView;
import com.jxxc.jingxi.mvp.BasePresenter;

import java.util.List;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MainContract {
    interface View extends BaseView {
        void getUserInfoCallBack(UserInfoEntity data);
        void bannerCallBack(List<BannerEntity> data);
        void getStaticCallBack(GetStaticEntity data);
    }

    interface  Presenter extends BasePresenter<View> {
        void getUserInfo();
        //查询app版本
        void queryAppVersion(String type);
        void banner();//滚动广告
        void getStatic();
    }
}
