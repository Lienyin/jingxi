package com.jxxc.jingxi.ui.orderdetailsdaifuwu;


import android.view.View;
import android.widget.TextView;

import com.jxxc.jingxi.R;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.utils.AnimUtils;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin 已接单待服务
 *  邮箱 784787081@qq.com
 */

public class OrderDetailsDaiFuWuActivity extends MVPBaseActivity<OrderDetailsDaiFuWuContract.View, OrderDetailsDaiFuWuPresenter> implements OrderDetailsDaiFuWuContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @Override
    protected int layoutId() {
        return R.layout.order_details_dai_fu_wu_activity;
    }

    @Override
    public void initData() {
        tv_title.setText("订单详情");
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
