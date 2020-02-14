package com.jxxc.jingxi.utils;

import android.app.Activity;
import android.content.Context;


import com.jxxc.jingxi.alipay.AliPay;
import com.jxxc.jingxi.wxpay.WXSignBean;
import com.jxxc.jingxi.wxpay.WxPay;

import java.io.Serializable;

/**
 * Created by ruanjian on 2018/1/19.
 */

public class PayUtil implements Serializable {


    public static void payWeiXin(Context applicationContext, WXSignBean wxSignBean) {
        WxPay wxPay = new WxPay(applicationContext, wxSignBean);
        wxPay.wxPayMethod();

    }

    public static void payZhiFuBao(Activity context, AliPay.AliPayCallBack context1, String alipayParam) {
        AliPay aliPay = new AliPay(context, context1, alipayParam);
        aliPay.pay();
    }
}
