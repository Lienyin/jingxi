package com.jxxc.jingxi.ui.mapjingsi;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.utils.SpatialRelationUtil;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.dialog.MapJingXiDialog;
import com.jxxc.jingxi.entity.backparameter.NearbyConpanyEntity;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.ui.submitorder.SubmitOrderActivity;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.utils.SPUtils;
import com.jxxc.jingxi.utils.StatusBarUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MapJingSiActivity extends MVPBaseActivity<MapJingSiContract.View, MapJingSiPresenter> implements MapJingSiContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_affirm)
    TextView tv_affirm;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.mv_jingsi)
    MapView mMapView;
    @BindView(R.id.btn_xi_car)
    Button btn_xi_car;
    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    private boolean isFirstLoc = true; // 是否首次定位
    private double locationLatitude = 0;//当前经度
    private double locationLongitude = 0;//当前纬度
    private double datouzhenLatitude = 0;//大头针经度
    private double datouzhenLongitude = 0;//大头针纬度
    private String datouzhenAddress = "";//大头针地址
    private MapJingXiDialog dialog;
    private GeoCoder mCoder;
    private String distance="";
    private List<NearbyConpanyEntity> nearbyConpanyEntityList;
    private int isFuwu=0;//是否在服务范围内 0否 1是
    @Override
    protected int layoutId() {
        return R.layout.map_jing_si_activity;
    }

    @Override
    public void initData() {
        StatusBarUtil.setStatusBarMode(this, false, R.color.white);
        tv_title.setText("附近技师");
        initMap();
        tv_affirm.setVisibility(View.VISIBLE);
        Drawable img = this.getResources().getDrawable(R.mipmap.icon_54);
        img.setBounds(0, 0, img.getMinimumWidth(), img.getMinimumHeight());
        tv_affirm.setCompoundDrawables(img, null, null, null); //设置左图标
        dialog = new MapJingXiDialog(this);
        dialog.setOnFenxiangClickListener(new MapJingXiDialog.OnFenxiangClickListener() {
            @Override
            public void onFenxiangClick(int radius) {
                distance = radius*1000+"";
                mPresenter.nearbyConpany(distance,locationLongitude,locationLatitude);
            }
        });
        //大头针位置定位
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {
            }

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                datouzhenLatitude = mapStatus.target.latitude;
                datouzhenLongitude = mapStatus.target.longitude;
                //获取周边技师
                mPresenter.nearbyConpany(distance,mapStatus.target.longitude,mapStatus.target.latitude);

                boolean a = false;
                for (NearbyConpanyEntity site : nearbyConpanyEntityList) {
                    LatLng point = new LatLng(site.lat, site.lng);
                    a = SpatialRelationUtil.isCircleContainsPoint(point,site.serviceRadius,new LatLng(datouzhenLatitude, datouzhenLongitude));
                    if (a == true){ break;}
                }
                //POI检索
                mCoder = GeoCoder.newInstance();
                mCoder.setOnGetGeoCodeResultListener(listener);
                mCoder.reverseGeoCode(new ReverseGeoCodeOption()
                        .location(new LatLng(mapStatus.target.latitude, mapStatus.target.longitude)));
                if (a==true){
                    isFuwu = 1;
                }else{
                    isFuwu = 0;
                }
            }
        });
    }

    private void initMap(){
        //获取地图控件引用
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);//普通地图
        mBaiduMap.setMyLocationEnabled(true);// 开启定位图层
        mMapView.showZoomControls(false);// 不显示地图缩放控件（+-按钮）
        //自定义地图定位图片
