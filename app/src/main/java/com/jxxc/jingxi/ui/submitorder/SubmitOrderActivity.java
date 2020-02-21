package com.jxxc.jingxi.ui.submitorder;


import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.CarListEntity;
import com.jxxc.jingxi.entity.backparameter.MyCoupon;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.utils.AnimUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class SubmitOrderActivity extends MVPBaseActivity<SubmitOrderContract.View, SubmitOrderPresenter> implements SubmitOrderContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.rb_shangmen_service)
    RadioButton rb_shangmen_service;
    @BindView(R.id.rb_daodian_service)
    RadioButton rb_daodian_service;
    @BindView(R.id.rb_wai_guan)
    RadioButton rb_wai_guan;
    @BindView(R.id.rb_zheng_che)
    RadioButton rb_zheng_che;
    @BindView(R.id.shang_men)
    View shang_men;
    @BindView(R.id.dao_dian)
    View dao_dian;
    @BindView(R.id.ll_car_fuwu)
    LinearLayout ll_car_fuwu;
    @BindView(R.id.ll_fuwu_no_data)
    LinearLayout ll_fuwu_no_data;
    @BindView(R.id.ll_add_car)
    LinearLayout ll_add_car;
    @BindView(R.id.ll_car_info)
    LinearLayout ll_car_info;
    @BindView(R.id.tv_car_number)
    TextView tv_car_number;
    @BindView(R.id.tv_car_type)
    TextView tv_car_type;
    @BindView(R.id.tv_car_color)
    TextView tv_car_color;
    @BindView(R.id.tv_car_fuwu1)
    TextView tv_car_fuwu1;
    @BindView(R.id.tv_car_fuwu2)
    TextView tv_car_fuwu2;
    @BindView(R.id.tv_car_fuwu3)
    TextView tv_car_fuwu3;
    @BindView(R.id.tv_car_fuwu4)
    TextView tv_car_fuwu4;
    @BindView(R.id.tv_car_fuwu5)
    TextView tv_car_fuwu5;
    @BindView(R.id.tv_car_fuwu6)
    TextView tv_car_fuwu6;
    @BindView(R.id.tv_car_fuwu7)
    TextView tv_car_fuwu7;
    @BindView(R.id.tv_car_fuwu8)
    TextView tv_car_fuwu8;
    @BindView(R.id.iv_call_phone)
    ImageView iv_call_phone;
    @BindView(R.id.lv_coupon_data)
    ListView lv_coupon_data;
    private int fuwu1;
    private int fuwu2;
    private int fuwu3;
    private CouponAdapter adapter;
    private List<MyCoupon> list = new ArrayList<>();
    @Override
    protected int layoutId() {
        return R.layout.submit_order_activity;
    }

    @Override
    public void initData() {
        tv_title.setText("菁喜洗车");
        StyledDialog.buildLoading("数据加载中").setActivity(this).show();
        mPresenter.getCarList();
        mPresenter.queryMyCoupon(0);
        fuwu1 = getIntent().getIntExtra("fuwu1",0);
        fuwu2 = getIntent().getIntExtra("fuwu2",0);
        fuwu3 = getIntent().getIntExtra("fuwu3",0);
        tv_car_fuwu1.setSelected(true);
        tv_car_fuwu2.setSelected(true);
        tv_car_fuwu3.setSelected(true);
        tv_car_fuwu4.setSelected(true);
        tv_car_fuwu5.setSelected(true);
        if (fuwu1>0){
            tv_car_fuwu6.setSelected(true);
        }
        if (fuwu2>0){
            tv_car_fuwu7.setSelected(true);
        }
        if (fuwu3>0){
            tv_car_fuwu8.setSelected(true);
        }
        adapter = new CouponAdapter(this);
        adapter.setData(list);
        lv_coupon_data.setAdapter(adapter);
    }

    @OnClick({R.id.tv_back,R.id.rb_shangmen_service,R.id.rb_daodian_service,R.id.rb_wai_guan,
            R.id.rb_zheng_che,R.id.tv_car_fuwu1,R.id.tv_car_fuwu2,R.id.tv_car_fuwu3,R.id.tv_car_fuwu4
            ,R.id.tv_car_fuwu5,R.id.tv_car_fuwu6,R.id.tv_car_fuwu7,R.id.tv_car_fuwu8,R.id.iv_call_phone})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
            case R.id.rb_shangmen_service://上门
                shang_men.setVisibility(View.VISIBLE);
                dao_dian.setVisibility(View.GONE);
                break;
            case R.id.rb_daodian_service://到店
                shang_men.setVisibility(View.GONE);
                dao_dian.setVisibility(View.VISIBLE);
                break;
            case R.id.rb_wai_guan://外观清洗
                ll_car_fuwu.setVisibility(View.VISIBLE);
                ll_fuwu_no_data.setVisibility(View.GONE);
                break;
            case R.id.rb_zheng_che://整车清洗
                ll_car_fuwu.setVisibility(View.GONE);
                ll_fuwu_no_data.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_car_fuwu1:
            case R.id.tv_car_fuwu2:
            case R.id.tv_car_fuwu3:
            case R.id.tv_car_fuwu4:
            case R.id.tv_car_fuwu5:
                break;
            case R.id.tv_car_fuwu6:
                if (tv_car_fuwu6.isSelected()==true){
                    tv_car_fuwu6.setSelected(false);
                }else{
                    tv_car_fuwu6.setSelected(true);
                }
                break;
            case R.id.tv_car_fuwu7:
                if (tv_car_fuwu7.isSelected()==true){
                    tv_car_fuwu7.setSelected(false);
                }else{
                    tv_car_fuwu8.setSelected(true);
                }
                break;
            case R.id.tv_car_fuwu8:
                if (tv_car_fuwu8.isSelected()==true){
                    tv_car_fuwu8.setSelected(false);
                }else{
                    tv_car_fuwu8.setSelected(true);
                }
                break;
            case R.id.iv_call_phone:
                if (iv_call_phone.isSelected()==true){
                    iv_call_phone.setSelected(false);
                }else{
                    iv_call_phone.setSelected(true);
                }
                break;
            default:
        }
    }

    //车辆信息
    @Override
    public void getCarListCallBack(List<CarListEntity> data) {
        if (data.size()>0){//y有车
            ll_add_car.setVisibility(View.GONE);
            ll_car_info.setVisibility(View.VISIBLE);
            tv_car_number.setText(data.get(0).carNum);
            tv_car_type.setText(data.get(0).brandName+"  "+data.get(0).typeName);
            if (data.get(0).color==1){
                tv_car_color.setBackgroundResource(R.drawable.car_color_1);
            }else if (data.get(0).color==2){
                tv_car_color.setBackgroundResource(R.drawable.car_color_2);
            }else if (data.get(0).color==3){
                tv_car_color.setBackgroundResource(R.drawable.car_color_3);
            }else if (data.get(0).color==4){
                tv_car_color.setBackgroundResource(R.drawable.car_color_4);
            }else if (data.get(0).color==5){
                tv_car_color.setBackgroundResource(R.drawable.car_color_5);
            }else if (data.get(0).color==6){
                tv_car_color.setBackgroundResource(R.drawable.car_color_6);
            }else if (data.get(0).color==7){
                tv_car_color.setBackgroundResource(R.drawable.car_color_7);
            }else if (data.get(0).color==8){
                tv_car_color.setBackgroundResource(R.drawable.car_color_8);
            }else if (data.get(0).color==9){
                tv_car_color.setBackgroundResource(R.drawable.car_color_9);
            }else if (data.get(0).color==10){
                tv_car_color.setBackgroundResource(R.drawable.car_color_10);
            }else{
                tv_car_color.setBackgroundResource(R.drawable.car_color_8);
            }
        }else{
            ll_add_car.setVisibility(View.VISIBLE);
            ll_car_info.setVisibility(View.GONE);
        }
    }

    //优惠券返回数据
    @Override
    public void queryMyCouponCallback(List<MyCoupon> data) {
        list = data;
        adapter.setData(list);
        adapter.notifyDataSetChanged();
    }
}
