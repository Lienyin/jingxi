package com.jxxc.jingxi.ui.main.firstfragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.adapter.HomeDataAdapter;
import com.jxxc.jingxi.dialog.XiaOrderDialog;
import com.jxxc.jingxi.entity.backparameter.AppointmentListEntity;
import com.jxxc.jingxi.entity.backparameter.BannerEntity;
import com.jxxc.jingxi.entity.backparameter.ProductInfoEntity;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.mvp.MVPBaseFragment;
import com.jxxc.jingxi.ui.mapjingsi.MapJingSiActivity;
import com.jxxc.jingxi.ui.maptest.MapTestActivity;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.utils.AppUtils;
import com.jxxc.jingxi.utils.MyImageView;
import com.jxxc.jingxi.utils.SPUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class FirstFragment extends MVPBaseFragment<FirseFramentContract.View, FirseFramentPresenter> implements View.OnClickListener, FirseFramentContract.View, SwipeRefreshLayout.OnRefreshListener {
    private Context context;
    private TextView tv_map_jingsi,tv_location_city;
    private RadioButton rb_work_order_all,rb_work_order_dai_jie;
    private LinearLayout ll_dao_dian,ll_shang_men;
    private List<ProductInfoEntity.Combo.ProductInfo> list = new ArrayList<>();
    private LocationClient mLocationClient;
    private BDLocationListener mBDLocationListener;
    private XiaOrderDialog dialog;
    private TextView tv_car_fuwu1,tv_car_fuwu2,tv_car_fuwu3,tv_car_fuwu4,tv_car_fuwu5,tv_car_fuwu6,
            tv_car_fuwu7,tv_car_fuwu8;
    private int num1=0;
    private int num2=0;
    private int num3=0;
    private MyImgScroll myPager; // 图片容器
    private LinearLayout ovalLayout; // 圆点容器
    private List<View> listViews; // 图片组
    private boolean isFirstLoc = true; // 是否首次定位

    public FirstFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment, container, false);
        myPager = (MyImgScroll) view.findViewById(R.id.myvp);
        ovalLayout = (LinearLayout) view.findViewById(R.id.vb);
        tv_map_jingsi = view.findViewById(R.id.tv_map_jingsi);
        rb_work_order_all = view.findViewById(R.id.rb_work_order_all);
        rb_work_order_dai_jie = view.findViewById(R.id.rb_work_order_dai_jie);
        ll_dao_dian = view.findViewById(R.id.ll_dao_dian);
        ll_shang_men = view.findViewById(R.id.ll_shang_men);
        tv_location_city = view.findViewById(R.id.tv_location_city);
        tv_car_fuwu1 = view.findViewById(R.id.tv_car_fuwu1);
        tv_car_fuwu2 = view.findViewById(R.id.tv_car_fuwu2);
        tv_car_fuwu3 = view.findViewById(R.id.tv_car_fuwu3);
        tv_car_fuwu4 = view.findViewById(R.id.tv_car_fuwu4);
        tv_car_fuwu5 = view.findViewById(R.id.tv_car_fuwu5);
        tv_car_fuwu6 = view.findViewById(R.id.tv_car_fuwu6);
        tv_car_fuwu7 = view.findViewById(R.id.tv_car_fuwu7);
        tv_car_fuwu8 = view.findViewById(R.id.tv_car_fuwu8);

        tv_map_jingsi.setOnClickListener(this);
        rb_work_order_all.setOnClickListener(this);
        rb_work_order_dai_jie.setOnClickListener(this);
        tv_car_fuwu1.setOnClickListener(this);
        tv_car_fuwu2.setOnClickListener(this);
        tv_car_fuwu3.setOnClickListener(this);
        tv_car_fuwu4.setOnClickListener(this);
        tv_car_fuwu5.setOnClickListener(this);
        tv_car_fuwu6.setOnClickListener(this);
        tv_car_fuwu7.setOnClickListener(this);
        tv_car_fuwu8.setOnClickListener(this);
        //mPresenter.comboInfo();//获取洗车组合套餐

        // 声明LocationClient类  
        mLocationClient = new LocationClient(context.getApplicationContext());
        mBDLocationListener = new MyBDLocationListener();
        initLocation();
        // 注册监听  
        mLocationClient.registerLocationListener(mBDLocationListener);

        dialog = new XiaOrderDialog(context);
        //mPresenter.comboInfo();//获取套餐
        InitViewPager();//初始化图片
        //开始滚动
        myPager.start((Activity) context, listViews, 4000, ovalLayout,
                R.layout.ad_bottom_item, R.id.ad_item_v,
                R.mipmap.dot_focused, R.mipmap.dot_normal);
        return view;
    }

    /**
     * 初始化图片
     */
    private void InitViewPager() {
        mPresenter.banner();
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        return null;
    }

    @Override
    public void initData() {

    }

    private void initLocation() {
        // 声明定位参数  
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式 高精度  
        option.setCoorType("bd09ll");// 设置返回定位结果是百度经纬度 默认gcj02  
        option.setScanSpan(5000);// 设置发起定位请求的时间间隔 单位ms  
        option.setIsNeedAddress(true);// 设置定位结果包含地址信息  
        option.setNeedDeviceDirect(true);// 设置定位结果包含手机机头 的方向  
        // 设置定位参数  
        mLocationClient.setLocOption(option);
        // 启动定位  
        mLocationClient.start();
    }
    private class MyBDLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // 非空判断  
            if (location != null) {
                if (!AppUtils.isEmpty(location.getCity())){
                    tv_location_city.setText(location.getCity());
                }else{
                    tv_location_city.setText("当前位置");
                }
                if (isFirstLoc) {
                    isFirstLoc = false;
                    double locationLatitude = location.getLatitude();
                    double locationLongitude = location.getLongitude();
                    if ("4.9E-324".equals(locationLongitude) && "4.9E-324".equals(locationLatitude)) {
                        toast(context, "百度地图定位失败");
                    } else if ("5e-324".equals(locationLongitude) && "5e-324".equals(locationLatitude)) {
                        toast(context, "百度地图定位失败");
                    } else {
                        String lat = SPUtils.get(context, "lat", "");
                        String lng = SPUtils.get(context, "lng", "");
                        if (!"".equals(lat) && !"".equals(lng)) {
                            SPUtils.remove(context, lat);
                            SPUtils.remove(context, lng);
                        }
                        //保存经纬度
                        SPUtils.put("lat", location.getLatitude());
                        SPUtils.put("lng", location.getLongitude());
                    }
                    if (mLocationClient.isStarted()) {
                        // 获得位置之后停止定位  
                        mLocationClient.stop();
                    }
                }
            }
        }
    }
    @Override
    public void onClick(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_map_jingsi://菁喜技师
                ZzRouter.gotoActivity((Activity) context, MapJingSiActivity.class);
                break;
            case R.id.rb_work_order_all://上门
                ll_shang_men.setVisibility(View.VISIBLE);
                ll_dao_dian.setVisibility(View.GONE);
                break;
            case R.id.rb_work_order_dai_jie://到店
                ll_dao_dian.setVisibility(View.VISIBLE);
                ll_shang_men.setVisibility(View.GONE);
                break;
            case R.id.tv_car_fuwu1:
            case R.id.tv_car_fuwu2:
            case R.id.tv_car_fuwu3:
            case R.id.tv_car_fuwu4:
            case R.id.tv_car_fuwu5:
                if (tv_car_fuwu1.isSelected()==true){
                    tv_car_fuwu1.setSelected(false);
                    tv_car_fuwu2.setSelected(false);
                    tv_car_fuwu3.setSelected(false);
                    tv_car_fuwu4.setSelected(false);
                    tv_car_fuwu5.setSelected(false);
                }else{
                    tv_car_fuwu1.setSelected(true);
                    tv_car_fuwu2.setSelected(true);
                    tv_car_fuwu3.setSelected(true);
                    tv_car_fuwu4.setSelected(true);
                    tv_car_fuwu5.setSelected(true);
                    dialog.showShareDialog(true,0,0,0);
                }
                break;
            case R.id.tv_car_fuwu6:
                if (tv_car_fuwu6.isSelected()==true){
                    tv_car_fuwu6.setSelected(false);
                    num1 = 0;
                }else{
                    tv_car_fuwu6.setSelected(true);
                    num1=6;
                    if (tv_car_fuwu1.isSelected()==false){
                        tv_car_fuwu1.setSelected(true);
                        tv_car_fuwu2.setSelected(true);
                        tv_car_fuwu3.setSelected(true);
                        tv_car_fuwu4.setSelected(true);
                        tv_car_fuwu5.setSelected(true);
                    }
                    dialog.showShareDialog(true,num1,num2,num3);
                }
                break;
            case R.id.tv_car_fuwu7:
                if (tv_car_fuwu7.isSelected()==true){
                    tv_car_fuwu7.setSelected(false);
                    num2=0;
                }else{
                    tv_car_fuwu7.setSelected(true);
                    num2=7;
                    if (tv_car_fuwu1.isSelected()==false){
                        tv_car_fuwu1.setSelected(true);
                        tv_car_fuwu2.setSelected(true);
                        tv_car_fuwu3.setSelected(true);
                        tv_car_fuwu4.setSelected(true);
                        tv_car_fuwu5.setSelected(true);
                    }
                    dialog.showShareDialog(true,num1,num2,num3);
                }
                break;
            case R.id.tv_car_fuwu8:
                if (tv_car_fuwu8.isSelected()==true){
                    tv_car_fuwu8.setSelected(false);
                    num3=0;
                }else{
                    tv_car_fuwu8.setSelected(true);
                    num3=8;
                    if (tv_car_fuwu1.isSelected()==false){
                        tv_car_fuwu1.setSelected(true);
                        tv_car_fuwu2.setSelected(true);
                        tv_car_fuwu3.setSelected(true);
                        tv_car_fuwu4.setSelected(true);
                        tv_car_fuwu5.setSelected(true);
                    }
                    dialog.showShareDialog(true,num1,num2,num3);
                }
                break;
        }
    }

    //刷新
    @Override
    public void onRefresh() {

    }

    //获取洗车组合套餐返回数据
    @Override
    public void comboInfoCallBack(ProductInfoEntity data) {
        list = data.combo.get(0).productList;
    }

    //广告滚动数据
    @Override
    public void bannerCallBack(List<BannerEntity> data) {
        if (data.size()>0){
            listViews = new ArrayList<View>();
            for (int i = 0; i < data.size(); i++) {
                MyImageView imageView = new MyImageView (context);
                imageView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {// 设置图片点击事件
//                    Toast.makeText(context,
//                            "点击了:" + myPager.getCurIndex(), Toast.LENGTH_SHORT)
//                            .show();
                    }
                });
                imageView.setImageURL(data.get(i).imgUrl);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                listViews.add(imageView);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLocationClient != null) {
            mLocationClient.unRegisterLocationListener(mBDLocationListener);
        }
    }
}