//        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromResource(R.mipmap.icon_79);
//        MyLocationConfiguration mLocationConfiguration = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL,
//                false, mCurrentMarker, 0, 0);
       // mBaiduMap.setMyLocationConfiguration(mLocationConfiguration);
        //默认显示普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        //配置定位SDK参数
        initLocation();
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        //开启定位
        mLocationClient.start();
        //图片点击事件，回到定位点
        mLocationClient.requestLocation();
    };
    //配置定位SDK参数
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation
        // .getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);
        option.setOpenGps(true); // 打开gps
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }

    //周边技师返回数据
    @Override
    public void nearbyConpanyCallBack(List<NearbyConpanyEntity> data) {
        //定义Maker坐标点
        mBaiduMap.clear();
        nearbyConpanyEntityList = data;
        for (NearbyConpanyEntity site : data) {
            LatLng point = new LatLng(site.lat, site.lng);
            View view = View.inflate(this, R.layout.site_pop_view_img, null);
            ImageView ivView = (ImageView) view.findViewById(R.id.iv_pop_view_img_battery);
            BitmapDescriptor bitmap = BitmapDescriptorFactory.fromView(view);
            Bundle bundle = new Bundle();
            bundle.putSerializable("batSite", site);
            OverlayOptions option = new MarkerOptions()
                    .animateType(MarkerOptions.MarkerAnimateType.jump)
                    .position(point)
                    .extraInfo(bundle)
                    .icon(bitmap);
            //在地图上添加Marker，并显示
            mBaiduMap.addOverlay(option);

            //构造CircleOptions对象
            CircleOptions mCircleOptions = new CircleOptions().center(point)
                    .radius(site.serviceRadius)
                    .fillColor(0x4400B487) //填充颜色
                    .stroke(new Stroke(2, 0xAA00b487)); //边框宽和边框颜色
            //在地图上显示圆
            Overlay mCircle = mBaiduMap.addOverlay(mCircleOptions);

        }
    }

    //实现BDLocationListener接口,BDLocationListener为结果监听接口，异步获取定位结果
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            // 构造定位数据
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            // 设置定位数据
            mBaiduMap.setMyLocationData(locData);
            // 当不需要定位图层时关闭定位图层
            //mBaiduMap.setMyLocationEnabled(false);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
                locationLatitude = location.getLatitude();
                locationLongitude = location.getLongitude();
                mPresenter.nearbyConpany(distance,locationLongitude,locationLatitude);
                if ("4.9E-324".equals(locationLongitude) && "4.9E-324".equals(locationLatitude)) {
                    toast(MapJingSiActivity.this, "百度地图定位失败");
                } else if ("5e-324".equals(locationLongitude) && "5e-324".equals(locationLatitude)) {
                    toast(MapJingSiActivity.this, "百度地图定位失败");
                } else {
                    String lat = SPUtils.get(MapJingSiActivity.this, "lat", "");
                    String lng = SPUtils.get(MapJingSiActivity.this, "lng", "");
                    if (!"".equals(lat) && !"".equals(lng)) {
                        SPUtils.remove(MapJingSiActivity.this, lat);
                        SPUtils.remove(MapJingSiActivity.this, lng);
                    }
                    //保存经纬度
                    SPUtils.put("lat", location.getLatitude());
                    SPUtils.put("lng", location.getLongitude());
                }
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(16.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

                //POI检索
                mCoder = GeoCoder.newInstance();
                mCoder.setOnGetGeoCodeResultListener(listener);
                mCoder.reverseGeoCode(new ReverseGeoCodeOption()
                        .location(new LatLng(location.getLatitude(), location.getLongitude())));
            }
        }
    }

    OnGetGeoCoderResultListener listener = new OnGetGeoCoderResultListener() {
        @Override
        public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {

        }

        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
            if (reverseGeoCodeResult == null || reverseGeoCodeResult.error != SearchResult.ERRORNO.NO_ERROR) {
                //没有找到检索结果
                return;
            } else {
                datouzhenAddress = reverseGeoCodeResult.getAddress()+""+reverseGeoCodeResult.getSematicDescription();
                tv_address.setText(reverseGeoCodeResult.getAddress()+""+reverseGeoCodeResult.getSematicDescription());
            }
        }
    };

    @OnClick({R.id.tv_back,R.id.tv_affirm,R.id.btn_xi_car})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
            case R.id.tv_affirm://筛选
                dialog.showShareDialog(true);
                break;
            case R.id.btn_xi_car://下单洗车
                if (isFuwu==1){
                    Intent intent = new Intent();
                    intent.setAction("jingxi_car_addres_12002");
                    intent.putExtra("datouzhenLatitude",datouzhenLatitude+"");
                    intent.putExtra("datouzhenLongitude",datouzhenLongitude+"");
                    intent.putExtra("datouzhenAddress",datouzhenAddress+"");
                    sendOrderedBroadcast(intent,null);
                    finish();
                }else{
                    toast(this,"不在服务范围内");
                }
                break;
            default:
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        mLocationClient.stop();
    }
}
