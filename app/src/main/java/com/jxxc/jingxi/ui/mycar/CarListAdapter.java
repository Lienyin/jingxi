package com.jxxc.jingxi.ui.mycar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.CarListEntity;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.ui.addcar.AddCarActivity;
import com.jxxc.jingxi.utils.GlideImgManager;

import java.util.List;

public class CarListAdapter extends BaseAdapter {
    private Context context;
    private List<CarListEntity> list;
    private String type="";

    public CarListAdapter(Context context,String type){
        this.context=context;
        this.type=type;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
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
        final CarListEntity data = list.get(position);
        GlideImgManager.loadCircleImage(context, data.brandIcon, holder.iv_car_logo);
        holder.tv_car_paizhao.setText(data.carNum);
        if (data.isDefault == 1){
            holder.tv_car_moren.setVisibility(View.VISIBLE);
        }else{
            holder.tv_car_moren.setVisibility(View.GONE);
        }
        holder.tv_car_name.setText(data.brandName+"  "+data.typeName);
        holder.tv_car_color_name.setText("颜色");
        if (data.color==1){
            holder.tv_car_color.setBackgroundResource(R.drawable.car_color_1);
        }else if (data.color==2){
            holder.tv_car_color.setBackgroundResource(R.drawable.car_color_2);
        }else if (data.color==3){
            holder.tv_car_color.setBackgroundResource(R.drawable.car_color_3);
        }else if (data.color==4){
            holder.tv_car_color.setBackgroundResource(R.drawable.car_color_4);
        }else if (data.color==5){
            holder.tv_car_color.setBackgroundResource(R.drawable.car_color_5);
        }else if (data.color==6){
            holder.tv_car_color.setBackgroundResource(R.drawable.car_color_6);
        }else if (data.color==7){
            holder.tv_car_color.setBackgroundResource(R.drawable.car_color_7);
        }else if (data.color==8){
            holder.tv_car_color.setBackgroundResource(R.drawable.car_color_8);
        }else if (data.color==9){
            holder.tv_car_color.setBackgroundResource(R.drawable.car_color_9);
        }else if (data.color==10){
            holder.tv_car_color.setBackgroundResource(R.drawable.car_color_10);
        }else{
            holder.tv_car_color.setBackgroundResource(R.drawable.car_color_8);
        }

        holder.tv_car_updata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //编辑
                Intent intent = new Intent((Activity) context, AddCarActivity.class);
                intent.putExtra("carData",data);
                context.startActivity(intent);
            }
        });
        holder.tv_car_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //删除
                onFenxiangClickListener.onFenxiangClick(data.carNum,null);
            }
        });
        if ("1".equals(type)){
            holder.tv_car_use.setVisibility(View.VISIBLE);
        }else{
            holder.tv_car_use.setVisibility(View.GONE);
        }
        holder.tv_car_use.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //使用
                onFenxiangClickListener.onFenxiangClick("",list.get(position));
            }
        });
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

    private OnFenxiangClickListener onFenxiangClickListener;

    public void setOnFenxiangClickListener(OnFenxiangClickListener onFenxiangClickListener) {
        this.onFenxiangClickListener = onFenxiangClickListener;
    }

    public interface OnFenxiangClickListener{
        void onFenxiangClick(String carNum,CarListEntity data);
    }
}
