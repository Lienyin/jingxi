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
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.ColorEntity;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.ui.cartypeselect.CarTypeSelectActivity;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.utils.KeyboardUtil;

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
    GridView gv_color_data;
    private KeyboardUtil keyboardUtil;
    private String brandId;
    private String carTypeId;
    private AddCarAdapter addCarAdapter;
    private List<ColorEntity.Color> list = new ArrayList<>();
    @Override
    protected int layoutId() {
        return R.layout.add_car_activity;
    }

    @Override
    public void initData() {
        tv_title.setText("添加车辆");
        mEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (keyboardUtil == null) {
                    keyboardUtil = new KeyboardUtil(AddCarActivity.this, mEditText);
                    keyboardUtil.hideSoftInputMethod();
                    keyboardUtil.showKeyboard();
                } else {
                    keyboardUtil.showKeyboard();
                }
                return false;
            }
        });
        mEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                Log.i("字符变换后", "afterTextChanged");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.i("字符变换前", s + "-" + start + "-" + count + "-" + after);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.i("字符变换中", s + "-" + "-" + start + "-" + before + "-" + count);
            }
        });

        registerReceiver(receiver, new IntentFilter("car_type_choose_120021"));

        mPresenter.queryAppVersion("3");
        addCarAdapter = new AddCarAdapter(this);
        addCarAdapter.setData(list);
        gv_color_data.setAdapter(addCarAdapter);
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

    @OnClick({R.id.tv_back,R.id.ll_car_type,R.id.tv_car_type})
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
            default:
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (keyboardUtil.isShow()) {
                keyboardUtil.hideKeyboard();
            } else {
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
    }
}
