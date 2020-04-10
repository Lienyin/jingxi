package com.jxxc.jingxi.ui.shopdetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.CompanyDetailsEntity;
import com.jxxc.jingxi.utils.GlideImgManager;

import java.util.List;

public class TechnicianAdapter extends BaseAdapter {
    private Context context;
    private int defaultSelection=0;
    private List<CompanyDetailsEntity.Jishi> list;

    public TechnicianAdapter(Context context){
        this.context=context;
    }

    public void setData(List<CompanyDetailsEntity.Jishi> list){
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
            convertView = LayoutInflater.from(context).inflate(R.layout.technician_adapter,null);
            holder.iv_jishi_hand = convertView.findViewById(R.id.iv_jishi_hand);
            holder.tv_jishi_name = convertView.findViewById(R.id.tv_jishi_name);
            holder.tv_hao_ping_number = convertView.findViewById(R.id.tv_hao_ping_number);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        CompanyDetailsEntity.Jishi data = list.get(position);
        GlideImgManager.loadCircleImage(context, data.avatar, holder.iv_jishi_hand);
        if (data.userName.length()>5){
            holder.tv_jishi_name.setText(data.userName.substring(0,4)+"...");
        }else{
            holder.tv_jishi_name.setText(data.userName);
        }
        holder.tv_hao_ping_number.setText(data.orderNum);
        return convertView;
    }

    class ViewHolder{
        ImageView iv_jishi_hand;
        TextView tv_jishi_name;
        TextView tv_hao_ping_number;
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
