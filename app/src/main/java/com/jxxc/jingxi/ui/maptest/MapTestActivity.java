package com.jxxc.jingxi.ui.maptest;


import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.ui.addressdetails.AddressDetailsActivity;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.utils.SPUtils;
import com.jxxc.jingxi.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class MapTestActivity extends MVPBaseActivity<MapTestContract.View, MapTestPresenter> implements MapTestContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    private LocationClient mLocationClient;
    private BDLocationListener mBDLocationListener;

    @Override
    protected int layoutId() {
        return R.layout.map_test_activity;
    }

    @Override
    public void initData() {
        tv_title.setText("地图");

        // 声明LocationClient类  
        mLocationClient = new LocationClient(getApplicationContext());
        mBDLocationListener = new MyBDLocationListener();
        // 注册监听  
        mLocationClient.registerLocationListener(mBDLocationListener);
    }

    @OnClick({R.id.tv_back})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
            default:
        }
    }

    /**
     *  获得所在位置经纬度及详细地址 
     */
    public void getLocation(View view) {
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

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub  
        super.onDestroy();
        // 取消监听函数  
        if (mLocationClient != null) {
            mLocationClient.unRegisterLocationListener(mBDLocationListener);
        }
    }

    private class MyBDLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // 非空判断  
            if (location != null) {
                // 根据BDLocation 对象获得经纬度以及详细地址信息  
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                Log.i("TAG","longitude=="+longitude);
                String address = location.getAddrStr();
                if (mLocationClient.isStarted()) {
                    // 获得位置之后停止定位  
                    mLocationClient.stop();
                }
            }
        }
    }
}
