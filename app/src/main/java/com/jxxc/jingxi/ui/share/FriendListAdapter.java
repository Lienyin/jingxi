package com.jxxc.jingxi.ui.share;


import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.GetInfoEntity;

import java.util.List;

/**
 * Created by 31373 on 2018/5/25.
 */

public class FriendListAdapter extends BaseAdapter {

    private List<GetInfoEntity.Customer> list;
    private Context context;
    private Typeface typeFace;//自定义字体
    public FriendListAdapter(Context context, List<GetInfoEntity.Customer> list) {
        this.context = context;
        this.list = list;
        //typeFace = Typeface.createFromAsset(context.getAssets(), "fonts/DIN-MEDIUM_1.OTF");//自定义字体
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
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_share_adapter,null);
            holder.tv_share_phone = (TextView) convertView.findViewById(R.id.tv_share_phone);
            holder.tv_share_time = (TextView) convertView.findViewById(R.id.tv_share_time);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        GetInfoEntity.Customer data = list.get(position);
        holder.tv_share_phone.setTypeface(typeFace);
        holder.tv_share_time.setTypeface(typeFace);

        holder.tv_share_phone.setText(data.mobile);
        holder.tv_share_time.setText(data.date);
        return convertView;
    }

    class ViewHolder{
        TextView tv_share_phone;
        TextView tv_share_time;
    }
}
