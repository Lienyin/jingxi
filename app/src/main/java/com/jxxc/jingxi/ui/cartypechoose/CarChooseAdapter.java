package com.jxxc.jingxi.ui.cartypechoose;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.BandAndTypeEntity;
import com.jxxc.jingxi.utils.GlideImgManager;

import java.util.ArrayList;
import java.util.List;

public class CarChooseAdapter extends BaseAdapter {

    private Context context;
    private int defaultSelection = -1;
    private List<BandAndTypeEntity.CarType> list = new ArrayList<>();

    public CarChooseAdapter(Context context){
        this.context=context;
    }

    public void setData(List<BandAndTypeEntity.CarType> list){
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
            convertView = LayoutInflater.from(context).inflate(R.layout.car_choose_adapter,null);
            holder.iv_car_type_icon = convertView.findViewById(R.id.iv_car_type_icon);
            holder.tv_car_type_name = convertView.findViewById(R.id.tv_car_type_name);
            holder.iv_car_type_s = convertView.findViewById(R.id.iv_car_type_s);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        BandAndTypeEntity.CarType data = list.get(position);
        GlideImgManager.loadImage(context, data.carTypeIcon, holder.iv_car_type_icon);
        holder.tv_car_type_name.setText(data.carTypeName);

        if (position == defaultSelection) {// 选中时设置单纯颜色
            holder.iv_car_type_s.setVisibility(View.VISIBLE);
        } else {// 未选中时设置selector
            holder.iv_car_type_s.setVisibility(View.GONE);
        }
        return convertView;
    }

    class ViewHolder{
        ImageView iv_car_type_icon;
        ImageView iv_car_type_s;
        TextView tv_car_type_name;
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
