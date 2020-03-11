package com.jxxc.jingxi.ui.shopdetails;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.AppointmentListEntity;
import com.jxxc.jingxi.entity.backparameter.CompanyDetailsEntity;
import com.jxxc.jingxi.utils.GlideImgManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TimeAdapter extends BaseAdapter {
    private Context context;
    private int defaultSelection=-1;
    private int serveType = 0;//0上门 1到店
    private List<AppointmentListEntity> list;

    public TimeAdapter(Context context,int serveType){
        this.context=context;
        this.serveType=serveType;
    }

    public void setData(List<AppointmentListEntity> list){
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
            convertView = LayoutInflater.from(context).inflate(R.layout.time_adapter,null);
            holder.tv_time_num = convertView.findViewById(R.id.tv_time_num);
            holder.tv_time_name = convertView.findViewById(R.id.tv_time_name);
            holder.ll_time_bg = convertView.findViewById(R.id.ll_time_bg);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        AppointmentListEntity data = list.get(position);
        holder.tv_time_name.setText(data.title);
        if (data.isFull==1){//是否以预约满 1是0否
            data.setForbidden(true);
            holder.tv_time_name.setTextColor(context.getResources().getColor(R.color.set_bg));
            if (serveType == 1){
                if (data.num>0){
                    holder.tv_time_num.setText(Html.fromHtml("<font color=\"#cccccc\">时间已过</font>"));
                }else{
                    holder.tv_time_num.setText(Html.fromHtml("<font color=\"#cccccc\">已预约满</font>"));
                }
            }else{
                holder.tv_time_num.setText(Html.fromHtml("<font color=\"#cccccc\">时间已过</font>"));
            }
        }else {
            data.setForbidden(false);
            holder.tv_time_name.setTextColor(context.getResources().getColor(R.color.black));
            if (serveType == 1){
                holder.tv_time_num.setText(Html.fromHtml("尚余<font color=\"#00B487\">"+data.num+"</font>个"));
            }else{
                holder.tv_time_num.setText(Html.fromHtml("可预约"));
            }
        }
        if (position == defaultSelection) {// 选中时设置单纯颜色
            holder.ll_time_bg.setSelected(true);
        } else {// 未选中时设置selector
            holder.ll_time_bg.setSelected(false);
        }
        return convertView;
    }

    class ViewHolder{
        TextView tv_time_num;
        TextView tv_time_name;
        LinearLayout ll_time_bg;
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
