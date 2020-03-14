package com.jxxc.jingxi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.RecommendComboInfoEntity;
import com.jxxc.jingxi.utils.GlideImgManager;

import java.text.DecimalFormat;
import java.util.List;

public class RecommendSetMealAdapter extends BaseAdapter {
    private Context context;
    private int defaultSelection=0;
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
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        RecommendComboInfoEntity data = list.get(position);
        GlideImgManager.loadRectangleImage(context, data.imgUrl, holder.iv_recommend_icon);
        holder.tv_recommend_name.setText(data.comboName);
        holder.tv_recommend_context.setText(data.comboComment);
        holder.tv_recommend_money.setText("￥"+new DecimalFormat("0.00").format(data.totalPrice));
        holder.tv_recommend_num.setText("已售"+data.salesVolume);
        return convertView;
    }

    class ViewHolder{
        ImageView iv_recommend_icon;
        TextView tv_recommend_name;
        TextView tv_recommend_context;
        TextView tv_recommend_money;
        TextView tv_recommend_num;
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
