package com.jxxc.jingxi.ui.shopdetails;


import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.AppointmentListEntity;
import com.jxxc.jingxi.entity.backparameter.CompanyDetailsEntity;
import com.jxxc.jingxi.entity.requestparameter.ExitLogin;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.utils.AppUtils;
import com.jxxc.jingxi.utils.GlideImgManager;

import org.greenrobot.eventbus.EventBus;

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
    @BindView(R.id.gv_technician_data)
    GridView gv_technician_data;
    @BindView(R.id.gv_time_data)
    GridView gv_time_data;
    @BindView(R.id.gv_weekOf_date)
    GridView gv_weekOf_date;
    @BindView(R.id.btn_subscribe_time)
    Button btn_subscribe_time;
    private String cId;
    private String phonenumber="";
    private String address="";
    private TechnicianAdapter technicianAdapter;
    private TimeAdapter timeAdapter;
    private WeekOfAdapter weekOfAdapter;
    private List<AppointmentListEntity> list = new ArrayList<>();
    private String timeStr;
    private String dateStr;
    @Override
    protected int layoutId() {
        return R.layout.shop_details_activity;
    }

    @Override
    public void initData() {
        tv_title.setText("门店详情");
        cId = ZzRouter.getIntentData(this,String.class);
        StyledDialog.buildLoading("数据加载中").setActivity(this).show();
        mPresenter.getCompany(cId);
        //获取当前日期
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String queryDate = formatter.format(date);//今天日期
        dateStr = queryDate;//默认日期
        mPresenter.appointmentList(cId,queryDate);

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
        weekOfAdapter.setData(test(7));
        gv_weekOf_date.setAdapter(weekOfAdapter);
        //获取周几
        gv_weekOf_date.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                StyledDialog.buildLoading("数据加载中").setActivity(ShopDetailsActivity.this).show();
                weekOfAdapter.setSelectPosition(position);
                Calendar date = Calendar.getInstance();
                String year = String.valueOf(date.get(Calendar.YEAR));
                dateStr = year+"-"+test(7).get(position).toString().substring(0,5);
                mPresenter.appointmentList(cId,dateStr);
            }
        });
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

    @OnClick({R.id.tv_back,R.id.tv_details_shop_call,R.id.btn_subscribe_time})
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
            default:
        }
    }

    //返回详情
    @Override
    public void getCompanyCallBack(CompanyDetailsEntity data) {
        technicianAdapter = new TechnicianAdapter(this);
        technicianAdapter.setData(data.technicians);
        gv_technician_data.setAdapter(technicianAdapter);

        GlideImgManager.loadRectangleSiteImage(this, data.company.imgUrl,iv_details_shop_mendian);
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
        timeAdapter = new TimeAdapter(this);
        timeAdapter.setData(data);
        gv_time_data.setAdapter(timeAdapter);
    }
}