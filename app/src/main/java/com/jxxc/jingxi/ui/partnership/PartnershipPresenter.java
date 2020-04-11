package com.jxxc.jingxi.ui.partnership;

import android.content.Context;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.Api;
import com.jxxc.jingxi.entity.backparameter.SelectAllAreaEntity;
import com.jxxc.jingxi.entity.backparameter.SelectByPhoneEntity;
import com.jxxc.jingxi.http.EventCenter;
import com.jxxc.jingxi.http.HttpResult;
import com.jxxc.jingxi.http.JsonCallback;
import com.jxxc.jingxi.mvp.BasePresenterImpl;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.List;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class PartnershipPresenter extends BasePresenterImpl<PartnershipContract.View> implements PartnershipContract.Presenter{

    @Override
    protected void onEventComing(EventCenter center) {

    }

    @Override
    public void selectAllArea() {
        OkGo.<HttpResult<List<SelectAllAreaEntity>>>post(Api.SELECT_ALL_AREA)
                .tag(this)
                .execute(new JsonCallback<HttpResult<List<SelectAllAreaEntity>>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<List<SelectAllAreaEntity>>> response) {
                        List<SelectAllAreaEntity> d = response.body().data;
                        if (response.body().code==0){
                            mView.selectAllAreaCallBack(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }

    /**
     * 查询是否已申请
     * @param phonenumber
     */
    @Override
    public void selectByPhone(String phonenumber) {
        OkGo.<HttpResult<SelectByPhoneEntity>>post(Api.SELECT_BY_PHONE)
                .params("phonenumber",phonenumber)
                .execute(new JsonCallback<HttpResult<SelectByPhoneEntity>>() {
                    @Override
                    public void onSuccess(Response<HttpResult<SelectByPhoneEntity>> response) {
                        SelectByPhoneEntity d = response.body().data;
                        if (response.body().code==0){
                            mView.selectByPhoneCallBack(d);
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }

    /**
     * 申请加入接口
     * @param phonenumber
     * @param contacts
     * @param provinceId
     * @param cityId
     * @param districtId
     * @param remark
     */
    @Override
    public void apply(String phonenumber, String contacts, String provinceId,
                      String cityId, String districtId, String remark,String type) {
        OkGo.<HttpResult>post(Api.APPLY)
                .params("phonenumber",phonenumber)
                .params("contacts",contacts)
                .params("provinceId",provinceId)
                .params("cityId",cityId)
                .params("districtId",districtId)
                .params("remark",remark)
                .params("type",type)
                .execute(new JsonCallback<HttpResult>() {
                    @Override
                    public void onSuccess(Response<HttpResult> response) {
                        StyledDialog.dismissLoading();
                        if (response.body().code==0){
                            mView.applyCallBack();
                        }else{
                            toast(mContext,response.body().message);
                        }
                    }
                });
    }
}
