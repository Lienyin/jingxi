package com.jxxc.jingxi.ui.payaccomplish;


import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.CreateOrderEntity;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.ui.orderdetailsdaifuwu.OrderDetailsDaiFuWuActivity;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class PayAccomplishActivity extends MVPBaseActivity<PayAccomplishContract.View, PayAccomplishPresenter> implements PayAccomplishContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_back_home)
    TextView tv_back_home;
    @BindView(R.id.tv_order_money)
    TextView tv_order_money;
    @BindView(R.id.btn_order_look)
    Button btn_order_look;
    private String orderPrice;
    private String orderId;
    @Override
    protected int layoutId() {
        return R.layout.pay_accomplish_activity;
    }

    @Override
    public void initData() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.public_all);//状态栏颜色
        tv_title.setText("支付完成");
        orderId = getIntent().getStringExtra("orderId");
        orderPrice = getIntent().getStringExtra("orderPrice");
        tv_order_money.setText("￥"+orderPrice);
    }

    @OnClick({R.id.tv_back,R.id.tv_back_home,R.id.btn_order_look})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
            case R.id.tv_back_home://返回
                finish();
                break;
            case R.id.btn_order_look://查看订单
                ZzRouter.gotoActivity(this, OrderDetailsDaiFuWuActivity.class,orderId);
                break;
            default:
        }
    }
}
