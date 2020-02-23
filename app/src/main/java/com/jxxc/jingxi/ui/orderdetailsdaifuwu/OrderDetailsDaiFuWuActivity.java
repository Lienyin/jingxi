package com.jxxc.jingxi.ui.orderdetailsdaifuwu;


import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;
import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.adapter.OrderDetailsDataAdapter;
import com.jxxc.jingxi.entity.backparameter.MyOrderEntity;
import com.jxxc.jingxi.entity.backparameter.OrderEntity;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.ui.mapjingsi.MapJingSiActivity;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.utils.AppUtils;
import com.jxxc.jingxi.utils.GlideImgManager;
import com.jxxc.jingxi.utils.SPUtils;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin 已接单待服务
 *  邮箱 784787081@qq.com
 */

public class OrderDetailsDaiFuWuActivity extends MVPBaseActivity<OrderDetailsDaiFuWuContract.View, OrderDetailsDaiFuWuPresenter> implements OrderDetailsDaiFuWuContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_details_call_phone)
    TextView tv_details_call_phone;
    @BindView(R.id.tv_details_cancel_order)
    TextView tv_details_cancel_order;
    @BindView(R.id.tv_details_cui_order)
    TextView tv_details_cui_order;
    @BindView(R.id.tv_details_jishi_name)
    TextView tv_details_jishi_name;
    @BindView(R.id.tv_details_order_id)
    TextView tv_details_order_id;
    @BindView(R.id.tv_details_order_static)
    TextView tv_details_order_static;
    @BindView(R.id.tv_details_order_xia_time)
    TextView tv_details_order_xia_time;
    @BindView(R.id.tv_details_order_address)
    TextView tv_details_order_address;
    @BindView(R.id.tv_details_order_memo)
    TextView tv_details_order_memo;
    @BindView(R.id.tv_details_order_coupon)
    TextView tv_details_order_coupon;
    @BindView(R.id.tv_details_order_money)
    TextView tv_details_order_money;
    @BindView(R.id.iv_jishi_hand)
    ImageView iv_jishi_hand;
    @BindView(R.id.gv_fuwu_data)
    GridView gv_fuwu_data;
    @BindView(R.id.mv_map)
    MapView mMapView;
    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    private boolean isFirstLoc = true; // 是否首次定位
    private double locationLatitude = 0;//当前经度
    private double locationLongitude = 0;//当前纬度
    private OrderEntity orderEntity = new OrderEntity();
    private String orderId;
    private OrderDetailsDataAdapter adapter;
    private RoutePlanSearch mSearch;
    @Override
    protected int layoutId() {
        return R.layout.order_details_dai_fu_wu_activity;
    }

    @Override
    public void initData() {
        tv_title.setText("订单详情");
        orderId = ZzRouter.getIntentData(this,String.class);
        StyledDialog.buildLoading("数据加载中").setActivity(this).show();
        mPresenter.getOrder(orderId);
        initMap();
    }

    @OnClick({R.id.tv_back,R.id.tv_details_call_phone,R.id.tv_details_cancel_order,R.id.tv_details_cui_order})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
            case R.id.tv_details_call_phone://联系技师
                if (!AppUtils.isEmpty(orderEntity.technicianPhonenumber)){
                    AppUtils.callPhone(this,orderEntity.technicianPhonenumber);
                }else{
                    toast(this,"空号");
                }
                break;
            case R.id.tv_details_cancel_order://取消订单
                //
                break;
            case R.id.tv_details_cui_order://催单
                //
                break;
            default:
        }
    }

    @Override
    public void getOrderCallBack(OrderEntity data) {
        orderEntity = data;
        adapter = new OrderDetailsDataAdapter(this);
        adapter.setData(orderEntity.products);
        gv_fuwu_data.setAdapter(adapter);

        GlideImgManager.loadCircleImage(this, data.technicianAvatar, iv_jishi_hand);
        tv_details_jishi_name.setText(data.technicianRealName);
        tv_details_order_id.setText(data.orderId);
        tv_details_order_static.setText(data.statusName);
        tv_details_order_xia_time.setText(data.appointmentTime);
        tv_details_order_address.setText(data.address);
        tv_details_order_memo.setText(data.remark);
        tv_details_order_coupon.setText("-￥"+data.discountsPrice);
        tv_details_order_money.setText("￥"+data.price);
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
                locationLatitude = location.getLatitude();//当前经纬度
                locationLongitude = location.getLongitude();

                if ("4.9E-324".equals(locationLongitude) && "4.9E-324".equals(locationLatitude)) {
                    toast(OrderDetailsDaiFuWuActivity.this, "百度地图定位失败");
                } else if ("5e-324".equals(locationLongitude) && "5e-324".equals(locationLatitude)) {
                    toast(OrderDetailsDaiFuWuActivity.this, "百度地图定位失败");
                } else {
                    String lat = SPUtils.get(OrderDetailsDaiFuWuActivity.this, "lat", "");
                    String lng = SPUtils.get(OrderDetailsDaiFuWuActivity.this, "lng", "");
                    if (!"".equals(lat) && !"".equals(lng)) {
                        SPUtils.remove(OrderDetailsDaiFuWuActivity.this, lat);
                        SPUtils.remove(OrderDetailsDaiFuWuActivity.this, lng);
                    }
                    //保存经纬度
                    SPUtils.put("lat", location.getLatitude());
                    SPUtils.put("lng", location.getLongitude());
                }
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(16.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));

//                mSearch = RoutePlanSearch.newInstance();
//                mSearch.setOnGetRoutePlanResultListener(listener);
            }
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