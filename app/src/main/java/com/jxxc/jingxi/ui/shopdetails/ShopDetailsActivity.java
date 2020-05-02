package com.jxxc.jingxi.ui.shopdetails;


import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.ConfigApplication;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.adapter.RecommendSetMealAdapter;
import com.jxxc.jingxi.entity.backparameter.AppointmentListEntity;
import com.jxxc.jingxi.entity.backparameter.CarListEntity;
import com.jxxc.jingxi.entity.backparameter.CompanyDetailsEntity;
import com.jxxc.jingxi.entity.backparameter.RecommendComboInfoEntity;
import com.jxxc.jingxi.entity.requestparameter.ExitLogin;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.ui.setmealpay.SetMealPayActivity;
import com.jxxc.jingxi.ui.setmealpayinfo.SetMealPayInfoActivity;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.utils.AppUtils;
import com.jxxc.jingxi.utils.GlideImgManager;
import com.jxxc.jingxi.utils.HorizontalListView;
import com.jxxc.jingxi.utils.ListViewForScrollView;
import com.jxxc.jingxi.utils.SPUtils;
import com.jxxc.jingxi.utils.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class ShopDetailsActivity extends MVPBaseActivity<ShopDetailsContract.View, ShopDetailsPresenter> implements ShopDetailsContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_details_shop_mendian)
    ImageView iv_details_shop_mendian;
    @BindView(R.id.tv_details_shop_name)
    TextView tv_details_shop_name;
    @BindView(R.id.tv_details_shop_yy)
    TextView tv_details_shop_yy;
    @BindView(R.id.tv_details_shop_jm)
    TextView tv_details_shop_jm;
    @BindView(R.id.tv_details_shop_pf)
    TextView tv_details_shop_pf;
    @BindView(R.id.tv_details_shop_dd)
    TextView tv_details_shop_dd;
    @BindView(R.id.tv_details_shop_js)
    TextView tv_details_shop_js;
    @BindView(R.id.tv_details_shop_address)
    TextView tv_details_shop_address;
    @BindView(R.id.tv_details_shop_call_number)
    TextView tv_details_shop_call_number;
    @BindView(R.id.tv_details_shop_call)
    TextView tv_details_shop_call;
    @BindView(R.id.tv_details_shop_juli)
    TextView tv_details_shop_juli;
    @BindView(R.id.gv_technician_data)
    HorizontalListView gv_technician_data;
    @BindView(R.id.gv_time_data)
    GridView gv_time_data;
    @BindView(R.id.gv_weekOf_date)
    HorizontalListView gv_weekOf_date;
    @BindView(R.id.btn_subscribe_time)
    Button btn_subscribe_time;
    @BindView(R.id.lv_set_meal_data)
    ListView lv_set_meal_data;
    @BindView(R.id.ll_xia_dan)
    LinearLayout ll_xia_dan;
    @BindView(R.id.rb_xiche)
    RadioButton rb_xiche;
    @BindView(R.id.rb_meirong)
    RadioButton rb_meirong;
    @BindView(R.id.rb_huli)
    RadioButton rb_huli;
    @BindView(R.id.scrollView)
    ScrollView scrollView;

    private String cId;
    private double distance;
    private String phonenumber="";
    private String address="";
    private String companyName="";
    private TechnicianAdapter technicianAdapter;
    private TimeAdapter timeAdapter;
    private WeekOfAdapter weekOfAdapter;
    private List<AppointmentListEntity> list = new ArrayList<>();
    private String timeStr;
    private String dateStr;
    private RecommendSetMealAdapter recommendSetMealAdapter;
    private List<RecommendComboInfoEntity> recommendComboInfoEntity = new ArrayList<>();//服务类型 1洗车2美容3护理
    private List<RecommendComboInfoEntity> recommendComboInfoEntityList0 = new ArrayList<>();//服务类型 1洗车2美容3护理
    private List<RecommendComboInfoEntity> recommendComboInfoEntityList1 = new ArrayList<>();//服务类型 1洗车2美容3护理
    private List<RecommendComboInfoEntity> recommendComboInfoEntityList2 = new ArrayList<>();//服务类型 1洗车2美容3护理
    private List<RecommendComboInfoEntity> commitEntity = new ArrayList<>();//提交数据
    private int carType=0;//车型
    @Override
    protected int layoutId() {
        return R.layout.shop_details_activity;
    }

    @Override
    public void initData() {
        StatusBarUtil.setStatusBarMode(this, false, R.color.white);
        tv_title.setText("门店详情");
        cId = getIntent().getStringExtra("companyId");
        distance = getIntent().getDoubleExtra("distance",0);
        StyledDialog.buildLoading("数据加载中").setActivity(this).show();
        mPresenter.getCompany(cId);//加盟商详细
        mPresenter.getCarList();
        //设置套餐
        recommendSetMealAdapter = new RecommendSetMealAdapter(this);
        //获取当前日期
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String queryDate = formatter.format(date);//今天日期
        dateStr = queryDate;//默认日期

        //预约时间段
        gv_time_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (list.get(position).isForbidden()){
                    toast(ShopDetailsActivity.this,"时间已过或已预约满");
                }else{
                    timeAdapter.setSelectPosition(position);
                    timeStr = list.get(position).title;//时间
                }
            }
        });

        //日期设置
        weekOfAdapter = new WeekOfAdapter(this);
        weekOfAdapter.setData(test(30));
        gv_weekOf_date.setAdapter(weekOfAdapter);
        //获取周几
        gv_weekOf_date.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                StyledDialog.buildLoading("数据加载中").setActivity(ShopDetailsActivity.this).show();
                weekOfAdapter.setSelectPosition(position);
                Calendar date = Calendar.getInstance();
                String year = String.valueOf(date.get(Calendar.YEAR));
                dateStr = year+"-"+test(30).get(position).toString().substring(0,5);
                mPresenter.appointmentList(cId,dateStr);
            }
        });

