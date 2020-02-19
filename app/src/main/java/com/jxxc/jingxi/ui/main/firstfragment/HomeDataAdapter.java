package com.jxxc.jingxi.ui.main.firstfragment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.ProductInfoEntity;
import com.jxxc.jingxi.utils.GlideImgManager;

import java.util.List;

public class HomeDataAdapter extends BaseAdapter {
    private Context context;
    private List<ProductInfoEntity.Combo.ProductInfo> list;
    private int defaultSelection = -1;

    public HomeDataAdapter(Context context){
        this.context=context;
    }

    public void setData(List<ProductInfoEntity.Combo.ProductInfo> list){
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
            convertView = LayoutInflater.from(context).inflate(R.layout.home_data_adapter,null);
            holder.iv_home_fuwu = convertView.findViewById(R.id.iv_home_fuwu);
            holder.tv_home_fuwu_text = convertView.findViewById(R.id.tv_home_fuwu_text);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        ProductInfoEntity.Combo.ProductInfo data = list.get(position);
        holder.tv_home_fuwu_text.setText(data.productName);

        if (position == defaultSelection) {// 选中时设置单纯颜色
            GlideImgManager.loadImage(context, data.selectedImg, holder.iv_home_fuwu);
        } else {// 未选中时设置selector
            GlideImgManager.loadImage(context, data.imgUrl, holder.iv_home_fuwu);
        }
        return convertView;
    }

    class ViewHolder{
        ImageView iv_home_fuwu;
        TextView tv_home_fuwu_text;
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