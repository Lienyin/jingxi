package com.jxxc.jingxi.ui.main.myCarfragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
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

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.AddressEntity;
import com.jxxc.jingxi.entity.backparameter.CarListEntity;
import com.jxxc.jingxi.entity.backparameter.CreateOrderEntity;
import com.jxxc.jingxi.entity.backparameter.MyCoupon;
import com.jxxc.jingxi.entity.backparameter.ProductInfoEntity;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.mvp.MVPBaseFragment;
import com.jxxc.jingxi.ui.addressdetails.AddressDetailsActivity;
import com.jxxc.jingxi.ui.mycar.MyCarActivity;
import com.jxxc.jingxi.ui.payorder.PayOrderActivity;
import com.jxxc.jingxi.ui.shoplist.ShopListActivity;
import com.jxxc.jingxi.ui.submitorder.CouponAdapter;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.utils.AppUtils;
import com.jxxc.jingxi.utils.ListViewForScrollView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import cn.qqtheme.framework.picker.DateTimePicker;

@SuppressLint("ValidFragment")
public class MyCarFragment extends MVPBaseFragment<MyCarFragmentContract.View, MyCarFragmentPresenter> implements View.OnClickListener, MyCarFragmentContract.View {
    private Context context;
    private TextView tv_user_name;
    private RadioButton rb_shangmen_service;
    private RadioButton rb_daodian_service;
    private RadioButton rb_wai_guan;
    private RadioButton rb_zheng_che;
    private LinearLayout ll_car_fuwu;
    private LinearLayout ll_fuwu_no_data;
    private LinearLayout ll_add_car;
    private LinearLayout ll_car_info;
    private TextView tv_car_number;
    private TextView tv_car_type;
    private TextView tv_car_color;
    private TextView tv_car_fuwu1;
    private TextView tv_car_fuwu2;
    private TextView tv_car_fuwu3;
    private TextView tv_car_fuwu4;
    private TextView tv_car_fuwu5;
    private TextView tv_car_fuwu6;
    private TextView tv_car_fuwu7;
    private TextView tv_car_fuwu8;
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
    private ImageView iv_call_phone;
    private ImageView iv_address;
    private ImageView iv_time_date;
    private ListViewForScrollView lv_coupon_data;
    private TextView et_car_address;
    private TextView tv_huan_car;
    private EditText et_phone_number;
    private EditText et_car_memo;
    private LinearLayout ll_daodian,ll_address,ll_shop_site;
    private int fuwu1;
    private int fuwu2;
    private int fuwu3;
    private CouponAdapter adapter;
    private List<MyCoupon> list = new ArrayList<>();
    private AddressEntity addressEntity = new AddressEntity();
    private String siteLat="";
    private String siteLng="";
    private String comboTypeId="";//车类型（SUN,轿车，MYVP）
    private int serviceType=0;
    private String counponId="";
    private String comboProductId="";
    private String comboProductId6="";
    private String comboProductId7="";
    private String comboProductId8="";
    private String appointmentStartTime="";
    private String appointmentEndTime="";
    private ProductInfoEntity.Combo comboData = new ProductInfoEntity.Combo();//车型套餐数据
    private double comboMoney=0;//套餐金额
    private double couponMoney=0;//优惠券金额
    private double fuwuTypeMoney6=0;
    private double fuwuTypeMoney7=0;
    private double fuwuTypeMoney8=0;
    private int HuanCar=0;
    private ProductInfoEntity productInfoEntity = new ProductInfoEntity();

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
        rb_zheng_che = view.findViewById(R.id.rb_zheng_che);
        ll_car_fuwu = view.findViewById(R.id.ll_car_fuwu);
        ll_shop_site = view.findViewById(R.id.ll_shop_site);
        ll_fuwu_no_data = view.findViewById(R.id.ll_fuwu_no_data);
        ll_add_car = view.findViewById(R.id.ll_add_car);
        ll_car_info = view.findViewById(R.id.ll_car_info);
        tv_car_number = view.findViewById(R.id.tv_car_number);
        ll_daodian = view.findViewById(R.id.ll_daodian);
        ll_address = view.findViewById(R.id.ll_address);
        tv_car_type = view.findViewById(R.id.tv_car_type);
        tv_car_color = view.findViewById(R.id.tv_car_color);
        tv_car_fuwu1 = view.findViewById(R.id.tv_car_fuwu1);
        tv_car_fuwu2 = view.findViewById(R.id.tv_car_fuwu2);
        tv_car_fuwu3 = view.findViewById(R.id.tv_car_fuwu3);
        tv_car_fuwu4 = view.findViewById(R.id.tv_car_fuwu4);
        tv_car_fuwu5 = view.findViewById(R.id.tv_car_fuwu5);
        tv_car_fuwu6 = view.findViewById(R.id.tv_car_fuwu6);
        tv_car_fuwu7 = view.findViewById(R.id.tv_car_fuwu7);
        tv_car_fuwu8 = view.findViewById(R.id.tv_car_fuwu8);
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
        iv_address = view.findViewById(R.id.iv_address);
        iv_time_date = view.findViewById(R.id.iv_time_date);
        lv_coupon_data = view.findViewById(R.id.lv_coupon_data);
        et_car_address = view.findViewById(R.id.et_car_address);
        tv_huan_car = view.findViewById(R.id.tv_huan_car);
        et_phone_number = view.findViewById(R.id.et_phone_number);
        et_car_memo = view.findViewById(R.id.et_car_memo);
        tv_xia_order_discounts = view.findViewById(R.id.tv_xia_order_discounts);

