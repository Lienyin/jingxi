package com.jxxc.jingxi.ui.commissionlist;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.CommissionListEntity;

import java.util.List;

/**
 * @authorfeisher on 2017/11/21.14:58
 * email:458079442@qq.com
 */

public class CommissionAdapter extends BaseQuickAdapter<CommissionListEntity, BaseViewHolder> {

    public CommissionAdapter(@LayoutRes int layoutResId, @Nullable List<CommissionListEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final CommissionListEntity item) {
        helper.setText(R.id.tv_order_type, item.createTime);
        helper.setText(R.id.tv_order_id, "交易完成");
        helper.setText(R.id.tv_order_time, item.sourceType+"("+item.payType+"支付)");
        helper.setText(R.id.tv_order_money, "￥"+item.money);
    }

    private OnFenxiangClickListener onFenxiangClickListener;

    public void setOnFenxiangClickListener(OnFenxiangClickListener onFenxiangClickListener) {
        this.onFenxiangClickListener = onFenxiangClickListener;
    }

    public interface OnFenxiangClickListener{
        void onFenxiangClick(double lat, double lng, String siteName, int type, int isAction);
    }
}
