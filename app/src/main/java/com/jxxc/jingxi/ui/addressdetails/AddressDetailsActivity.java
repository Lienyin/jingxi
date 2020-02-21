package com.jxxc.jingxi.ui.addressdetails;


import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.dialog.AddressSeekDialog;
import com.jxxc.jingxi.entity.backparameter.AddressEntity;
import com.jxxc.jingxi.entity.backparameter.AddressSeek;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.utils.AppUtils;
import com.jxxc.jingxi.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class AddressDetailsActivity extends MVPBaseActivity<AddressDetailsContract.View, AddressDetailsPresenter> implements AddressDetailsContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.mapView_address)
    MapView mMapView;
    @BindView(R.id.lv_address_data)
    ListView lv_address_data;
    @BindView(R.id.et_input_ling_pay_info)
    EditText et_input_ling_pay_info;
    public BaiduMap mBaiduMap;
    private LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    private boolean isFirstLoc = true;
    private List<AddressEntity> list = new ArrayList<>();
    private List<AddressSeek> listSeek = new ArrayList<>();
    private GeoCoder mCoder;
    private LatLng latLng;
    private AddressDetailsAdapter adapter;
    private SuggestionSearch mSuggestionSearch;
    private String locationCity;
    private AddressSeekDialog addressDialog;
    private MapStatus startLatLng;//地图移动开始经纬度，结束经纬度

    @Override
    protected int layoutId() {
        return R.layout.address_details_activity;
    }

    @Override
    public void initData() {
        tv_title.setText("详细地址");
        initMap();
        adapter = new AddressDetailsAdapter(this);
        adapter.setData(list);
        lv_address_data.setAdapter(adapter);
        addressDialog = new AddressSeekDialog(this);

        //地址POi搜索
        et_input_ling_pay_info.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH){
                    String keytag = et_input_ling_pay_info.getText().toString().trim();
                    if (!AppUtils.isEmpty(keytag)){
                        /**
                         * 经纬度检索
                         */
                        mSuggestionSearch = SuggestionSearch.newInstance();
                        mSuggestionSearch.setOnGetSuggestionResultListener(listener1);
                        /**
                         * 在您的项目中，keyword为随您的输入变化的值
                         */
                        mSuggestionSearch.requestSuggestion(new SuggestionSearchOption()
                                .city(locationCity)
                                .keyword(keytag));
                    }else{
                        toast(AddressDetailsActivity.this,"请输入搜索关键字");
                    }
                    return true;
                }
                return false;
            }
        });

        addressDialog.setOnFenxiangClickListener(new AddressSeekDialog.OnFenxiangClickListener() {
            @Override
            public void onFenxiangClick(String siteName, double lat, double lng) {
                //POI检索
                mCoder = GeoCoder.newInstance();
                mCoder.setOnGetGeoCodeResultListener(listener);
                mCoder.reverseGeoCode(new ReverseGeoCodeOption()
                        .location(new LatLng(lat, lng)));
                addressDialog.cleanDialog();
            }
        });

        lv_address_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setAction("jingxi_car_addres_12002");
                intent.putExtra("addressEntity",list.get(position));
                sendOrderedBroadcast(intent,null);
                finish();
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
                startLatLng = mapStatus;
                //POI检索
                mCoder = GeoCoder.newInstance();
                mCoder.setOnGetGeoCodeResultListener(listener);
                mCoder.reverseGeoCode(new ReverseGeoCodeOption()
                        .location(new LatLng(mapStatus.target.latitude, mapStatus.target.longitude)));
            }
        });
    }

    OnGetSuggestionResultListener listener1 = new OnGetSuggestionResultListener() {
        @Override
        public void onGetSuggestionResult(SuggestionResult suggestionResult) {
            //处理sug检索结果
            if (listSeek.size()>0){listSeek.clear();}
            for (int i = 0; i < suggestionResult.getAllSuggestions().size(); i++) {
                if (!AppUtils.isEmpty(suggestionResult.getAllSuggestions().get(i).pt.latitude)){
                    Log.i("TAG",suggestionResult.getAllSuggestions().get(i).pt.latitude+"");
                    final AddressSeek addressEntity = new AddressSeek();
                    addressEntity.setName(suggestionResult.getAllSuggestions().get(i).key);
                    addressEntity.setLatitude(suggestionResult.getAllSuggestions().get(i).pt.latitude);
                    addressEntity.setLongitude(suggestionResult.getAllSuggestions().get(i).pt.longitude);
                    listSeek.add(addressEntity);
                }
            }
            addressDialog.showShareDialog(true,listSeek);
        }
    };

    private void initMap() {
        //获取地图控件引用
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);//普通地图
        mBaiduMap.setMyLocationEnabled(true);// 开启定位图层
        mMapView.showZoomControls(false);// 不显示地图缩放控件（+-按钮）
        //自定义地图定位图片
        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory.fromResource(R.mipmap.dingwei_dian);
        MyLocationConfiguration mLocationConfiguration = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL,
                false, mCurrentMarker, 0, 0);
        mBaiduMap.setMyLocationConfiguration(mLocationConfiguration);
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        //配置定位SDK参数
        initLocation();
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        //开启定位
        mLocationClient.start();
        //图片点击事件，回到定位点
        mLocationClient.requestLocation();
    }

    //配置定位SDK参数
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        option.setScanSpan(1000);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
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

    //实现BDLocationListener接口,BDLocationListener为结果监听接口，异步获取定位结果
    public class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            SPUtils.put("city", location.getCity());
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
                latLng = new LatLng(location.getLatitude(), location.getLongitude());
//                locationLatitude = location.getLatitude();
//                locationLongitude = location.getLongitude();
                locationCity = location.getCity();
//                locationProvince = location.getProvince();//当前定位省份
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(latLng).zoom(16.0f);//地图级别
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
                if (list.size()>0){list.clear();}
                for (int i = 0; i < reverseGeoCodeResult.getPoiList().size(); i++) {
                    final AddressEntity addressEntity = new AddressEntity();
                    addressEntity.setName(reverseGeoCodeResult.getPoiList().get(i).name);
                    addressEntity.setAddress(reverseGeoCodeResult.getPoiList().get(i).address);
                    addressEntity.setLat(reverseGeoCodeResult.getPoiList().get(i).location.latitude + "");
                    addressEntity.setLng(reverseGeoCodeResult.getPoiList().get(i).location.longitude + "");
                    list.add(addressEntity);
                }
                adapter.setData(list);
                lv_address_data.setAdapter(adapter);
            }
        }
    };

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

    @Override
    protected void onPause() {
        super.onPause();
        if (!AppUtils.isEmpty(mMapView)) {
            mMapView.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        if (!AppUtils.isEmpty(mMapView)) {
            mLocationClient.stop();
            mBaiduMap.setMyLocationEnabled(false);
            mMapView.onDestroy();
            mMapView = null;
        }
        mCoder.destroy();
        mSuggestionSearch.destroy();
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        if (!AppUtils.isEmpty(mMapView)) {
            mMapView.onResume();
        }
        super.onResume();
    }
}
