package com.jxxc.jingxi.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.jxxc.jingxi.R;
import com.jxxc.jingxi.adapter.CityAdapter;
import com.jxxc.jingxi.adapter.DistrictAdapter;
import com.jxxc.jingxi.adapter.ProvinceAdapter;
import com.jxxc.jingxi.entity.backparameter.AreaListEntity;
import com.jxxc.jingxi.entity.backparameter.CityEntity;
import com.jxxc.jingxi.entity.backparameter.DistrictEntity;
import com.jxxc.jingxi.entity.backparameter.ProvinceEntity;

import java.util.ArrayList;
import java.util.List;


public class PopFiltrateCity extends PopupWindow {
    private View conentView;
    private ListView lv_filtrate_city1,lv_filtrate_city2,lv_filtrate_city3;
    private Context mContext;
    private List<AreaListEntity> allData = new ArrayList<>();//省市区总数据
    private List<ProvinceEntity> provinceEntityList = new ArrayList<>();//省份
    private List<DistrictEntity> districtEntityList = new ArrayList<>();//区域
    private List<CityEntity> cityEntityList = new ArrayList<>();//城市
    private ProvinceAdapter provinceAdapter;
    private DistrictAdapter districtAdapter;
    private CityAdapter cityAdapter;

    public PopFiltrateCity(final Activity context) {
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.title_filtrate_city, null);
        int h = context.getWindowManager().getDefaultDisplay().getHeight();
        int w = context.getWindowManager().getDefaultDisplay().getWidth();
        // 设置SelectPicPopupWindow的View
        this.setContentView(conentView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(w);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        this.setOutsideTouchable(false);
        // 刷新状态
        this.update();
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0000000000);
        // 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        this.setBackgroundDrawable(dw);
        // mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        //this.setAnimationStyle(R.style.AnimationPreview);
        //this.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        //this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        initView();
    }

    private void initView(){
        lv_filtrate_city1 = conentView.findViewById(R.id.lv_filtrate_city1);
        lv_filtrate_city2 = conentView.findViewById(R.id.lv_filtrate_city2);
        lv_filtrate_city3 = conentView.findViewById(R.id.lv_filtrate_city3);

        //设置区域数据
        districtAdapter = new DistrictAdapter(mContext);
        districtAdapter.setData(districtEntityList);
        lv_filtrate_city2.setAdapter(districtAdapter);
        //设置城市数据
        cityAdapter = new CityAdapter(mContext);
        cityAdapter.setData(cityEntityList);
        lv_filtrate_city3.setAdapter(cityAdapter);

        lv_filtrate_city1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (districtEntityList.size()>0){
                    districtEntityList.clear();
                }
                for (int i=0;i<allData.size();i++){
                    if (provinceEntityList.get(position).areaId.equals(allData.get(i).parentId)){
                        DistrictEntity districtEntity = new DistrictEntity();
                        districtEntity.setName(allData.get(i).areaName);
                        districtEntity.setAreaId(allData.get(i).areaId);
                        districtEntity.setAreaCode(allData.get(i).areaCode);
                        districtEntityList.add(districtEntity);
                    }
                }
                districtAdapter.setData(districtEntityList);
                districtAdapter.notifyDataSetChanged();
            }
        });
        lv_filtrate_city2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (cityEntityList.size()>0){
                    cityEntityList.clear();
                }
                for (int i=0;i<allData.size();i++){
                    if (districtEntityList.get(position).areaId.equals(allData.get(i).parentId)){
                        CityEntity cityEntity = new CityEntity();
                        cityEntity.setName(allData.get(i).areaName);
                        cityEntity.setAreaId(allData.get(i).areaId);
                        cityEntity.setAreaCode(allData.get(i).areaCode);
                        cityEntityList.add(cityEntity);
                    }
                }
                cityAdapter.setData(cityEntityList);
                cityAdapter.notifyDataSetChanged();
            }
        });
        lv_filtrate_city3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                onFenxiangClickListener.onFenxiangClick(cityEntityList.get(position).areaId,
                        cityEntityList.get(position).name);
            }
        });
    }

    public void showPopupWindow(View parent, List<AreaListEntity> data, List<ProvinceEntity> pList) {
        if (!this.isShowing()) {
            allData = data;
            provinceEntityList = pList;
            //s设置省份数据
            provinceAdapter = new ProvinceAdapter(mContext);
            provinceAdapter.setData(pList);
            lv_filtrate_city1.setAdapter(provinceAdapter);

            this.showAsDropDown(parent, parent.getLayoutParams().width, 0);
        } else {
            this.dismiss();
        }
    }

    private OnFenxiangClickListener onFenxiangClickListener;

    public void setOnFenxiangClickListener(OnFenxiangClickListener onFenxiangClickListener) {
        this.onFenxiangClickListener = onFenxiangClickListener;
    }

    public interface OnFenxiangClickListener{
        void onFenxiangClick(String type,String cityName);
    }
}
