package com.jxxc.jingxi.ui.addcar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.ColorEntity;

import java.util.List;

public class AddCarAdapter extends BaseAdapter {
    private Context context;
    private int defaultSelection=-1;
    private List<ColorEntity.Color> list;

    public AddCarAdapter(Context context){
        this.context=context;
    }

    public void setData(List<ColorEntity.Color> list){
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
            convertView = LayoutInflater.from(context).inflate(R.layout.add_car_adapter,null);
            holder.view_bg = convertView.findViewById(R.id.view_bg);
            holder.ll_car_type = convertView.findViewById(R.id.ll_car_type);
            holder.color_name = convertView.findViewById(R.id.color_name);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        ColorEntity.Color data = list.get(position);
        if (data.color==1){
            holder.view_bg.setBackgroundResource(R.drawable.car_color_1);
            holder.color_name.setText("绿色");
        }else if (data.color==2){
            holder.view_bg.setBackgroundResource(R.drawable.car_color_2);
            holder.color_name.setText("蓝色");
        }else if (data.color==3){
            holder.view_bg.setBackgroundResource(R.drawable.car_color_3);
            holder.color_name.setText("橙色");
        }else if (data.color==4){
            holder.view_bg.setBackgroundResource(R.drawable.car_color_4);
            holder.color_name.setText("黄色");
        }else if (data.color==5){
            holder.view_bg.setBackgroundResource(R.drawable.car_color_5);
            holder.color_name.setText("红色");
        }else if (data.color==6){
            holder.view_bg.setBackgroundResource(R.drawable.car_color_6);
            holder.color_name.setText("黑色");
        }else if (data.color==7){
            holder.view_bg.setBackgroundResource(R.drawable.car_color_7);
            holder.color_name.setText("灰色");
        }else if (data.color==8){
            holder.view_bg.setBackgroundResource(R.drawable.car_color_8);
            holder.color_name.setText("白色");
        }else if (data.color==9){
            holder.view_bg.setBackgroundResource(R.drawable.car_color_9);
            holder.color_name.setText("紫色");
        }else if (data.color==10){
            holder.view_bg.setBackgroundResource(R.drawable.car_color_10);
            holder.color_name.setText("棕色");
        }else{
            holder.view_bg.setBackgroundResource(R.drawable.car_color_8);
            holder.color_name.setText("其他");
        }

        if (position == defaultSelection) {// 选中时设置单纯颜色
            holder.ll_car_type.setBackgroundResource(R.drawable.car_color_yes);
        } else {// 未选中时设置selector
            holder.ll_car_type.setBackgroundResource(R.drawable.car_color_no);
        }
        return convertView;
    }

    class ViewHolder{
        View view_bg;
        LinearLayout ll_car_type;
        TextView color_name;
    }

    /**
     * @param position
     * 选中状态
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
