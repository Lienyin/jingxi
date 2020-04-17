package com.jxxc.jingxi.ui.setmealpayinfo;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.Html;
import android.util.Log;
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
import com.jxxc.jingxi.entity.backparameter.ProductInfoEntity;
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
import com.jxxc.jingxi.utils.StatusBarUtil;
import com.jxxc.jingxi.utils.ZQImageViewRoundOval;

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
    ZQImageViewRoundOval iv_recommend_icon;
    @BindView(R.id.tv_recommend_name)
    TextView tv_recommend_name;
    @BindView(R.id.tv_recommend_context)
    TextView tv_recommend_context;
    @BindView(R.id.tv_recommend_money)
    TextView tv_recommend_money;
    @BindView(R.id.tv_fuwu_name)
    TextView tv_fuwu_name;
    @BindView(R.id.tv_fuwu_money)
    TextView tv_fuwu_money;
    @BindView(R.id.tv_fuwu_time)
    TextView tv_fuwu_time;
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
    @BindView(R.id.tv_car_fuwu1_money)
    TextView tv_car_fuwu1_money;
    @BindView(R.id.tv_car_fuwu2_money)
    TextView tv_car_fuwu2_money;
    @BindView(R.id.tv_car_fuwu3_money)
    TextView tv_car_fuwu3_money;
    @BindView(R.id.tv_car_fuwu4_money)
    TextView tv_car_fuwu4_money;
    @BindView(R.id.tv_car_fuwu5_money)
    TextView tv_car_fuwu5_money;
    @BindView(R.id.tv_car_fuwu6_money)
    TextView tv_car_fuwu6_money;
    @BindView(R.id.tv_car_fuwu7_money)
    TextView tv_car_fuwu7_money;
    @BindView(R.id.tv_car_fuwu8_money)
    TextView tv_car_fuwu8_money;
    @BindView(R.id.tv_address_text)
    TextView tv_address_text;
    @BindView(R.id.ll_set_type1)
    LinearLayout ll_set_type1;
    @BindView(R.id.ll_set_type2)
    LinearLayout ll_set_type2;
    @BindView(R.id.ll_fuwu_detail)
    LinearLayout ll_fuwu_detail;

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
    private String address="";
    private double orderMoney=0;//订单金额
    private double comboMoney=0;//套餐金额
    private double couponMoney=0;//优惠券金额
    private double activityMoney=0;//活动金额
    private ActivityDataAdapter activityDataAdapter;
    private ProductInfoEntity productInfoEntity = new ProductInfoEntity();
    private String comboTypeId="";
    private String comboProductId0="";
    private String comboProductId6="";
    private String comboProductId7="";
    private String comboProductId8="";
    private String comboProductIds="";
    private String comboRecommendIds="";//进店套餐id
    private double fuwuTypeMoney6=0;
    private double fuwuTypeMoney7=0;
    private double fuwuTypeMoney8=0;
    private ProductInfoEntity.Combo comboData = new ProductInfoEntity.Combo();//车型套餐数据

    @Override
    protected int layoutId() {
        return R.layout.set_meal_pay_info_activity;
    }

    @Override
    public void initData() {
        StatusBarUtil.setStatusBarMode(this, false, R.color.white);
        StyledDialog.buildLoading("数据加载中").setActivity(this).show();
        serviceType = getIntent().getStringExtra("serviceType");
        companyId = getIntent().getStringExtra("companyId");
        appointmentStartTime = getIntent().getStringExtra("appointmentStartTime");
        appointmentEndTime = getIntent().getStringExtra("appointmentEndTime");
        address = getIntent().getStringExtra("address");//店铺地址
        if (!AppUtils.isEmpty(appointmentStartTime)){
            tv_appointment_time.setText(appointmentStartTime.substring(5)+"—至—"+appointmentEndTime.substring(5));
        }
        tv_phone_number.setText(SPUtils.get(SPUtils.K_SESSION_MOBILE,""));//手机号码
        //获取当前日期
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String queryDate = formatter.format(date);//今天日期
        mPresenter.appointmentList(companyId,queryDate);//查询预约时间

        if (!AppUtils.isEmpty(companyId)){
            recommendComboInfoEntity = (RecommendComboInfoEntity) getIntent().getSerializableExtra("recommendComboInfoEntity");
            tv_title.setText("到店洗车");
            tv_address_text.setText("门店地址");
            tv_car_address.setText(address);
            tv_car_address.setClickable(true);
            //到店服务
            ll_set_type1.setVisibility(View.VISIBLE);
            ll_set_type2.setVisibility(View.GONE);
            //套餐信息
            comboId = recommendComboInfoEntity.comboId+"";
            tv_recommend_name.setText(recommendComboInfoEntity.comboName);
            tv_recommend_context.setText(recommendComboInfoEntity.comboComment);
            tv_recommend_money.setText("￥"+new DecimalFormat("0.00").format(recommendComboInfoEntity.totalPrice));
            //tv_recommend_num.setText("已售"+recommendComboInfoEntity.salesVolume);
            tv_fuwu_name.setText(recommendComboInfoEntity.comboComment);
            tv_fuwu_time.setText(recommendComboInfoEntity.serviceHours);
            String carType1 = "";//1-SUV 2-轿车 3-MPV
            String carType2 = "";//1-SUV 2-轿车 3-MPV
            String carType3 = "";//1-SUV 2-轿车 3-MPV
            for (int i=0;i<recommendComboInfoEntity.carTypePrices.size();i++){
                if (recommendComboInfoEntity.carTypePrices.get(i).carTypeId==1){
                    carType1 = "SUV" + recommendComboInfoEntity.carTypePrices.get(i).totalPrice+"元";
                }else if (recommendComboInfoEntity.carTypePrices.get(i).carTypeId==2){
                    carType2 = "轿车" + recommendComboInfoEntity.carTypePrices.get(i).totalPrice+"元";
                }else{
                    carType3 = "MPV" + recommendComboInfoEntity.carTypePrices.get(i).totalPrice+"元";
                }
            }
            tv_fuwu_money.setText(carType1+" "+" "+carType2+" "+carType3);
            comboMoney = recommendComboInfoEntity.totalPrice;//套餐金额
            //订单金额=套餐金额-活动金额-优惠券金额
            orderMoney = comboMoney-activityMoney-couponMoney;
            if (orderMoney<0){
                tv_xia_order_money.setText(Html.fromHtml("订单金额: <font color=\"#FF2700\">"+new DecimalFormat("0.00").format(0)+"元</font>"));
            }else{
                tv_xia_order_money.setText(Html.fromHtml("订单金额: <font color=\"#FF2700\">"+new DecimalFormat("0.00").format(orderMoney)+"元</font>"));
            }
            mPresenter.getCarList();//车辆列表
            mPresenter.queryMyCoupon(0);//优惠券
            mPresenter.getActivities();//活动
        }else {
            //上门服务
            tv_address_text.setText("停车地址");
            tv_title.setText("上门洗车");
            ll_set_type1.setVisibility(View.GONE);
            ll_set_type2.setVisibility(View.VISIBLE);
            mPresenter.comboInfo();//查套餐
            //默认选择前5项套餐
            tv_car_fuwu1.setSelected(true);
            tv_car_fuwu2.setSelected(true);
            tv_car_fuwu3.setSelected(true);
            tv_car_fuwu4.setSelected(true);
            tv_car_fuwu5.setSelected(true);
        }

        if (!AppUtils.isEmpty(companyId)){
            tv_hint1.setBackgroundColor(getResources().getColor(R.color.qqq));
            tv_hint2.setBackgroundColor(getResources().getColor(R.color.white));
        }else{
            tv_hint1.setBackgroundColor(getResources().getColor(R.color.white));
            tv_hint2.setBackgroundColor(getResources().getColor(R.color.qqq));
        }

        registerReceiver(receiver, new IntentFilter("jingxi_car_addres_12002"));
        registerReceiver(receiverCarInfo, new IntentFilter("jing_xi_my_car_info"));
        registerReceiver(receiverRemark, new IntentFilter("jingxi_user_remark_209344"));


        timeDialog = new TimeDialog(this);
        timeDialog.setOnFenxiangClickListener(new TimeDialog.OnFenxiangClickListener() {
            @Override
            public void onFenxiangClick(String time,String startTime,String endTime,int type) {
                if (type==0){
                    //刷新时间段
                    mPresenter.appointmentList(companyId,time);
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
                    double orderMoney = couponMoney+activityMoney;
                    tv_xia_order_discounts.setText("已优惠："+new DecimalFormat("0.00").format(couponMoney+activityMoney)+"元");
                }else if (coupon.couponRuleType==1){
                    couponMoney = coupon.money;
                    tv_xia_order_discounts.setText("已优惠："+new DecimalFormat("0.00").format(couponMoney+activityMoney)+"元");
                }else{
                    //折扣券
                    double num = recommendComboInfoEntity.totalPrice-activityMoney;
                    couponMoney =num - (num *(coupon.discount/10));
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
                if (orderMoney<0){
                    tv_xia_order_money.setText(Html.fromHtml("订单金额: <font color=\"#FF2700\">"+new DecimalFormat("0.00").format(0)+"元</font>"));
                }else{
                    tv_xia_order_money.setText(Html.fromHtml("订单金额: <font color=\"#FF2700\">"+new DecimalFormat("0.00").format(orderMoney)+"元</font>"));
                }
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
            comboTypeId = carListEntity.typeId;
            setService(productInfoEntity);
            tv_car_fuwu6.setSelected(false);
            tv_car_fuwu7.setSelected(false);
            tv_car_fuwu8.setSelected(false);
            fuwuTypeMoney6 = 0;
            comboProductId6="";
            fuwuTypeMoney7 = 0;
            comboProductId7="";
            fuwuTypeMoney8 = 0;
            comboProductId8="";
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
    R.id.ll_discount_coupon,R.id.tv_create_order,R.id.ll_remark,R.id.tv_car_fuwu6,R.id.tv_car_fuwu7,
            R.id.tv_car_fuwu8,R.id.ll_set_type1})
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
                if (myCouponList.size()>0){
                    discountCouponDialog.showShareDialog(true,myCouponList,comboMoney);
                }else{
                    toast(this,"暂无优惠券可用");
                }
                break;
            case R.id.ll_remark://备注
                ZzRouter.gotoActivity(this, RemarkActivity.class);
                break;
            case R.id.tv_create_order://提交订单
                if(AppUtils.isEmpty(companyId)){//上门
                    if (AppUtils.isEmpty(tv_car_info.getText().toString())){
                        toast(this,"请添加车辆");
                    }else if (AppUtils.isEmpty(tv_car_address.getText().toString())){
                        toast(this,"请选择停车地点");
                    }else if (AppUtils.isEmpty(tv_appointment_time.getText().toString())){
                        toast(this,"请选择服务时间");
                    }else{
                        StyledDialog.buildLoading("正在下单").setActivity(this).show();
                        comboProductIds = comboProductId0+comboProductId6+comboProductId7+comboProductId8;
                        mPresenter.createOrder(
                                Integer.valueOf(serviceType),
                                counponId,
                                carNum,
                                "",
                                tv_phone_number.getText().toString(),
                                tv_car_address.getText().toString(),
                                siteLng,
                                siteLat,
                                appointmentStartTime,
                                appointmentEndTime,
                                remark,
                                companyId,
                                comboProductIds,
                                comboId);
                    }
                }else{
                    //进店
                    StyledDialog.buildLoading("正在下单").setActivity(this).show();
                    mPresenter.create2(
                            counponId,
                            carNum,
                            "",
                            tv_phone_number.getText().toString(),
                            appointmentStartTime,
                            appointmentEndTime,
                            remark,
                            companyId,
                            comboProductIds);
                }
                break;
            case R.id.tv_car_fuwu6:
                if (tv_car_fuwu6.isSelected()==true){
                    tv_car_fuwu6.setSelected(false);
                    fuwuTypeMoney6 = 0;
                    comboProductId6="";
                }else{
                    tv_car_fuwu6.setSelected(true);
                    for (int j=0;j<comboData.productList.size();j++){
                        if (comboData.productList.get(j).productId==6){
                            fuwuTypeMoney6 = comboData.productList.get(j).price;
                            comboProductId6= comboData.productList.get(j).comboProductId+",";
                        }
                    }
                }
                //套餐金额=基础套餐金额+fuwuTypeMoney6
                comboMoney = comboData.basicPrice+fuwuTypeMoney6+fuwuTypeMoney7+fuwuTypeMoney8;
                //订单金额=(基础套餐金额+选择服务项金额)-优惠券金额-活动金额
                orderMoney = comboMoney-couponMoney-activityMoney;
                if (orderMoney<0){
                    tv_xia_order_money.setText(Html.fromHtml("订单金额: <font color=\"#FF2700\">"+new DecimalFormat("0.00").format(0)+"元</font>"));
                }else{
                    tv_xia_order_money.setText(Html.fromHtml("订单金额: <font color=\"#FF2700\">"+new DecimalFormat("0.00").format(orderMoney)+"元</font>"));
                }
                break;
            case R.id.tv_car_fuwu7:
                if (tv_car_fuwu7.isSelected()==true){
                    tv_car_fuwu7.setSelected(false);
                    fuwuTypeMoney7 = 0;
                    comboProductId7="";
                }else{
                    tv_car_fuwu7.setSelected(true);
                    for (int j=0;j<comboData.productList.size();j++){
                        if (comboData.productList.get(j).productId==7){
                            fuwuTypeMoney7 = comboData.productList.get(j).price;
                            comboProductId7= comboData.productList.get(j).comboProductId+",";
                        }
                    }
                }
                //套餐金额=基础套餐金额+fuwuTypeMoney7
                comboMoney = comboData.basicPrice+fuwuTypeMoney6+fuwuTypeMoney7+fuwuTypeMoney8;
                //订单金额=(基础套餐金额+选择服务项金额)-优惠券金额-活动金额
                orderMoney = comboMoney-couponMoney-activityMoney;
                if (orderMoney<0){
                    tv_xia_order_money.setText(Html.fromHtml("订单金额: <font color=\"#FF2700\">"+new DecimalFormat("0.00").format(0)+"元</font>"));
                }else{
                    tv_xia_order_money.setText(Html.fromHtml("订单金额: <font color=\"#FF2700\">"+new DecimalFormat("0.00").format(orderMoney)+"元</font>"));
                }
                break;
            case R.id.tv_car_fuwu8:
                if (tv_car_fuwu8.isSelected()==true){
                    tv_car_fuwu8.setSelected(false);
                    fuwuTypeMoney8 = 0;
                    comboProductId8="";
                }else{
                    tv_car_fuwu8.setSelected(true);
                    for (int j=0;j<comboData.productList.size();j++){
                        if (comboData.productList.get(j).productId==8){
                            fuwuTypeMoney8 = comboData.productList.get(j).price;
                            comboProductId8= comboData.productList.get(j).comboProductId+",";
                        }
                    }
                }
                //套餐金额=基础套餐金额+fuwuTypeMoney6
                comboMoney = comboData.basicPrice+fuwuTypeMoney6+fuwuTypeMoney7+fuwuTypeMoney8;
                //订单金额=(基础套餐金额+选择服务项金额)-优惠券金额-活动金额
                orderMoney = comboMoney-couponMoney-activityMoney;
                if (orderMoney<0){
                    tv_xia_order_money.setText(Html.fromHtml("订单金额: <font color=\"#FF2700\">"+new DecimalFormat("0.00").format(0)+"元</font>"));
                }else{
                    tv_xia_order_money.setText(Html.fromHtml("订单金额: <font color=\"#FF2700\">"+new DecimalFormat("0.00").format(orderMoney)+"元</font>"));
                }
                break;
            case R.id.ll_set_type1:
                if (ll_fuwu_detail.getVisibility()==View.VISIBLE){
                    ll_fuwu_detail.setVisibility(View.GONE);
                }else{
                    ll_fuwu_detail.setVisibility(View.VISIBLE);
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
                    comboTypeId = data.get(i).typeId;
                    tv_car_info.setText(data.get(i).carNum+"  "+data.get(i).brandName+"  "+data.get(i).typeName);
                }
            }
            //没有设置默认车辆
            if (a==0){
                carNum = data.get(0).carNum;
                comboTypeId = data.get(0).typeId;
                tv_car_info.setText(data.get(0).carNum+"  "+data.get(0).brandName+"  "+data.get(0).typeName);
            }
        }
    }

    @Override
    public void appointmentListCallBack(List<AppointmentListEntity> data) {
        if (data.size()>0){
            timeDialog.updateTimeAdapter(data,Integer.valueOf(serviceType));
        }
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

    //下单（进店服务）返回数据
    @Override
    public void create2CallBack(CreateOrderEntity data) {
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
                if (comboMoney>=data.get(i).doorsillMoney){
                    if (data.get(i).money>activityMoney){
                        activityMoney = data.get(i).money;
                    }
                }
            }
            if (activityMoney>0){//大于0说明有活动
                tv_xia_order_discounts.setVisibility(View.VISIBLE);//显示优惠
                double num = activityMoney+couponMoney;//总共优惠的金额
                tv_xia_order_discounts.setText("已优惠："+new DecimalFormat("0.00").format(num)+"元");
                //订单金额=基础套餐金额+选择服务项金额-优惠券金额-活动金额
                orderMoney = comboMoney+fuwuTypeMoney6+fuwuTypeMoney7+fuwuTypeMoney8-couponMoney-activityMoney;
                if (orderMoney<0){
                    tv_xia_order_money.setText(Html.fromHtml("订单金额: <font color=\"#FF2700\">"+new DecimalFormat("0.00").format(0)+"元</font>"));
                }else{
                    tv_xia_order_money.setText(Html.fromHtml("订单金额: <font color=\"#FF2700\">"+new DecimalFormat("0.00").format(orderMoney)+"元</font>"));
                }
            }
        }
    }

    //组合套餐数据
    @Override
    public void comboInfoCallBack(ProductInfoEntity proData) {
        productInfoEntity = proData;
        setService(productInfoEntity);
        mPresenter.queryMyCoupon(0);//优惠券
        mPresenter.getCarList();//车辆列表
        mPresenter.getActivities();//活动
    }

    //设置服务选项UI
    private void setService(ProductInfoEntity data){
        //默认拿 默认车型洗车组合套餐，如果没有默认车型拿第一个洗车组合套餐
        //默认车型comboTypeId==1 SUV
        int a =0;
        for (int i=0;i<data.combo.size();i++){
            if (comboTypeId.equals(data.combo.get(i).comboTypeId)){
                a++;
                //默认车型数据
                comboData = data.combo.get(i);
                comboId = data.combo.get(i).comboTypeId;
            }
        }
        if (a==0){
            comboData = data.combo.get(0);
            comboId = data.combo.get(0).comboTypeId;
        }
        //拿前五项基本套餐id(--------------)
        comboProductId0="";
        for (int i=0;i<5;i++){
            comboProductId0 += comboData.productList.get(i).comboProductId+",";
        }
        //如果是推荐套餐,根据选择拿对应套餐id
        if (!AppUtils.isEmpty(recommendComboInfoEntity)){
            for (int j=0;j<comboData.productList.size();j++){
                if (tv_car_fuwu6.isSelected()==true){
                    if (comboData.productList.get(j).productId==6){
                        comboProductId6= comboData.productList.get(j).comboProductId+",";
                    }
                }
                if (tv_car_fuwu7.isSelected()==true){
                    if (comboData.productList.get(j).productId==7){
                        comboProductId7= comboData.productList.get(j).comboProductId+",";
                    }
                }
                if (tv_car_fuwu8.isSelected()==true){
                    if (comboData.productList.get(j).productId==8){
                        comboProductId8= comboData.productList.get(j).comboProductId+",";
                    }
                }
            }
        }

        //筛选后车型数据（设置每项对应的套餐价格）
        for (int j=0;j<comboData.productList.size();j++){
            if (comboData.productList.get(j).productId==1){
                tv_car_fuwu1_money.setText("+￥"+comboData.productList.get(j).price);
            }else if (comboData.productList.get(j).productId==2){
                tv_car_fuwu2_money.setText("+￥"+comboData.productList.get(j).price);
            }else if (comboData.productList.get(j).productId==3){
                tv_car_fuwu3_money.setText("+￥"+comboData.productList.get(j).price);
            }else if (comboData.productList.get(j).productId==4){
                tv_car_fuwu4_money.setText("+￥"+comboData.productList.get(j).price);
            }else if (comboData.productList.get(j).productId==5){
                tv_car_fuwu5_money.setText("+￥"+comboData.productList.get(j).price);
            }else if (comboData.productList.get(j).productId==6){
                if (tv_car_fuwu6.isSelected()==true){
                    fuwuTypeMoney6 = comboData.productList.get(j).price;
                }
                tv_car_fuwu6_money.setText("+￥"+new DecimalFormat("0.00").format(comboData.productList.get(j).price));
            }else if (comboData.productList.get(j).productId==7){
                if (tv_car_fuwu7.isSelected()==true){
                    fuwuTypeMoney7 = comboData.productList.get(j).price;
                }
                tv_car_fuwu7_money.setText("+￥"+new DecimalFormat("0.00").format(comboData.productList.get(j).price));
            }else if (comboData.productList.get(j).productId==8){
                if (tv_car_fuwu8.isSelected()==true){
                    fuwuTypeMoney8 = comboData.productList.get(j).price;
                }
                tv_car_fuwu8_money.setText("+￥"+new DecimalFormat("0.00").format(comboData.productList.get(j).price));
            }else{
                //外加服务
            }
        }
        //套餐金额=基础套餐金额
        comboMoney = comboData.basicPrice;
        //订单金额=基础套餐金额+选择服务项金额-优惠券金额-活动金额
        orderMoney = comboMoney+fuwuTypeMoney6+fuwuTypeMoney7+fuwuTypeMoney8-couponMoney-activityMoney;
        if (orderMoney<0){
            tv_xia_order_money.setText(Html.fromHtml("订单金额: <font color=\"#FF2700\">"+new DecimalFormat("0.00").format(0)+"元</font>"));
        }else{
            tv_xia_order_money.setText(Html.fromHtml("订单金额: <font color=\"#FF2700\">"+new DecimalFormat("0.00").format(orderMoney)+"元</font>"));
        }
    }
}
