package com.jxxc.jingxi.ui.setmealpayinfo;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.adapter.ActivityDataAdapter;
import com.jxxc.jingxi.dialog.DiscountCouponDialog;
import com.jxxc.jingxi.dialog.TimeDialog;
import com.jxxc.jingxi.entity.backparameter.ActivitiesEntity;
import com.jxxc.jingxi.entity.backparameter.AddressEntity;
import com.jxxc.jingxi.entity.backparameter.AppointmentListEntity;
import com.jxxc.jingxi.entity.backparameter.CarListEntity;
import com.jxxc.jingxi.entity.backparameter.CreateOrderEntity;
import com.jxxc.jingxi.entity.backparameter.MyCoupon;
import com.jxxc.jingxi.entity.backparameter.RecommendComboInfoEntity;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.ui.addressdetails.AddressDetailsActivity;
import com.jxxc.jingxi.ui.mapjingsi.MapJingSiActivity;
import com.jxxc.jingxi.ui.mycar.MyCarActivity;
import com.jxxc.jingxi.ui.payorder.PayOrderActivity;
import com.jxxc.jingxi.ui.remark.RemarkActivity;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.utils.AppUtils;
import com.jxxc.jingxi.utils.GlideImgManager;
import com.jxxc.jingxi.utils.ListViewForScrollView;
import com.jxxc.jingxi.utils.SPUtils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class SetMealPayInfoActivity extends MVPBaseActivity<SetMealPayInfoContract.View, SetMealPayInfoPresenter> implements SetMealPayInfoContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_recommend_icon)
    ImageView iv_recommend_icon;
    @BindView(R.id.tv_recommend_name)
    TextView tv_recommend_name;
    @BindView(R.id.tv_recommend_context)
    TextView tv_recommend_context;
    @BindView(R.id.tv_recommend_money)
    TextView tv_recommend_money;
    @BindView(R.id.tv_recommend_num)
    TextView tv_recommend_num;
    @BindView(R.id.tv_car_address)
    TextView tv_car_address;
    @BindView(R.id.tv_car_info)
    TextView tv_car_info;
    @BindView(R.id.tv_phone_number)
    TextView tv_phone_number;
    @BindView(R.id.tv_appointment_time)
    TextView tv_appointment_time;
    @BindView(R.id.ll_stop_car_address)
    LinearLayout ll_stop_car_address;
    @BindView(R.id.ll_car_info)
    LinearLayout ll_car_info;
    @BindView(R.id.ll_yuyue_time)
    LinearLayout ll_yuyue_time;
    @BindView(R.id.ll_discount_coupon)
    LinearLayout ll_discount_coupon;
    @BindView(R.id.tv_create_order)
    TextView tv_create_order;
    @BindView(R.id.tv_xia_order_money)
    TextView tv_xia_order_money;
    @BindView(R.id.tv_xia_order_discounts)
    TextView tv_xia_order_discounts;
    @BindView(R.id.tv_hint1)
    TextView tv_hint1;
    @BindView(R.id.tv_hint2)
    TextView tv_hint2;
    @BindView(R.id.tv_user_remark)
    TextView tv_user_remark;
    @BindView(R.id.tv_discounts)
    TextView tv_discounts;
    @BindView(R.id.ll_remark)
    LinearLayout ll_remark;
    @BindView(R.id.activity_data)
    ListViewForScrollView activity_data;
    private String siteLat="";
    private String siteLng="";
    private TimeDialog timeDialog;
    private String appointmentStartTime="";
    private String appointmentEndTime="";
    private DiscountCouponDialog discountCouponDialog;//优惠券对话框
    private List<MyCoupon> myCouponList = new ArrayList<>();
    private RecommendComboInfoEntity recommendComboInfoEntity;
    private String serviceType="";
    private String counponId="";
    private String comboId="";
    private String carNum="";
    private String companyId="";//运营商ID 进店类型必传 上门不用传
    private String remark="";
    private double orderMoney=0;//订单金额
    private double comboMoney=0;//套餐金额
    private double couponMoney=0;//优惠券金额
    private double activityMoney=0;//活动金额
    private ActivityDataAdapter activityDataAdapter;

    @Override
    protected int layoutId() {
        return R.layout.set_meal_pay_info_activity;
    }

    @Override
    public void initData() {
        tv_title.setText("填写信息");
        StyledDialog.buildLoading("数据加载中").setActivity(this).show();
        recommendComboInfoEntity = (RecommendComboInfoEntity) getIntent().getSerializableExtra("recommendComboInfoEntity");
        serviceType = getIntent().getStringExtra("serviceType");
        companyId = getIntent().getStringExtra("companyId");
        comboMoney = recommendComboInfoEntity.totalPrice;//套餐金额
        if (!AppUtils.isEmpty(companyId)){
            tv_hint1.setBackgroundColor(getResources().getColor(R.color.qqq));
            tv_hint2.setBackgroundColor(getResources().getColor(R.color.white));
        }else{
            tv_hint1.setBackgroundColor(getResources().getColor(R.color.white));
            tv_hint2.setBackgroundColor(getResources().getColor(R.color.qqq));
        }

        //套餐信息
        comboId = recommendComboInfoEntity.comboId+"";
        GlideImgManager.loadRectangleImage(this, recommendComboInfoEntity.imgUrl, iv_recommend_icon);
        tv_recommend_name.setText(recommendComboInfoEntity.comboName);
        tv_recommend_context.setText(recommendComboInfoEntity.comboComment);
        tv_recommend_money.setText("￥"+new DecimalFormat("0.00").format(recommendComboInfoEntity.totalPrice));
        tv_recommend_num.setText("已售"+recommendComboInfoEntity.salesVolume);
        tv_phone_number.setText(SPUtils.get(SPUtils.K_SESSION_MOBILE,""));
        //订单金额=套餐金额-活动金额-优惠券金额
        orderMoney = comboMoney-activityMoney-couponMoney;
        tv_xia_order_money.setText("订单金额："+new DecimalFormat("0.00").format(orderMoney)+"元");

        registerReceiver(receiver, new IntentFilter("jingxi_car_addres_12002"));
        registerReceiver(receiverCarInfo, new IntentFilter("jing_xi_my_car_info"));
        registerReceiver(receiverRemark, new IntentFilter("jingxi_user_remark_209344"));

        //获取当前日期
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String queryDate = formatter.format(date);//今天日期
        mPresenter.appointmentList("",queryDate);
        mPresenter.queryMyCoupon(0);
        mPresenter.getCarList();
        mPresenter.getActivities();

        timeDialog = new TimeDialog(this);
        timeDialog.setOnFenxiangClickListener(new TimeDialog.OnFenxiangClickListener() {
            @Override
            public void onFenxiangClick(String time,String startTime,String endTime,int type) {
                if (type==0){
                    //刷新时间段
                    mPresenter.appointmentList("",time);
                }else{
                    //获得时间段
                    appointmentStartTime = startTime;
                    appointmentEndTime = endTime;
                    tv_appointment_time.setText(appointmentStartTime.substring(5)+"—至—"+appointmentEndTime.substring(5));
                    timeDialog.cleanDialog();
                }
            }
        });
        discountCouponDialog = new DiscountCouponDialog(this);
        discountCouponDialog.setOnFenxiangClickListener(new DiscountCouponDialog.OnFenxiangClickListener() {
            @Override
            public void onFenxiangClick(MyCoupon coupon) {
                //优惠券
                counponId = coupon.counponId+"";
                tv_xia_order_discounts.setVisibility(View.VISIBLE);//显示优惠
                //优惠券类型 0无门槛减N 1满N减N 2折扣券
                if (coupon.couponRuleType==0){
                    couponMoney = coupon.money;
                    tv_xia_order_discounts.setText("已优惠："+new DecimalFormat("0.00").format(couponMoney+activityMoney)+"元");
                }else if (coupon.couponRuleType==1){
                    couponMoney = coupon.money;
                    tv_xia_order_discounts.setText("已优惠："+new DecimalFormat("0.00").format(couponMoney+activityMoney)+"元");
                }else{
                    //折扣券
                    double num = recommendComboInfoEntity.totalPrice-activityMoney;
                    couponMoney =num - (num *(coupon.discount/10));
                    //double zheMoney = (recommendComboInfoEntity.totalPrice) - comboMoney;
                    tv_xia_order_discounts.setText("已优惠："+new DecimalFormat("0.00").format(couponMoney+activityMoney)+"元");
                }
                if (!"不使用优惠券".equals(coupon.counponName)){
                    tv_discounts.setText(new DecimalFormat("0.00").format(coupon.money)+"元优惠券");
                    if (coupon.couponRuleType==0){
                        tv_discounts.setText(new DecimalFormat("0.00").format(coupon.money)+"元优惠券");
                    }else if (coupon.couponRuleType == 1){
                        tv_discounts.setText(new DecimalFormat("0.00").format(coupon.money)+"元优惠券");
                    }else{
                        tv_discounts.setText(new DecimalFormat("0.00").format(coupon.discount)+"折优惠券");
                    }
                }else{
                    tv_discounts.setText("");
                }
                //订单金额=套餐金额-活动金额-优惠券金额
                orderMoney = comboMoney-activityMoney-couponMoney;
                tv_xia_order_money.setText("订单金额："+new DecimalFormat("0.00").format(orderMoney)+"元");
            }
        });
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //停车地点
            tv_car_address.setText(intent.getStringExtra("datouzhenAddress"));
            siteLat = intent.getStringExtra("datouzhenLatitude");
            siteLng = intent.getStringExtra("datouzhenLongitude");
        }
    };

    private BroadcastReceiver receiverCarInfo = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //换辆车
            CarListEntity carListEntity = (CarListEntity) intent.getSerializableExtra("carInfo");
            tv_car_info.setText(carListEntity.carNum+"  "+carListEntity.brandName+"  "+carListEntity.typeName);
            carNum = carListEntity.carNum;
        }
    };
    private BroadcastReceiver receiverRemark = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //评价
            remark = intent.getStringExtra("remark");
            tv_user_remark.setText(remark);
        }
    };

    @OnClick({R.id.tv_back,R.id.ll_stop_car_address,R.id.ll_car_info,R.id.ll_yuyue_time,
    R.id.ll_discount_coupon,R.id.tv_create_order,R.id.ll_remark})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
            case R.id.ll_stop_car_address://停车地址
                ZzRouter.gotoActivity(this, MapJingSiActivity.class);
                break;
            case R.id.ll_car_info://爱车信息
                ZzRouter.gotoActivity(this, MyCarActivity.class,"1");
                break;
            case R.id.ll_yuyue_time://服务时间
                timeDialog.showShareDialog(true);
                break;
            case R.id.ll_discount_coupon://优惠券
                discountCouponDialog.showShareDialog(true,myCouponList,recommendComboInfoEntity.totalPrice);
                break;
            case R.id.ll_remark://备注
                ZzRouter.gotoActivity(this, RemarkActivity.class);
                break;
            case R.id.tv_create_order://提交订单
                if (AppUtils.isEmpty(tv_car_info.getText().toString())){
                    toast(this,"请添加车辆");
                }else if (AppUtils.isEmpty(tv_car_address.getText().toString())){
                    toast(this,"请选择停车地点");
                }else if (AppUtils.isEmpty(tv_appointment_time.getText().toString())){
                    toast(this,"请选择服务时间");
                }else{
                    StyledDialog.buildLoading("正在下单").setActivity(this).show();
                    mPresenter.createOrder(
                            serviceType,
                            counponId,
                            comboId,
                            carNum,
                            "",
                            tv_phone_number.getText().toString(),
                            tv_car_address.getText().toString(),
                            siteLng,
                            siteLat,
                            appointmentStartTime,
                            appointmentEndTime,
                            remark,
                            companyId);
                }
                break;
            default:
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (!AppUtils.isEmpty(appointmentListReceiver)){
//            unregisterReceiver(appointmentListReceiver);
//        }
        if (!AppUtils.isEmpty(receiverCarInfo)){
            unregisterReceiver(receiverCarInfo);
        }
        if (!AppUtils.isEmpty(receiver)){
            unregisterReceiver(receiver);
        }
        if (!AppUtils.isEmpty(receiverRemark)){
            unregisterReceiver(receiverRemark);
        }
    }

    //车辆列表返回数据
    @Override
    public void getCarListCallBack(List<CarListEntity> data) {
        if (data.size()>0){//y有车
            //展示默认车辆，没有默认车辆展示第一辆
            //是否默认 1是0否
            int a=0;
            for (int i=0;i<data.size();i++){
                if (data.get(i).isDefault==1){
                    a++;
                    carNum = data.get(i).carNum;
                    tv_car_info.setText(data.get(i).carNum+"  "+data.get(i).brandName+"  "+data.get(i).typeName);
                }
            }
            //没有设置默认车辆
            if (a==0){
                carNum = data.get(0).carNum;
                tv_car_info.setText(data.get(0).carNum+"  "+data.get(0).brandName+"  "+data.get(0).typeName);
            }
        }
    }

    @Override
    public void appointmentListCallBack(List<AppointmentListEntity> data) {
        timeDialog.updateTimeAdapter(data);
    }

    //优惠券返回数据
    @Override
    public void queryMyCouponCallback(List<MyCoupon> data) {
        if (data.size()>0){
            myCouponList = data;
        }
    }

    //提交订单返回数据
    @Override
    public void createOrderCallBack(CreateOrderEntity data) {
        //支付订单
        Intent intent = new Intent(this, PayOrderActivity.class);
        intent.putExtra("orderId",data.orderId);
        intent.putExtra("orderPrice",data.payPrice);
        startActivity(intent);
    }

    //活动数据
    @Override
    public void getActivitiesCallBack(List<ActivitiesEntity> data) {
        //组装活动
        if (data.size()>0){
            activityDataAdapter = new ActivityDataAdapter(this);
            activityDataAdapter.setData(data);
            activity_data.setAdapter(activityDataAdapter);

            for (int i=0;i<data.size();i++){
                if (comboMoney>data.get(i).doorsillMoney){
                    if (data.get(i).money>activityMoney){
                        activityMoney = data.get(i).money;
                    }
                }
            }
            if (activityMoney>0){//大于0说明有活动
                tv_xia_order_discounts.setVisibility(View.VISIBLE);//显示优惠
                double num = activityMoney+couponMoney;//总共优惠的金额
                tv_xia_order_discounts.setText("已优惠："+new DecimalFormat("0.00").format(num)+"元");
                //订单金额=套餐金额-活动金额-优惠券金额
                orderMoney = comboMoney-activityMoney-couponMoney;
                tv_xia_order_money.setText("订单金额："+new DecimalFormat("0.00").format(orderMoney)+"元");
            }
        }
    }
}
