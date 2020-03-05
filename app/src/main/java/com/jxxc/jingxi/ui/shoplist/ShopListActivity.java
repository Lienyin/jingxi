package com.jxxc.jingxi.ui.shoplist;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.jxxc.jingxi.dialog.PopFiltrate;
import com.jxxc.jingxi.dialog.PopFiltrateCity;
import com.jxxc.jingxi.dialog.PopFiltrateOne;
import com.jxxc.jingxi.entity.backparameter.AreaListEntity;
import com.jxxc.jingxi.entity.backparameter.CityEntity;
import com.jxxc.jingxi.entity.backparameter.CommissionListEntity;
import com.jxxc.jingxi.entity.backparameter.DistrictEntity;
import com.jxxc.jingxi.entity.backparameter.ProvinceEntity;
import com.jxxc.jingxi.entity.backparameter.companyListEntity;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.ui.commissionlist.CommissionAdapter;
import com.jxxc.jingxi.ui.main.firstfragment.FirstFragment;
import com.jxxc.jingxi.ui.shopdetails.ShopDetailsActivity;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.utils.SPUtils;

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
    private ShopListAdapter adapter;
    private int offset = 2;
    private PopFiltrate popFiltrate;
    private PopFiltrateOne popFiltrateOne;
    private PopFiltrateCity popFiltrateCity;
    private LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    private double lat;
    private double lng;
    private String queryFlag="";
    private String sort="";
    private String cityId="";
    private List<companyListEntity> list = new ArrayList<>();
    private List<AreaListEntity> allData = new ArrayList<>();//省市区总数据
    private List<ProvinceEntity> provinceEntityList = new ArrayList<>();//省份
    @Override
    protected int layoutId() {
        return R.layout.shop_list_activity;
    }

    @Override
    public void initData() {
        tv_title.setText("门店列表");
        tv_location_city.setText(SPUtils.get(SPUtils.K_CITY,"选择城市"));
        popFiltrate = new PopFiltrate(this);
        popFiltrateOne = new PopFiltrateOne(this);
        popFiltrateCity = new PopFiltrateCity(this);
        StyledDialog.buildLoading("数据加载中").setActivity(this).show();
        mPresenter.areaList();
        mLocationClient = new LocationClient(getApplicationContext());
        initLocation();
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        //开启定位
        mLocationClient.start();
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
            public void onFenxiangClick(String type,String cityName) {
                cityId = type;
                tv_location_city.setText(cityName);
                mPresenter.companyList(lng,lat,queryFlag,sort,cityId,1,10);
                popFiltrateCity.dismiss();
            }
        });
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        //就是这个方法设置为true，才能获取当前的位置信息
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("gcj02");//可选，默认gcj02，设置返回的定位结果坐标系
        //int span = 1000;
        //option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        mLocationClient.setLocOption(option);
    }
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            //当前定位经纬度
            lat = location.getLatitude();
            lng = location.getLongitude();
            //tv_location_city.setText(location.getCity());//当前定位城市
            initAdapter();
            onRefresh();
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
                ZzRouter.gotoActivity(ShopListActivity.this, ShopDetailsActivity.class,list.get(position).companyId);
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
        adapter.disableLoadMoreIfNotFullPage();
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
}
