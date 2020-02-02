package com.jxxc.jingxi.ui.discountcoupon.coupontype.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.MyCoupon;
import com.jxxc.jingxi.utils.AppUtils;

import java.util.List;

/**
 * @authorfeisher on 2017/11/21.14:58
 * email:458079442@qq.com
 */

public class MyCouponAdapter extends BaseQuickAdapter<MyCoupon, BaseViewHolder> {
    public MyCouponAdapter(@LayoutRes int layoutResId, @Nullable List<MyCoupon> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyCoupon item) {
        helper.setText(R.id.tv_name_coupon, (AppUtils.isEmpty(item.name)?"未知":item.name));
        helper.setText(R.id.tv_date_coupon, AppUtils.isEmpty(item.beginTime)?"无说明":"有效期至："+item.beginTime+"~"+item.endTime);
        helper.setText(R.id.tv_money_coupon, "￥"+item.price);

        if (item.status == 1 || item.status == 2){
            helper.setTextColor(R.id.tv_money_coupon,mContext.getResources().getColor(R.color.help_button_view));
        }else{
            helper.setTextColor(R.id.tv_money_coupon,mContext.getResources().getColor(R.color.public_all));
        }
    }
}