        rb_shangmen_service.setOnClickListener(this);
        rb_daodian_service.setOnClickListener(this);
        rb_wai_guan.setOnClickListener(this);
        rb_zheng_che.setOnClickListener(this);
        //基础套餐不需要选择
//        tv_car_fuwu1.setOnClickListener(this);
//        tv_car_fuwu2.setOnClickListener(this);
//        tv_car_fuwu3.setOnClickListener(this);
//        tv_car_fuwu4.setOnClickListener(this);
//        tv_car_fuwu5.setOnClickListener(this);

        tv_car_fuwu6.setOnClickListener(this);
        tv_car_fuwu7.setOnClickListener(this);
        tv_car_fuwu8.setOnClickListener(this);
        iv_address.setOnClickListener(this);
        iv_time_date.setOnClickListener(this);
        tv_huan_car.setOnClickListener(this);
        tv_create_order.setOnClickListener(this);
        ll_add_car.setOnClickListener(this);
        ll_shop_site.setOnClickListener(this);
        mPresenter.getCarList();
        mPresenter.queryMyCoupon(0);
        mPresenter.comboInfo();

        adapter = new CouponAdapter(context);
        adapter.setData(list);
        lv_coupon_data.setAdapter(adapter);
        lv_coupon_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //选择优惠券
                if (list.get(i).isForbidden()==false){
                    adapter.setSelectPosition(i);
                    adapter.notifyDataSetChanged();
                    counponId = list.get(i).counponId+"";
                    tv_xia_order_discounts.setVisibility(View.VISIBLE);//显示优惠
                    //金额显示
                    //优惠券类型 0无门槛减N 1满N减N 2折扣券
                    if (list.get(i).couponRuleType==0){
                        couponMoney = list.get(i).money;
                        comboMoney = comboData.basicPrice+fuwuTypeMoney6+fuwuTypeMoney7+fuwuTypeMoney8-couponMoney;//套餐金额=基础套餐金额+选择服务项金额-优惠券金额
                        tv_xia_order_discounts.setText("已优惠："+new DecimalFormat("0.00").format(list.get(i).money)+"元");
                    }else if (list.get(i).couponRuleType==1){
                        couponMoney = list.get(i).money;
                        comboMoney = comboData.basicPrice+fuwuTypeMoney6+fuwuTypeMoney7+fuwuTypeMoney8-couponMoney;//套餐金额=基础套餐金额+选择服务项金额-优惠券金额
                        tv_xia_order_discounts.setText("已优惠："+new DecimalFormat("0.00").format(list.get(i).money)+"元");
                    }else{
                        //折扣券
                        comboMoney = (comboData.basicPrice+fuwuTypeMoney6+fuwuTypeMoney7+fuwuTypeMoney8)*(list.get(i).discount/10);
                        double zheMoney = (comboData.basicPrice+fuwuTypeMoney6+fuwuTypeMoney7+fuwuTypeMoney8) - comboMoney;
                        tv_xia_order_discounts.setText("已优惠："+new DecimalFormat("0.00").format(zheMoney)+"元");
                    }
                    tv_xia_order_money.setText("订单金额："+new DecimalFormat("0.00").format(comboMoney)+"元");
                }else{
                    Toast.makeText(context,"优惠券不可用",Toast.LENGTH_SHORT).show();
                }
            }
        });

        context.registerReceiver(receiver, new IntentFilter("jingxi_car_addres_12002"));
        context.registerReceiver(receiverCarInfo, new IntentFilter("jing_xi_my_car_info"));

        //默认选择前5想基础套餐
        tv_car_fuwu1.setSelected(true);
        tv_car_fuwu2.setSelected(true);
        tv_car_fuwu3.setSelected(true);
        tv_car_fuwu4.setSelected(true);
        tv_car_fuwu5.setSelected(true);
        return view;
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //停车地点
            addressEntity = (AddressEntity) intent.getSerializableExtra("addressEntity");
            if (!AppUtils.isEmpty(addressEntity)){
                et_car_address.setText(addressEntity.getAddress());
                siteLat = addressEntity.getLat();
                siteLng = addressEntity.getLng();
            }
        }
    };
    private BroadcastReceiver receiverCarInfo = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //换辆车
            CarListEntity carListEntity = (CarListEntity) intent.getSerializableExtra("carInfo");
            if (!AppUtils.isEmpty(carListEntity)){
                ll_add_car.setVisibility(View.GONE);
                ll_car_info.setVisibility(View.VISIBLE);
            }
            tv_car_number.setText(carListEntity.carNum);
            tv_car_type.setText(carListEntity.brandName+"  "+carListEntity.typeName);
            comboTypeId = carListEntity.typeId;
            setService(productInfoEntity);
            carColor(carListEntity.color);
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
                ll_daodian.setVisibility(View.GONE);
                ll_address.setVisibility(View.VISIBLE);
                serviceType = 0;
                break;
            case R.id.rb_daodian_service://到店
                ll_daodian.setVisibility(View.VISIBLE);
                ll_address.setVisibility(View.GONE);
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
                }
                break;
            case R.id.tv_car_fuwu6:
                if (tv_car_fuwu6.isSelected()==true){
                    tv_car_fuwu6.setSelected(false);
                    fuwuTypeMoney6 = 0;
                    comboProductId6="";
                }else{
                    comboProductId6=",6";
                    tv_car_fuwu6.setSelected(true);
                    if (tv_car_fuwu1.isSelected()==false){
                        tv_car_fuwu1.setSelected(true);
                        tv_car_fuwu2.setSelected(true);
                        tv_car_fuwu3.setSelected(true);
                        tv_car_fuwu4.setSelected(true);
                        tv_car_fuwu5.setSelected(true);
                    }
                    for (int j=0;j<comboData.productList.size();j++){
                        if (comboData.productList.get(j).productId==6){
                            fuwuTypeMoney6 = comboData.productList.get(j).price;
                        }
                    }
                }
                comboMoney = comboData.basicPrice+fuwuTypeMoney6+fuwuTypeMoney7+fuwuTypeMoney8-couponMoney;//套餐金额=基础套餐金额+选择服务项金额-优惠券金额
                tv_xia_order_money.setText("订单金额："+new DecimalFormat("0.00").format(comboMoney)+"元");
                rb_wai_guan.setText("外观清洗("+(comboData.basicPrice+fuwuTypeMoney6+fuwuTypeMoney7+fuwuTypeMoney8)+"元)");
                adapter.setOrderMoney(comboMoney);//优惠券适配器使用
                adapter.notifyDataSetChanged();
                break;
            case R.id.tv_car_fuwu7:
                if (tv_car_fuwu7.isSelected()==true){
                    tv_car_fuwu7.setSelected(false);
                    fuwuTypeMoney7 = 0;
                    comboProductId7="";
                }else{
                    comboProductId7=",7";
                    tv_car_fuwu7.setSelected(true);
                    if (tv_car_fuwu1.isSelected()==false){
                        tv_car_fuwu1.setSelected(true);
                        tv_car_fuwu2.setSelected(true);
                        tv_car_fuwu3.setSelected(true);
                        tv_car_fuwu4.setSelected(true);
                        tv_car_fuwu5.setSelected(true);
                    }
                    for (int j=0;j<comboData.productList.size();j++){
                        if (comboData.productList.get(j).productId==7){
                            fuwuTypeMoney7 = comboData.productList.get(j).price;
                        }
                    }
                }
                comboMoney = comboData.basicPrice+fuwuTypeMoney6+fuwuTypeMoney7+fuwuTypeMoney8-couponMoney;//套餐金额=基础套餐金额+选择服务项金额-优惠券金额
                tv_xia_order_money.setText("订单金额："+new DecimalFormat("0.00").format(comboMoney)+"元");
                rb_wai_guan.setText("外观清洗("+(comboData.basicPrice+fuwuTypeMoney6+fuwuTypeMoney7+fuwuTypeMoney8)+"元)");
                adapter.setOrderMoney(comboMoney);//优惠券适配器使用
                adapter.notifyDataSetChanged();
                break;
            case R.id.tv_car_fuwu8:
                if (tv_car_fuwu8.isSelected()==true){
                    tv_car_fuwu8.setSelected(false);
                    fuwuTypeMoney8 = 0;
                    comboProductId8="";
                }else{
                    comboProductId8=",8";
                    tv_car_fuwu8.setSelected(true);
                    if (tv_car_fuwu1.isSelected()==false){
                        tv_car_fuwu1.setSelected(true);
                        tv_car_fuwu2.setSelected(true);
                        tv_car_fuwu3.setSelected(true);
                        tv_car_fuwu4.setSelected(true);
                        tv_car_fuwu5.setSelected(true);
                    }
                    for (int j=0;j<comboData.productList.size();j++){
                        if (comboData.productList.get(j).productId==8){
                            fuwuTypeMoney8 = comboData.productList.get(j).price;
                        }
                    }
                }
                comboMoney = comboData.basicPrice+fuwuTypeMoney6+fuwuTypeMoney7+fuwuTypeMoney8-couponMoney;//套餐金额=基础套餐金额+选择服务项金额-优惠券金额
                tv_xia_order_money.setText("订单金额："+new DecimalFormat("0.00").format(comboMoney)+"元");
                rb_wai_guan.setText("外观清洗("+(comboData.basicPrice+fuwuTypeMoney6+fuwuTypeMoney7+fuwuTypeMoney8)+"元)");
                adapter.setOrderMoney(comboMoney);//优惠券适配器使用
                adapter.notifyDataSetChanged();
                break;
            case R.id.iv_address://选择地址
                HuanCar = 1;
                ZzRouter.gotoActivity((Activity) context, AddressDetailsActivity.class);
                break;
            case R.id.iv_time_date://选择时间
                getTime();
                break;
            case R.id.ll_add_car://添加 车辆信息
                ZzRouter.gotoActivity((Activity) context, MyCarActivity.class,"1");
                break;
            case R.id.tv_huan_car://换辆车
                HuanCar = 1;
                ZzRouter.gotoActivity((Activity) context, MyCarActivity.class,"1");
                break;
            case R.id.tv_create_order://立即下单
                if (AppUtils.isEmpty(tv_car_number.getText().toString())){
                    Toast.makeText(context,"请添加车辆",Toast.LENGTH_SHORT).show();
                }else if (AppUtils.isEmpty(et_car_address.getText().toString())){
                    Toast.makeText(context,"请选择停车地点",Toast.LENGTH_SHORT).show();
                }else if (AppUtils.isEmpty(et_phone_number.getText().toString())){
                    Toast.makeText(context,"请输入联系方式",Toast.LENGTH_SHORT).show();
                }else if (AppUtils.isEmpty(tv_appointment_time.getText().toString())){
                    Toast.makeText(context,"请选择服务时间",Toast.LENGTH_SHORT).show();
                }else{
                    StyledDialog.buildLoading("正在下单").setActivity((Activity) context).show();
                    comboProductId = "1,2,3,4,5"+comboProductId6+comboProductId7+comboProductId8;
                    mPresenter.createOrder(
                            comboProductId,
                            serviceType,
                            counponId,
                            comboTypeId,
                            tv_car_number.getText().toString(),
                            "",
                            et_phone_number.getText().toString(),
                            et_car_address.getText().toString(),
                            siteLng,
                            siteLat,
                            appointmentStartTime,
                            appointmentEndTime,
                            et_car_memo.getText().toString());
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
                tv_appointment_time.setText(appointmentStartTime+"—至—"+appointmentEndTime);
            }
        });
        picker.show();
    }

    //获取车辆列表
    @Override
    public void getCarListCallBack(List<CarListEntity> data) {
        if (data.size()>0){//y有车
            ll_add_car.setVisibility(View.GONE);
            ll_car_info.setVisibility(View.VISIBLE);
            //展示默认车辆，没有默认车辆展示第一辆
            //是否默认 1是0否
            int a=0;
            for (int i=0;i<data.size();i++){
                if (data.get(i).isDefault==1){
                    a++;
                    tv_car_number.setText(data.get(i).carNum);
                    tv_car_type.setText(data.get(i).brandName+"  "+data.get(i).typeName);
                    comboTypeId = data.get(i).typeId;//默认套餐组合套餐Id
                    carColor(data.get(i).color);
                }
            }
            //没有设置默认车辆
            if (a==0){
                tv_car_number.setText(data.get(0).carNum);
                tv_car_type.setText(data.get(0).brandName+"  "+data.get(0).typeName);
                comboTypeId = data.get(0).typeId;//套餐组合套餐Id
                carColor(data.get(0).color);
            }
        }else{
            ll_add_car.setVisibility(View.VISIBLE);
            ll_car_info.setVisibility(View.GONE);
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
        list = data;
        adapter.setData(list);
        adapter.notifyDataSetChanged();
    }

    //洗车组合套餐返回数据
    @Override
    public void comboInfoCallBack(ProductInfoEntity proData) {
        productInfoEntity = proData;
        setService(productInfoEntity);
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
        comboMoney = comboData.basicPrice;//获得基础套餐金额
        tv_xia_order_money.setText("订单金额："+new DecimalFormat("0.00").format(comboData.basicPrice)+"元");
        rb_wai_guan.setText("外观清洗("+new DecimalFormat("0.00").format(comboData.basicPrice)+"元)");//基础套餐价格
        adapter.setOrderMoney(comboMoney);//优惠券适配器使用
        adapter.notifyDataSetChanged();
    }

    @Override
    public void createOrderCallBack(CreateOrderEntity data) {
        //支付订单
        Intent intent = new Intent(context, PayOrderActivity.class);
        intent.putExtra("orderId",data.orderId);
        intent.putExtra("orderPrice",data.payPrice);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (HuanCar == 1){
            HuanCar =0;
        }else{
            mPresenter.getCarList();
            mPresenter.queryMyCoupon(0);
            mPresenter.comboInfo();
        }
    }
}
