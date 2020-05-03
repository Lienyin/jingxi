package com.jxxc.jingxi.ui.setmealpayinfo;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.adapter.ActivityDataAdapter;
import com.jxxc.jingxi.adapter.OrderPaySetMealAdapter;
import com.jxxc.jingxi.adapter.RecommendSetMealAdapter;
import com.jxxc.jingxi.adapter.ShopListAdapter;
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
import com.jxxc.jingxi.entity.backparameter.companyListEntity;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.ui.addressdetails.AddressDetailsActivity;
import com.jxxc.jingxi.ui.mapjingsi.MapJingSiActivity;
import com.jxxc.jingxi.ui.mycar.MyCarActivity;
import com.jxxc.jingxi.ui.orderdetails.OrderDetailsActivity;
import com.jxxc.jingxi.ui.payorder.PayOrderActivity;
import com.jxxc.jingxi.ui.remark.RemarkActivity;
import com.jxxc.jingxi.ui.shopdetails.ShopDetailsActivity;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.utils.AppUtils;
import com.jxxc.jingxi.utils.GlideImgManager;
import com.jxxc.jingxi.utils.ListViewForScrollView;
import com.jxxc.jingxi.utils.SPUtils;
import com.jxxc.jingxi.utils.StatusBarUtil;
import com.jxxc.jingxi.utils.ZQImageViewRoundOval;
import com.yuyh.easyadapter.helper.ViewHelper;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class SetMealPayInfoActivity extends MVPBaseActivity<SetMealPayInfoContract.View, SetMealPayInfoPresenter> implements SetMealPayInfoContract.View, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_fuwu_name)
    TextView tv_fuwu_name;
    @BindView(R.id.tv_fuwu_money)
    TextView tv_fuwu_money;
    @BindView(R.id.tv_fuwu_time)
    TextView tv_fuwu_time;
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
    @BindView(R.id.lv_set_meal_data)
    ListViewForScrollView lv_set_meal_data;
    @BindView(R.id.iv_car_fuwu1)
    ImageView iv_car_fuwu1;
    @BindView(R.id.iv_car_fuwu2)
    ImageView iv_car_fuwu2;
    @BindView(R.id.iv_car_fuwu3)
    ImageView iv_car_fuwu3;
    @BindView(R.id.iv_car_fuwu4)
    ImageView iv_car_fuwu4;
    @BindView(R.id.iv_car_fuwu5)
    ImageView iv_car_fuwu5;
    @BindView(R.id.iv_car_fuwu6)
    ImageView iv_car_fuwu6;
    @BindView(R.id.iv_car_fuwu7)
    ImageView iv_car_fuwu7;
    @BindView(R.id.iv_car_fuwu8)
    ImageView iv_car_fuwu8;
    @BindView(R.id.view_qiye)
    View view_qiye;
    @BindView(R.id.et_var_type_1)
    EditText et_var_type_1;
    @BindView(R.id.et_var_type_2)
    EditText et_var_type_2;
    @BindView(R.id.et_var_type_3)
    EditText et_var_type_3;
    @BindView(R.id.ll_order_sty)
    LinearLayout ll_order_sty;
    @BindView(R.id.ll_fuwu_item1)
    LinearLayout ll_fuwu_item1;
    @BindView(R.id.ll_fuwu_item2)
    LinearLayout ll_fuwu_item2;
    @BindView(R.id.ll_fuwu_item3)
    LinearLayout ll_fuwu_item3;
    @BindView(R.id.ll_fuwu_item4)
    LinearLayout ll_fuwu_item4;
    @BindView(R.id.ll_fuwu_item5)
    LinearLayout ll_fuwu_item5;
    @BindView(R.id.ll_fuwu_item6)
    LinearLayout ll_fuwu_item6;
    @BindView(R.id.ll_fuwu_item7)
    LinearLayout ll_fuwu_item7;
    @BindView(R.id.ll_fuwu_item8)
    LinearLayout ll_fuwu_item8;
    @BindView(R.id.ll_shangmen_daodian_btn)
    LinearLayout ll_shangmen_daodian_btn;
    @BindView(R.id.rb_shangmen_service)
    RadioButton rb_shangmen_service;
    @BindView(R.id.rb_daodian_service)
    RadioButton rb_daodian_service;
    @BindView(R.id.shang_men)
    View shang_men;
    @BindView(R.id.view_daodian_fuwu)
    View view_daodian_fuwu;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.rv_list)
    RecyclerView rv_list;

    private String siteLat = "";
    private String siteLng = "";
    private String queryFlag="";//筛选 1直营 2加盟 3合作 4营业中
    private String sort="";
    private String cityId="";
    private TimeDialog timeDialog;
    private String appointmentStartTime = "";
    private String appointmentEndTime = "";
    private DiscountCouponDialog discountCouponDialog;//优惠券对话框
    private List<MyCoupon> myCouponList = new ArrayList<>();
    private List<RecommendComboInfoEntity> recommendComboInfoEntity;
    private String serviceType = "";
    private String counponId = "";
    private String comboId = "0";
    private String carNum = "";
    private String companyId = "";//运营商ID 进店类型必传 上门不用传
    private String remark = "";
    private String address = "";
    private String companyName = "";
    private String gotoType = "";//0从洗车按钮进来 1从预约上门进来
    private double orderMoney = 0;//订单金额
    private double comboMoney = 0;//套餐金额
    private double couponMoney = 0;//优惠券金额
    private double activityMoney = 0;//活动金额
    private ActivityDataAdapter activityDataAdapter;
    private List<ActivitiesEntity> activitiesEntityList = new ArrayList<>();
    private ProductInfoEntity productInfoEntity = new ProductInfoEntity();
    private String productIds = "";//企业服务id
    private String productIds0 = "";
    private String productIds6 = "";
    private String productIds7 = "";
    private String productIds8 = "";
    private String comboTypeId = "";//个人服务id
    private String comboProductId0 = "";
    private String comboProductId6 = "";
    private String comboProductId7 = "";
    private String comboProductId8 = "";
    private String comboProductIds = "";
    private String comboRecommendIds = "";//进店套餐id
    private double fuwuTypeMoney6 = 0;
    private double fuwuTypeMoney7 = 0;
    private double fuwuTypeMoney8 = 0;
    private ProductInfoEntity.Combo comboData = new ProductInfoEntity.Combo();//车型套餐数据
    private OrderPaySetMealAdapter recommendSetMealAdapter;
    private int carType = 0;
    private String OrderId = "";
    private int offset = 2;
    private ShopListAdapter shopListAdapter;
    private List<companyListEntity> companyListEntityList = new ArrayList<>();

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
        companyName = getIntent().getStringExtra("companyName");//店铺地址
        gotoType = getIntent().getStringExtra("gotoType");//0从洗车按钮进来 1从预约上门进来
        if ("1".equals(gotoType)){
            ll_shangmen_daodian_btn.setVisibility(View.GONE);
        }else{
            ll_shangmen_daodian_btn.setVisibility(View.VISIBLE);
        }
        carType = SPUtils.get(SPUtils.K_CAR_TYPE, 0);//获取车型
        SPUtils.put(SPUtils.K_COMPANY_ID, companyId);
        if (!AppUtils.isEmpty(appointmentStartTime)) {
            tv_appointment_time.setText(appointmentStartTime.substring(10) + "—至—" + appointmentEndTime.substring(10));
        }
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(getResources().getColor(R.color.public_all));
        siteLat = SPUtils.get("lat","");
        siteLng = SPUtils.get("lng","");
        tv_phone_number.setText(SPUtils.get(SPUtils.K_SESSION_MOBILE, ""));//手机号码
        //获取当前日期
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String queryDate = formatter.format(date);//今天日期
        mPresenter.appointmentList(companyId, queryDate);//查询预约时间
        mPresenter.getCarList();//车辆列表

        if (!AppUtils.isEmpty(companyId)) {
            recommendComboInfoEntity = (List<RecommendComboInfoEntity>) getIntent().getSerializableExtra("recommendComboInfoEntity");
            tv_title.setText("到店洗车");
            tv_address_text.setText("预约门店");
            tv_car_address.setText(companyName);
            tv_car_address.setClickable(true);
            //到店服务
            ll_set_type1.setVisibility(View.VISIBLE);
            ll_set_type2.setVisibility(View.GONE);
            //套餐信息
            //设置套餐
            recommendSetMealAdapter = new OrderPaySetMealAdapter(this);
            recommendSetMealAdapter.setData(recommendComboInfoEntity, 2, carType);
            lv_set_meal_data.setAdapter(recommendSetMealAdapter);
            comboMoney = 0;
            comboRecommendIds = "";
            for (int i = 0; i < recommendComboInfoEntity.size(); i++) {
                for (int j = 0; j < recommendComboInfoEntity.get(i).carTypePrices.size(); j++) {
                    if (recommendComboInfoEntity.get(i).carTypePrices.get(j).carTypeId == carType) {//默认车型
                        comboMoney += recommendComboInfoEntity.get(i).carTypePrices.get(j).totalPrice;//套餐金额
                    }
                }
                comboRecommendIds += recommendComboInfoEntity.get(i).comboId + ",";
            }
            //订单金额=套餐金额-活动金额-优惠券金额
            orderMoney = comboMoney - activityMoney - couponMoney;
            if (orderMoney < 0) {
                tv_xia_order_money.setText(Html.fromHtml("订单金额: <font color=\"#FF2700\">" + new DecimalFormat("0.00").format(0) + "元</font>"));
            } else {
                tv_xia_order_money.setText(Html.fromHtml("订单金额: <font color=\"#FF2700\">" + new DecimalFormat("0.00").format(orderMoney) + "元</font>"));
            }
        } else {
            //上门服务
            tv_address_text.setText("停车地址");
            if (!AppUtils.isEmpty(address)){
                tv_car_address.setText(address);//订单详情再来一单过来
            }else if (!AppUtils.isEmpty(SPUtils.get(SPUtils.K_ADDRESS,""))){
                tv_car_address.setText(SPUtils.get(SPUtils.K_ADDRESS,""));//默认显示当前定位地址
            }

            //默认拿当前时间
            Time t=new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
            t.setToNow(); // 取得系统时间。
            int year = t.year;
            int month = t.month+1;
            int day = t.monthDay;
            int hour = t.hour; // 0-23
            String minute = "";
            if (t.minute<10){
                minute = "0"+t.minute;
            }else{
                minute = ""+t.minute;
            }
            int second = t.second;
            appointmentStartTime = year+"-"+month+"-"+day+"  "+hour+":"+minute;
            appointmentEndTime = year+"-"+month+"-"+day+"  "+(hour+1)+":"+minute;
            tv_appointment_time.setText(appointmentStartTime+"—至—"+appointmentEndTime);

            tv_title.setText("上门洗车");
            ll_set_type1.setVisibility(View.GONE);
            ll_set_type2.setVisibility(View.VISIBLE);
            //默认选择前5项套餐
            tv_car_fuwu1.setSelected(true);
            iv_car_fuwu1.setSelected(true);
            tv_car_fuwu2.setSelected(true);
            iv_car_fuwu2.setSelected(true);
            tv_car_fuwu3.setSelected(true);
            iv_car_fuwu3.setSelected(true);
            tv_car_fuwu4.setSelected(true);
            iv_car_fuwu4.setSelected(true);
            tv_car_fuwu5.setSelected(true);
            iv_car_fuwu5.setSelected(true);
        }

        if (!AppUtils.isEmpty(companyId)) {
            tv_hint1.setBackgroundColor(getResources().getColor(R.color.qqq));
            tv_hint2.setBackgroundColor(getResources().getColor(R.color.white));
        } else {
            tv_hint1.setBackgroundColor(getResources().getColor(R.color.white));
            tv_hint2.setBackgroundColor(getResources().getColor(R.color.qqq));
        }

        registerReceiver(receiver, new IntentFilter("jingxi_car_addres_12002"));
        registerReceiver(receiverCarInfo, new IntentFilter("jing_xi_my_car_info"));
        registerReceiver(receiverRemark, new IntentFilter("jingxi_user_remark_209344"));


        timeDialog = new TimeDialog(this);
        timeDialog.setOnFenxiangClickListener(new TimeDialog.OnFenxiangClickListener() {
            @Override
            public void onFenxiangClick(String time, String startTime, String endTime, int type) {
                if (type == 0) {
                    //刷新时间段
                    mPresenter.appointmentList(companyId, time);
                } else {
                    //获得时间段
                    appointmentStartTime = startTime;
                    appointmentEndTime = endTime;
                    tv_appointment_time.setText(appointmentStartTime.substring(10) + "—至—" + appointmentEndTime.substring(10));
                    timeDialog.cleanDialog();
                }
            }
        });
        discountCouponDialog = new DiscountCouponDialog(this);
        discountCouponDialog.setOnFenxiangClickListener(new DiscountCouponDialog.OnFenxiangClickListener() {
            @Override
            public void onFenxiangClick(MyCoupon coupon) {
                if (!"不使用优惠券".equals(coupon.counponName)) {
                    //优惠券
                    counponId = coupon.counponId + "";
                    //优惠券类型 0无门槛减N 1满N减N 2折扣券
                    if (coupon.couponRuleType == 0||coupon.couponRuleType == 1) {
                        couponMoney = coupon.money;
                    } else {
                        //折扣券
                        double num = comboMoney - activityMoney;
                        couponMoney = num - (num * (coupon.discount / 10));
                    }
                    if (coupon.couponRuleType == 0||coupon.couponRuleType == 1) {
                        tv_discounts.setText(new DecimalFormat("0.00").format(coupon.money) + "元优惠券");
                    } else {
                        tv_discounts.setText(new DecimalFormat("0.00").format(coupon.discount) + "折优惠券");
                    }
                } else {
                    counponId = "";
                    couponMoney = 0;
                    tv_discounts.setText("");
                }
                //订单金额=套餐金额-活动金额-优惠券金额
                orderMoney = comboMoney - activityMoney - couponMoney;
                if (orderMoney < 0) {
                    tv_xia_order_money.setText(Html.fromHtml("订单金额: <font color=\"#FF2700\">" + new DecimalFormat("0.00").format(0) + "元</font>"));
                } else {
                    tv_xia_order_money.setText(Html.fromHtml("订单金额: <font color=\"#FF2700\">" + new DecimalFormat("0.00").format(orderMoney) + "元</font>"));
                }

                //优惠布局设置
                double num = couponMoney + activityMoney;//优惠券金额+订单金额
                if (orderMoney > 0){
                    tv_xia_order_discounts.setText("已优惠：" + new DecimalFormat("0.00").format(num) + "元");
                }else{
                    if (num > orderMoney){
                        tv_xia_order_discounts.setText("已优惠：" + new DecimalFormat("0.00").format(comboMoney) + "元");
                    }else{
                        tv_xia_order_discounts.setText("已优惠：" + new DecimalFormat("0.00").format(num) + "元");
                    }
                }
                if (num > 0){
                    tv_xia_order_discounts.setVisibility(View.VISIBLE);
                }else {
                    tv_xia_order_discounts.setVisibility(View.GONE);
                }
            }
        });

        //显示企业模块
        if ("1".equals(SPUtils.get(SPUtils.K_ROLE, "0"))) {
            view_qiye.setVisibility(View.VISIBLE);
            ll_car_info.setVisibility(View.GONE);
            ll_order_sty.setVisibility(View.INVISIBLE);
        } else {
            view_qiye.setVisibility(View.GONE);
            ll_car_info.setVisibility(View.VISIBLE);
            ll_order_sty.setVisibility(View.VISIBLE);
        }
        et_var_type_1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("TAG", "afterTextChanged 被执行---->" + s);
                //算价格
                if (!AppUtils.isEmpty(s.toString())) {
                    int carNum = Integer.valueOf(s.toString());
                    //订单金额=套餐金额X数量-活动金额-优惠券金额
                    orderMoney = (comboMoney * carNum) - activityMoney - couponMoney;
                    if (orderMoney < 0) {
                        tv_xia_order_money.setText(Html.fromHtml("订单金额: <font color=\"#FF2700\">" + new DecimalFormat("0.00").format(0) + "元</font>"));
                    } else {
                        tv_xia_order_money.setText(Html.fromHtml("订单金额: <font color=\"#FF2700\">" + new DecimalFormat("0.00").format(orderMoney) + "元</font>"));
                    }
                }
            }
        });

        initAdapter();
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
            tv_car_info.setText(carListEntity.carNum + "  " + carListEntity.brandName + "  " + carListEntity.typeName);
            carNum = carListEntity.carNum;
            comboTypeId = carListEntity.typeId + "";
            if (!AppUtils.isEmpty(companyId)) {
                recommendSetMealAdapter.setData(recommendComboInfoEntity, 2, carListEntity.typeId);
                recommendSetMealAdapter.notifyDataSetChanged();
                comboMoney = 0;
                comboRecommendIds = "";
                for (int i = 0; i < recommendComboInfoEntity.size(); i++) {
                    for (int j = 0; j < recommendComboInfoEntity.get(i).carTypePrices.size(); j++) {
                        if (recommendComboInfoEntity.get(i).carTypePrices.get(j).carTypeId == carListEntity.typeId) {//默认车型
                            comboMoney += recommendComboInfoEntity.get(i).carTypePrices.get(j).totalPrice;//套餐金额
                        }
                    }
                    comboRecommendIds += recommendComboInfoEntity.get(i).comboId + ",";
                }
                //订单金额=套餐金额-活动金额-优惠券金额
                orderMoney = comboMoney - activityMoney - couponMoney;
                if (orderMoney < 0) {
                    tv_xia_order_money.setText(Html.fromHtml("订单金额: <font color=\"#FF2700\">" + new DecimalFormat("0.00").format(0) + "元</font>"));
                } else {
                    tv_xia_order_money.setText(Html.fromHtml("订单金额: <font color=\"#FF2700\">" + new DecimalFormat("0.00").format(orderMoney) + "元</font>"));
                }
            } else {
                setService(productInfoEntity);
                tv_car_fuwu6.setSelected(false);
                iv_car_fuwu6.setSelected(false);
                tv_car_fuwu7.setSelected(false);
                iv_car_fuwu7.setSelected(false);
                tv_car_fuwu8.setSelected(false);
                iv_car_fuwu8.setSelected(false);
                fuwuTypeMoney6 = 0;
                comboProductId6 = "";
                fuwuTypeMoney7 = 0;
                comboProductId7 = "";
                fuwuTypeMoney8 = 0;
                comboProductId8 = "";
            }

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

    @OnClick({R.id.tv_back, R.id.ll_stop_car_address, R.id.ll_car_info, R.id.ll_yuyue_time,
            R.id.ll_discount_coupon, R.id.tv_create_order, R.id.ll_remark,
            R.id.tv_car_fuwu6, R.id.tv_car_fuwu7, R.id.tv_car_fuwu8, R.id.ll_set_type1,
            R.id.iv_car_fuwu6, R.id.iv_car_fuwu7, R.id.iv_car_fuwu8,R.id.rb_shangmen_service,
            R.id.rb_daodian_service})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
            case R.id.rb_shangmen_service://上门
                serviceType = "0";
                shang_men.setVisibility(View.VISIBLE);
                view_daodian_fuwu.setVisibility(View.GONE);
                break;
            case R.id.rb_daodian_service://到店
                //企业账户不提供到店服务
                if ("1".equals(SPUtils.get(SPUtils.K_ROLE,"0"))){
                    Toast.makeText(this,"企业账户不提供到店服务",Toast.LENGTH_SHORT).show();
                    return;
                }
                onRefresh();
                shang_men.setVisibility(View.GONE);
                view_daodian_fuwu.setVisibility(View.VISIBLE);
                serviceType = "1";
                break;
            case R.id.ll_stop_car_address://停车地址
                //ZzRouter.gotoActivity(this, MapJingSiActivity.class);
                ZzRouter.gotoActivity(this, AddressDetailsActivity.class);
                break;
            case R.id.ll_car_info://爱车信息
                ZzRouter.gotoActivity(this, MyCarActivity.class, "1");
                break;
            case R.id.ll_yuyue_time://服务时间
                timeDialog.showShareDialog(true);
                break;
            case R.id.ll_discount_coupon://优惠券
                if (myCouponList.size() > 0) {
                    discountCouponDialog.showShareDialog(true, myCouponList, comboMoney);
                } else {
                    toast(this, "暂无优惠券可用");
                }
                break;
            case R.id.ll_remark://备注
                ZzRouter.gotoActivity(this, RemarkActivity.class);
                break;
            case R.id.tv_create_order://提交订单
                if (AppUtils.isEmpty(companyId)) {//上门
                    if (AppUtils.isEmpty(tv_car_info.getText().toString()) &&
                            "0".equals(SPUtils.get(SPUtils.K_ROLE, "0"))) {
                        toast(this, "请添加车辆");
                    } else if (AppUtils.isEmpty(tv_car_address.getText().toString())) {
                        toast(this, "请选择停车地点");
                    } else if (AppUtils.isEmpty(tv_appointment_time.getText().toString())) {
                        toast(this, "请选择服务时间");
                    } else {
                        String carNumStr = "";
                        if ("1".equals(SPUtils.get(SPUtils.K_ROLE, "0"))) {
                            //企业车辆数目统计
                            int jiaoche = 0;
                            int suv = 0;
                            int mpv = 0;
                            if (AppUtils.isEmpty(et_var_type_1.getText().toString().trim())) {
                                jiaoche = 0;
                            } else {
                                jiaoche = Integer.valueOf(et_var_type_1.getText().toString().trim());
                            }
                            if (AppUtils.isEmpty(et_var_type_2.getText().toString().trim())) {
                                suv = 0;
                            } else {
                                suv = Integer.valueOf(et_var_type_2.getText().toString().trim());
                            }
                            if (AppUtils.isEmpty(et_var_type_3.getText().toString().trim())) {
                                mpv = 0;
                            } else {
                                mpv = Integer.valueOf(et_var_type_3.getText().toString().trim());
                            }
                            carNumStr = jiaoche + "," + suv + "," + mpv;
                            //企业账户
                            if (jiaoche == 0 && suv == 0 && mpv == 0) {
                                toast(this, "请输入数量");
                                return;
                            }
                        }
                        StyledDialog.buildLoading("正在下单").setActivity(this).show();
                        comboProductIds = comboProductId0 + comboProductId6 + comboProductId7 + comboProductId8;
                        productIds = productIds0 + productIds6 + productIds7 + productIds8;
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
                                comboId,
                                productIds,
                                carNumStr);
                    }
                } else {
                    //进店
                    if (AppUtils.isEmpty(tv_car_info.getText().toString())) {
                        toast(this, "请添加车辆");
                    } else if (AppUtils.isEmpty(tv_appointment_time.getText().toString())) {
                        toast(this, "请选择服务时间");
                    } else {
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
                                comboRecommendIds);
                    }
                }
                break;
            case R.id.tv_car_fuwu6:
            case R.id.iv_car_fuwu6:
                if (tv_car_fuwu6.isSelected() == true || iv_car_fuwu6.isSelected() == true) {
                    tv_car_fuwu6.setSelected(false);
                    iv_car_fuwu6.setSelected(false);
                    fuwuTypeMoney6 = 0;
                    comboProductId6 = "";
                    productIds6 = "";
                } else {
                    tv_car_fuwu6.setSelected(true);
                    iv_car_fuwu6.setSelected(true);
                    for (int j = 0; j < comboData.productList.size(); j++) {
                        if (comboData.productList.get(j).productId == 6) {
                            fuwuTypeMoney6 = comboData.productList.get(j).price;
                            comboProductId6 = comboData.productList.get(j).comboProductId + ",";
                            productIds6 = comboData.productList.get(j).productId + ",";
                        }
                    }
                }
                //套餐金额=基础套餐金额+fuwuTypeMoney6
                comboMoney = comboData.basicPrice + fuwuTypeMoney6 + fuwuTypeMoney7 + fuwuTypeMoney8;
                //订单金额=(基础套餐金额+选择服务项金额)-优惠券金额-活动金额
                orderMoney = comboMoney - couponMoney - activityMoney;
                if (orderMoney < 0) {
                    tv_xia_order_money.setText(Html.fromHtml("订单金额: <font color=\"#FF2700\">" + new DecimalFormat("0.00").format(0) + "元</font>"));
                } else {
                    tv_xia_order_money.setText(Html.fromHtml("订单金额: <font color=\"#FF2700\">" + new DecimalFormat("0.00").format(orderMoney) + "元</font>"));
                }
                break;
            case R.id.tv_car_fuwu7:
            case R.id.iv_car_fuwu7:
                if (tv_car_fuwu7.isSelected() == true || iv_car_fuwu7.isSelected() == true) {
                    tv_car_fuwu7.setSelected(false);
                    iv_car_fuwu7.setSelected(false);
                    fuwuTypeMoney7 = 0;
                    comboProductId7 = "";
                    productIds7 = "";
                } else {
                    tv_car_fuwu7.setSelected(true);
                    iv_car_fuwu7.setSelected(true);
                    for (int j = 0; j < comboData.productList.size(); j++) {
                        if (comboData.productList.get(j).productId == 7) {
                            fuwuTypeMoney7 = comboData.productList.get(j).price;
                            comboProductId7 = comboData.productList.get(j).comboProductId + ",";
                            productIds7 = comboData.productList.get(j).productId + ",";
                        }
                    }
                }
                //套餐金额=基础套餐金额+fuwuTypeMoney7
                comboMoney = comboData.basicPrice + fuwuTypeMoney6 + fuwuTypeMoney7 + fuwuTypeMoney8;
                //订单金额=(基础套餐金额+选择服务项金额)-优惠券金额-活动金额
                orderMoney = comboMoney - couponMoney - activityMoney;
                if (orderMoney < 0) {
                    tv_xia_order_money.setText(Html.fromHtml("订单金额: <font color=\"#FF2700\">" + new DecimalFormat("0.00").format(0) + "元</font>"));
                } else {
                    tv_xia_order_money.setText(Html.fromHtml("订单金额: <font color=\"#FF2700\">" + new DecimalFormat("0.00").format(orderMoney) + "元</font>"));
                }
                break;
            case R.id.tv_car_fuwu8:
            case R.id.iv_car_fuwu8:
                if (tv_car_fuwu8.isSelected() == true || iv_car_fuwu8.isSelected() == true) {
                    tv_car_fuwu8.setSelected(false);
                    iv_car_fuwu8.setSelected(false);
                    fuwuTypeMoney8 = 0;
                    comboProductId8 = "";
                    productIds8 = "";
                } else {
                    tv_car_fuwu8.setSelected(true);
                    iv_car_fuwu8.setSelected(true);
                    for (int j = 0; j < comboData.productList.size(); j++) {
                        if (comboData.productList.get(j).productId == 8) {
                            fuwuTypeMoney8 = comboData.productList.get(j).price;
                            comboProductId8 = comboData.productList.get(j).comboProductId + ",";
                            productIds8 = comboData.productList.get(j).productId + ",";
                        }
                    }
                }
                //套餐金额=基础套餐金额+fuwuTypeMoney6
                comboMoney = comboData.basicPrice + fuwuTypeMoney6 + fuwuTypeMoney7 + fuwuTypeMoney8;
                //订单金额=(基础套餐金额+选择服务项金额)-优惠券金额-活动金额
                orderMoney = comboMoney - couponMoney - activityMoney;
                if (orderMoney < 0) {
                    tv_xia_order_money.setText(Html.fromHtml("订单金额: <font color=\"#FF2700\">" + new DecimalFormat("0.00").format(0) + "元</font>"));
                } else {
                    tv_xia_order_money.setText(Html.fromHtml("订单金额: <font color=\"#FF2700\">" + new DecimalFormat("0.00").format(orderMoney) + "元</font>"));
                }
                break;
            case R.id.ll_set_type1:
                if (ll_fuwu_detail.getVisibility() == View.VISIBLE) {
                    ll_fuwu_detail.setVisibility(View.GONE);
                } else {
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
        if (!AppUtils.isEmpty(receiverCarInfo)) {
            unregisterReceiver(receiverCarInfo);
        }
        if (!AppUtils.isEmpty(receiver)) {
            unregisterReceiver(receiver);
        }
        if (!AppUtils.isEmpty(receiverRemark)) {
            unregisterReceiver(receiverRemark);
        }
    }

    //车辆列表返回数据
    @Override
    public void getCarListCallBack(List<CarListEntity> data) {
        if (data.size() > 0) {//y有车
            //展示默认车辆，没有默认车辆展示第一辆
            //是否默认 1是0否
            int a = 0;
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).isDefault == 1) {
                    a++;
                    carNum = data.get(i).carNum;
                    comboTypeId = data.get(i).typeId + "";
                    tv_car_info.setText(data.get(i).carNum + "  " + data.get(i).brandName + "  " + data.get(i).typeName);
                    SPUtils.put(SPUtils.K_CAR_TYPE, comboTypeId);
                }
            }
            //没有设置默认车辆
            if (a == 0) {
                carNum = data.get(0).carNum;
                comboTypeId = data.get(0).typeId + "";
                tv_car_info.setText(data.get(0).carNum + "  " + data.get(0).brandName + "  " + data.get(0).typeName);
                SPUtils.put(SPUtils.K_CAR_TYPE, comboTypeId);
            }
        }
        mPresenter.comboInfo();//查套餐
    }

    @Override
    public void appointmentListCallBack(List<AppointmentListEntity> data) {
        if (data.size() > 0) {
            timeDialog.updateTimeAdapter(data, Integer.valueOf(serviceType));
        }
    }

    //优惠券返回数据
    @Override
    public void queryMyCouponCallback(List<MyCoupon> data) {
        if (data.size() > 0) {
            myCouponList = data;

            double a = 0;
            int pos = 0;
            for (int i=0;i < data.size();i++){
                if (data.get(i).money>a){
                    a = data.get(i).money;
                    pos = i;
                }
            }
            //优惠券
            counponId = data.get(pos).counponId + "";
            tv_discounts.setText(new DecimalFormat("0.00").format(a) + "元优惠券");
            //优惠券类型 0无门槛减N 1满N减N 2折扣券
            if (data.get(pos).couponRuleType == 0||data.get(pos).couponRuleType == 1) {
                couponMoney = data.get(pos).money;
            } else {
                //折扣券
                double num = comboMoney - activityMoney;
                couponMoney = num - (num * (data.get(pos).discount / 10));
            }
            //订单金额=套餐金额-活动金额-优惠券金额
            orderMoney = comboMoney - activityMoney - couponMoney;
            if (orderMoney < 0) {
                tv_xia_order_money.setText(Html.fromHtml("订单金额: <font color=\"#FF2700\">" + new DecimalFormat("0.00").format(0) + "元</font>"));
            } else {
                tv_xia_order_money.setText(Html.fromHtml("订单金额: <font color=\"#FF2700\">" + new DecimalFormat("0.00").format(orderMoney) + "元</font>"));
            }

            double num = couponMoney + activityMoney;
            if (orderMoney > 0){
                tv_xia_order_discounts.setText("已优惠：" + new DecimalFormat("0.00").format(num) + "元");
            }else{
                if (num > orderMoney){
                    tv_xia_order_discounts.setText("已优惠：" + new DecimalFormat("0.00").format(comboMoney) + "元");
                }else{
                    tv_xia_order_discounts.setText("已优惠：" + new DecimalFormat("0.00").format(num) + "元");
                }
            }
            if (num > 0){
                tv_xia_order_discounts.setVisibility(View.VISIBLE);
            }else {
                tv_xia_order_discounts.setVisibility(View.GONE);
            }
        }

    }

    //提交订单返回数据
    @Override
    public void createOrderCallBack(CreateOrderEntity data) {
        if ("1".equals(SPUtils.get(SPUtils.K_ROLE, "0"))) {
            OrderId = data.orderId;
            mPresenter.BalancePay(data.orderId);
        } else {
            //支付订单
            Intent intent = new Intent(this, PayOrderActivity.class);
            intent.putExtra("orderId", data.orderId);
            intent.putExtra("orderPrice", data.payPrice);
            startActivity(intent);
        }
    }

    //下单（进店服务）返回数据
    @Override
    public void create2CallBack(CreateOrderEntity data) {
        //支付订单
        Intent intent = new Intent(this, PayOrderActivity.class);
        intent.putExtra("orderId", data.orderId);
        intent.putExtra("orderPrice", data.payPrice);
        startActivity(intent);
        finish();
    }

    //企业余额支付
    @Override
    public void BalancePayCallBack() {
        ZzRouter.gotoActivity(this, OrderDetailsActivity.class, OrderId);
    }

    //活动数据
    @Override
    public void getActivitiesCallBack(List<ActivitiesEntity> data) {
        //组装活动
        if (data.size() > 0) {
            activitiesEntityList = data;
            activityDataAdapter = new ActivityDataAdapter(this);
            activityDataAdapter.setData(data);
            activity_data.setAdapter(activityDataAdapter);

            for (int i = 0; i < data.size(); i++) {
                if (comboMoney >= data.get(i).doorsillMoney) {
                    if (data.get(i).money > activityMoney) {
                        activityMoney = data.get(i).money;
                    }
                }
            }
            if (activityMoney > 0) {//大于0说明有活动
                //订单金额=基础套餐金额+选择服务项金额-优惠券金额-活动金额
                orderMoney = comboMoney + fuwuTypeMoney6 + fuwuTypeMoney7 + fuwuTypeMoney8 - couponMoney - activityMoney;
                if (orderMoney < 0) {
                    tv_xia_order_money.setText(Html.fromHtml("订单金额: <font color=\"#FF2700\">" + new DecimalFormat("0.00").format(0) + "元</font>"));
                } else {
                    tv_xia_order_money.setText(Html.fromHtml("订单金额: <font color=\"#FF2700\">" + new DecimalFormat("0.00").format(orderMoney) + "元</font>"));
                }
                double num = activityMoney + couponMoney;//总共优惠的金额
                if (orderMoney > 0){
                    tv_xia_order_discounts.setText("已优惠：" + new DecimalFormat("0.00").format(num) + "元");
                }else{
                    if (num > orderMoney){
                        tv_xia_order_discounts.setText("已优惠：" + new DecimalFormat("0.00").format(comboMoney) + "元");
                    }else{
                        tv_xia_order_discounts.setText("已优惠：" + new DecimalFormat("0.00").format(num) + "元");
                    }
                }
                if (num > 0){
                    tv_xia_order_discounts.setVisibility(View.VISIBLE);
                }else {
                    tv_xia_order_discounts.setVisibility(View.GONE);
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
        mPresenter.getActivities();//活动
    }

    //设置服务选项UI
    private void setService(ProductInfoEntity data) {
        //默认拿 默认车型洗车组合套餐，如果没有默认车型拿第一个洗车组合套餐
        //默认车型comboTypeId==1 SUV
        int a = 0;
        for (int i = 0; i < data.combo.size(); i++) {
            if (comboTypeId.equals(data.combo.get(i).comboTypeId)) {
                a++;
                //默认车型数据
                comboData = data.combo.get(i);
                comboId = data.combo.get(i).comboTypeId;
            }
        }
        if (a == 0) {
            comboData = data.combo.get(0);
            comboId = data.combo.get(0).comboTypeId;
        }
        //拿前五项基本套餐id(--------------)
        comboProductId0 = "";
        productIds0 = "";
        for (int i = 0; i < 5; i++) {
            comboProductId0 += comboData.productList.get(i).comboProductId + ",";
            productIds0 += comboData.productList.get(i).productId + ",";
        }
        //如果是推荐套餐,根据选择拿对应套餐id
        if (!AppUtils.isEmpty(recommendComboInfoEntity)) {
            for (int j = 0; j < comboData.productList.size(); j++) {
                if (tv_car_fuwu6.isSelected() == true || iv_car_fuwu6.isSelected() == true) {
                    if (comboData.productList.get(j).productId == 6) {
                        comboProductId6 = comboData.productList.get(j).comboProductId + ",";
                    }
                }
                if (tv_car_fuwu7.isSelected() == true || iv_car_fuwu7.isSelected() == true) {
                    if (comboData.productList.get(j).productId == 7) {
                        comboProductId7 = comboData.productList.get(j).comboProductId + ",";
                    }
                }
                if (tv_car_fuwu8.isSelected() == true || iv_car_fuwu8.isSelected() == true) {
                    if (comboData.productList.get(j).productId == 8) {
                        comboProductId8 = comboData.productList.get(j).comboProductId + ",";
                    }
                }
            }
        }

        ll_fuwu_item1.setVisibility(View.INVISIBLE);
        ll_fuwu_item2.setVisibility(View.INVISIBLE);
        ll_fuwu_item3.setVisibility(View.INVISIBLE);
        ll_fuwu_item4.setVisibility(View.INVISIBLE);
        ll_fuwu_item5.setVisibility(View.INVISIBLE);
        ll_fuwu_item6.setVisibility(View.INVISIBLE);
        ll_fuwu_item7.setVisibility(View.INVISIBLE);
        ll_fuwu_item8.setVisibility(View.INVISIBLE);
        //筛选后车型数据（设置每项对应的套餐价格）
        for (int j = 0; j < comboData.productList.size(); j++) {
            if (comboData.productList.get(j).productId == 1) {
                ll_fuwu_item1.setVisibility(View.VISIBLE);
                tv_car_fuwu1.setText(comboData.productList.get(j).productName);
                tv_car_fuwu1_money.setText("+￥" + comboData.productList.get(j).price);
            } else if (comboData.productList.get(j).productId == 2) {
                ll_fuwu_item2.setVisibility(View.VISIBLE);
                tv_car_fuwu2.setText(comboData.productList.get(j).productName);
                tv_car_fuwu2_money.setText("+￥" + comboData.productList.get(j).price);
            } else if (comboData.productList.get(j).productId == 3) {
                ll_fuwu_item3.setVisibility(View.VISIBLE);
                tv_car_fuwu3.setText(comboData.productList.get(j).productName);
                tv_car_fuwu3_money.setText("+￥" + comboData.productList.get(j).price);
            } else if (comboData.productList.get(j).productId == 4) {
                ll_fuwu_item4.setVisibility(View.VISIBLE);
                tv_car_fuwu4.setText(comboData.productList.get(j).productName);
                tv_car_fuwu4_money.setText("+￥" + comboData.productList.get(j).price);
            } else if (comboData.productList.get(j).productId == 5) {
                ll_fuwu_item5.setVisibility(View.VISIBLE);
                tv_car_fuwu5.setText(comboData.productList.get(j).productName);
                tv_car_fuwu5_money.setText("+￥" + comboData.productList.get(j).price);
            } else if (comboData.productList.get(j).productId == 6) {
                if (tv_car_fuwu6.isSelected() == true || iv_car_fuwu6.isSelected() == true) {
                    fuwuTypeMoney6 = comboData.productList.get(j).price;
                }
                ll_fuwu_item6.setVisibility(View.VISIBLE);
                tv_car_fuwu6.setText(comboData.productList.get(j).productName);
                tv_car_fuwu6_money.setText("+￥" + new DecimalFormat("0.00").format(comboData.productList.get(j).price));
            } else if (comboData.productList.get(j).productId == 7) {
                if (tv_car_fuwu7.isSelected() == true || iv_car_fuwu7.isSelected() == true) {
                    fuwuTypeMoney7 = comboData.productList.get(j).price;
                }
                ll_fuwu_item7.setVisibility(View.VISIBLE);
                tv_car_fuwu7.setText(comboData.productList.get(j).productName);
                tv_car_fuwu7_money.setText("+￥" + new DecimalFormat("0.00").format(comboData.productList.get(j).price));
            } else if (comboData.productList.get(j).productId == 8) {
                if (tv_car_fuwu8.isSelected() == true || iv_car_fuwu8.isSelected() == true) {
                    fuwuTypeMoney8 = comboData.productList.get(j).price;
                }
                ll_fuwu_item8.setVisibility(View.VISIBLE);
                tv_car_fuwu8.setText(comboData.productList.get(j).productName);
                tv_car_fuwu8_money.setText("+￥" + new DecimalFormat("0.00").format(comboData.productList.get(j).price));
            } else {
                //外加服务
            }
        }
        //套餐金额=基础套餐金额
        comboMoney = comboData.basicPrice;
        //订单金额=基础套餐金额+选择服务项金额-优惠券金额-活动金额
        orderMoney = comboMoney + fuwuTypeMoney6 + fuwuTypeMoney7 + fuwuTypeMoney8 - couponMoney - activityMoney;
        if (orderMoney < 0) {
            tv_xia_order_money.setText(Html.fromHtml("订单金额: <font color=\"#FF2700\">" + new DecimalFormat("0.00").format(0) + "元</font>"));
        } else {
            tv_xia_order_money.setText(Html.fromHtml("订单金额: <font color=\"#FF2700\">" + new DecimalFormat("0.00").format(orderMoney) + "元</font>"));
        }

        //组装活动金额
        if (activitiesEntityList.size()>0){
            for (int i = 0; i < activitiesEntityList.size(); i++) {
                if (comboMoney >= activitiesEntityList.get(i).doorsillMoney) {
                    if (activitiesEntityList.get(i).money > activityMoney) {
                        activityMoney = activitiesEntityList.get(i).money;
                    }
                }
            }
            if (activityMoney > 0) {//大于0说明有活动
                //订单金额=基础套餐金额+选择服务项金额-优惠券金额-活动金额
                orderMoney = comboMoney + fuwuTypeMoney6 + fuwuTypeMoney7 + fuwuTypeMoney8 - couponMoney - activityMoney;
                if (orderMoney < 0) {
                    tv_xia_order_money.setText(Html.fromHtml("订单金额: <font color=\"#FF2700\">" + new DecimalFormat("0.00").format(0) + "元</font>"));
                } else {
                    tv_xia_order_money.setText(Html.fromHtml("订单金额: <font color=\"#FF2700\">" + new DecimalFormat("0.00").format(orderMoney) + "元</font>"));
                }
                double num = activityMoney + couponMoney;//总共优惠的金额
                if (orderMoney > 0){
                    tv_xia_order_discounts.setText("已优惠：" + new DecimalFormat("0.00").format(num) + "元");
                }else{
                    if (num > orderMoney){
                        tv_xia_order_discounts.setText("已优惠：" + new DecimalFormat("0.00").format(comboMoney) + "元");
                    }else{
                        tv_xia_order_discounts.setText("已优惠：" + new DecimalFormat("0.00").format(num) + "元");
                    }
                }
                if (num > 0){
                    tv_xia_order_discounts.setVisibility(View.VISIBLE);
                }else {
                    tv_xia_order_discounts.setVisibility(View.GONE);
                }
            }
        }
    }

    //下来刷新
    @Override
    public void onRefresh() {
        offset=2;
        mPresenter.companyList(Double.valueOf(siteLng),Double.valueOf(siteLat),queryFlag,sort,cityId,1,10);
    }

    @Override
    public void onLoadMoreRequested() {
        swipeLayout.setRefreshing(false);
        mPresenter.companyListMore(Double.valueOf(siteLng),Double.valueOf(siteLat),queryFlag,sort,cityId,offset,10);
    }

    //门店数据
    @Override
    public void companyListCallBack(List<companyListEntity> data) {
        companyListEntityList = data;
        swipeLayout.setRefreshing(false);
        shopListAdapter.setNewData(data);
        if (data.size() < 10) {
            shopListAdapter.loadMoreEnd();
        }else{
            shopListAdapter.disableLoadMoreIfNotFullPage();
        }
    }

    //门店数据更多
    @Override
    public void companyListCallBackMore(List<companyListEntity> data) {
        companyListEntityList.addAll(data);
        swipeLayout.setRefreshing(false);
        offset++;
        shopListAdapter.addData(data);
        shopListAdapter.loadMoreComplete();
        if (data.size() < 10) {
            shopListAdapter.loadMoreEnd();
        }
    }

    private void initAdapter() {
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(getResources().getColor(R.color.public_all));
        rv_list.setLayoutManager(new LinearLayoutManager(this));
        shopListAdapter = new ShopListAdapter(R.layout.shop_list_adapter, new ArrayList<companyListEntity>());
        rv_list.setAdapter(shopListAdapter);
        shopListAdapter.setOnLoadMoreListener(this, rv_list);
        shopListAdapter.setEmptyView(R.layout.layout_nothing);

        shopListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(SetMealPayInfoActivity.this, ShopDetailsActivity.class);
                intent.putExtra("companyId",companyListEntityList.get(position).companyId);
                intent.putExtra("distance",companyListEntityList.get(position).distance);
                startActivity(intent);
            }
        });
    }
}
