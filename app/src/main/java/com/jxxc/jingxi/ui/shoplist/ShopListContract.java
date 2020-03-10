package com.jxxc.jingxi.ui.shoplist;

import android.content.Context;

import com.jxxc.jingxi.entity.backparameter.AreaListEntity;
import com.jxxc.jingxi.entity.backparameter.companyListEntity;
import com.jxxc.jingxi.mvp.BasePresenter;
import com.jxxc.jingxi.mvp.BaseView;

import java.util.List;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class ShopListContract {
    interface View extends BaseView {
        void companyListCallBack(List<companyListEntity> data);
        void companyListCallBackMore(List<companyListEntity> data);

        void areaListCallBack(List<AreaListEntity> data);
    }

    interface  Presenter extends BasePresenter<View> {
        void companyList(double lng,double lat,String queryFlag,String sort,String areaId,int pageNum,int pageSize);
        void companyListMore(double lng,double lat,String queryFlag,String sort,String areaId,int pageNum,int pageSize);

        void areaList();
    }
}
