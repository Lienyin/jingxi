package com.jxxc.jingxi.ui.orderdetails;


import android.view.View;
import android.widget.TextView;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.entity.backparameter.OrderEntity;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.mvp.MVPBaseActivity;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 订单详情（服务已完成）
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class OrderDetailsActivity extends MVPBaseActivity<OrderDetailsContract.View, OrderDetailsPresenter> implements OrderDetailsContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    private String OrderId;
    @Override
    protected int layoutId() {
        return R.layout.acivity_order_details;
    }

    @Override
    public void initData() {
        tv_title.setText("订单详情");
        OrderId = ZzRouter.getIntentData(this,String.class);
        StyledDialog.buildLoading("数据加载中").setActivity(this).show();
        mPresenter.getOrder(OrderId);
    }
    @OnClick({R.id.tv_back})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            default:
        }
    }

    //订单详情返回数据
    @Override
    public void getOrderCallBack(OrderEntity data) {
        //
    }
}
