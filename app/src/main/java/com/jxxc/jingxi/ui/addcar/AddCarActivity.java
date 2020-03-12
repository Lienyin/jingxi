package com.jxxc.jingxi.ui.addcar;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.CarListEntity;
import com.jxxc.jingxi.entity.backparameter.ColorEntity;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.ui.cartypeselect.CarTypeSelectActivity;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.utils.AppUtils;
import com.jxxc.jingxi.utils.KeyboardUtil;
import com.jxxc.jingxi.utils.MyGridView;
import com.wanjian.cockroach.App;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class AddCarActivity extends MVPBaseActivity<AddCarContract.View, AddCarPresenter> implements AddCarContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_car_type)
    TextView tv_car_type;
    @BindView(R.id.edit_text)
    EditText mEditText;
    @BindView(R.id.ll_car_type)
    LinearLayout ll_car_type;
    @BindView(R.id.gv_color_data)
    MyGridView gv_color_data;
    @BindView(R.id.btn_add_car)
    Button btn_add_car;
    @BindView(R.id.ll_car_moren)
    LinearLayout ll_car_moren;
    @BindView(R.id.cb_car_moren)
    CheckBox cb_car_moren;
    @BindView(R.id.t1)
    TextView t1;
    @BindView(R.id.t2)
    TextView t2;
    @BindView(R.id.t3)
    TextView t3;
    @BindView(R.id.t4)
    TextView t4;
    @BindView(R.id.t5)
    TextView t5;
    @BindView(R.id.t6)
    TextView t6;
    @BindView(R.id.t7)
    TextView t7;
    @BindView(R.id.t8)
    TextView t8;
    @BindView(R.id.tv_new)
    TextView tv_new;
    private KeyboardUtil keyboardUtil;
    private String brandId;
    private String carTypeId;
    private String colorId;
    private AddCarAdapter addCarAdapter;
    private List<ColorEntity.Color> list = new ArrayList<>();
    private CarListEntity carData;
    private String key = "";
    private int isNewEnergy;//是否新能源 1是0否
    @Override
    protected int layoutId() {
        return R.layout.add_car_activity;
    }

    @Override
    public void initData() {
        carData = (CarListEntity) getIntent().getSerializableExtra("carData");
        if (!AppUtils.isEmpty(carData)){
            tv_title.setText("修改车辆");
            btn_add_car.setText("修改车辆");
            ll_car_moren.setVisibility(View.VISIBLE);
            mEditText.setFocusable(false);
            tv_new.setClickable(false);
        }else{
            tv_title.setText("添加车辆");
            btn_add_car.setText("添加车辆");
            ll_car_moren.setVisibility(View.GONE);
//            mEditText.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View view, MotionEvent event) {
//
//                    return false;
//                }
//            });
            keyboardUtil = new KeyboardUtil(AddCarActivity.this, mEditText);
            keyboardUtil.hideSoftInputMethod();
            keyboardUtil.hideKeyboard();
            mEditText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (keyboardUtil.isShow()){
                        keyboardUtil.hideKeyboard();
                    }else{
                        keyboardUtil.showKeyboard();
                    }
                }
            });
        }
        mEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                //Log.i("字符变换后", "afterTextChanged");
                key = s.toString();
                setKey();
                if (mEditText.getText().toString().length()==7&&t8.getVisibility()==View.GONE){
                    if (keyboardUtil == null) {
                        keyboardUtil = new KeyboardUtil(AddCarActivity.this, mEditText);
                        keyboardUtil.hideSoftInputMethod();
                        keyboardUtil.hideKeyboard();
                    } else {
                        keyboardUtil.hideKeyboard();
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Log.i("字符变换前", s + "-" + start + "-" + count + "-" + after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Log.i("字符变换中", s + "-" + "-" + start + "-" + before + "-" + count);
            }
        });

        registerReceiver(receiver, new IntentFilter("car_type_choose_120021"));

        mPresenter.queryAppVersion("3");
        addCarAdapter = new AddCarAdapter(this);
        addCarAdapter.setData(list);
        gv_color_data.setAdapter(addCarAdapter);
        gv_color_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                addCarAdapter.setSelectPosition(i);
                addCarAdapter.notifyDataSetChanged();
                colorId = list.get(i).color+"";
            }
        });
    }

    private void setKey() {
        char[] arr = key.toCharArray();
        t1.setText("");
        t2.setText("");
        t3.setText("");
        t4.setText("");
        t5.setText("");
        t6.setText("");
        t7.setText("");
        t8.setText("");
        for (int i = 0; i < arr.length; i++) {
            if (i == 0) {
                t1.setText(String.valueOf(arr[0]));
            } else if (i == 1) {
                t2.setText(String.valueOf(arr[1]));
            }else if (i == 2) {
                t3.setText(String.valueOf(arr[2]));
            } else if (i == 3) {
                t4.setText(String.valueOf(arr[3]));
            } else if (i == 4) {
                t5.setText(String.valueOf(arr[4]));
            } else if (i == 5) {
                t6.setText(String.valueOf(arr[5]));
            } else if (i == 6) {
                t7.setText(String.valueOf(arr[6]));
            } else if (i == 7) {
                if (t8.getVisibility()==View.VISIBLE){
                    t8.setText(String.valueOf(arr[7]));
                }
            }
        }
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //接收到广播以后要做的事
            brandId = intent.getStringExtra("brandId");
            String brandName = intent.getStringExtra("brandName");
            carTypeId = intent.getStringExtra("carTypeId");
            String carTypeName = intent.getStringExtra("carTypeName");
            tv_car_type.setText(brandName+"·"+carTypeName);
        }
    };

    @OnClick({R.id.tv_back,R.id.ll_car_type,R.id.tv_car_type,R.id.btn_add_car,R.id.tv_new,R.id.t8})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
            case R.id.ll_car_type://车型选择
            case R.id.tv_car_type://车型选择
                ZzRouter.gotoActivity(this, CarTypeSelectActivity.class);
                break;
            case R.id.btn_add_car://添加/修改车辆
                if (AppUtils.isEmpty(mEditText.getText().toString())){
                    toast(this,"请输入车牌");
                }else if (mEditText.getText().toString().length()<7){
                    toast(this,"请输入正确车牌");
                }else if (AppUtils.isEmpty(brandId)){
                    toast(this,"请选择车型");
                }else if (AppUtils.isEmpty(colorId)){
                    toast(this,"请选择车身颜色");
                }else{
                    String carNum = "";
                    if (t8.getVisibility()==View.VISIBLE){
                        isNewEnergy=1;//新能源
                        carNum = mEditText.getText().toString();
                    }else{
                        isNewEnergy=0;//普车
                        carNum = mEditText.getText().toString().substring(0,7);
                    }
                    if (!AppUtils.isEmpty(carData)){
                        //修改车辆
                        String isDefault ="";//是否默认车辆 1是0否
                        if (cb_car_moren.isChecked()==true){
                            isDefault ="1";
                        }else{
                            isDefault ="0";
                        }
                        mPresenter.editCar(carNum,brandId,carTypeId,colorId,isNewEnergy+"",isDefault);
                    }else{
                        //添加车辆
                        mPresenter.addCar(carNum,brandId,carTypeId,colorId,isNewEnergy+"");
                    }
                }
                break;
            case R.id.tv_new://新能源
                tv_new.setVisibility(View.GONE);
                t8.setVisibility(View.VISIBLE);
                if (keyboardUtil == null) {
                    keyboardUtil = new KeyboardUtil(AddCarActivity.this, mEditText);
                    keyboardUtil.hideSoftInputMethod();
                    keyboardUtil.showKeyboard();
                } else {
                    keyboardUtil.showKeyboard();
                }
                break;
            case R.id.t8://第8位
                if (AppUtils.isEmpty(carData)){
                    tv_new.setVisibility(View.VISIBLE);
                    t8.setVisibility(View.GONE);
                    keyboardUtil.hideKeyboard();
                }
                break;
            default:
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!AppUtils.isEmpty(keyboardUtil)){
                if (keyboardUtil.isShow()) {
                    keyboardUtil.hideKeyboard();
                } else {
                    finish();
                }
            }else{
                finish();
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    //颜色返回数据
    @Override
    public void queryAppVersionCallBack(ColorEntity data) {
        list = data.colors;
        addCarAdapter.setData(list);
        addCarAdapter.notifyDataSetChanged();

        if (!AppUtils.isEmpty(carData)){
            //编辑车辆
            brandId = carData.brandId;
            carTypeId = carData.typeId;
            colorId = carData.color+"";
            if (carData.carNum.length()>7){
                //新能源车牌
                tv_new.setVisibility(View.GONE);
                t8.setVisibility(View.VISIBLE);
                mEditText.setText(carData.carNum);
            }else{
                mEditText.setText(carData.carNum);
            }
            tv_car_type.setText(carData.brandName+"·"+carData.typeName);
            addCarAdapter.setSelectPosition(carData.color-1);
            addCarAdapter.notifyDataSetChanged();
            if (carData.isDefault==1){
                cb_car_moren.setChecked(true);
            }else{
                cb_car_moren.setChecked(false);
            }

        }
    }

    //添加车辆成功返回数据
    @Override
    public void addCarCallBack() {
        finish();
    }
}
