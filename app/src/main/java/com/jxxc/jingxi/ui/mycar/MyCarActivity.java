package com.jxxc.jingxi.ui.mycar;


import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.CarListEntity;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.ui.addcar.AddCarActivity;
import com.jxxc.jingxi.utils.AnimUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MyCarActivity extends MVPBaseActivity<MyCarContract.View, MyCarPresenter> implements MyCarContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.btn_add_car)
    Button btn_add_car;
    @BindView(R.id.lv_car)
    ListView lv_car;
    private CarListAdapter adapter;
    private List<CarListEntity> list = new ArrayList<>();

    @Override
    protected int layoutId() {
        return R.layout.my_car_activity;
    }

    @Override
    public void initData() {
        tv_title.setText("我的车辆");
        mPresenter.getCarList();
        adapter = new CarListAdapter(this);
        adapter.setData(list);
        lv_car.setAdapter(adapter);
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
        list = data;
    }
}
