package com.jxxc.jingxi.ui.mycar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.CarListEntity;

import java.util.List;

public class CarListAdapter extends BaseAdapter {
    private Context context;
    private List<CarListEntity> list;

    public CarListAdapter(Context context){
        this.context=context;
    }

    public void setData(List<CarListEntity> list){
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
            convertView = LayoutInflater.from(context).inflate(R.layout.car_list_adapter,null);
            holder.iv_car_logo = convertView.findViewById(R.id.iv_car_logo);
            holder.tv_car_paizhao = convertView.findViewById(R.id.tv_car_paizhao);
            holder.tv_car_moren = convertView.findViewById(R.id.tv_car_moren);
            holder.tv_car_name = convertView.findViewById(R.id.tv_car_name);
            holder.tv_car_color_name = convertView.findViewById(R.id.tv_car_color_name);
            holder.tv_car_color = convertView.findViewById(R.id.tv_car_color);
            holder.tv_car_updata = convertView.findViewById(R.id.tv_car_updata);
            holder.tv_car_delete = convertView.findViewById(R.id.tv_car_delete);
            holder.tv_car_use = convertView.findViewById(R.id.tv_car_use);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        return convertView;
    }

    class ViewHolder{
        ImageView iv_car_logo;
        TextView tv_car_paizhao;
        TextView tv_car_moren;
        TextView tv_car_name;
        TextView tv_car_color_name;
        TextView tv_car_color;
        TextView tv_car_updata;
        TextView tv_car_delete;
        TextView tv_car_use;
    }
}
