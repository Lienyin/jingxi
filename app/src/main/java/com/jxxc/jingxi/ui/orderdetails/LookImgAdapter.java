package com.jxxc.jingxi.ui.orderdetails;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.dialog.ActivityDialog;
import com.jxxc.jingxi.utils.GlideImgManager;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

public class LookImgAdapter extends PagerAdapter{

    private Context context;
    private List<String> list;
    private int pos = 0;
    public LookImgAdapter(Context context, List<String> list,int pos) {
        this.context=context;
        this.list=list;
        this.pos=pos;

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(context));

    }

    @Override
    public int getCount() {

        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView=new ImageView(context);
        //ImageLoader.getInstance().displayImage(list.get(position%list.size()),imageView);
        GlideImgManager.loadRectangleSiteImage(context, list.get(position%list.size()), imageView);
        //GlideImgManager.loadRectangleSiteImage(context, list.get(pos), imageView);

        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        container.addView(imageView);
        StyledDialog.dismissLoading();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFenxiangClickListener.onFenxiangClick();
            }
        });
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    private OnFenxiangClickListener onFenxiangClickListener;

    public void setOnFenxiangClickListener(OnFenxiangClickListener onFenxiangClickListener) {
        this.onFenxiangClickListener = onFenxiangClickListener;
    }

    public interface OnFenxiangClickListener {
        void onFenxiangClick();
    }
}
