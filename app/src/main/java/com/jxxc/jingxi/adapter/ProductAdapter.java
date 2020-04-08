package com.jxxc.jingxi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.ProductIdListEntity;
import com.jxxc.jingxi.entity.backparameter.RecommendComboInfoEntity;
import com.jxxc.jingxi.utils.GlideImgManager;
import com.jxxc.jingxi.utils.ZQImageViewRoundOval;

import java.text.DecimalFormat;
import java.util.List;

public class ProductAdapter extends BaseAdapter {
    private Context context;
    private int defaultSelection=0;
    private List<ProductIdListEntity> list;

    public ProductAdapter(Context context){
        this.context=context;
    }

    public void setData(List<ProductIdListEntity> list){
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
            convertView = LayoutInflater.from(context).inflate(R.layout.product_adapter,null);
            holder.iv_product_icon = convertView.findViewById(R.id.iv_product_icon);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        ProductIdListEntity data = list.get(position);
        holder.iv_product_icon.setType(ZQImageViewRoundOval.TYPE_ROUND);
        holder.iv_product_icon.setRoundRadius(20);//矩形凹行大小
        GlideImgManager.loadRectangleImage(context, data.imgUrl, holder.iv_product_icon);
        return convertView;
    }

    class ViewHolder{
        ZQImageViewRoundOval iv_product_icon;
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
