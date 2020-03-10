package com.jxxc.jingxi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.RecommendCompanyListEntity;
import com.jxxc.jingxi.utils.GlideImgManager;

import java.util.List;

public class ShopRecommendAdapter extends BaseAdapter {
    private Context context;
    private int defaultSelection=0;
    private List<RecommendCompanyListEntity> list;

    public ShopRecommendAdapter(Context context){
        this.context=context;
    }

    public void setData(List<RecommendCompanyListEntity> list){
        this.list = list;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.shop_recommend_adapter,null);
            holder.iv_shop_icon = convertView.findViewById(R.id.iv_shop_icon);
            holder.tv_shop_name = convertView.findViewById(R.id.tv_shop_name);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        RecommendCompanyListEntity data = list.get(position);
        GlideImgManager.loadRectangleImage(context, data.imgUrl, holder.iv_shop_icon);
        holder.tv_shop_name.setText(data.companyName);
        return convertView;
    }

    class ViewHolder{
        ImageView iv_shop_icon;
        TextView tv_shop_name;
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
