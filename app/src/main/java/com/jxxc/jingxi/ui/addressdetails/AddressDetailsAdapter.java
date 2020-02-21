package com.jxxc.jingxi.ui.addressdetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.AddressEntity;

import java.util.List;

public class AddressDetailsAdapter extends BaseAdapter {
    private Context context;
    private List<AddressEntity> list;

    public AddressDetailsAdapter(Context context){
        this.context=context;
    }

    public void setData(List<AddressEntity> list){
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
            convertView = LayoutInflater.from(context).inflate(R.layout.address_details_adapter,null);
            holder.tv_address_name = convertView.findViewById(R.id.tv_address_name);
            holder.tv_address_details = convertView.findViewById(R.id.tv_address_details);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        AddressEntity data = list.get(position);
        holder.tv_address_name.setText(data.getName());
        holder.tv_address_details.setText(data.getAddress());
        return convertView;
    }

    class ViewHolder{
        TextView tv_address_name;
        TextView tv_address_details;
    }
}
