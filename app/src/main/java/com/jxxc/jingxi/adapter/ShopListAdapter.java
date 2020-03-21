package com.jxxc.jingxi.adapter;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.ShopListEntity;
import com.jxxc.jingxi.entity.backparameter.companyListEntity;
import com.jxxc.jingxi.utils.AppUtils;
import com.jxxc.jingxi.utils.GlideImgManager;
import com.jxxc.jingxi.utils.ZQImageViewRoundOval;


import java.text.DecimalFormat;
import java.util.List;

/**
 * @authorfeisher on 2017/11/21.14:58
 * email:458079442@qq.com
 */

public class ShopListAdapter extends BaseQuickAdapter<companyListEntity, BaseViewHolder> {

    public ShopListAdapter(@LayoutRes int layoutResId, @Nullable List<companyListEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final companyListEntity item) {
        ZQImageViewRoundOval iv = helper.getView(R.id.iv_shop_mendian);
        iv.setType(ZQImageViewRoundOval.TYPE_ROUND);
        iv.setRoundRadius(20);//矩形凹行大小
        GlideImgManager.loadRectangleSiteImage(mContext, item.imgUrl,iv);
        helper.setText(R.id.tv_shop_list_name, item.companyName);
        helper.setText(R.id.tv_shop_list_pf, item.score);
        helper.setText(R.id.tv_shop_list_dd, item.orderNum);
        helper.setText(R.id.tv_shop_list_js, item.technicianNum);
        helper.setText(R.id.tv_shop_list_address, item.address);
        //计算距离distance
        String showDistance = "?? m";
        if (item.distance > 1000) {
            showDistance = new DecimalFormat("0.00").format(item.distance / 1000d) + " km";
        } else {
            showDistance = new DecimalFormat("0").format(item.distance) + " m";
        }
        helper.setText(R.id.tv_shop_jilu, showDistance);

        if (item.isBusiness==1){
            helper.setText(R.id.tv_shop_list_yy, "营业中");
        }else{
            helper.setText(R.id.tv_shop_list_yy, "休息中");
        }
        //加盟商类型 1直营 2加盟 3合作
        if (item.companyType==1){
            helper.setText(R.id.tv_shop_list_hz, "直营店");
        }else if (item.companyType==2){
            helper.setText(R.id.tv_shop_list_hz, "加盟店");
        }else{
            helper.setText(R.id.tv_shop_list_hz, "合作店");
        }

        helper.setOnClickListener(R.id.tv_shop_list_call, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppUtils.isEmpty(item.phonenumber)){
                    Toast.makeText(mContext,"空号",Toast.LENGTH_SHORT).show();
                }else{
                    AppUtils.callPhone(mContext,item.phonenumber);
                }
            }
        });
    }
}
