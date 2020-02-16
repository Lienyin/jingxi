package com.jxxc.jingxi.ui.myorder;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.MyOrderEntity;

import java.util.List;

/**
 * @authorfeisher on 2017/11/21.14:58
 * email:458079442@qq.com
 */

public class BillAdapter extends BaseQuickAdapter<MyOrderEntity, BaseViewHolder> {

    public BillAdapter(@LayoutRes int layoutResId, @Nullable List<MyOrderEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final MyOrderEntity item) {
        helper.setText(R.id.tv_order_id, item.orderId);
        helper.setText(R.id.tv_fuwu_address, item.address);
        //订单状态 不传查默认所有 ( 0, “待支付”),( 1, “已支付待接单”),
        // ( 2, “已接单待服务”),( 3, “服务中”),( 4, “服务已完成”),( 5, “取消订单”)
        if (item.status==0){
            //待支付
            helper.setText(R.id.iv_order_static,"待支付");
        }else if (item.status==1){
            //已支付待接单
            helper.setText(R.id.iv_order_static,"待接单");
        }else if (item.status==2){
            //已接单待服务
            helper.setText(R.id.iv_order_static,"待服务");
        }else if (item.status==3){
            //服务中
            helper.setText(R.id.iv_order_static,"服务中");
        }else if (item.status==4){
            //服务已完成
            helper.setText(R.id.iv_order_static,"已完成");
        }else if (item.status==5){
            //取消订单
            helper.setText(R.id.iv_order_static,"取消订单");
        }else{
            //
            helper.setText(R.id.iv_order_static,"订单异常");
        }
    }

    private OnFenxiangClickListener onFenxiangClickListener;

    public void setOnFenxiangClickListener(OnFenxiangClickListener onFenxiangClickListener) {
        this.onFenxiangClickListener = onFenxiangClickListener;
    }

    public interface OnFenxiangClickListener{
        void onFenxiangClick(String orderId, int type);
    }
}
