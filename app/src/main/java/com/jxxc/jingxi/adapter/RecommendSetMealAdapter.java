package com.jxxc.jingxi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.RecommendComboInfoEntity;
import com.jxxc.jingxi.utils.GlideImgManager;
import com.jxxc.jingxi.utils.ZQImageViewRoundOval;

import java.text.DecimalFormat;
import java.util.List;

public class RecommendSetMealAdapter extends BaseAdapter {
    private Context context;
    private int defaultSelection=-1;
    private List<RecommendComboInfoEntity> list;
    private int type;

    public RecommendSetMealAdapter(Context context){
        this.context=context;
    }

    public void setData(List<RecommendComboInfoEntity> list,int type){
        this.list = list;
        this.type = type;
    }

    @Override
    public int getCount() {
        int num;
        if (type==1){
            num = 2;
        }else{
            num = list.size();
        }
        return num;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.recommend_set_meal_adapter,null);
            holder.iv_recommend_icon = convertView.findViewById(R.id.iv_recommend_icon);
            holder.tv_recommend_name = convertView.findViewById(R.id.tv_recommend_name);
            holder.tv_recommend_context = convertView.findViewById(R.id.tv_recommend_context);
            holder.tv_recommend_money = convertView.findViewById(R.id.tv_recommend_money);
            holder.tv_recommend_num = convertView.findViewById(R.id.tv_recommend_num);
            holder.tv_fuwu_name = convertView.findViewById(R.id.tv_fuwu_name);
            holder.tv_fuwu_time = convertView.findViewById(R.id.tv_fuwu_time);
            holder.tv_fuwu_money = convertView.findViewById(R.id.tv_fuwu_money);
            holder.ll_fuwu_detail = convertView.findViewById(R.id.ll_fuwu_detail);
            holder.ll_fuwu_item = convertView.findViewById(R.id.ll_fuwu_item);
            holder.iv_fuwu_item = convertView.findViewById(R.id.iv_fuwu_item);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        RecommendComboInfoEntity data = list.get(position);
        holder.iv_recommend_icon.setType(ZQImageViewRoundOval.TYPE_ROUND);
        holder.iv_recommend_icon.setRoundRadius(20);//矩形凹行大小
        GlideImgManager.loadRectangleImage(context, data.imgUrl, holder.iv_recommend_icon);
        holder.tv_recommend_name.setText(data.comboName);
        if (data.comboComment.length()>20){
            holder.tv_recommend_context.setText(data.comboComment.substring(0,20)+"...");
        }else{
            holder.tv_recommend_context.setText(data.comboComment+"...");
        }
        holder.tv_recommend_money.setText("￥"+new DecimalFormat("0.00").format(data.totalPrice));
        holder.tv_recommend_num.setText("已售"+data.salesVolume);
        holder.tv_fuwu_name.setText(data.comboComment);
        holder.tv_fuwu_time.setText(data.serviceHours);
        String carType1 = "";//1-SUV 2-轿车 3-MPV
        String carType2 = "";//1-SUV 2-轿车 3-MPV
        String carType3 = "";//1-SUV 2-轿车 3-MPV
        for (int i=0;i<data.carTypePrices.size();i++){
            if (data.carTypePrices.get(i).carTypeId==1){
                carType1 = "SUV" + data.carTypePrices.get(i).totalPrice+"元";
            }else if (data.carTypePrices.get(i).carTypeId==2){
                carType2 = "轿车" + data.carTypePrices.get(i).totalPrice+"元";
            }else{
                carType3 = "MPV" + data.carTypePrices.get(i).totalPrice+"元";
            }
        }
        holder.tv_fuwu_money.setText(carType1+" "+" "+carType2+" "+carType3);

        final ViewHolder finalHolder = holder;
        holder.ll_fuwu_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (finalHolder.ll_fuwu_detail.getVisibility()==View.VISIBLE){
                    finalHolder.ll_fuwu_detail.setVisibility(View.GONE);
                }else{
                    finalHolder.ll_fuwu_detail.setVisibility(View.VISIBLE);
                }
            }
        });

        if (position == defaultSelection) {// 选中时设置单纯颜色
            //holder.iv_fuwu_item.setBackgroundResource(R.mipmap.icon_38);
        } else {// 未选中时设置selector
            //holder.iv_fuwu_item.setBackgroundResource(R.mipmap.icon_38_24);
        }
        return convertView;
    }

    class ViewHolder{
        ZQImageViewRoundOval iv_recommend_icon;
        TextView tv_recommend_name;
        TextView tv_recommend_context;
        TextView tv_recommend_money;
        TextView tv_recommend_num;
        TextView tv_fuwu_name;
        TextView tv_fuwu_time;
        TextView tv_fuwu_money;
        LinearLayout ll_fuwu_detail;
        LinearLayout ll_fuwu_item;
        LinearLayout ll_fuwu_select;
        CheckBox iv_fuwu_item;
    }

/**
     * @param position
     * ���ø���״̬��item
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
