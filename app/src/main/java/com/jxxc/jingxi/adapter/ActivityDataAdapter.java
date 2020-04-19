package com.jxxc.jingxi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.ActivitiesEntity;
import com.jxxc.jingxi.entity.backparameter.RecommendComboInfoEntity;

import java.text.DecimalFormat;
import java.util.List;

public class ActivityDataAdapter extends BaseAdapter {
    private Context context;
    private int defaultSelection=0;
    private List<ActivitiesEntity> list;

    public ActivityDataAdapter(Context context){
        this.context=context;
    }

    public void setData(List<ActivitiesEntity> list){
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
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_data_adapter,null);
            holder.activity_name = convertView.findViewById(R.id.activity_name);
            holder.activity_memo = convertView.findViewById(R.id.activity_memo);
            holder.tv_activity_type = convertView.findViewById(R.id.tv_activity_type);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        ActivitiesEntity data = list.get(position);
        //活动类型1满减，3立减
        if (data.type==1){
            holder.tv_activity_type.setText("满减");
        }else{
            holder.tv_activity_type.setText("立减");
        }
        if (data.doorsillMoney>0){
            holder.activity_name.setText(data.activitiesName+"满减"+new DecimalFormat("0.00").format(data.money)+"元");
            holder.activity_memo.setText("(满"+new DecimalFormat("0.00").format(data.doorsillMoney)+"元使用)");
        }else{
            holder.activity_name.setText(data.activitiesName+"立减"+new DecimalFormat("0.00").format(data.money)+"元");
            holder.activity_memo.setText("");
        }
        return convertView;
    }

    class ViewHolder{
        TextView activity_name;
        TextView activity_memo;
        TextView tv_activity_type;
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
