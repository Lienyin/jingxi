package com.jxxc.jingxi.ui.main;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.jxxc.jingxi.ConfigApplication;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.dialog.ActivityDialog;
import com.jxxc.jingxi.dialog.ShareDialog;
import com.jxxc.jingxi.entity.backparameter.BannerEntity;
import com.jxxc.jingxi.entity.backparameter.GetStaticEntity;
import com.jxxc.jingxi.entity.backparameter.UserInfoEntity;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.ui.finddetails.FindDetailsActivity;
import com.jxxc.jingxi.ui.login.LoginActivity;
import com.jxxc.jingxi.ui.main.firstfragment.FirstFragment;
import com.jxxc.jingxi.ui.main.msg.MsgFragment;
import com.jxxc.jingxi.ui.main.my.MyFragment;
import com.jxxc.jingxi.ui.main.myCarfragment.MyCarFragment;
import com.jxxc.jingxi.ui.main.secondfragment.SecondFragment;
import com.jxxc.jingxi.ui.message.MessageActivity;
import com.jxxc.jingxi.ui.mycar.MyCarActivity;
import com.jxxc.jingxi.ui.myorder.MyOrderActivity;
import com.jxxc.jingxi.ui.mywallet.MyWalletActivity;
import com.jxxc.jingxi.ui.partnership.PartnershipActivity;
import com.jxxc.jingxi.ui.recharge.RechargeActivity;
import com.jxxc.jingxi.ui.seting.SetingActivity;
import com.jxxc.jingxi.ui.setmealpayinfo.SetMealPayInfoActivity;
import com.jxxc.jingxi.ui.share.ShareActivity;
import com.jxxc.jingxi.ui.shoplist.ShopListActivity;
import com.jxxc.jingxi.ui.usercenter.UsercenterActivity;
import com.jxxc.jingxi.utils.AppUtils;
import com.jxxc.jingxi.utils.GlideImgManager;
import com.jxxc.jingxi.utils.MyImageView;
import com.jxxc.jingxi.utils.SPUtils;
import com.jxxc.jingxi.utils.StatusBarUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;


