package com.jxxc.jingxi.adapter;

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
        //订单状态可能为空 0 待支付 1 已支付 2 待接单 3 已接单待服务 4 服务中 5 服务已完成 6取消订单
        if (item.orderStatus==5){
            helper.setBackgroundRes(R.id.tv_msg_icon,R.mipmap.icon_user_29);
        }else if (item.orderStatus==6){
            helper.setBackgroundRes(R.id.tv_msg_icon,R.mipmap.icon_user_33);
        }else{
            helper.setBackgroundRes(R.id.tv_msg_icon,R.mipmap.icon_user_31);
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
