package com.jxxc.jingxi.ui.recharge;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.alipay.AliPay;
import com.jxxc.jingxi.entity.backparameter.AliPayInfo;
import com.jxxc.jingxi.entity.backparameter.PayByWeChat;
import com.jxxc.jingxi.entity.backparameter.RechargeSet;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.utils.AppUtils;
import com.jxxc.jingxi.utils.PayUtil;
import com.jxxc.jingxi.utils.SPUtils;
import com.jxxc.jingxi.view.SmoothCheckBox;
import com.jxxc.jingxi.wxpay.WXSignBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

import static com.jxxc.jingxi.ui.login.LoginActivity.isAvilible;


/**
 * 充值
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class RechargeActivity extends MVPBaseActivity<RechargeContract.View, RechargePresenter> implements RechargeContract.View, AliPay.AliPayCallBack {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.et_money)
    EditText etMoney;
    @BindView(R.id.scb_alipay)
    SmoothCheckBox scbAlipay;
    @BindView(R.id.ll_alipay)
    LinearLayout llAlipay;
    @BindView(R.id.scb_wx_pay)
    SmoothCheckBox scbWxPay;
    @BindView(R.id.btn_pay)
    Button btnPay;
    private RechargeAdapter adapter;
    private int type = 1;
    private double money;
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    private String AuthCode;
    @Override
    protected int layoutId() {
        return R.layout.activity_recharge;
    }

    @Override
    public void initData() {
        tv_title.setText("充值");
        initAdapter();
        mPresenter.getRechargeConfiguration();
        scbWxPay.setChecked(true);
        //注册广播
        registerReceiver(receiver, new IntentFilter("wei_xin_pay_sucess"));
    }

    private void initAdapter() {
        rvList.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new RechargeAdapter(R.layout.item_recharge, new ArrayList<RechargeSet>());
        rvList.setAdapter(adapter);
        adapter.bindToRecyclerView(rvList);
        adapter.setEmptyView(R.layout.layout_nothing);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                RechargeSet o = (RechargeSet) adapter.getData().get(position);
                for (RechargeSet oo : (List<RechargeSet>) adapter.getData()) {
                    oo.isSelected = false;
                }
                o.isSelected = !o.isSelected;
                if (o.isSelected) {
                    money=o.money;
                }
                etMoney.setText("");
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    protected void initListener() {
        super.initListener();
        etMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String temp = s.toString();
                if (temp.contains(".")){
                    int posDot = temp.indexOf(".");
                    if (posDot <= 0) return;
                    if (temp.length() - posDot - 1 > 2)
                    {
                        s.delete(posDot + 3, posDot + 4);
                    }
                    String number = etMoney.getText().toString();
                    if (!TextUtils.isEmpty(number)) {
                        for (RechargeSet oo : (List<RechargeSet>) adapter.getData()) {
                            oo.isSelected = false;
                        }
                        adapter.notifyDataSetChanged();
                        if (number.startsWith(".")) {
                            etMoney.setText("0.");
                            CharSequence cs = etMoney.getText();
                            if (cs instanceof Spannable) {
                                Selection.setSelection((Spannable) cs, cs.length());
                            }
                        } else {
                            money = Double.parseDouble(number);
                        }
                    }
                }else{
                    if (!AppUtils.isEmpty(etMoney.getText().toString())){
                        for (RechargeSet oo : (List<RechargeSet>) adapter.getData()) {
                            oo.isSelected = false;
                        }
                        adapter.notifyDataSetChanged();
                        money = Double.parseDouble(etMoney.getText().toString());
                    }
                }
            }
        });
    }

    @OnClick({R.id.tv_back, R.id.scb_alipay, R.id.ll_alipay, R.id.scb_wx_pay, R.id.ll_wx_pay,
            R.id.btn_pay})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
            case R.id.scb_alipay:
            case R.id.ll_alipay:
                type = 0;
                scbAlipay.setChecked(true);
                scbWxPay.setChecked(false);
                break;
            case R.id.scb_wx_pay:
            case R.id.ll_wx_pay:
                type = 1;
                scbWxPay.setChecked(true);
                scbAlipay.setChecked(false);
                break;
            case R.id.btn_pay://立即充值
                if (money <= 0){
                    toast(this,"充值金额要大于0");
                    return;
                }
                if (type == 0){
                    //支付宝
                    if (!isAvilible(this,"com.eg.android.AlipayGphone")){
                        toast(this,"目前您安装的支付宝版本过低或尚未安装");
                    }else{
                        StyledDialog.buildLoading("正在支付").setActivity(this).show();
                        mPresenter.payByAliPay(money,0);
                    }
                }else if (type == 1){
                    //微信
                    if (!isAvilible(this,"com.tencent.mm")){
                        toast(this,"目前您安装的微信版本过低或尚未安装");
                    }else{
                        StyledDialog.buildLoading("正在支付").setActivity(this).show();
                        mPresenter.payByWeChat(money,1);
                    }
                }
                break;
            default:
        }
    }

    @Override
    public void getRechargeConfigurationCallback(List<RechargeSet> list) {
        adapter.getData().clear();
        adapter.setNewData(list);
    }

    /**
     * 支付宝返回参数
     * @param data
     */
    @Override
    public void payByAliPayCallBack(AliPayInfo data) {
        PayUtil.payZhiFuBao(this, this, data.alipayParam);
    }

    /**
     * 微信返回参数
     * @param data
     */
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
        finish();
    }

    @Override
    public void payFail() {
        toast(this,"充值失败");
    }

    @Override
    public void payConfirm() {

    }

    /**
     * 微信支付成功回调
     */
    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //接收到广播以后要做的事
            finish();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
