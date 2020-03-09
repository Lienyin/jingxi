package com.jxxc.jingxi.ui.main.secondfragment;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.CommissionListEntity;
import com.jxxc.jingxi.entity.backparameter.FindEntity;
import com.jxxc.jingxi.utils.GlideImgManager;

import java.util.List;

/**
 * @authorfeisher on 2017/11/21.14:58
 * email:458079442@qq.com
 */

public class FindListAdapter extends BaseQuickAdapter<FindEntity, BaseViewHolder> {

    public FindListAdapter(@LayoutRes int layoutResId, @Nullable List<FindEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final FindEntity item) {
        helper.setText(R.id.tv_find_title, item.title);
        helper.setText(R.id.tv_find_time, item.createTime);
        ImageView iv = helper.getView(R.id.iv_find_url);
        GlideImgManager.loadRectangleSiteImage(mContext, item.mediaUrl, iv);
    }
}
