package com.jxxc.jingxi.ui.remark;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class RemarkActivity extends MVPBaseActivity<RemarkContract.View, RemarkPresenter> implements RemarkContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.ed_remark)
    EditText ed_remark;
    @BindView(R.id.btn_remark)
    Button btn_remark;
    @Override
    protected int layoutId() {
        return R.layout.remark_activity;
    }

    @Override
    public void initData() {
        tv_title.setText("备注信息");
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
