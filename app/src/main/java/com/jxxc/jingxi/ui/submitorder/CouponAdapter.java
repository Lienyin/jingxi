package com.jxxc.jingxi.ui.submitorder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.MyCoupon;
import com.jxxc.jingxi.utils.AppUtils;

import java.text.DecimalFormat;
import java.util.List;

public class CouponAdapter extends BaseAdapter {
    private Context context;
    private int defaultSelection=-1;
    private List<MyCoupon> list;
    private double orderMoney;

    public CouponAdapter(Context context){
        this.context=context;
    }

    public void setData(List<MyCoupon> list){
        this.list = list;
    }

    //订单金额
    public void setOrderMoney(double orderMoney){
        this.orderMoney = orderMoney;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.coupon_adapter,null);
            holder.tv_coupon_money = convertView.findViewById(R.id.tv_coupon_money);
            holder.tv_coupon_memo = convertView.findViewById(R.id.tv_coupon_memo);
            holder.iv_coupon = convertView.findViewById(R.id.iv_coupon);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        MyCoupon data = list.get(position);
        //优惠券类型 0无门槛减N 1满N减N 2折扣券
        if (data.couponRuleType==0){
            holder.tv_coupon_money.setText(new DecimalFormat("0.00").format(data.money)+"元优惠券");
            holder.tv_coupon_memo.setText("");
        }else if (data.couponRuleType == 1){
            holder.tv_coupon_money.setText(new DecimalFormat("0.00").format(data.money)+"元优惠券");
            holder.tv_coupon_memo.setText("(满"+new DecimalFormat("0.00").format(data.doorsillMoney)+"元使用)");
        }else{
            holder.tv_coupon_money.setText(new DecimalFormat("0.00").format(data.discount)+"折优惠券");
            if (data.doorsillMoney>0){
                holder.tv_coupon_memo.setText("(满"+new DecimalFormat("0.00").format(data.doorsillMoney)+"元使用)");
            }else{
                holder.tv_coupon_memo.setText("");
            }
        }
        //如果订单金额大于门槛金额
        if (orderMoney>=data.doorsillMoney){
            holder.tv_coupon_money.setTextColor(context.getResources().getColor(R.color.public_all));
            holder.tv_coupon_memo.setTextColor(context.getResources().getColor(R.color.black555));
            final ViewHolder finalHolder = holder;
            if (position == defaultSelection) {// 选中时设置单纯颜色
                holder.iv_coupon.setSelected(true);
            } else {// 未选中时设置selector
                holder.iv_coupon.setSelected(false);
            }
            data.setForbidden(false);
        }else{
            holder.tv_coupon_money.setTextColor(context.getResources().getColor(R.color.black999));
            holder.tv_coupon_memo.setTextColor(context.getResources().getColor(R.color.black999));
            data.setForbidden(true);
        }
        return convertView;
    }

    class ViewHolder{
        TextView tv_coupon_money;
        TextView tv_coupon_memo;
        ImageView iv_coupon;
    }

    /**
     * @param position
     * 设置高亮状态的item
     */
    public void setSelectPosition(int position) {
        if (!(position < 0 || position > list.size())) {
            defaultSelection = position;
            notifyDataSetChanged();
        }else{
            defaultSelection = -1;
            notifyDataSetChanged();
        }
    }
}
