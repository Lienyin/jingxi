package com.jxxc.jingxi.ui.recharge;

import com.jxxc.jingxi.entity.backparameter.AliPayInfo;
import com.jxxc.jingxi.entity.backparameter.PayByWeChat;
import com.jxxc.jingxi.entity.backparameter.RechargeSet;
import com.jxxc.jingxi.mvp.BasePresenter;
import com.jxxc.jingxi.mvp.BaseView;

import java.util.List;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class RechargeContract {
    interface View extends BaseView {
        void getRechargeConfigurationCallback(List<RechargeSet> list);
        void payByAliPayCallBack(AliPayInfo data);
        void payByWeChatCallBack(PayByWeChat data);
    }

    interface  Presenter extends BasePresenter<View> {
        void getRechargeConfiguration();
        void payByAliPay(double money, int payType);
        void payByWeChat(double money, int payType);
    }
}
