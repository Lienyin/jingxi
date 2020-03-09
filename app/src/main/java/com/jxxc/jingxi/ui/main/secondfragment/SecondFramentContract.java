package com.jxxc.jingxi.ui.main.secondfragment;


import com.jxxc.jingxi.entity.backparameter.FindEntity;
import com.jxxc.jingxi.mvp.BasePresenter;
import com.jxxc.jingxi.mvp.BaseView;

import java.util.List;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class SecondFramentContract {
    interface View extends BaseView {
        void findCallBackCall(List<FindEntity> data);
        void findMoreCallBackCall(List<FindEntity> data);
    }

    interface  Presenter extends BasePresenter<View> {
        void find(String type,int pageNum,int pageSize);
        void findMore(String type,int pageNum,int pageSize);
    }
}
