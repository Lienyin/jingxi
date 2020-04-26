package com.jxxc.jingxi.ui.main.myCarfragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.ConfigApplication;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.adapter.ActivityDataAdapter;
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
import com.jxxc.jingxi.entity.backparameter.companyListEntity;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.mvp.MVPBaseFragment;
import com.jxxc.jingxi.ui.addressdetails.AddressDetailsActivity;
import com.jxxc.jingxi.ui.mapjingsi.MapJingSiActivity;
import com.jxxc.jingxi.ui.mycar.MyCarActivity;
import com.jxxc.jingxi.ui.orderdetails.OrderDetailsActivity;
import com.jxxc.jingxi.ui.payorder.PayOrderActivity;
import com.jxxc.jingxi.ui.remark.RemarkActivity;
import com.jxxc.jingxi.ui.shopdetails.ShopDetailsActivity;
import com.jxxc.jingxi.ui.shoplist.ShopListActivity;
import com.jxxc.jingxi.adapter.CouponAdapter;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.utils.AppUtils;
import com.jxxc.jingxi.utils.ListViewForScrollView;
import com.jxxc.jingxi.utils.SPUtils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.qqtheme.framework.picker.DateTimePicker;

@SuppressLint("ValidFragment")
public class MyCarFragment extends MVPBaseFragment<MyCarFragmentContract.View, MyCarFragmentPresenter> implements MyCarFragmentContract.View, SwipeRefreshLayout.OnRefreshListener,View.OnClickListener, BaseQuickAdapter.RequestLoadMoreListener {
    private Context context;
    private TextView tv_user_name;
    private RadioButton rb_shangmen_service;
    private RadioButton rb_daodian_service;
    private RadioButton rb_wai_guan;
    private RadioButton rb_zheng_che;
    private LinearLayout ll_car_fuwu;
    private LinearLayout ll_fuwu_no_data;
    private LinearLayout ll_car_info;
    private TextView tv_car_color;
    private TextView tv_car_fuwu1;
    private TextView tv_car_fuwu2;
    private TextView tv_car_fuwu3;
    private TextView tv_car_fuwu4;
    private TextView tv_car_fuwu5;
    private TextView tv_car_fuwu6;
    private TextView tv_car_fuwu7;
    private TextView tv_car_fuwu8;
    private ImageView iv_car_fuwu1;
    private ImageView iv_car_fuwu2;
    private ImageView iv_car_fuwu3;
    private ImageView iv_car_fuwu4;
    private ImageView iv_car_fuwu5;
    private ImageView iv_car_fuwu6;
    private ImageView iv_car_fuwu7;
    private ImageView iv_car_fuwu8;
    private TextView tv_car_fuwu1_money;
    private TextView tv_car_fuwu2_money;
    private TextView tv_car_fuwu3_money;
    private TextView tv_car_fuwu4_money;
    private TextView tv_car_fuwu5_money;
    private TextView tv_car_fuwu6_money;
    private TextView tv_car_fuwu7_money;
    private TextView tv_car_fuwu8_money;
    private TextView tv_appointment_time;
    private TextView tv_create_order;
    private TextView tv_xia_order_money;
    private TextView tv_xia_order_discounts;
    private TextView tv_car_info;
    private TextView tv_user_remark;
    private ImageView iv_call_phone;
    private LinearLayout iv_time_date;
    private ListViewForScrollView lv_coupon_data;
    private TextView et_car_address;
    private TextView et_car_address_daodian;
    private TextView et_phone_number;
    private TextView tv_discounts;
    private EditText et_car_memo;
    private LinearLayout ll_daodian,ll_address,ll_shop_site;
    private LinearLayout ll_discount_coupon,ll_remark;
    private LinearLayout ll_car_info_view;
    private View shang_men,view_daodian_fuwu;
    private SwipeRefreshLayout swipeLayout;
    private RecyclerView rv_list;
    private ListViewForScrollView activity_data;
    private View view_qiye;
    private EditText et_var_type_1,et_var_type_2,et_var_type_3;
    private LinearLayout ll_order_sty;
    private int fuwu1;
    private int fuwu2;
    private int fuwu3;
    private List<MyCoupon> list = new ArrayList<>();
    private AddressEntity addressEntity = new AddressEntity();
    private String siteLat="";
    private String siteLng="";
    private String comboTypeId="";//车类型（SUN,轿车，MYVP）
    private int serviceType=0;
    private String counponId="";
    private String productIds="";//企业服务id
    private String productIds0="";
    private String productIds6="";
    private String productIds7="";
    private String productIds8="";
    private String comboProductId="";//个人服务id
    private String comboProductId0="";
    private String comboProductId6="";
    private String comboProductId7="";
    private String comboProductId8="";
    private String appointmentStartTime="";
    private String appointmentEndTime="";
    private ProductInfoEntity.Combo comboData = new ProductInfoEntity.Combo();//车型套餐数据
    private double orderMoney=0;//订单金额
    private double comboMoney=0;//套餐金额
    private double couponMoney=0;//优惠券金额
    private double activityMoney=0;//活动金额
    private double fuwuTypeMoney6=0;
    private double fuwuTypeMoney7=0;
    private double fuwuTypeMoney8=0;
    private int HuanCar=0;
    private ProductInfoEntity productInfoEntity = new ProductInfoEntity();
    private String companyId="";
    private String address="";
    private TimeDialog timeDialog;
    private int offset = 2;
    private String lat ="";
    private String lng ="";
    private String queryFlag="";//筛选 1直营 2加盟 3合作 4营业中
    private String sort="";
    private String cityId="";
    private List<companyListEntity> companyListEntityList = new ArrayList<>();
    private ShopListAdapter shopListAdapter;
    private String carNum="";//车牌
    private List<MyCoupon> myCouponList = new ArrayList<>();
    private DiscountCouponDialog discountCouponDialog;//优惠券对话框
    private ActivityDataAdapter activityDataAdapter;
    private String remark="";
    private String OrderId="";

