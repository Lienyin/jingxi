package com.jxxc.jingxi.ui.main.firstfragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.adapter.HomeDataAdapter;
import com.jxxc.jingxi.dialog.XiaOrderDialog;
import com.jxxc.jingxi.entity.backparameter.ProductInfoEntity;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.mvp.MVPBaseFragment;
import com.jxxc.jingxi.ui.mapjingsi.MapJingSiActivity;
import com.jxxc.jingxi.utils.AnimUtils;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class FirstFragment extends MVPBaseFragment<FirseFramentContract.View, FirseFramentPresenter> implements View.OnClickListener, FirseFramentContract.View, SwipeRefreshLayout.OnRefreshListener {
    private Context context;
    private TextView tv_map_jingsi,tv_location_city;
    private RadioButton rb_work_order_all,rb_work_order_dai_jie;
    private LinearLayout ll_dao_dian,ll_shang_men;
    private List<ProductInfoEntity.Combo.ProductInfo> list = new ArrayList<>();
    private LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    private XiaOrderDialog dialog;
    private TextView tv_car_fuwu1,tv_car_fuwu2,tv_car_fuwu3,tv_car_fuwu4,tv_car_fuwu5,tv_car_fuwu6,
            tv_car_fuwu7,tv_car_fuwu8;
    private int num1=0;
    private int num2=0;
    private int num3=0;

    public FirstFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment, container, false);
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

        mLocationClient = new LocationClient(context.getApplicationContext());
        initLocation();
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        //开启定位
        mLocationClient.start();

        dialog = new XiaOrderDialog(context);
        //mPresenter.comboInfo();//获取套餐
        return view;
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        return null;
    }

    @Override
    public void initData() {

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
            double lati = location.getLatitude();
            double longa = location.getLongitude();
            tv_location_city.setText(location.getCity());//当前定位城市
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
}
