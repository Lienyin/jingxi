package com.jxxc.jingxi.ui.main.msg;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.MessageListEntity;
import com.jxxc.jingxi.entity.backparameter.MyOrderEntity;

import java.util.List;

/**
 * @authorfeisher on 2017/11/21.14:58
 * email:458079442@qq.com
 */

public class MsgAdapter extends BaseQuickAdapter<MessageListEntity, BaseViewHolder> {

    public MsgAdapter(@LayoutRes int layoutResId, @Nullable List<MessageListEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final MessageListEntity item) {
        helper.setText(R.id.tv_msg_name, item.messageTopic);
        helper.setText(R.id.tv_msg_time, item.sendTime);
        helper.setText(R.id.tv_msg_text, item.content);
    }

    private OnFenxiangClickListener onFenxiangClickListener;

    public void setOnFenxiangClickListener(OnFenxiangClickListener onFenxiangClickListener) {
        this.onFenxiangClickListener = onFenxiangClickListener;
    }

    public interface OnFenxiangClickListener{
        void onFenxiangClick(String orderId, int type);
    }
}