    public MyCarFragment(Context context){
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_car_fragment,container,false);
        tv_user_name = view.findViewById(R.id.tv_user_name);
        rb_shangmen_service = view.findViewById(R.id.rb_shangmen_service);
        rb_daodian_service = view.findViewById(R.id.rb_daodian_service);
        rb_wai_guan = view.findViewById(R.id.rb_wai_guan);
        shang_men = view.findViewById(R.id.shang_men);
        swipeLayout = view.findViewById(R.id.swipeLayout);
        rv_list = view.findViewById(R.id.rv_list);
        ll_order_sty = view.findViewById(R.id.ll_order_sty);
        activity_data = view.findViewById(R.id.activity_data);
        tv_car_info = view.findViewById(R.id.tv_car_info);
        ll_discount_coupon = view.findViewById(R.id.ll_discount_coupon);
        tv_discounts = view.findViewById(R.id.tv_discounts);
        view_daodian_fuwu = view.findViewById(R.id.view_daodian_fuwu);
        rb_zheng_che = view.findViewById(R.id.rb_zheng_che);
        ll_car_fuwu = view.findViewById(R.id.ll_car_fuwu);
        ll_shop_site = view.findViewById(R.id.ll_shop_site);
        ll_fuwu_no_data = view.findViewById(R.id.ll_fuwu_no_data);
        ll_car_info = view.findViewById(R.id.ll_car_info);
        ll_daodian = view.findViewById(R.id.ll_daodian);
        ll_address = view.findViewById(R.id.ll_address);
        ll_remark = view.findViewById(R.id.ll_remark);
        tv_user_remark = view.findViewById(R.id.tv_user_remark);
        tv_car_color = view.findViewById(R.id.tv_car_color);
        tv_car_fuwu1 = view.findViewById(R.id.tv_car_fuwu1);
        iv_car_fuwu1 = view.findViewById(R.id.iv_car_fuwu1);
        tv_car_fuwu2 = view.findViewById(R.id.tv_car_fuwu2);
        iv_car_fuwu2 = view.findViewById(R.id.iv_car_fuwu2);
        tv_car_fuwu3 = view.findViewById(R.id.tv_car_fuwu3);
        iv_car_fuwu3 = view.findViewById(R.id.iv_car_fuwu3);
        tv_car_fuwu4 = view.findViewById(R.id.tv_car_fuwu4);
        iv_car_fuwu4 = view.findViewById(R.id.iv_car_fuwu4);
        tv_car_fuwu5 = view.findViewById(R.id.tv_car_fuwu5);
        iv_car_fuwu5 = view.findViewById(R.id.iv_car_fuwu5);
        tv_car_fuwu6 = view.findViewById(R.id.tv_car_fuwu6);
        iv_car_fuwu6 = view.findViewById(R.id.iv_car_fuwu6);
        tv_car_fuwu7 = view.findViewById(R.id.tv_car_fuwu7);
        iv_car_fuwu7 = view.findViewById(R.id.iv_car_fuwu7);
        tv_car_fuwu8 = view.findViewById(R.id.tv_car_fuwu8);
        iv_car_fuwu8 = view.findViewById(R.id.iv_car_fuwu8);
        tv_xia_order_money = view.findViewById(R.id.tv_xia_order_money);
        tv_car_fuwu1_money = view.findViewById(R.id.tv_car_fuwu1_money);
        tv_car_fuwu2_money = view.findViewById(R.id.tv_car_fuwu2_money);
        tv_car_fuwu3_money = view.findViewById(R.id.tv_car_fuwu3_money);
        tv_car_fuwu4_money = view.findViewById(R.id.tv_car_fuwu4_money);
        tv_car_fuwu5_money = view.findViewById(R.id.tv_car_fuwu5_money);
        tv_car_fuwu6_money = view.findViewById(R.id.tv_car_fuwu6_money);
        tv_car_fuwu7_money = view.findViewById(R.id.tv_car_fuwu7_money);
        tv_car_fuwu8_money = view.findViewById(R.id.tv_car_fuwu8_money);
        tv_appointment_time = view.findViewById(R.id.tv_appointment_time);
        tv_create_order = view.findViewById(R.id.tv_create_order);
        iv_call_phone = view.findViewById(R.id.iv_call_phone);
        iv_time_date = view.findViewById(R.id.iv_time_date);
        lv_coupon_data = view.findViewById(R.id.lv_coupon_data);
        et_car_address = view.findViewById(R.id.et_car_address);
        et_phone_number = view.findViewById(R.id.et_phone_number);
        et_car_memo = view.findViewById(R.id.et_car_memo);
        tv_xia_order_discounts = view.findViewById(R.id.tv_xia_order_discounts);
        et_car_address_daodian = view.findViewById(R.id.et_car_address_daodian);
        view_qiye = view.findViewById(R.id.view_qiye);
        ll_car_info_view = view.findViewById(R.id.ll_car_info_view);
        et_var_type_1 = view.findViewById(R.id.et_var_type_1);
        et_var_type_2 = view.findViewById(R.id.et_var_type_2);
        et_var_type_3 = view.findViewById(R.id.et_var_type_3);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(getResources().getColor(R.color.public_all));
        rb_shangmen_service.setOnClickListener(this);
        rb_daodian_service.setOnClickListener(this);
        rb_wai_guan.setOnClickListener(this);
        rb_zheng_che.setOnClickListener(this);
        ll_remark.setOnClickListener(this);
        //基础套餐不需要选择
//        tv_car_fuwu1.setOnClickListener(this);
//        tv_car_fuwu2.setOnClickListener(this);
//        tv_car_fuwu3.setOnClickListener(this);
//        tv_car_fuwu4.setOnClickListener(this);
//        tv_car_fuwu5.setOnClickListener(this);

