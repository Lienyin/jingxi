package com.jxxc.jingxi.ui.payorder;


import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.CreateOrderEntity;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.ui.payaccomplish.PayAccomplishActivity;
import com.jxxc.jingxi.utils.AnimUtils;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class PayOrderActivity extends MVPBaseActivity<PayOrderContract.View, PayOrderPresenter> implements PayOrderContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.ll_balance_pay)
    LinearLayout ll_balance_pay;
    @BindView(R.id.ll_wx_pay)
    LinearLayout ll_wx_pay;
    @BindView(R.id.ll_zfb_pay)
    LinearLayout ll_zfb_pay;
    @BindView(R.id.iv_balance)
    ImageView iv_balance;
    @BindView(R.id.iv_wx)
    ImageView iv_wx;
    @BindView(R.id.iv_zfb)
    ImageView iv_zfb;
    @BindView(R.id.tv_order_id)
    TextView tv_order_id;
    @BindView(R.id.tv_order_money)
    TextView tv_order_money;
    @BindView(R.id.btn_order_pay)
    Button btn_order_pay;
    private int payType=0;
    private CreateOrderEntity data;
    @Override
    protected int layoutId() {
        return R.layout.pay_order_activity;
    }

    @Override
    public void initData() {
        tv_title.setText("订单支付");
        data = ZzRouter.getIntentData(this,CreateOrderEntity.class);
        tv_order_id.setText(data.orderId);
        tv_order_money.setText("￥"+data.payPrice);
    }

    @OnClick({R.id.tv_back,R.id.ll_balance_pay,R.id.ll_wx_pay,R.id.ll_zfb_pay,R.id.btn_order_pay})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
            case R.id.ll_balance_pay://余额支付
                iv_balance.setImageResource(R.mipmap.icon_38);
                iv_wx.setImageResource(R.mipmap.icon_38_24);
                iv_zfb.setImageResource(R.mipmap.icon_38_24);
                payType = 1;
                break;
            case R.id.ll_wx_pay://微信支付
                iv_balance.setImageResource(R.mipmap.icon_38_24);
                iv_wx.setImageResource(R.mipmap.icon_38);
                iv_zfb.setImageResource(R.mipmap.icon_38_24);
                payType = 2;
                break;
            case R.id.ll_zfb_pay://支付宝支付
                iv_balance.setImageResource(R.mipmap.icon_38_24);
                iv_wx.setImageResource(R.mipmap.icon_38_24);
                iv_zfb.setImageResource(R.mipmap.icon_38);
                payType = 3;
                break;
            case R.id.btn_order_pay://立即支付
                if (payType == 1){
                    //余额支付
                    StyledDialog.buildLoading("正在支付").setActivity(this).show();
                    mPresenter.BalancePay(data.orderId);
                }else if (payType==2){
                    //微信支付
                }else if (payType==3){
                    //支付宝支付
                }else{
                    toast(this,"请选择支付方式");
                }
                break;
            default:
        }
    }

    //余额支付返回数据
    @Override
    public void BalancePayCallBack() {
        toast(this,"支付成功");
        ZzRouter.gotoActivity(this, PayAccomplishActivity.class,data);
    }
}
