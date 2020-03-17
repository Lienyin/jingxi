package com.jxxc.jingxi.ui.payorder;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.alipay.AliPay;
import com.jxxc.jingxi.entity.backparameter.AliPayInfo;
import com.jxxc.jingxi.entity.backparameter.CreateOrderEntity;
import com.jxxc.jingxi.entity.backparameter.PayByWeChat;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.ui.payaccomplish.PayAccomplishActivity;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.utils.PayUtil;
import com.jxxc.jingxi.utils.StatusBarUtil;
import com.jxxc.jingxi.wxpay.WXSignBean;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class PayOrderActivity extends MVPBaseActivity<PayOrderContract.View, PayOrderPresenter> implements PayOrderContract.View, AliPay.AliPayCallBack {

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
    //private CreateOrderEntity data;
    private String orderId;
    private String orderPrice;
    @Override
    protected int layoutId() {
        return R.layout.pay_order_activity;
    }

    @Override
    public void initData() {
        tv_title.setText("订单支付");
        orderId = getIntent().getStringExtra("orderId");
        orderPrice = getIntent().getStringExtra("orderPrice");
        tv_order_id.setText(orderId);
        tv_order_money.setText("￥"+orderPrice);
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
                StyledDialog.buildLoading("正在支付").setActivity(this).show();
                if (payType == 1){
                    //余额支付
                    mPresenter.BalancePay(orderId);
                }else if (payType==2){
                    //微信支付
                    mPresenter.payByWeChat(orderId,payType);
                }else if (payType==3){
                    //支付宝支付
                    mPresenter.payByAliPay(orderId,payType);
                }else{
                    StyledDialog.dismissLoading();
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
        Intent intent = new Intent(this, PayAccomplishActivity.class);
        intent.putExtra("orderId",orderId);
        intent.putExtra("orderPrice",orderPrice);
        startActivity(intent);
        finish();
    }

    //支付宝支付返回数据
    @Override
    public void payByAliPayCallBack(AliPayInfo data) {
        PayUtil.payZhiFuBao(this, this, data.alipayParam);
    }

    //微信返回数据
    @Override
    public void payByWeChatCallBack(PayByWeChat data) {
        WXSignBean wxSignBean = new WXSignBean();
        wxSignBean.setappId(data.appid);
        wxSignBean.setnonceStr(data.noncestr);
        wxSignBean.setPackageX(data.packageX);
        wxSignBean.setpartnerId(data.partnerid);
        wxSignBean.setprepayId(data.prepayid);
        wxSignBean.setSign(data.sign);
        wxSignBean.settimeStamp(data.timestamp);
        PayUtil.payWeiXin(this.getApplicationContext(), wxSignBean);
    }

    @Override
    public void paySuccess() {
        //支付宝支付成功回调
    }

    @Override
    public void payFail() {
        //支付宝支付失败回调
    }

    @Override
    public void payConfirm() {
        //支付宝支付确定回调
    }
}
