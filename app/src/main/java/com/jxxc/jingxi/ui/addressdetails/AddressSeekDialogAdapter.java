package com.jxxc.jingxi.ui.addressdetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.AddressSeek;

import java.util.List;

//（废弃）
public class AddressSeekDialogAdapter extends BaseAdapter {

    private Context context;
    private List<AddressSeek> list;
    public AddressSeekDialogAdapter(Context context, List<AddressSeek> list){
        this.context = context;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.address_seek_dialog_adapter,null);
            holder.tv_address_name = (TextView) convertView.findViewById(R.id.tv_address_name);
            holder.tv_address_distance = (TextView) convertView.findViewById(R.id.tv_address_distance);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        AddressSeek data = list.get(position);
        holder.tv_address_name.setText(data.name);
        return convertView;
    }

    class ViewHolder{
        TextView tv_address_name;
        TextView tv_address_distance;
    }
}
