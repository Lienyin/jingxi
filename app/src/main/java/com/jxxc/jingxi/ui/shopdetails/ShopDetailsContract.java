package com.jxxc.jingxi.ui.shopdetails;

import android.content.Context;

import com.jxxc.jingxi.entity.backparameter.CompanyDetailsEntity;
import com.jxxc.jingxi.mvp.BasePresenter;
import com.jxxc.jingxi.mvp.BaseView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class ShopDetailsContract {
    interface View extends BaseView {
        void getCompanyCallBack(CompanyDetailsEntity data);
    }

    interface  Presenter extends BasePresenter<View> {
        void getCompany(String companyId);
    }
}