        tv_car_fuwu6.setOnClickListener(this);
        iv_car_fuwu6.setOnClickListener(this);
        tv_car_fuwu7.setOnClickListener(this);
        iv_car_fuwu7.setOnClickListener(this);
        tv_car_fuwu8.setOnClickListener(this);
        iv_car_fuwu8.setOnClickListener(this);
        ll_address.setOnClickListener(this);
        iv_time_date.setOnClickListener(this);
        ll_car_info.setOnClickListener(this);
        tv_create_order.setOnClickListener(this);
        ll_shop_site.setOnClickListener(this);
        ll_discount_coupon.setOnClickListener(this);
        StyledDialog.buildLoading("数据加载中").setActivity((Activity) context).show();
        mPresenter.comboInfo();//查询套餐

        et_phone_number.setText(SPUtils.get(SPUtils.K_SESSION_MOBILE,""));//手机号码
        lat = SPUtils.get(context, "lat", "");
        lng = SPUtils.get(context, "lng", "");
        onRefresh();
        initAdapter();
        //获取当前日期
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String queryDate = formatter.format(date);//今天日期
        mPresenter.appointmentList("",queryDate);

        context.registerReceiver(receiver, new IntentFilter("jingxi_car_addres_12002"));
        context.registerReceiver(receiverCarInfo, new IntentFilter("jing_xi_my_car_info"));
        context.registerReceiver(appointmentListReceiver, new IntentFilter("shop_daodian_19830_fuwu_110"));
        context.registerReceiver(receiverRemark, new IntentFilter("jingxi_user_remark_209344"));

