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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.UserInfoEntity;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.mvp.MVPBaseFragment;
import com.jxxc.jingxi.ui.mycar.MyCarActivity;
import com.jxxc.jingxi.ui.myorder.MyOrderActivity;
import com.jxxc.jingxi.ui.mywallet.MyWalletActivity;
import com.jxxc.jingxi.ui.seting.SetingActivity;
import com.jxxc.jingxi.ui.usercenter.UsercenterActivity;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.utils.AppUtils;
import com.jxxc.jingxi.utils.GlideImgManager;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

@SuppressLint("ValidFragment")
public class MyFragment extends MVPBaseFragment<MyContract.View, MyPresenter> implements MyContract.View, SwipeRefreshLayout.OnRefreshListener,View.OnClickListener {

    private Context context;
    private LinearLayout ll_my_car,ll_my_order,ll_my_wallet,ll_msg,ll_setting;
    private TextView tv_user_name,tv_phone_number;
    private ImageView iv_user_head;
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
        tv_user_name = view.findViewById(R.id.tv_user_name);
        tv_phone_number = view.findViewById(R.id.tv_phone_number);
        iv_user_head = view.findViewById(R.id.iv_user_head);

        ll_my_car.setOnClickListener(this);
        ll_my_order.setOnClickListener(this);
        ll_my_wallet.setOnClickListener(this);
        ll_msg.setOnClickListener(this);
        ll_setting.setOnClickListener(this);
        iv_user_head.setOnClickListener(this);

        mPresenter.getUserInfo();
        return view;
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        return null;
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.ll_my_car://我的车辆
                ZzRouter.gotoActivity((Activity) context, MyCarActivity.class,"0");
                break;
            case R.id.ll_my_order://我的订单
                ZzRouter.gotoActivity((Activity) context, MyOrderActivity.class);
                break;
            case R.id.ll_my_wallet://我的钱包
                ZzRouter.gotoActivity((Activity) context, MyWalletActivity.class);
                break;
            case R.id.ll_msg://消息通知
                ZzRouter.gotoActivity((Activity) context, MyOrderActivity.class);
                break;
            case R.id.ll_setting://系统设置
                ZzRouter.gotoActivity((Activity) context, SetingActivity.class);
                break;
            case R.id.iv_user_head://头像
                ZzRouter.gotoActivity((Activity) context, UsercenterActivity.class);
                break;
        }
    }

    //刷新
    @Override
    public void onRefresh() {

    }

    //个人信息返回数据
    @Override
    public void getUserInfoCallBack(UserInfoEntity data) {
        GlideImgManager.loadCircleImage(context, data.avatar, iv_user_head);
        tv_user_name.setText(AppUtils.isEmpty(data.userName)?data.realName:data.userName);
        tv_phone_number.setText(data.phonenumber);
    }
}
