package com.jxxc.jingxi.ui.partnership;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.SelectAllAreaEntity;
import com.jxxc.jingxi.entity.backparameter.SelectByPhoneEntity;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.utils.AppUtils;
import com.jxxc.jingxi.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class PartnershipActivity extends MVPBaseActivity<PartnershipContract.View, PartnershipPresenter> implements PartnershipContract.View {

    @BindView(R.id.iv_back_s)
    ImageView iv_back_s;
    @BindView(R.id.tv_hehuo_shen)
    TextView tv_hehuo_shen;
    @BindView(R.id.tv_hehuo_city)
    TextView tv_hehuo_city;
    @BindView(R.id.tv_hehuo_qu)
    TextView tv_hehuo_qu;
    @BindView(R.id.rb_hehuoren)
    RadioButton rb_hehuoren;
    @BindView(R.id.rb_dailishang)
    RadioButton rb_dailishang;
    @BindView(R.id.et_user_name)
    EditText et_user_name;
    @BindView(R.id.et_user_phone)
    EditText et_user_phone;
    @BindView(R.id.et_memo)
    EditText et_memo;
    @BindView(R.id.btn_tijioa_he)
    Button btn_tijioa_he;
    private List<SelectAllAreaEntity> shengList = new ArrayList<>();
    private List<SelectAllAreaEntity.Citys> cityList = new ArrayList<>();
    private List<SelectAllAreaEntity.Citys.Districts> quList = new ArrayList<>();
    private String provinceId="";
    private String cityId="";
    private String districtId="";
    private String type="";
    @Override
    protected int layoutId() {
        return R.layout.partnership_activity;
    }

    @Override
    public void initData() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);//状态栏颜色
        mPresenter.selectAllArea();
    }

    @OnClick({R.id.iv_back_s,R.id.tv_hehuo_shen,R.id.tv_hehuo_city,R.id.tv_hehuo_qu,
            R.id.btn_tijioa_he,R.id.rb_hehuoren,R.id.rb_dailishang})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.iv_back_s://返回
                finish();
                break;
            case R.id.rb_hehuoren://加入菁喜合伙人
                type = "3";
                break;
            case R.id.rb_dailishang://加入菁喜代理商
                type = "2";
                break;
            case R.id.tv_hehuo_shen://省
                final String[] items = new String[shengList.size()];
                for (int i=0;i<shengList.size();i++){
                    items[i] = shengList.get(i).value;
                }
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("选择省份")
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tv_hehuo_shen.setText(items[which]);
                                provinceId = shengList.get(which).name;
                                cityList = shengList.get(which).citys;

                                //换省清空市和区
                                tv_hehuo_city.setText("市");
                                cityId="";
                                tv_hehuo_qu.setText("区");
                                districtId="";
                            }
                        });
                dialog.show();
                break;
            case R.id.tv_hehuo_city://市
                final String[] items1 = new String[cityList.size()];
                for (int i=0;i<cityList.size();i++){
                    items1[i] = cityList.get(i).value;
                }
                AlertDialog.Builder dialog1 = new AlertDialog.Builder(this);
                dialog1.setTitle("选择城市")
                        .setItems(items1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tv_hehuo_city.setText(items1[which]);
                                cityId = cityList.get(which).name;
                                quList = cityList.get(which).districts;
                                //换市清空区
                                tv_hehuo_qu.setText("区");
                                districtId="";
                            }
                        });
                dialog1.show();
                break;
            case R.id.tv_hehuo_qu://区
                final String[] items2 = new String[quList.size()];
                for (int i=0;i<quList.size();i++){
                    items2[i] = quList.get(i).value;
                }
                AlertDialog.Builder dialog2 = new AlertDialog.Builder(this);
                dialog2.setTitle("选择区域")
                        .setItems(items2, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tv_hehuo_qu.setText(items2[which]);
                                districtId = quList.get(which).name;
                            }
                        });
                dialog2.show();
                break;
            case R.id.btn_tijioa_he://提交
                if (AppUtils.isEmpty(type)){
                    toast(this,"请选择加入方式");
                }else if (AppUtils.isEmpty(et_user_name.getText().toString())){
                    toast(this,"请输入您的姓名");
                }else if (AppUtils.isEmpty(et_user_phone.getText().toString())){
                    toast(this,"请输入您的手机号码");
                }else if (AppUtils.isEmpty(provinceId)){
                    toast(this,"请选择省份");
                }else if (AppUtils.isEmpty(cityId)){
                    toast(this,"请选择城市");
                }else if (AppUtils.isEmpty(districtId)){
                    toast(this,"请选择区域");
                }else {
                    StyledDialog.buildLoading("正在提交").setActivity(this).show();
                    //mPresenter.selectByPhone(et_user_phone.getText().toString());
                    mPresenter.apply(et_user_phone.getText().toString(),et_user_name.getText().toString(),
                            provinceId,cityId,districtId,et_memo.getText().toString(),type);
                }
                break;
            default:
        }
    }

    //省市区返回数据
    @Override
    public void selectAllAreaCallBack(List<SelectAllAreaEntity> data) {
        shengList = data;
    }

    //查询是否已申请
    @Override
    public void selectByPhoneCallBack(SelectByPhoneEntity data) {
        if (!AppUtils.isEmpty(data)){
            toast(this,"已申请加入");
        }else{
            mPresenter.apply(et_user_phone.getText().toString(),et_user_name.getText().toString(),
                    provinceId,cityId,districtId,et_memo.getText().toString(),type);
        }
    }

    //申请加入接口
    @Override
    public void applyCallBack() {
        toast(this,"申请成功");
    }
}
