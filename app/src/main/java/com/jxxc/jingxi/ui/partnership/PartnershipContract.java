package com.jxxc.jingxi.ui.partnership;

import android.content.Context;

import com.jxxc.jingxi.entity.backparameter.SelectAllAreaEntity;
import com.jxxc.jingxi.entity.backparameter.SelectByPhoneEntity;
import com.jxxc.jingxi.mvp.BasePresenter;
import com.jxxc.jingxi.mvp.BaseView;

import java.util.List;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class PartnershipContract {
    interface View extends BaseView {
        void selectAllAreaCallBack(List<SelectAllAreaEntity> data);
        void selectByPhoneCallBack(SelectByPhoneEntity data);
        void applyCallBack();
    }
    interface  Presenter extends BasePresenter<View> {
        void selectAllArea();
        void selectByPhone(String phonenumber);
        void apply(String phonenumber,String contacts,String provinceId,String cityId,
                   String districtId,String remark,String type);
    }
}
