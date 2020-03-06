package com.jxxc.jingxi.ui.shopdetails;


import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.AppointmentListEntity;
import com.jxxc.jingxi.entity.backparameter.CompanyDetailsEntity;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.utils.AppUtils;
import com.jxxc.jingxi.utils.GlideImgManager;

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
    private String cId;
    private String phonenumber="";
    private TechnicianAdapter technicianAdapter;
    private TimeAdapter timeAdapter;
    private List<AppointmentListEntity> list = new ArrayList<>();
    @Override
    protected int layoutId() {
        return R.layout.shop_details_activity;
    }

    @Override
    public void initData() {
        tv_title.setText("门店详情");
        cId = ZzRouter.getIntentData(this,String.class);
        StyledDialog.buildLoading("正在发送").setActivity(this).show();
        mPresenter.getCompany(cId);


        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String queryDate = formatter.format(date);
        mPresenter.appointmentList(cId,queryDate);

        gv_time_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (list.get(position).isForbidden()){
                    toast(ShopDetailsActivity.this,"当前时间段已预约满");
                }else{
                    timeAdapter.setSelectPosition(position);
                }
            }
        });
    }

    @OnClick({R.id.tv_back,R.id.tv_details_shop_call})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
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
