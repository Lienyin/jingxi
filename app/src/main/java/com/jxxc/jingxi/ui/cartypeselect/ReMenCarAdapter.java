package com.jxxc.jingxi.ui.cartypeselect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.BandAndTypeEntity;
import com.jxxc.jingxi.utils.GlideImgManager;

import java.util.List;

public class ReMenCarAdapter extends BaseAdapter {
    private Context context;
    private int defaultSelection=0;
    private List<BandAndTypeEntity.CarBrand> list;

    public ReMenCarAdapter(Context context){
        this.context=context;
    }

    public void setData(List<BandAndTypeEntity.CarBrand> list){
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
            convertView = LayoutInflater.from(context).inflate(R.layout.re_men_car_adapter,null);
            holder.iv_car_icon = convertView.findViewById(R.id.iv_car_icon);
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        BandAndTypeEntity.CarBrand data = list.get(position);
        GlideImgManager.loadCircleImage(context, data.brandIcon, holder.iv_car_icon);
        holder.tv_name.setText(data.brandName);
        return convertView;
    }

    class ViewHolder{
        ImageView iv_car_icon;
        TextView tv_name;
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
