package com.jxxc.jingxi.ui.recharge;


import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.RechargeSet;

import java.text.DecimalFormat;
import java.util.List;


/**
 * @explain 常见故障列表适配器
 * @author feisher.qq:458079442
 * @time 2017/11/7 17:08.
 */
public class RechargeAdapter extends BaseQuickAdapter<RechargeSet, BaseViewHolder> {

    public RechargeAdapter(@LayoutRes int layoutResId, @Nullable List<RechargeSet> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final RechargeSet item) {
        double v = (double) item.money;
        DecimalFormat format =new DecimalFormat("0.00");
        String showV = "";
        if (v%1 ==0) {
            showV = (int)v+"元";
        }else {
            showV = format.format(v)+"元";
        }

        helper.setText(R.id.tv_money,showV);
        helper.setTextColor(R.id.tv_money,item.isSelected? Color.WHITE: Color.GRAY);
        double gift = (double) item.gift;
        String showGift = "";
        if (gift%1 ==0) {
            showGift = (int)gift+"元";
        }else {
            showGift = format.format(gift)+"元";
        }
        helper.setText(R.id.tv_song,"送"+ showGift);
        helper.setTextColor(R.id.tv_song,item.isSelected? Color.WHITE: Color.GRAY);
        helper.setBackgroundRes(R.id.ll_bg,item.isSelected?R.drawable.shape_round_rect_teal:R.drawable.shape_round_rect_loop_gray);

    }


}