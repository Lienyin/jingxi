package com.jxxc.jingxi.ui.commissionlist;

import com.jxxc.jingxi.entity.backparameter.CommissionListEntity;
import com.jxxc.jingxi.mvp.BasePresenter;
import com.jxxc.jingxi.mvp.BaseView;

import java.util.List;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class CommissionListContract {
    interface View extends BaseView {
        void CommissionDetailCallBack(List<CommissionListEntity> data);
        void CommissionDetailMoreCallBack(List<CommissionListEntity> data);
    }

    interface  Presenter extends BasePresenter<View> {
        void CommissionDetail(String createTime, int pageNum, int pageSize);
        void CommissionDetailMore(String createTime, int pageNum, int pageSize);
    }
}
