package com.jxxc.jingxi.ui.shoplist;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxxc.jingxi.entity.backparameter.ShopListEntity;


import java.util.List;

/**
 * @authorfeisher on 2017/11/21.14:58
 * email:458079442@qq.com
 */

public class ShopListAdapter extends BaseQuickAdapter<ShopListEntity, BaseViewHolder> {

    public ShopListAdapter(@LayoutRes int layoutResId, @Nullable List<ShopListEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ShopListEntity item) {
        //helper.setText(R.id.tv_car_info_name, item.siteName);
    }

    private OnFenxiangClickListener onFenxiangClickListener;

    public void setOnFenxiangClickListener(OnFenxiangClickListener onFenxiangClickListener) {
        this.onFenxiangClickListener = onFenxiangClickListener;
    }

    public interface OnFenxiangClickListener{
        void onFenxiangClick(double lat, double lng, String siteName, int type, int isAction);
    }
}
