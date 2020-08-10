package com.jxxc.jingxi.ui.submitorder;


import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.adapter.CouponAdapter;
import com.jxxc.jingxi.dialog.TimeDialog;
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
import com.jxxc.jingxi.ui.mycar.MyCarActivity;
import com.jxxc.jingxi.ui.payorder.PayOrderActivity;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.utils.AppUtils;
import com.jxxc.jingxi.utils.ListViewForScrollView;
import com.jxxc.jingxi.utils.SPUtils;
import com.jxxc.jingxi.utils.StatusBarUtil;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.DateTimePicker;


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
    @BindView(R.id.tv_appointment_time)
    TextView tv_appointment_time;
    @BindView(R.id.tv_create_order)
    TextView tv_create_order;
    @BindView(R.id.iv_call_phone)
    ImageView iv_call_phone;
    @BindView(R.id.iv_address)
    ImageView iv_address;
    @BindView(R.id.iv_time_date)
    LinearLayout iv_time_date;
    @BindView(R.id.lv_coupon_data)
    ListViewForScrollView lv_coupon_data;
    @BindView(R.id.et_car_address)
    TextView et_car_address;
    @BindView(R.id.tv_huan_car)
    TextView tv_huan_car;
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
    @BindView(R.id.tv_xia_order_money)
    TextView tv_xia_order_money;
    @BindView(R.id.tv_xia_order_discounts)
    TextView tv_xia_order_discounts;
    @BindView(R.id.et_phone_number)
    TextView et_phone_number;
    @BindView(R.id.et_car_memo)
    EditText et_car_memo;
    @BindView(R.id.ll_daodian)
    LinearLayout ll_daodian;
    @BindView(R.id.ll_address)
    LinearLayout ll_address;
    @BindView(R.id.et_car_address_daodian)
    TextView et_car_address_daodian;
    private CouponAdapter adapter;
    private List<MyCoupon> list = new ArrayList<>();
    private AddressEntity addressEntity = new AddressEntity();
    private String siteLat="";
    private String siteLng="";
    private String comboTypeId="";
    private int serviceType=0;
    private String counponId="";
    private String comboProductId="";
    private String comboProductId0="";
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
    private ProductInfoEntity productInfoEntity = new ProductInfoEntity();
    private String companyId="";
    private String address="";
    private RecommendComboInfoEntity recommendComboInfoEntity = new RecommendComboInfoEntity();
    private TimeDialog timeDialog;

    @Override
    protected int layoutId() {
        return R.layout.submit_order_activity;
    }

    @Override
    public void initData() {
        StatusBarUtil.setStatusBarMode(this, false, R.color.white);
        tv_title.setText("菁喜");
        //两种方式下单（1.推荐套餐过来的 2.正常套餐查询的）
        //推荐套餐过来选择
        recommendComboInfoEntity = (RecommendComboInfoEntity) getIntent().getSerializableExtra("recommendComboInfoEntity");
        if (!AppUtils.isEmpty(recommendComboInfoEntity)){
            //根据车型显示套餐选项
        }else{
            //默认选择前5项套餐
            tv_car_fuwu1.setSelected(true);
            tv_car_fuwu2.setSelected(true);
            tv_car_fuwu3.setSelected(true);
            tv_car_fuwu4.setSelected(true);
            tv_car_fuwu5.setSelected(true);
        }

        StyledDialog.buildLoading("数据加载中").setActivity(this).show();
        mPresenter.queryMyCoupon(0);//优惠券
        mPresenter.getCarList();//车型查询
        mPresenter.comboInfo();//套餐查询

        //获取当前日期
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String queryDate = formatter.format(date);//今天日期
        mPresenter.appointmentList("",queryDate);

        //优惠券查询
        adapter = new CouponAdapter(this);
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
                    Toast.makeText(SubmitOrderActivity.this,"优惠券不可用",Toast.LENGTH_SHORT).show();
                }
            }
        });

        registerReceiver(receiver, new IntentFilter("jingxi_car_addres_12002"));
        registerReceiver(receiverCarInfo, new IntentFilter("jing_xi_my_car_info"));
        registerReceiver(appointmentListReceiver, new IntentFilter("shop_daodian_19830_fuwu_110"));

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
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //接收到广播以后要做的事
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
            comboTypeId = carListEntity.typeId+"";
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
    private BroadcastReceiver appointmentListReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //接收到广播以后要做的事
            appointmentStartTime = intent.getStringExtra("timeStart");
            appointmentEndTime = intent.getStringExtra("timeEnd");
            companyId = intent.getStringExtra("companyId");
            address = intent.getStringExtra("address");
            et_car_address_daodian.setText(address);
            tv_appointment_time.setText(appointmentStartTime.substring(5)+"—至—"+appointmentEndTime.substring(5));
        }
    };

    @OnClick({R.id.tv_back,R.id.rb_shangmen_service,R.id.rb_daodian_service,R.id.rb_wai_guan,
            R.id.rb_zheng_che,R.id.tv_car_fuwu6,R.id.tv_car_fuwu7,R.id.tv_car_fuwu8,R.id.iv_call_phone
    ,R.id.iv_address,R.id.iv_time_date,R.id.tv_create_order,R.id.tv_huan_car})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
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
            case R.id.rb_wai_guan://外观清洗
                ll_car_fuwu.setVisibility(View.VISIBLE);
                ll_fuwu_no_data.setVisibility(View.GONE);
                break;
            case R.id.rb_zheng_che://整车清洗
                ll_car_fuwu.setVisibility(View.GONE);
                ll_fuwu_no_data.setVisibility(View.VISIBLE);
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
                comboMoney = comboData.basicPrice+fuwuTypeMoney6+fuwuTypeMoney7+fuwuTypeMoney8-couponMoney;//套餐金额=基础套餐金额+选择服务项金额-优惠券金额
                tv_xia_order_money.setText("订单金额："+new DecimalFormat("0.00").format(comboMoney)+"元");
                rb_wai_guan.setText(Html.fromHtml("外观清洗 <font color=\"#BB222F\">("+(comboData.basicPrice+fuwuTypeMoney6+fuwuTypeMoney7+fuwuTypeMoney8)+"元)</font>"));
                adapter.setOrderMoney(comboMoney);//优惠券适配器使用
                adapter.notifyDataSetChanged();
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
                comboMoney = comboData.basicPrice+fuwuTypeMoney6+fuwuTypeMoney7+fuwuTypeMoney8-couponMoney;//套餐金额=基础套餐金额+选择服务项金额-优惠券金额
                tv_xia_order_money.setText("订单金额："+new DecimalFormat("0.00").format(comboMoney)+"元");
                rb_wai_guan.setText(Html.fromHtml("外观清洗 <font color=\"#BB222F\">("+(comboData.basicPrice+fuwuTypeMoney6+fuwuTypeMoney7+fuwuTypeMoney8)+"元)</font>"));
                adapter.setOrderMoney(comboMoney);//优惠券适配器使用
                adapter.notifyDataSetChanged();
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
                comboMoney = comboData.basicPrice+fuwuTypeMoney6+fuwuTypeMoney7+fuwuTypeMoney8-couponMoney;//套餐金额=基础套餐金额+选择服务项金额-优惠券金额
                tv_xia_order_money.setText("订单金额："+new DecimalFormat("0.00").format(comboMoney)+"元");
                rb_wai_guan.setText(Html.fromHtml("外观清洗 <font color=\"#BB222F\">("+(comboData.basicPrice+fuwuTypeMoney6+fuwuTypeMoney7+fuwuTypeMoney8)+"元)</font>"));
                adapter.setOrderMoney(comboMoney);//优惠券适配器使用
                adapter.notifyDataSetChanged();
                break;
            case R.id.iv_call_phone:
                if (iv_call_phone.isSelected()==true){
                    iv_call_phone.setSelected(false);
                }else{
                    iv_call_phone.setSelected(true);
                }
                break;
            case R.id.iv_address://选择地址
                ZzRouter.gotoActivity(this, AddressDetailsActivity.class);
                break;
            case R.id.iv_time_date://选择时间
                //getTime();
                timeDialog.showShareDialog(true);
                break;
            case R.id.tv_create_order://立即下单
                if (AppUtils.isEmpty(tv_car_number.getText().toString())){
                    toast(this,"请添加车辆");
                }else if (AppUtils.isEmpty(et_car_address.getText().toString())&&serviceType==0){
                    toast(this,"请选择停车地点");
                }else if (AppUtils.isEmpty(et_phone_number.getText().toString())){
                    toast(this,"请输入联系方式");
                }else if (AppUtils.isEmpty(tv_appointment_time.getText().toString())){
                    toast(this,"请选择服务时间");
                }else{
                    StyledDialog.buildLoading("正在下单").setActivity(this).show();
                    comboProductId = comboProductId0+comboProductId6+comboProductId7+comboProductId8;
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
                            et_car_memo.getText().toString(),
                            companyId);
                }
                break;
            case R.id.tv_huan_car://换辆车
                ZzRouter.gotoActivity(this, MyCarActivity.class,"1");
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
            //展示默认车辆，没有默认车辆展示第一辆
            //是否默认 1是0否
            int a=0;
            for (int i=0;i<data.size();i++){
                if (data.get(i).isDefault==1){
                    a++;
                    tv_car_number.setText(data.get(i).carNum);
                    tv_car_type.setText(data.get(i).brandName+"  "+data.get(i).typeName);
                    comboTypeId = data.get(i).typeId+"";//默认车型id
                    carColor(data.get(i).color);
                    SPUtils.put(SPUtils.K_CAR_TYPE,comboTypeId);
                }
            }
            //没有设置默认车辆
            if (a==0){
                tv_car_number.setText(data.get(0).carNum);
                tv_car_type.setText(data.get(0).brandName+"  "+data.get(0).typeName);
                comboTypeId = data.get(0).typeId+"";//套餐组合套餐Id
                carColor(data.get(0).color);
                SPUtils.put(SPUtils.K_CAR_TYPE,comboTypeId);
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

    //组合套餐数据
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
        comboMoney = comboData.basicPrice+fuwuTypeMoney6+fuwuTypeMoney7+fuwuTypeMoney8-couponMoney;//套餐金额=基础套餐金额+选择服务项金额-优惠券金额
        tv_xia_order_money.setText("订单金额："+new DecimalFormat("0.00").format(comboMoney)+"元");
        rb_wai_guan.setText(Html.fromHtml("外观清洗 <font color=\"#BB222F\">("+(comboMoney)+"元)</font>"));
        adapter.setOrderMoney(comboMoney);//优惠券适配器使用
        adapter.notifyDataSetChanged();
    }

    //下单返回数据
    @Override
    public void createOrderCallBack(CreateOrderEntity data) {
        //支付订单
        Intent intent = new Intent(this, PayOrderActivity.class);
        intent.putExtra("orderId",data.orderId);
        intent.putExtra("orderPrice",data.payPrice);
        startActivity(intent);
        finish();
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
        DateTimePicker picker = new DateTimePicker(this, DateTimePicker.YEAR_MONTH_DAY);//年月日
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

    //查询预约时间返回
    @Override
    public void appointmentListCallBack(List<AppointmentListEntity> data) {
        timeDialog.updateTimeAdapter(data,0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!AppUtils.isEmpty(receiver)){
            unregisterReceiver(receiver);
        }
        if (!AppUtils.isEmpty(receiverCarInfo)){
            unregisterReceiver(receiverCarInfo);
        }
        if (!AppUtils.isEmpty(appointmentListReceiver)){
            unregisterReceiver(appointmentListReceiver);
        }
    }
}
