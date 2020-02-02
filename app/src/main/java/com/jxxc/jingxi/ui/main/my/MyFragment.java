package com.jxxc.jingxi.ui.main.my;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jxxc.jingxi.R;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.mvp.MVPBaseFragment;
import com.jxxc.jingxi.ui.mycar.MyCarActivity;
import com.jxxc.jingxi.ui.myorder.MyOrderActivity;
import com.jxxc.jingxi.utils.AnimUtils;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

@SuppressLint("ValidFragment")
public class MyFragment extends MVPBaseFragment<MyContract.View, MyPresenter> implements MyContract.View, SwipeRefreshLayout.OnRefreshListener,View.OnClickListener {

    private Context context;
    private LinearLayout ll_my_car,ll_my_order,ll_my_wallet,ll_msg,ll_setting;
    public MyFragment(Context context){
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_fragment, container, false);
        ll_my_car = view.findViewById(R.id.ll_my_car);
        ll_my_order = view.findViewById(R.id.ll_my_order);
        ll_my_wallet = view.findViewById(R.id.ll_my_wallet);
        ll_msg = view.findViewById(R.id.ll_msg);
        ll_setting = view.findViewById(R.id.ll_setting);

        ll_my_car.setOnClickListener(this);
        ll_my_order.setOnClickListener(this);
        ll_my_wallet.setOnClickListener(this);
        ll_msg.setOnClickListener(this);
        ll_setting.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.ll_my_car://我的车辆
                ZzRouter.gotoActivity((Activity) context, MyCarActivity.class);
                break;
            case R.id.ll_my_order://我的订单
                ZzRouter.gotoActivity((Activity) context, MyOrderActivity.class);
                break;
            case R.id.ll_my_wallet://我的钱包
                ZzRouter.gotoActivity((Activity) context, MyOrderActivity.class);
                break;
            case R.id.ll_msg://消息通知
                ZzRouter.gotoActivity((Activity) context, MyOrderActivity.class);
                break;
            case R.id.ll_setting://系统设置
                ZzRouter.gotoActivity((Activity) context, MyOrderActivity.class);
                break;
        }
    }

    //刷新
    @Override
    public void onRefresh() {

    }
}
