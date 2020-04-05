package com.jxxc.jingxi.ui.cartypechoose;


import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.BandAndTypeEntity;
import com.jxxc.jingxi.entity.requestparameter.ExitLogin;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.utils.AppUtils;
import com.jxxc.jingxi.utils.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class CarTypeChooseActivity extends MVPBaseActivity<CarTypeChooseContract.View, CarTypeChoosePresenter> implements CarTypeChooseContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.btn_save)
    TextView btn_save;
    @BindView(R.id.gv_car_type)
    GridView gv_car_type;
    private CarChooseAdapter adapter;
    private List<BandAndTypeEntity.CarType> list = new ArrayList<>();
    private String brandId;
    private String brandName;
    private String carTypeId;
    private String carTypeName;
    @Override
    protected int layoutId() {
        return R.layout.car_type_choose_activity;
    }

    @Override
    public void initData() {
        StatusBarUtil.setStatusBarMode(this, false, R.color.white);
        tv_title.setText("车型选择");
        StyledDialog.buildLoading("数据加载中").setActivity(this).show();
        mPresenter.getBandAndType();
        adapter = new CarChooseAdapter(this);
        adapter.setData(list);
        gv_car_type.setAdapter(adapter);

        brandId = getIntent().getStringExtra("brandId");
        brandName = getIntent().getStringExtra("brandName");
        gv_car_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapter.setSelectPosition(i);
                adapter.notifyDataSetChanged();
                carTypeId = list.get(i).carTypeId;
                carTypeName = list.get(i).carTypeName;
            }
        });
    }

    @OnClick({R.id.tv_back,R.id.btn_save})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
            case R.id.btn_save://保存
                if (AppUtils.isEmpty(carTypeId)){
                    toast(this,"请选择车型");
                }else{
                    Intent intent = new Intent();
                    intent.setAction("car_type_choose_120021");
                    intent.putExtra("brandId",brandId);
                    intent.putExtra("brandName",brandName);
                    intent.putExtra("carTypeId",carTypeId);
                    intent.putExtra("carTypeName",carTypeName);
                    sendOrderedBroadcast(intent,null);
                    EventBus.getDefault().post(new ExitLogin());
                    finish();
                }
                break;
            default:
        }
    }

    //获取所有车品牌车型返回数据
    @Override
    public void getBandAndTypeCallBack(BandAndTypeEntity data) {
        list = data.type;
        adapter.setData(list);
        adapter.notifyDataSetChanged();
    }
}
