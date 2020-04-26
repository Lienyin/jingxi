package com.jxxc.jingxi.ui.myorder;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.adapter.HomeDataAdapter;
import com.jxxc.jingxi.entity.backparameter.MyOrderEntity;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.ui.payorder.PayOrderActivity;

import java.text.DecimalFormat;
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
        helper.setText(R.id.tv_fuwu_type, item.serviceType==0?"上门服务":"到店服务");
        helper.setText(R.id.tv_order_money, "￥"+item.price);
        adapter = new HomeDataAdapter(mContext);
        adapter.setData(item.products);
        helper.setAdapter(R.id.gv_fuwu_data,adapter);

        //订单状态 不传查默认所有 ( 0, “待支付”),( 1, “已支付待接单”),
        // ( 2, “已接单待服务”),( 3, “服务中”),( 4, “服务已完成”),( 5, “取消订单”)
        if (item.status==0){
            //待支付
            helper.setText(R.id.iv_order_static,"待支付");
            helper.setBackgroundRes(R.id.iv_order_static,R.drawable.order_static_yellow);
            helper.setGone(R.id.ll_evaluate,false);
            helper.setGone(R.id.ll_cancel_order,true);
            helper.setGone(R.id.ll_fuwu_time,false);
            helper.setBackgroundRes(R.id.ll_order_itme,R.drawable.attestation_bg_edittext);
            helper.setGone(R.id.ll_call_phone,false);
        }else if (item.status==1){
            //已支付待接单
            helper.setText(R.id.iv_order_static,"已支付");
            helper.setBackgroundRes(R.id.iv_order_static,R.drawable.order_static);
            helper.setGone(R.id.ll_evaluate,false);
            helper.setGone(R.id.ll_cancel_order,true);
            helper.setGone(R.id.ll_fuwu_time,false);
            helper.setBackgroundRes(R.id.ll_order_itme,R.drawable.attestation_bg_edittext);
            helper.setGone(R.id.ll_call_phone,false);
        }else if (item.status==2){
            //已接单待服务
            helper.setText(R.id.iv_order_static,"已接单");
            helper.setBackgroundRes(R.id.iv_order_static,R.drawable.order_static);
            helper.setGone(R.id.ll_evaluate,false);
            helper.setGone(R.id.ll_cancel_order,true);
            helper.setGone(R.id.ll_fuwu_time,false);
            helper.setBackgroundRes(R.id.ll_order_itme,R.drawable.attestation_bg_edittext);
            helper.setGone(R.id.ll_call_phone,true);
        }else if (item.status==3){
            //服务中
            int m = item.surplusCompleteTime/60;
            if (m>=0){
                helper.setText(R.id.tv_fuwu_shengyu_ime,Html.fromHtml("剩余<font color=\"#008487\">"+m+"分钟</font>完成"));
            }else{
                helper.setText(R.id.tv_fuwu_shengyu_ime,Html.fromHtml("已超时<font color=\"#666666\">"+Math.abs(m)+"分钟</font>"));
            }
            helper.setGone(R.id.ll_fuwu_time,true);
            helper.setGone(R.id.iv_order_static,false);
            helper.setBackgroundRes(R.id.iv_order_static,R.drawable.order_static);
            helper.setGone(R.id.ll_evaluate,false);
            helper.setGone(R.id.ll_cancel_order,false);
            helper.setBackgroundRes(R.id.ll_order_itme,R.drawable.order_staic_bg);
            helper.setGone(R.id.ll_call_phone,true);
        }else if (item.status==4){
            //服务已完成
            helper.setText(R.id.iv_order_static,"已完成");
            helper.setBackgroundRes(R.id.iv_order_static,R.drawable.order_static_blue);
            helper.setGone(R.id.ll_evaluate,true);
            helper.setGone(R.id.ll_cancel_order,false);
            helper.setGone(R.id.ll_fuwu_time,false);
            helper.setBackgroundRes(R.id.ll_order_itme,R.drawable.attestation_bg_edittext);
            helper.setGone(R.id.ll_call_phone,true);
        }else if (item.status==5){
            //取消订单
            helper.setText(R.id.iv_order_static,"已取消");
            helper.setBackgroundRes(R.id.iv_order_static,R.drawable.order_static_gray);
            helper.setGone(R.id.ll_evaluate,false);
            helper.setGone(R.id.ll_cancel_order,false);
            helper.setGone(R.id.ll_fuwu_time,false);
            helper.setBackgroundRes(R.id.ll_order_itme,R.drawable.attestation_bg_edittext);
            helper.setGone(R.id.ll_call_phone,false);
        }else{
            helper.setText(R.id.iv_order_static,"订单异常");
            helper.setBackgroundRes(R.id.iv_order_static,R.drawable.order_static_red);
            helper.setGone(R.id.ll_evaluate,false);
            helper.setGone(R.id.ll_cancel_order,false);
            helper.setGone(R.id.ll_fuwu_time,false);
            helper.setBackgroundRes(R.id.ll_order_itme,R.drawable.attestation_bg_edittext);
            helper.setGone(R.id.ll_call_phone,false);
        }
        helper.setOnClickListener(R.id.ll_call_phone, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //联系技师
                onFenxiangClickListener.onFenxiangClick(1,item.technicianPhonenumber,"","");
            }
        });
        helper.setOnClickListener(R.id.ll_evaluate, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //评价
                onFenxiangClickListener.onFenxiangClick(2,item.technicianPhonenumber,item.orderId,"");
            }
        });
        helper.setOnClickListener(R.id.ll_cancel_order, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //取消订单
                onFenxiangClickListener.onFenxiangClick(3,item.technicianPhonenumber,item.orderId,"");
            }
        });
        helper.setOnClickListener(R.id.ll_gv_data, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //取消订单
                onFenxiangClickListener.onFenxiangClick(4,"",item.orderId,item.status+"");
            }
        });
        helper.setOnClickListener(R.id.iv_order_static, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.status==0){
                    Intent intent = new Intent(mContext, PayOrderActivity.class);
                    intent.putExtra("orderId",item.orderId);
                    intent.putExtra("orderPrice",item.price);
                    mContext.startActivity(intent);
                }
            }
        });

    }

    private OnFenxiangClickListener onFenxiangClickListener;

    public void setOnFenxiangClickListener(OnFenxiangClickListener onFenxiangClickListener) {
        this.onFenxiangClickListener = onFenxiangClickListener;
    }

    public interface OnFenxiangClickListener{
        void onFenxiangClick(int type,String mobile,String orderId,String status);
    }
}
