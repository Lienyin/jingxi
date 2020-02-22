package com.jxxc.jingxi.ui.myorder;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.adapter.HomeDataAdapter;
import com.jxxc.jingxi.entity.backparameter.MyOrderEntity;

import java.util.List;

/**
 * @authorfeisher on 2017/11/21.14:58
 * email:458079442@qq.com
 */

public class BillAdapter extends BaseQuickAdapter<MyOrderEntity, BaseViewHolder> {

    HomeDataAdapter adapter;

    public BillAdapter(@LayoutRes int layoutResId, @Nullable List<MyOrderEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final MyOrderEntity item) {
        helper.setText(R.id.tv_order_id, item.orderId);
        helper.setText(R.id.tv_fuwu_address, item.address);
        adapter = new HomeDataAdapter(mContext);
        adapter.setData(item.products);
        helper.setAdapter(R.id.gv_fuwu_data,adapter);

        //订单状态 不传查默认所有 ( 0, “待支付”),( 1, “已支付待接单”),
        // ( 2, “已接单待服务”),( 3, “服务中”),( 4, “服务已完成”),( 5, “取消订单”)
        if (item.status==0){
            //待支付
            helper.setText(R.id.iv_order_static,"待支付");
            helper.setGone(R.id.ll_evaluate,false);
            helper.setGone(R.id.ll_cancel_order,true);
        }else if (item.status==1){
            //已支付待接单
            helper.setText(R.id.iv_order_static,"待接单");
            helper.setGone(R.id.ll_evaluate,false);
            helper.setGone(R.id.ll_cancel_order,true);
        }else if (item.status==2){
            //已接单待服务
            helper.setText(R.id.iv_order_static,"待服务");
            helper.setGone(R.id.ll_evaluate,false);
            helper.setGone(R.id.ll_cancel_order,true);
        }else if (item.status==3){
            //服务中
            helper.setText(R.id.iv_order_static,"服务中");
            helper.setGone(R.id.ll_evaluate,false);
            helper.setGone(R.id.ll_cancel_order,false);
        }else if (item.status==4){
            //服务已完成
            helper.setText(R.id.iv_order_static,"已完成");
            helper.setGone(R.id.ll_evaluate,true);
            helper.setGone(R.id.ll_cancel_order,false);
        }else if (item.status==5){
            //取消订单
            helper.setText(R.id.iv_order_static,"取消订单");
            helper.setGone(R.id.ll_evaluate,false);
            helper.setGone(R.id.ll_cancel_order,false);
        }else{
            helper.setText(R.id.iv_order_static,"订单异常");
            helper.setGone(R.id.ll_evaluate,false);
            helper.setGone(R.id.ll_cancel_order,false);
        }
        helper.setOnClickListener(R.id.ll_call_phone, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //联系技师
                onFenxiangClickListener.onFenxiangClick(1,item.technicianPhonenumber,item.orderId);
            }
        });
        helper.setOnClickListener(R.id.ll_evaluate, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //评价
                onFenxiangClickListener.onFenxiangClick(2,item.technicianPhonenumber,item.orderId);
            }
        });
        helper.setOnClickListener(R.id.ll_cancel_order, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //取消订单
                onFenxiangClickListener.onFenxiangClick(3,item.technicianPhonenumber,item.orderId);
            }
        });
    }

    private OnFenxiangClickListener onFenxiangClickListener;

    public void setOnFenxiangClickListener(OnFenxiangClickListener onFenxiangClickListener) {
        this.onFenxiangClickListener = onFenxiangClickListener;
    }

    public interface OnFenxiangClickListener{
        void onFenxiangClick(int type,String mobile,String orderId);
    }
}
