package com.jxxc.jingxi.ui.discountcoupon.coupontype.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.MyCoupon;
import com.jxxc.jingxi.utils.AppUtils;

import java.text.DecimalFormat;
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
        //优惠券类型 0无门槛减N 1满N减N 2折扣券
        if (item.couponRuleType==0){
            helper.setText(R.id.tv_money_coupon, "￥"+new DecimalFormat("0.00").format(item.money));
        }else if (item.couponRuleType == 1){
            helper.setText(R.id.tv_money_coupon, "￥"+new DecimalFormat("0.00").format(item.money));
        }else{
            helper.setText(R.id.tv_money_coupon, new DecimalFormat("0.00").format(item.discount)+"折");
        }

        helper.setText(R.id.tv_name_coupon, (AppUtils.isEmpty(item.counponName)?"未知":item.counponName));
        helper.setText(R.id.tv_date_coupon, "有效期至："+item.startTime+"~"+item.endTime);


        if (item.status == 1 || item.status == 2){
            helper.setBackgroundColor(R.id.ll_coupon_bg,mContext.getResources().getColor(R.color.help_button_view));
        }else{
            helper.setBackgroundColor(R.id.ll_coupon_bg,mContext.getResources().getColor(R.color.coupon_bg));
        }
    }
}
