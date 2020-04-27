package com.jxxc.jingxi.ui.shoplist;


import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.adapter.ShopListAdapter;
import com.jxxc.jingxi.dialog.PopFiltrate;
import com.jxxc.jingxi.dialog.PopFiltrateCity;
import com.jxxc.jingxi.dialog.PopFiltrateOne;
import com.jxxc.jingxi.entity.backparameter.AreaListEntity;
import com.jxxc.jingxi.entity.backparameter.ProvinceEntity;
import com.jxxc.jingxi.entity.backparameter.companyListEntity;
import com.jxxc.jingxi.entity.requestparameter.ExitLogin;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.ui.shopdetails.ShopDetailsActivity;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.utils.AppUtils;
import com.jxxc.jingxi.utils.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class ShopListActivity extends MVPBaseActivity<ShopListContract.View, ShopListPresenter> implements ShopListContract.View, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_location_city)
    TextView tv_location_city;
    @BindView(R.id.ll_city_filtrate)
    LinearLayout ll_city_filtrate;
    @BindView(R.id.ll_paixu_filtrate)
    LinearLayout ll_paixu_filtrate;
    @BindView(R.id.ll_static_filtrate)
    LinearLayout ll_static_filtrate;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.tv_dingwei_address)
    TextView tv_dingwei_address;
    private ShopListAdapter adapter;
    private int offset = 2;
    private PopFiltrate popFiltrate;
    private PopFiltrateOne popFiltrateOne;
    private PopFiltrateCity popFiltrateCity;
    private LocationClient mLocationClient;
    private BDLocationListener mBDLocationListener;
    private double lat;
    private double lng;
    private String queryFlag="";
    private String sort="";
    private String cityId="";
    private List<companyListEntity> list = new ArrayList<>();
    private List<AreaListEntity> allData = new ArrayList<>();//省市区总数据
    private List<ProvinceEntity> provinceEntityList = new ArrayList<>();//省份
    private boolean isFirstLoc = true; // 是否首次定位

    @Override
    protected int layoutId() {
        return R.layout.shop_list_activity;
    }

    @Override
    public void initData() {
        StatusBarUtil.setStatusBarMode(this, false, R.color.white);
        tv_title.setText("门店列表");
        popFiltrate = new PopFiltrate(this);
        popFiltrateOne = new PopFiltrateOne(this);
        popFiltrateCity = new PopFiltrateCity(this);
        EventBus.getDefault().register(this);
        StyledDialog.buildLoading("数据加载中").setActivity(this).show();
        mPresenter.areaList();//获取开通城市的省市区

        // 声明LocationClient类  
        mLocationClient = new LocationClient(getApplicationContext());
        mBDLocationListener = new MyBDLocationListener();
        initLocation();
        // 注册监听  
        mLocationClient.registerLocationListener(mBDLocationListener);
        //默认排序
        popFiltrate.setOnFenxiangClickListener(new PopFiltrate.OnFenxiangClickListener() {
            @Override
            public void onFenxiangClick(String type) {
                sort = type;
                mPresenter.companyList(lng,lat,queryFlag,sort,cityId,1,10);
                popFiltrate.dismiss();
            }
        });
        //筛选
        popFiltrateOne.setOnFenxiangClickListener(new PopFiltrateOne.OnFenxiangClickListener() {
            @Override
            public void onFenxiangClick(String type) {
                queryFlag = type;
                mPresenter.companyList(lng,lat,queryFlag,sort,cityId,1,10);
                popFiltrateOne.dismiss();
            }
        });
        //城市
        popFiltrateCity.setOnFenxiangClickListener(new PopFiltrateCity.OnFenxiangClickListener() {
            @Override
            public void onFenxiangClick(String areaId,String cityName,int level) {
                cityId = areaId;
                tv_location_city.setText(cityName);
                mPresenter.companyList(lng,lat,queryFlag,sort,cityId,1,10);
                if (level==3){
                    popFiltrateCity.dismiss();
                }
            }
        });
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
                if (isFirstLoc) {
                    isFirstLoc = false;
                    if (!AppUtils.isEmpty(location.getCity())){
                        tv_location_city.setText(location.getCity());
                    }else{
                        tv_location_city.setText("当前位置");
                    }
                    tv_dingwei_address.setText(location.getAddress().address);
                    //当前定位经纬度
                    lat = location.getLatitude();
                    lng = location.getLongitude();
                    initAdapter();
                    onRefresh();
                    if (mLocationClient.isStarted()) {
                        // 获得位置之后停止定位  
                        mLocationClient.stop();
                    }
                }
            }
        }
    }

    private void initAdapter() {
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(getResources().getColor(R.color.public_all));
        rvList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ShopListAdapter(R.layout.shop_list_adapter, new ArrayList<companyListEntity>());
        rvList.setAdapter(adapter);
        adapter.setOnLoadMoreListener(this, rvList);
        adapter.setEmptyView(R.layout.layout_nothing);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(ShopListActivity.this, ShopDetailsActivity.class);
                intent.putExtra("companyId",list.get(position).companyId);
                intent.putExtra("distance",list.get(position).distance);
                startActivity(intent);
            }
        });
    }

    @OnClick({R.id.tv_back,R.id.ll_city_filtrate,R.id.ll_paixu_filtrate,R.id.ll_static_filtrate})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
            case R.id.ll_city_filtrate://城市筛选
                popFiltrateCity.showPopupWindow(ll_city_filtrate,allData,provinceEntityList);
                break;
            case R.id.ll_paixu_filtrate://排序筛选
                popFiltrate.showPopupWindow(ll_paixu_filtrate);
                break;
            case R.id.ll_static_filtrate://筛选
                popFiltrateOne.showPopupWindow(ll_static_filtrate);
                break;
            default:
        }
    }

    @Override
    public void onRefresh() {
        offset = 2;
        mPresenter.companyList(lng,lat,queryFlag,sort,cityId,1,10);
    }

    @Override
    public void onLoadMoreRequested() {
        swipeLayout.setRefreshing(false);
        mPresenter.companyListMore(lng,lat,queryFlag,sort,cityId,offset,10);
    }

    @Override
    public void companyListCallBack(List<companyListEntity> data) {
        list  = data;
        swipeLayout.setRefreshing(false);
        adapter.setNewData(data);
        if (data.size() < 10) {
            adapter.loadMoreEnd();
        }else{
            adapter.disableLoadMoreIfNotFullPage();
        }
    }

    @Override
    public void companyListCallBackMore(List<companyListEntity> data) {
        list.addAll(data);
        swipeLayout.setRefreshing(false);
        offset++;
        adapter.addData(data);
        adapter.loadMoreComplete();
        if (data.size() < 10) {
            adapter.loadMoreEnd();
        }
    }

    //加盟商的省市区返回数据
    @Override
    public void areaListCallBack(List<AreaListEntity> data) {
        if (data.size()>0){
            allData = data;
            //循环添加省份
            for (int i=0;i<data.size();i++){
                if (data.get(i).areaLevel==1){//添加省份
                    ProvinceEntity provinceEntity = new ProvinceEntity();
                    provinceEntity.setName(data.get(i).areaName);
                    provinceEntity.setAreaId(data.get(i).areaId);
                    provinceEntity.setAreaCode(data.get(i).areaCode);
                    provinceEntityList.add(provinceEntity);
                }
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ExitLogin exitLogin) {
        finish();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
        // 取消监听函数  
        if (mLocationClient != null) {
            mLocationClient.unRegisterLocationListener(mBDLocationListener);
        }
    }
}