        //默认选择前5想基础套餐
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

        timeDialog = new TimeDialog(context);
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
                    tv_appointment_time.setText(appointmentStartTime.substring(10)+"—至—"+appointmentEndTime.substring(10));
                    timeDialog.cleanDialog();
                }
            }
        });
        //优惠券管理
        discountCouponDialog = new DiscountCouponDialog(context);
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
                    double num = comboMoney-activityMoney;
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
                if (orderMoney<0){
                    tv_xia_order_money.setText(Html.fromHtml("订单金额: <font color=\"#FF2700\">"+new DecimalFormat("0.00").format(0)+"元</font>"));
                }else{
                    tv_xia_order_money.setText(Html.fromHtml("订单金额: <font color=\"#FF2700\">"+new DecimalFormat("0.00").format(orderMoney)+"元</font>"));
                }
            }
        });
        //显示企业模块
        if ("1".equals(SPUtils.get(SPUtils.K_ROLE,"0"))){
            view_qiye.setVisibility(View.VISIBLE);
            ll_car_info_view.setVisibility(View.GONE);
            ll_order_sty.setVisibility(View.INVISIBLE);
        }else{
            view_qiye.setVisibility(View.GONE);
            ll_car_info_view.setVisibility(View.VISIBLE);
            ll_order_sty.setVisibility(View.VISIBLE);
        }
        return view;
    }

    private void initAdapter() {
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(getResources().getColor(R.color.public_all));
        rv_list.setLayoutManager(new LinearLayoutManager(context));
        shopListAdapter = new ShopListAdapter(R.layout.shop_list_adapter, new ArrayList<companyListEntity>());
        rv_list.setAdapter(shopListAdapter);
        shopListAdapter.setOnLoadMoreListener(this, rv_list);
        shopListAdapter.setEmptyView(R.layout.layout_nothing);

        shopListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ZzRouter.gotoActivity((Activity) context, ShopDetailsActivity.class,companyListEntityList.get(position).companyId);
            }
        });
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //停车地点
            et_car_address.setText(intent.getStringExtra("datouzhenAddress"));
            siteLat = intent.getStringExtra("datouzhenLatitude");
            siteLng = intent.getStringExtra("datouzhenLongitude");
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

    private BroadcastReceiver receiverCarInfo = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //换辆车
            CarListEntity carListEntity = (CarListEntity) intent.getSerializableExtra("carInfo");
            carNum = carListEntity.carNum;
            tv_car_info.setText(carListEntity.carNum+ "  "+carListEntity.brandName+"  "+carListEntity.typeName);
            comboTypeId = carListEntity.typeId+"";
            setService(productInfoEntity);
            carColor(carListEntity.color);
            tv_car_fuwu6.setSelected(false);
            iv_car_fuwu6.setSelected(false);
            tv_car_fuwu7.setSelected(false);
            iv_car_fuwu7.setSelected(false);
            tv_car_fuwu8.setSelected(false);
            iv_car_fuwu8.setSelected(false);
            fuwuTypeMoney6 = 0;
            comboProductId6="";
            fuwuTypeMoney7 = 0;
            comboProductId7="";
            fuwuTypeMoney8 = 0;
            comboProductId8="";
        }
    };

    private BroadcastReceiver appointmentListReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //接收到广播以后要做的事
            appointmentStartTime = intent.getStringExtra("timeStart");
            appointmentEndTime = intent.getStringExtra("timeEnd");
            companyId = intent.getStringExtra("companyId");
            address = intent.getStringExtra("address");
            et_car_address_daodian.setText(address);
            tv_appointment_time.setText(appointmentStartTime.substring(10)+"—至—"+appointmentEndTime.substring(10));
        }
    };

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        return null;
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()){
            case R.id.rb_shangmen_service://上门
                serviceType = 0;
                shang_men.setVisibility(View.VISIBLE);
                view_daodian_fuwu.setVisibility(View.GONE);
                break;
            case R.id.rb_daodian_service://到店
                //企业账户不提供到店服务
                if ("1".equals(SPUtils.get(SPUtils.K_ROLE,"0"))){
                    Toast.makeText(context,"企业账户不提供到店服务",Toast.LENGTH_SHORT).show();
                    return;
                }
                shang_men.setVisibility(View.GONE);
                view_daodian_fuwu.setVisibility(View.VISIBLE);
                serviceType = 1;
                break;
            case R.id.ll_shop_site://门店列表
                ZzRouter.gotoActivity((Activity) context, ShopListActivity.class);
                break;
            case R.id.rb_wai_guan://外观清洗
                ll_car_fuwu.setVisibility(View.VISIBLE);
                ll_fuwu_no_data.setVisibility(View.GONE);
                break;
            case R.id.rb_zheng_che://整车清洗
                ll_car_fuwu.setVisibility(View.GONE);
                ll_fuwu_no_data.setVisibility(View.VISIBLE);
                break;
            case R.id.ll_discount_coupon://优惠信息
                if (myCouponList.size()>0){
                    discountCouponDialog.showShareDialog(true,myCouponList,comboMoney);
                }else{
                    Toast.makeText(context,"暂无优惠券可用",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ll_remark://备注
                ZzRouter.gotoActivity((Activity) context, RemarkActivity.class);
                break;
            case R.id.tv_car_fuwu6:
            case R.id.iv_car_fuwu6:
                if (tv_car_fuwu6.isSelected()==true||iv_car_fuwu6.isSelected()==true){
                    tv_car_fuwu6.setSelected(false);
                    iv_car_fuwu6.setSelected(false);
                    fuwuTypeMoney6 = 0;
                    comboProductId6="";
                    productIds6="";
                }else{
                    tv_car_fuwu6.setSelected(true);
                    iv_car_fuwu6.setSelected(true);
                    for (int j=0;j<comboData.productList.size();j++){
                        if (comboData.productList.get(j).productId==6){
                            fuwuTypeMoney6 = comboData.productList.get(j).price;
                            comboProductId6=comboData.productList.get(j).comboProductId+",";
                            productIds6=comboData.productList.get(j).productId+",";
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
            case R.id.iv_car_fuwu7:
                if (tv_car_fuwu7.isSelected()==true||iv_car_fuwu7.isSelected()==true){
                    tv_car_fuwu7.setSelected(false);
                    iv_car_fuwu7.setSelected(false);
                    fuwuTypeMoney7 = 0;
                    comboProductId7="";
                    productIds7="";
                }else{
                    tv_car_fuwu7.setSelected(true);
                    iv_car_fuwu7.setSelected(true);
                    for (int j=0;j<comboData.productList.size();j++){
                        if (comboData.productList.get(j).productId==7){
                            fuwuTypeMoney7 = comboData.productList.get(j).price;
                            comboProductId7=comboData.productList.get(j).comboProductId+",";
                            productIds7=comboData.productList.get(j).productId+",";
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
            case R.id.iv_car_fuwu8:
                if (tv_car_fuwu8.isSelected()==true||iv_car_fuwu8.isSelected()==true){
                    tv_car_fuwu8.setSelected(false);
                    iv_car_fuwu8.setSelected(false);
                    fuwuTypeMoney8 = 0;
                    comboProductId8="";
                    productIds8="";
                }else{
                    tv_car_fuwu8.setSelected(true);
                    iv_car_fuwu8.setSelected(true);
                    for (int j=0;j<comboData.productList.size();j++){
                        if (comboData.productList.get(j).productId==8){
                            fuwuTypeMoney8 = comboData.productList.get(j).price;
                            comboProductId8=comboData.productList.get(j).comboProductId+",";
                            productIds8=comboData.productList.get(j).productId+",";
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
            case R.id.ll_address://选择地址
                ZzRouter.gotoActivity((Activity) context, MapJingSiActivity.class);
                break;
            case R.id.iv_time_date://选择时间
                if (ll_daodian.getVisibility()==View.VISIBLE){
                    //
                }else{
                    //getTime();
                    timeDialog.showShareDialog(true);
                }
                break;
            case R.id.ll_car_info://换辆车
                if (AppUtils.isEmpty(SPUtils.get(SPUtils.K_TOKEN,""))){
                    gotoLogin();
                    return;
                }
                HuanCar = 1;
                ZzRouter.gotoActivity((Activity) context, MyCarActivity.class,"1");
                break;
            case R.id.tv_create_order://立即下单
                if (AppUtils.isEmpty(SPUtils.get(SPUtils.K_TOKEN,""))){
                    gotoLogin();
                    return;
                }
                if (AppUtils.isEmpty(tv_car_info.getText().toString())&&
                        "0".equals(SPUtils.get(SPUtils.K_ROLE,"0"))){
                    Toast.makeText(context,"请添加车辆",Toast.LENGTH_SHORT).show();
                }else if (AppUtils.isEmpty(et_car_address.getText().toString())&&serviceType==0){
                    Toast.makeText(context,"请选择停车地点",Toast.LENGTH_SHORT).show();
                }else if (AppUtils.isEmpty(tv_appointment_time.getText().toString())){
                    Toast.makeText(context,"请选择服务时间",Toast.LENGTH_SHORT).show();
                }else{
                    String carNumStr = "";
                    if ("1".equals(SPUtils.get(SPUtils.K_ROLE,"0"))){
                        //企业车辆数目统计
                        int jiaoche = 0;
                        int suv = 0;
                        int mpv = 0;
                        if (AppUtils.isEmpty(et_var_type_1.getText().toString().trim())){
                            jiaoche = 0;
                        }else {
                            jiaoche = Integer.valueOf(et_var_type_1.getText().toString().trim());
                        }
                        if (AppUtils.isEmpty(et_var_type_2.getText().toString().trim())){
                            suv = 0;
                        }else {
                            suv = Integer.valueOf(et_var_type_2.getText().toString().trim());
                        }
                        if (AppUtils.isEmpty(et_var_type_3.getText().toString().trim())){
                            mpv = 0;
                        }else {
                            mpv = Integer.valueOf(et_var_type_3.getText().toString().trim());
                        }
                        carNumStr = jiaoche+","+suv+","+mpv;
                        //企业账户
                        if (jiaoche==0&&suv==0&&mpv==0){
                            Toast.makeText(context,"请输入数量",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    StyledDialog.buildLoading("正在下单").setActivity((Activity) context).show();
                    comboProductId = comboProductId0+comboProductId6+comboProductId7+comboProductId8;
                    productIds = productIds0+productIds6+productIds7+productIds8;
                    mPresenter.createOrder(
                            serviceType,
                            counponId,
                            carNum,
                            "",
                            et_phone_number.getText().toString(),
                            et_car_address.getText().toString(),
                            siteLng,
                            siteLat,
                            appointmentStartTime,
                            appointmentEndTime,
                            remark,
                            companyId,
                            comboProductId.substring(0,comboProductId.length()-1),
                            comboTypeId,
                            productIds,
                            carNumStr);
                }
                break;
        }
    }

    //时间选择器
    private void getTime(){
        //获取当前时间日期格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        int year = Integer.valueOf(simpleDateFormat.format(date).substring(0,4));
        int month = Integer.valueOf(simpleDateFormat.format(date).substring(5,7));
        int day = Integer.valueOf(simpleDateFormat.format(date).substring(8,10));
        int hour = Integer.valueOf(simpleDateFormat.format(date).substring(11,13));
        int minute = Integer.valueOf(simpleDateFormat.format(date).substring(14,16));

        @SuppressLint("WrongConstant")
        DateTimePicker picker = new DateTimePicker((Activity) context, DateTimePicker.YEAR_MONTH_DAY);//年月日
        picker.setDateRangeStart(year,month,day);//日期起点
        picker.setTimeRangeStart(hour,minute);//时间漆店
        picker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {
            @Override
            public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
                appointmentStartTime = year + "-" + month + "-" + day+"  "+hour+":"+minute;
                appointmentEndTime = year + "-" + month + "-" + day+"  "+(Integer.valueOf(hour)+1)+":"+minute;
                tv_appointment_time.setText(appointmentStartTime.substring(10)+"—至—"+appointmentEndTime.substring(10));
            }
        });
        picker.show();
    }

    private void gotoLogin() {
        toast(context, "请先登录后使用");
        ZzRouter.gotoActivity((Activity) context, ConfigApplication.LOGIN_PATH, ZzRouter.HOST_PLUGIN);
    }

    //活动数据
    @Override
    public void getActivitiesCallBack(List<ActivitiesEntity> data) {
        //组装活动
        if (data.size()>0){
            activityDataAdapter = new ActivityDataAdapter(context);
            activityDataAdapter.setData(data);
            activity_data.setAdapter(activityDataAdapter);

            for (int i=0;i<data.size();i++){
                if (comboMoney>=data.get(i).doorsillMoney){//套餐金额>=活动金额才有效果
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
                orderMoney = comboData.basicPrice+fuwuTypeMoney6+fuwuTypeMoney7+fuwuTypeMoney8-couponMoney-activityMoney;
                if (orderMoney<0){
                    tv_xia_order_money.setText(Html.fromHtml("订单金额: <font color=\"#FF2700\">"+new DecimalFormat("0.00").format(0)+"元</font>"));
                }else{
                    tv_xia_order_money.setText(Html.fromHtml("订单金额: <font color=\"#FF2700\">"+new DecimalFormat("0.00").format(orderMoney)+"元</font>"));
                }
            }
        }
    }

    //获取车辆列表
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
                    tv_car_info.setText(data.get(i).carNum+ "  "+data.get(i).brandName+"  "+data.get(i).typeName);
                    comboTypeId = data.get(i).typeId+"";//默认套餐组合套餐Id
                    carColor(data.get(i).color);
                    SPUtils.put(SPUtils.K_CAR_TYPE,comboTypeId);
                }
            }
            //没有设置默认车辆
            if (a==0){
                carNum = data.get(0).carNum;
                tv_car_info.setText(data.get(0).carNum+ "  "+data.get(0).brandName+"  "+data.get(0).typeName);
                comboTypeId = data.get(0).typeId+"";//套餐组合套餐Id
                carColor(data.get(0).color);
                SPUtils.put(SPUtils.K_CAR_TYPE,comboTypeId);
            }
        }
    }

    //车辆颜色
    private void carColor(int colorType){
        if (colorType==1){
            tv_car_color.setBackgroundResource(R.drawable.car_color_1);
        }else if (colorType==2){
            tv_car_color.setBackgroundResource(R.drawable.car_color_2);
        }else if (colorType==3){
            tv_car_color.setBackgroundResource(R.drawable.car_color_3);
        }else if (colorType==4){
            tv_car_color.setBackgroundResource(R.drawable.car_color_4);
        }else if (colorType==5){
            tv_car_color.setBackgroundResource(R.drawable.car_color_5);
        }else if (colorType==6){
            tv_car_color.setBackgroundResource(R.drawable.car_color_6);
        }else if (colorType==7){
            tv_car_color.setBackgroundResource(R.drawable.car_color_7);
        }else if (colorType==8){
            tv_car_color.setBackgroundResource(R.drawable.car_color_8);
        }else if (colorType==9){
            tv_car_color.setBackgroundResource(R.drawable.car_color_9);
        }else if (colorType==10){
            tv_car_color.setBackgroundResource(R.drawable.car_color_10);
        }else{
            tv_car_color.setBackgroundResource(R.drawable.car_color_8);
        }
    }

    //优惠券返回数据
    @Override
    public void queryMyCouponCallback(List<MyCoupon> data) {
        if (data.size()>0){
            myCouponList = data;
        }
    }

    //洗车组合套餐返回数据
    @Override
    public void comboInfoCallBack(ProductInfoEntity proData) {
        productInfoEntity = proData;
        setService(productInfoEntity);
        //查询套餐接口结束后调其他接口
        if (!AppUtils.isEmpty(SPUtils.get(SPUtils.K_TOKEN,""))){
            mPresenter.getCarList();
            mPresenter.queryMyCoupon(0);
            mPresenter.getActivities();
        }
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
            }
        }
        if (a==0){
            comboData = data.combo.get(0);
        }
        //拿前五项基本套餐
        comboProductId0="";
        productIds0="";
        for (int i=0;i<5;i++){
            comboProductId0 += comboData.productList.get(i).comboProductId+",";
            productIds0 += comboData.productList.get(i).productId+",";
        }
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
                tv_car_fuwu6_money.setText("+￥"+new DecimalFormat("0.00").format(comboData.productList.get(j).price));
            }else if (comboData.productList.get(j).productId==7){
                tv_car_fuwu7_money.setText("+￥"+new DecimalFormat("0.00").format(comboData.productList.get(j).price));
            }else if (comboData.productList.get(j).productId==8){
                tv_car_fuwu8_money.setText("+￥"+new DecimalFormat("0.00").format(comboData.productList.get(j).price));
            }else{
                tv_car_fuwu8_money.setText("外加服务");
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

    @Override
    public void createOrderCallBack(CreateOrderEntity data) {
        //显示企业模块
        if ("1".equals(SPUtils.get(SPUtils.K_ROLE,"0"))){
            OrderId = data.orderId;
            mPresenter.BalancePay(data.orderId);
        }else{
            //支付订单
            Intent intent = new Intent(context, PayOrderActivity.class);
            intent.putExtra("orderId",data.orderId);
            intent.putExtra("orderPrice",data.payPrice);
            startActivity(intent);
        }
    }

    //企业余额支付
    @Override
    public void BalancePayCallBack() {
        ZzRouter.gotoActivity((Activity) context, OrderDetailsActivity.class,OrderId);
    }

    //查询预约时间返回
    @Override
    public void appointmentListCallBack(List<AppointmentListEntity> data) {
        timeDialog.updateTimeAdapter(data,0);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (HuanCar == 1){
            HuanCar =0;
        }else{
            if (!AppUtils.isEmpty(SPUtils.get(SPUtils.K_TOKEN,""))){
                mPresenter.getCarList();
                mPresenter.queryMyCoupon(0);
                mPresenter.getActivities();
            }
            mPresenter.comboInfo();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!AppUtils.isEmpty(appointmentListReceiver)){
            context.unregisterReceiver(appointmentListReceiver);
        }
        if (!AppUtils.isEmpty(receiverCarInfo)){
            context.unregisterReceiver(receiverCarInfo);
        }
        if (!AppUtils.isEmpty(receiver)){
            context.unregisterReceiver(receiver);
        }
        if (!AppUtils.isEmpty(receiverRemark)){
            context.unregisterReceiver(receiverRemark);
        }
    }

    @Override
    public void onRefresh() {
        offset=2;
        mPresenter.companyList(Double.valueOf(lng),Double.valueOf(lat),queryFlag,sort,cityId,1,10);
    }

    @Override
    public void onLoadMoreRequested() {
        swipeLayout.setRefreshing(false);
        mPresenter.companyListMore(Double.valueOf(lng),Double.valueOf(lat),queryFlag,sort,cityId,offset,10);
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
}
