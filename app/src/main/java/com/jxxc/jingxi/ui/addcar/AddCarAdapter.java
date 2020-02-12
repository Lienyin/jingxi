package com.jxxc.jingxi.ui.addcar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.ColorEntity;

import java.util.List;

public class AddCarAdapter extends BaseAdapter {
    private Context context;
    private int defaultSelection=0;
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
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        ColorEntity.Color data = list.get(position);
        holder.view_bg.setBackgroundColor(context.getResources().getColor(R.color.public_all));
        return convertView;
    }

    class ViewHolder{
        View view_bg;
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
