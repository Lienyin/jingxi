package com.jxxc.jingxi.ui.evaluate;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.MyOrderEntity;
import com.jxxc.jingxi.entity.backparameter.OrderEntity;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.utils.AppUtils;
import com.jxxc.jingxi.utils.GlideImgManager;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class EvaluateActivity extends MVPBaseActivity<EvaluateContract.View, EvaluatePresenter> implements EvaluateContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_jishi_name)
    TextView tv_jishi_name;
    @BindView(R.id.iv_jishi_head)
    ImageView iv_jishi_head;
    @BindView(R.id.ratingBar1)
    RatingBar ratingBar1;
    @BindView(R.id.et_evaluate_text)
    EditText et_evaluate_text;
    @BindView(R.id.btn_submit_evaluate)
    Button btn_submit_evaluate;
    private String orderId;
    private float rBar1=2;
    @Override
    protected int layoutId() {
        return R.layout.evaluate_activity;
    }

    @Override
    public void initData() {
        tv_title.setText("评价");
        orderId = ZzRouter.getIntentData(this,String.class);
        StyledDialog.buildLoading("数据加载中").setActivity(this).show();
        mPresenter.getOrder(orderId);

        ratingBar1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                //获取星星数量
                rBar1 = v;
            }
        });
    }

    @OnClick({R.id.tv_back,R.id.btn_submit_evaluate})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
            case R.id.btn_submit_evaluate://提交评价
                if (AppUtils.isEmpty(et_evaluate_text.getText().toString())){
                    toast(this,"说点什么呗。");
                }else{
                    StyledDialog.buildLoading("数据加载中").setActivity(this).show();
                    int sourceI = (Float.valueOf(rBar1)).intValue();
                    mPresenter.comment(orderId,sourceI,et_evaluate_text.getText().toString());
                }
                break;
            default:
        }
    }

    //订单详情返回数据
    @Override
    public void getOrderCallBack(OrderEntity data) {
        GlideImgManager.loadCircleImage(this, data.technicianAvatar, iv_jishi_head);
        tv_jishi_name.setText(data.technicianRealName);
    }

    //评价返回数据
    @Override
    public void commentBackCall() {
        toast(this,"评价成功");
        finish();
    }
}