//        lv_set_meal_data.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                //scrollView.getScrollY()超出屏幕的高度
//                //scrollView.getHeight()屏幕显示的高度
//                //scrollView.getPaddingTop()，scrollView.getPaddingBottom()//上下Padding
//                //scrollView.getChildAt(0).getHeight()//scrollView唯一子View的高度
//                if(scrollView.getScrollY() + scrollView.getHeight() - scrollView.getPaddingTop() - scrollView.getPaddingBottom() == scrollView.getChildAt(0).getHeight()){
//                    scrollView.requestDisallowInterceptTouchEvent(true);
//                }
//                return false;
//            }
//        });

    }

    /**
     * 获取过去或者未来 任意天内的日期数组
     * @param intervals      intervals天内
     * @return              日期数组
     */
    public static ArrayList<String> test(int intervals ) {
        ArrayList<String> fetureDaysList = new ArrayList<>();
        for (int i = 0; i < intervals; i++) {
            fetureDaysList.add(getFetureDate(i));
        }
        return fetureDaysList;
    }

    /**
     * 获取未来 第 past 天的日期
     * @param past
     * @return
     */
    public static String getFetureDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("MM-dd");
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        Log.e(null, result);
        return result+getWeekOfDate(today);
    }

    /**
      * 获取当前日期是星期几<br>
      * @param dt
      * @return 当前日期是星期几
      */
    public static String getWeekOfDate(Date dt) {
         String[] weekDays = {"\n周日", "\n周一", "\n周二", "\n周三", "\n周四", "\n周五", "\n周六"};
         Calendar cal = Calendar.getInstance();
         cal.setTime(dt);
         int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
         if (w < 0)
             w = 0;
        return weekDays[w];
    }

    @OnClick({R.id.tv_back,R.id.tv_details_shop_call,R.id.btn_subscribe_time,R.id.ll_xia_dan,
    R.id.rb_xiche,R.id.rb_meirong,R.id.rb_huli})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
            case R.id.btn_subscribe_time://预约时间
                if (AppUtils.isEmpty(timeStr)){
                    toast(this,"请选择服务时间");
                }else {
                    String start = dateStr+" "+timeStr.substring(0,5);
                    String end = dateStr+" "+timeStr.substring(6,11);
                    Intent intent = new Intent();
                    intent.setAction("shop_daodian_19830_fuwu_110");
                    intent.putExtra("timeStart",start);
                    intent.putExtra("timeEnd",end);
                    intent.putExtra("companyId",cId);
                    intent.putExtra("address",address);
                    sendOrderedBroadcast(intent,null);
                    EventBus.getDefault().post(new ExitLogin());
                    finish();
                }
                break;
            case R.id.tv_details_shop_call://联系技师
                if (AppUtils.isEmpty(phonenumber)){
                    toast(this,"空号");
                }else{
                    AppUtils.callPhone(this,phonenumber);
                }
                break;
            case R.id.ll_xia_dan://预约下单
                if (AppUtils.isEmpty(SPUtils.get(SPUtils.K_TOKEN,""))){
                    gotoLogin();
                    return;
                }else{
                    if (recommendComboInfoEntity.size()>0){
                        if (commitEntity.size()>0){commitEntity.clear();}
                        for (int i=0;i<recommendComboInfoEntity.size();i++){
                            if (recommendComboInfoEntity.get(i).isSelect==true){
                                commitEntity.add(recommendComboInfoEntity.get(i));
                            }
                        }
                    }

                    if (commitEntity.size()<=0){toast(this,"请选择服务套餐");return;}
                    Intent intent = new Intent(ShopDetailsActivity.this, SetMealPayInfoActivity.class);
                    intent.putExtra("recommendComboInfoEntity", (Serializable) commitEntity);
                    intent.putExtra("serviceType","1");
                    intent.putExtra("companyId",cId);
                    intent.putExtra("companyName",companyName);
                    intent.putExtra("gotoType","1");
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.rb_xiche://洗车
                recommendSetMealAdapter.setSelectPosition(-1);
                recommendSetMealAdapter.setData(recommendComboInfoEntityList0,2,carType);
                recommendSetMealAdapter.notifyDataSetChanged();
                break;
            case R.id.rb_meirong://美容
                recommendSetMealAdapter.setSelectPosition(-1);
                recommendSetMealAdapter.setData(recommendComboInfoEntityList1,2,carType);
                recommendSetMealAdapter.notifyDataSetChanged();
                break;
            case R.id.rb_huli://护理
                recommendSetMealAdapter.setSelectPosition(-1);
                recommendSetMealAdapter.setData(recommendComboInfoEntityList2,2,carType);
                recommendSetMealAdapter.notifyDataSetChanged();
                break;
            default:
        }
    }

    private void gotoLogin() {
        toast(this, "请先登录后使用");
        ZzRouter.gotoActivity(this, ConfigApplication.LOGIN_PATH, ZzRouter.HOST_PLUGIN);
    }

    //返回详情
    @Override
    public void getCompanyCallBack(CompanyDetailsEntity data) {
        technicianAdapter = new TechnicianAdapter(this);
        technicianAdapter.setData(data.technicians);
        gv_technician_data.setAdapter(technicianAdapter);

        GlideImgManager.loadRectangleSiteImage(this, data.company.imgUrl,iv_details_shop_mendian);
        companyName = data.company.companyName;
        tv_details_shop_name.setText(data.company.companyName);
        if (data.company.isBusiness==1){
            tv_details_shop_yy.setText("营业中");
        }else{
            tv_details_shop_yy.setText("休息中");
        }
        if (data.company.companyType==1){
            tv_details_shop_jm.setText("直营店");
        }else if (data.company.companyType==2){
            tv_details_shop_jm.setText("加盟店");
        }else{
            tv_details_shop_jm.setText("合作店");
        }
        //计算距离distance
        String showDistance = "?? m";
        if (distance > 1000) {
            showDistance = new DecimalFormat("0.00").format(distance / 1000d) + " km";
        } else {
            showDistance = new DecimalFormat("0").format(distance) + " m";
        }
        tv_details_shop_juli.setText(showDistance);
        tv_details_shop_pf.setText(data.company.score);
        tv_details_shop_dd.setText(data.company.orderNum);
        tv_details_shop_js.setText(data.company.technicianNum);
        tv_details_shop_address.setText(data.company.address);
        address= data.company.address;
        phonenumber = data.company.phonenumber;
        tv_details_shop_call_number.setText(data.company.phonenumber);
    }

    //预约时间返回数据
    @Override
    public void appointmentListCallBack(List<AppointmentListEntity> data) {
        list = data;
        timeAdapter = new TimeAdapter(this,1);
        timeAdapter.setData(data);
        gv_time_data.setAdapter(timeAdapter);
    }

    //获取车辆列表
    @Override
    public void getCarListCallBack(List<CarListEntity> data) {
        mPresenter.recommendComboInfo("1",cId);//获取洗车套餐

        if (data.size()>0){//y有车
            //展示默认车辆，没有默认车辆展示第一辆
            //是否默认 1是0否
            int a=0;
            for (int i=0;i<data.size();i++){
                if (data.get(i).isDefault==1){
                    a++;
                    carType = data.get(i).typeId;//默认车型
                    SPUtils.put(SPUtils.K_CAR_TYPE,carType);
                }
            }
            //没有设置默认车辆
            if (a==0){
                carType = data.get(0).typeId;//第一个车型
                SPUtils.put(SPUtils.K_CAR_TYPE,carType);
            }
        }
    }

    //洗车套餐列表数据
    @Override
    public void recommendComboInfoCallBack(final List<RecommendComboInfoEntity> data) {
        if (data.size()>0){
            recommendComboInfoEntity = data;
            //根据绑定车型显示套餐
            //默认显示第0个套餐
            for (int i=0;i<data.size();i++){
                //服务类型 1洗车2美容3护理
                if (data.get(i).serviceType==1){
                    recommendComboInfoEntityList0.add(data.get(i));
                }else if (data.get(i).serviceType==2){
                    recommendComboInfoEntityList1.add(data.get(i));
                }else{
                    recommendComboInfoEntityList2.add(data.get(i));
                }
            }
            recommendSetMealAdapter.setData(recommendComboInfoEntityList0,2,carType);
            lv_set_meal_data.setAdapter(recommendSetMealAdapter);
            recommendSetMealAdapter.notifyDataSetChanged();
        }
    }
}
