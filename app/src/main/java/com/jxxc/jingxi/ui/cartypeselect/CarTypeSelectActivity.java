package com.jxxc.jingxi.ui.cartypeselect;


import android.view.View;
import android.widget.TextView;

import com.jxxc.jingxi.R;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.utils.AnimUtils;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class CarTypeSelectActivity extends MVPBaseActivity<CarTypeSelectContract.View, CarTypeSelectPresenter> implements CarTypeSelectContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @Override
    protected int layoutId() {
        return R.layout.car_type_select_activity;
    }

    @Override
    public void initData() {
        tv_title.setText("车型选择");
    }

    @OnClick({R.id.tv_back})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
            default:
        }
    }
}
