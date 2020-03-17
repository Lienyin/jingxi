package com.jxxc.jingxi.ui.mycar;


import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.CarListEntity;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.ui.addcar.AddCarActivity;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.utils.AppUtils;
import com.jxxc.jingxi.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MyCarActivity extends MVPBaseActivity<MyCarContract.View, MyCarPresenter> implements MyCarContract.View, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.btn_add_car)
    Button btn_add_car;
    @BindView(R.id.lv_car)
    ListView lv_car;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    private CarListAdapter adapter;
    private List<CarListEntity> list = new ArrayList<>();
    private String type="0";

    @Override
    protected int layoutId() {
        return R.layout.my_car_activity;
    }

    @Override
    public void initData() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.public_all);//状态栏颜色
        tv_title.setText("我的车辆");
        type = ZzRouter.getIntentData(this,String.class);//界面来源
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(getResources().getColor(R.color.public_all));
        mPresenter.getCarList();
        adapter = new CarListAdapter(this,type);
        adapter.setData(list);
        lv_car.setAdapter(adapter);
        adapter.setOnFenxiangClickListener(new CarListAdapter.OnFenxiangClickListener() {
            @Override
            public void onFenxiangClick(String carNum,CarListEntity data) {
                if (!AppUtils.isEmpty(carNum)){
                    StyledDialog.buildLoading("正在删除").setActivity(MyCarActivity.this).show();
                    mPresenter.removeCar(carNum);
                }else if (!AppUtils.isEmpty(data)){
                    Intent intent = new Intent();
                    intent.putExtra("carInfo",data);
                    intent.setAction("jing_xi_my_car_info");
                    sendOrderedBroadcast(intent,null);
                    finish();
                }
            }
        });
    }

    @OnClick({R.id.tv_back,R.id.btn_add_car})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
            case R.id.btn_add_car://添加车辆
                ZzRouter.gotoActivity(this, AddCarActivity.class);
                break;
            default:
        }
    }

    //获取车辆列表
    @Override
    public void getCarListCallBack(List<CarListEntity> data) {
        swipeLayout.setRefreshing(false);
        list = data;
        adapter.setData(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void removeCarCallBack() {
        mPresenter.getCarList();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.getCarList();
    }

    @Override
    public void onRefresh() {
        mPresenter.getCarList();
    }
}