/**
 * 主界面
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MainActivity extends MVPBaseActivity<MainContract.View, MainPresenter> implements MainContract.View, View.OnClickListener {

    private DrawerLayout drawerlayout;
    private TextView txt_deal1;
    private TextView txt_deal2;
    private TextView txt_deal3;
    private TextView txt_deal4;
    private TextView txt_deal5;
    //private TextView tv_location_city;
    private FrameLayout ly_content;

    private FirstFragment f1;
    private SecondFragment f2;
    private MyCarFragment f3;
    private MsgFragment f4;
    private MyFragment f5;

    @BindView(R.id.iv_user_logo)
    ImageView iv_user_logo;
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;
    @BindView(R.id.tv_user_dengji)
    ImageView tv_user_dengji;
    @BindView(R.id.tv_user_phonenumber)
    TextView tv_user_phonenumber;
    @BindView(R.id.ll_my_car)
    LinearLayout ll_my_car;
    @BindView(R.id.ll_my_order)
    LinearLayout ll_my_order;
    @BindView(R.id.ll_my_invoice)
    LinearLayout ll_my_invoice;
    @BindView(R.id.ll_my_wallet)
    LinearLayout ll_my_wallet;
    @BindView(R.id.ll_msg)
    LinearLayout ll_msg;
    @BindView(R.id.ll_setting)
    LinearLayout ll_setting;
    @BindView(R.id.ll_share)
    LinearLayout ll_share;
    @BindView(R.id.ll_user_info)
    LinearLayout ll_user_info;
    @BindView(R.id.ll_jiameng)
    LinearLayout ll_jiameng;
    @BindView(R.id.view_style)
    View view_style;
    @BindView(R.id.view_my_invoice)
    View view_my_invoice;
    @BindView(R.id.tv_call_phone)
    TextView tv_call_phone;
    @BindView(R.id.tv_jie_yong_shui)
    TextView tv_jie_yong_shui;
    @BindView(R.id.tv_jie_yong_shui_liang)
    TextView tv_jie_yong_shui_liang;
    @BindView(R.id.ll_jie_yue)
    LinearLayout ll_jie_yue;
    private FragmentManager fragmentManager;
    private long exitTime = 0;
    public static String registrationId;
    private List<View> listViews; // 图片组
    private LocationClient mLocationClient;
    private BDLocationListener mBDLocationListener;
    private boolean isFirstLoc = true; // 是否首次定位
    private double locationLatitude=0;
    private double locationLongitude=0;
    private ShareDialog shareDialog;
    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        StatusBarUtil.setStatusBarMode(this, false, R.color.black);
        if(!AppUtils.isEmpty(SPUtils.get(SPUtils.K_TOKEN,""))){
            mPresenter.getUserInfo();
            mPresenter.getStatic();
        }
        mPresenter.banner();//先请求广告数据，在加载界面
        mPresenter.queryAppVersion("3");//查询版本
        shareDialog = new ShareDialog(this);

        drawerlayout =(DrawerLayout)findViewById(R.id.drawerlayout);//抽屉
        //极光推送id
//        String pToken = JPushInterface.getRegistrationID(this);//1a0018970a33bcf8b75
//        Log.i("TAG","[MyReceiver] getRegistrationID===="+pToken);
        // 声明LocationClient类  
        mLocationClient = new LocationClient(getApplicationContext());
        mBDLocationListener = new MyBDLocationListener();
        initLocation();
        // 注册监听  
        mLocationClient.registerLocationListener(mBDLocationListener);

        //车辆管理
        ll_my_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ZzRouter.gotoActivity(MainActivity.this, MyCarActivity.class,"0");
            }
        });
        //我的订单
        ll_my_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ZzRouter.gotoActivity(MainActivity.this, MyOrderActivity.class);
            }
        });
        //我的钱包
        ll_my_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ZzRouter.gotoActivity(MainActivity.this, MyWalletActivity.class);
            }
        });
        //消息通知
        ll_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ZzRouter.gotoActivity(MainActivity.this, MessageActivity.class);
            }
        });
        //系统设置
        ll_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ZzRouter.gotoActivity(MainActivity.this, SetingActivity.class);
            }
        });
        //头像
        ll_user_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ZzRouter.gotoActivity(MainActivity.this, UsercenterActivity.class);
            }
        });
        //分享
        ll_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ZzRouter.gotoActivity(MainActivity.this, ShareActivity.class);
            }
        });
        view_style.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ZzRouter.gotoActivity(MainActivity.this, UsercenterActivity.class);
            }
        });
        ll_jiameng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ZzRouter.gotoActivity(MainActivity.this, PartnershipActivity.class);
            }
        });
        //400电话
        tv_call_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtils.callPhone(MainActivity.this,"4001008682");
            }
        });
        //空白
        ll_jie_yue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void initLocation() {
        // 声明定位参数  
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式 高精度  
        option.setCoorType("bd09ll");// 设置返回定位结果是百度经纬度 默认gcj02  
        option.setScanSpan(5000);// 设置发起定位请求的时间间隔 单位ms  
        option.setIsNeedAddress(true);// 设置定位结果包含地址信息  
        option.setNeedDeviceDirect(true);// 设置定位结果包含手机机头 的方向  
        // 设置定位参数  
        mLocationClient.setLocOption(option);
        // 启动定位  
        mLocationClient.start();
    }
    private class MyBDLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // 非空判断  
            if (location != null) {
//                if (!AppUtils.isEmpty(location.getCity())){
//                    tv_location_city.setText(location.getCity());
//                }else{
//                    tv_location_city.setText("菁喜");
//                }
                if (isFirstLoc) {
                    isFirstLoc = false;
                    locationLatitude = location.getLatitude();
                    locationLongitude = location.getLongitude();
                    SPUtils.put(SPUtils.K_ADDRESS,location.getAddress().address);
                    if ("4.9E-324".equals(locationLongitude) && "4.9E-324".equals(locationLatitude)) {
                        toast(MainActivity.this, "百度地图定位失败");
                    } else if ("5e-324".equals(locationLongitude) && "5e-324".equals(locationLatitude)) {
                        toast(MainActivity.this, "百度地图定位失败");
                    } else {
                        String lat = SPUtils.get(MainActivity.this, "lat", "");
                        String lng = SPUtils.get(MainActivity.this, "lng", "");
                        if (!"".equals(lat) && !"".equals(lng)) {
                            SPUtils.remove(MainActivity.this, lat);
                            SPUtils.remove(MainActivity.this, lng);
                        }
                        //保存经纬度
                        SPUtils.put("lat", location.getLatitude());
                        SPUtils.put("lng", location.getLongitude());
                    }
                    if (mLocationClient.isStarted()) {
                        // 获得位置之后停止定位  
                        mLocationClient.stop();
                    }
                }
            }
        }
    }

    //UI组件初始化与事件绑定
    private void bindView() {
        //tv_location_city = (TextView)this.findViewById(R.id.tv_location_city);
        txt_deal1 = (TextView)this.findViewById(R.id.txt_deal1);
        txt_deal2 = (TextView)this.findViewById(R.id.txt_deal2);
        txt_deal3 = (TextView)this.findViewById(R.id.txt_deal3);
        txt_deal4 = (TextView)this.findViewById(R.id.txt_deal4);
        txt_deal5 = (TextView)this.findViewById(R.id.txt_deal5);
        ly_content = (FrameLayout) findViewById(R.id.fragment_container);

        txt_deal1.setOnClickListener(this);
        txt_deal2.setOnClickListener(this);
        //txt_deal3.setOnClickListener(this);
        txt_deal4.setOnClickListener(this);
        txt_deal5.setOnClickListener(this);
        //默认显示第一个Fragment
        txt_deal1.performClick();//自动触发首页按钮

        txt_deal3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SetMealPayInfoActivity.class);
                intent.putExtra("serviceType", "0");
                intent.putExtra("companyId", "");
                intent.putExtra("gotoType", "0");
                startActivity(intent);
//                if (!AppUtils.isEmpty(SPUtils.get(SPUtils.K_TOKEN,""))){
//                }else{
//                    toast(MainActivity.this,"请先登录后使用");
//                    ZzRouter.gotoActivity(MainActivity.this, LoginActivity.class);
//                }
            }
        });
    }

    //重置所有文本的选中状态
    public void selected(){
        txt_deal1.setSelected(false);
        txt_deal2.setSelected(false);
        //txt_deal3.setSelected(false);
        txt_deal4.setSelected(false);
        txt_deal5.setSelected(false);
    }

    //隐藏所有Fragment
    public void hideAllFragment(FragmentTransaction transaction){
        if(f1!=null){
            transaction.hide(f1);
        }
        if(f2!=null){
            transaction.hide(f2);
        }
        if(f3!=null){
            transaction.hide(f3);
        }
        if(f4!=null){
            transaction.hide(f4);
        }
        if(f5!=null){
            transaction.hide(f5);
        }
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        switch(v.getId()){
            case R.id.txt_deal1:
                //StatusBarUtil.setStatusBarMode(this, false, R.color.home_ss_bg);
                selected();
                txt_deal1.setSelected(true);
                if(f1==null){
                    f1 = new FirstFragment(this,listViews);
                    transaction.add(R.id.fragment_container,f1);
                }else{
                    transaction.show(f1);
                }
                break;

            case R.id.txt_deal2:
                //StatusBarUtil.setStatusBarMode(this, false, R.color.white);
                selected();
                txt_deal2.setSelected(true);
                if(f2==null){
                    f2 = new SecondFragment(this);
                    transaction.add(R.id.fragment_container,f2);
                }else{
                    transaction.show(f2);
                }
                break;
//            case R.id.txt_deal3:
//                if (!AppUtils.isEmpty(SPUtils.get(SPUtils.K_TOKEN,""))){
//                    selected();
//                    txt_deal3.setSelected(true);
//                    if(f3==null){
//                        f3 = new MyCarFragment(this);
//                        transaction.add(R.id.fragment_container,f3);
//                    }else{
//                        transaction.show(f3);
//                    }
//                    Intent intent = new Intent(this, SetMealPayInfoActivity.class);
//                    intent.putExtra("serviceType", "0");
//                    intent.putExtra("companyId", "");
//                    startActivity(intent);
//                }else{
//                    toast(this,"请先登录后使用");
//                    ZzRouter.gotoActivity(this, LoginActivity.class);
//                }
//                break;
            case R.id.txt_deal4:
                if (!AppUtils.isEmpty(SPUtils.get(SPUtils.K_TOKEN,""))){
                    selected();
                    txt_deal4.setSelected(true);
                    if(f4==null){
                        f4 = new MsgFragment(this);
                        transaction.add(R.id.fragment_container,f4);
                    }else{
                        transaction.show(f4);
                    }
                }else{
                    toast(this,"请先登录后使用");
                    ZzRouter.gotoActivity(this, LoginActivity.class);
                }
                break;
            case R.id.txt_deal5:
                //StatusBarUtil.setStatusBarMode(this, false, R.color.white);
                if (!AppUtils.isEmpty(SPUtils.get(SPUtils.K_TOKEN,""))){
                    selected();
                    txt_deal5.setSelected(true);
                    if(f5==null){
                        f5 = new MyFragment(this);
                        transaction.add(R.id.fragment_container,f5);
                    }else{
                        transaction.show(f5);
                    }
                }else{
                    toast(this,"请先登录后使用");
                    ZzRouter.gotoActivity(this, LoginActivity.class);
                }
                break;
        }

        transaction.commit();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            toast(MainActivity.this, "再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            System.exit(0);
        }
    }

    //刷新数据
    @Override
    protected void onRestart() {
        super.onRestart();
    }

    //个人信息返回数据
    @Override
    public void getUserInfoCallBack(UserInfoEntity data) {
        GlideImgManager.loadCircleImage(this, data.avatar, iv_user_logo);
        tv_user_name.setText(AppUtils.isEmpty(data.userName)?data.realName:data.userName);
        tv_user_phonenumber.setText(data.phonenumber);
        tv_jie_yong_shui.setText(data.saveWater+"L");
//        if (data.finishedOrders > 0){
//            int shui = data.finishedOrders * data.saveWater;
//            tv_jie_yong_shui.setText(shui+"L");
//        }else{
//            tv_jie_yong_shui.setText("0L");
//        }
        //帐号类型，0：个人帐号；1企业帐号
        if (data.accountType==1){
            ll_my_invoice.setVisibility(View.VISIBLE);
            view_my_invoice.setVisibility(View.VISIBLE);
            ll_my_car.setVisibility(View.GONE);
        }else{
            ll_my_invoice.setVisibility(View.GONE);
            view_my_invoice.setVisibility(View.GONE);
            ll_my_car.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void bannerCallBack(final List<BannerEntity> data) {
        if (data.size()>0){
            listViews = new ArrayList<View>(); // 图片组
            for (int i = 0; i < data.size(); i++) {
                MyImageView imageView = new MyImageView (this);
                final int finalI = i;
                imageView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {// 设置图片点击事件
                        //跳转类型 1文章 2 活动 3 充值 4 门店5 下单
                        if (data.get(finalI).linkType==1){
                            if (AppUtils.isEmpty(SPUtils.get(SPUtils.K_TOKEN, ""))) {
                                gotoLogin();
                                return;
                            }
                            Intent intent = new Intent(MainActivity.this, FindDetailsActivity.class);
                            intent.putExtra("linkId",data.get(finalI).linkId);
                            startActivity(intent);
                        } else if (data.get(finalI).linkType == 2) {
                            if (AppUtils.isEmpty(SPUtils.get(SPUtils.K_TOKEN, ""))) {
                                gotoLogin();
                                return;
                            }
                            Intent intent = new Intent(MainActivity.this, ShareActivity.class);
                            //intent.putExtra("linkId", data.get(myPager.getCurIndex()).linkId);
                            startActivity(intent);
                        }else if (data.get(finalI).linkType == 3) {
                            if (AppUtils.isEmpty(SPUtils.get(SPUtils.K_TOKEN, ""))) {
                                gotoLogin();
                                return;
                            }
                            Intent intent = new Intent(MainActivity.this, RechargeActivity.class);
                            startActivity(intent);
                        }else if (data.get(finalI).linkType == 4) {
                            if (AppUtils.isEmpty(SPUtils.get(SPUtils.K_TOKEN, ""))) {
                                gotoLogin();
                                return;
                            }
                            Intent intent = new Intent(MainActivity.this, ShopListActivity.class);
                            startActivity(intent);
                        }else if (data.get(finalI).linkType == 5) {
                            if (AppUtils.isEmpty(SPUtils.get(SPUtils.K_TOKEN, ""))) {
                                gotoLogin();
                                return;
                            }
                            Intent intent = new Intent(MainActivity.this, SetMealPayInfoActivity.class);
                            intent.putExtra("serviceType", "0");
                            intent.putExtra("companyId", "");
                            startActivity(intent);
                        } else{
                            toast(MainActivity.this,"暂无标签");
                        }
                    }
                });
                imageView.setImageURL(data.get(i).imgUrl);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                listViews.add(imageView);
            }
        }
        bindView();
        //推荐套餐更多
        f1.setOnButtonClick(new FirstFragment.OnButtonClick() {
            @Override
            public void onClick(View view,int type) {
                if (type==1){
                    txt_deal4.performClick();//自动触发首页按钮
                }else{
                    //打开抽屉
                    if (!AppUtils.isEmpty(SPUtils.get(SPUtils.K_TOKEN,""))){
                        drawerlayout.openDrawer(Gravity.LEFT);//打开抽屉
                    }else{
                        ZzRouter.gotoActivity(MainActivity.this, LoginActivity.class);
                    }
                }
            }
        });
    }

    private void gotoLogin() {
        toast(this, "请先登录后使用");
        ZzRouter.gotoActivity(this, ConfigApplication.LOGIN_PATH, ZzRouter.HOST_PLUGIN);
    }

    //活动状态接口返回数据
    @Override
    public void getStaticCallBack(GetStaticEntity data) {
        //活动是否在有效期内标识 1是0否
        if (data.dateFlag==1){
            //弹邀请
            //每天只打开一次邀请活动弹窗
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());
            String nyr = simpleDateFormat.format(date);//当前时间
            if (!SPUtils.get("todayData","00").equals(nyr)){
                SPUtils.put("todayData",nyr);
                shareDialog.showShareDialog(true);
            }
        }
    }

    @Override
    public void updateCB(boolean must) {

    }

}
