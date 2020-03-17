package com.jxxc.jingxi.ui.remark;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jxxc.jingxi.R;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.utils.AppUtils;
import com.jxxc.jingxi.utils.StatusBarUtil;

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
        StatusBarUtil.setStatusBarMode(this, true, R.color.public_all);//状态栏颜色
        tv_title.setText("备注信息");
    }

    @OnClick({R.id.tv_back,R.id.btn_remark})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
            case R.id.btn_remark://提交备注
                if (!AppUtils.isEmpty(ed_remark.getText().toString().trim())){
                    Intent intent = new Intent();
                    intent.setAction("jingxi_user_remark_209344");
                    intent.putExtra("remark",ed_remark.getText().toString());
                    sendOrderedBroadcast(intent,null);
                    finish();
                }else{
                    toast(this,"请留下您的评论");
                }
                break;
            default:
        }
    }
}
