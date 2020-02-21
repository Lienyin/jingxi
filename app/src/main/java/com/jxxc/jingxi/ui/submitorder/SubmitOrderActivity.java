package com.jxxc.jingxi.ui.submitorder;


import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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
    @BindView(R.id.shang_men)
    View shang_men;
    @BindView(R.id.dao_dian)
    View dao_dian;
    @BindView(R.id.gv_fuwu_data)
    GridView gv_fuwu_data;
    @BindView(R.id.ll_fuwu_no_data)
    LinearLayout ll_fuwu_no_data;
    @Override
    protected int layoutId() {
        return R.layout.submit_order_activity;
    }

    @Override
    public void initData() {
        tv_title.setText("菁喜洗车");
    }

    @OnClick({R.id.tv_back,R.id.rb_shangmen_service,R.id.rb_daodian_service,R.id.rb_wai_guan,
            R.id.rb_zheng_che})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
            case R.id.rb_shangmen_service://上门
                shang_men.setVisibility(View.VISIBLE);
                dao_dian.setVisibility(View.GONE);
                break;
            case R.id.rb_daodian_service://到店
                shang_men.setVisibility(View.GONE);
                dao_dian.setVisibility(View.VISIBLE);
                break;
            case R.id.rb_wai_guan://外观清洗
                gv_fuwu_data.setVisibility(View.VISIBLE);
                ll_fuwu_no_data.setVisibility(View.GONE);
                break;
            case R.id.rb_zheng_che://整车清洗
                gv_fuwu_data.setVisibility(View.GONE);
                ll_fuwu_no_data.setVisibility(View.VISIBLE);
                break;
            default:
        }
    }
}
