package com.jxxc.jingxi.ui.setmealpayinfo;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jxxc.jingxi.R;
import com.jxxc.jingxi.dialog.TimeDialog;
import com.jxxc.jingxi.entity.backparameter.AddressEntity;
import com.jxxc.jingxi.entity.backparameter.AppointmentListEntity;
import com.jxxc.jingxi.entity.backparameter.CarListEntity;
import com.jxxc.jingxi.entity.backparameter.RecommendComboInfoEntity;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.ui.addressdetails.AddressDetailsActivity;
import com.jxxc.jingxi.ui.mycar.MyCarActivity;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.utils.AppUtils;
import com.jxxc.jingxi.utils.GlideImgManager;
import com.jxxc.jingxi.utils.SPUtils;

import java.text.SimpleDateFormat;
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
    private String siteLat="";
    private String siteLng="";
    private TimeDialog timeDialog;
    private String appointmentStartTime="";
    private String appointmentEndTime="";

    private RecommendComboInfoEntity.RecommendCombo recommendComboInfoEntity;

    @Override
    protected int layoutId() {
        return R.layout.set_meal_pay_info_activity;
    }

    @Override
    public void initData() {
        tv_title.setText("填写信息");
        recommendComboInfoEntity = ZzRouter.getIntentData(this,RecommendComboInfoEntity.RecommendCombo.class);
        //套餐信息
        GlideImgManager.loadRectangleImage(this, recommendComboInfoEntity.imgUrl, iv_recommend_icon);
        tv_recommend_name.setText(recommendComboInfoEntity.comboName);
        tv_recommend_context.setText(recommendComboInfoEntity.comboComment);
        tv_recommend_money.setText("￥"+recommendComboInfoEntity.totalPrice);
        tv_recommend_num.setText("已售"+recommendComboInfoEntity.salesVolume);
        tv_phone_number.setText(SPUtils.get(SPUtils.K_SESSION_MOBILE,""));

        registerReceiver(receiver, new IntentFilter("jingxi_car_addres_12002"));
        registerReceiver(receiverCarInfo, new IntentFilter("jing_xi_my_car_info"));

        //获取当前日期
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String queryDate = formatter.format(date);//今天日期
        mPresenter.appointmentList("",queryDate);

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
            //停车地点
            AddressEntity addressEntity = (AddressEntity) intent.getSerializableExtra("addressEntity");
            if (!AppUtils.isEmpty(addressEntity)){
                tv_car_address.setText(addressEntity.getAddress());
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
            tv_car_info.setText(carListEntity.carNum+"  "+carListEntity.brandName+"  "+carListEntity.typeName);
        }
    };

    @OnClick({R.id.tv_back,R.id.ll_stop_car_address,R.id.ll_car_info,R.id.ll_yuyue_time})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
            case R.id.ll_stop_car_address://停车地址
                ZzRouter.gotoActivity(this, AddressDetailsActivity.class);
                break;
            case R.id.ll_car_info://爱车信息
                ZzRouter.gotoActivity(this, MyCarActivity.class,"1");
                break;
            case R.id.ll_yuyue_time://服务时间
                timeDialog.showShareDialog(true);
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
//        if (!AppUtils.isEmpty(receiverCarInfo)){
//            unregisterReceiver(receiverCarInfo);
//        }
        if (!AppUtils.isEmpty(receiver)){
            unregisterReceiver(receiver);
        }
    }

    @Override
    public void appointmentListCallBack(List<AppointmentListEntity> data) {
        timeDialog.updateTimeAdapter(data);
    }
}
