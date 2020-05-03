package com.jxxc.jingxi.ui.share;


import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.alipay.share.sdk.openapi.APAPIFactory;
import com.alipay.share.sdk.openapi.APMediaMessage;
import com.alipay.share.sdk.openapi.APWebPageObject;
import com.alipay.share.sdk.openapi.IAPApi;
import com.alipay.share.sdk.openapi.SendMessageToZFB;
import com.jxxc.jingxi.Api;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.GetInfoEntity;
import com.jxxc.jingxi.entity.backparameter.QueryActivityDetailEntity;
import com.jxxc.jingxi.entity.backparameter.UserInfoEntity;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.ui.sharerule.ShareRuleActivity;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.utils.StatusBarUtil;
import com.jxxc.jingxi.wxapi.Constant;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.openapi.WXWebpageObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class ShareActivity extends MVPBaseActivity<ShareContract.View, SharePresenter> implements ShareContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_share_rule)
    TextView tv_share_rule;
    @BindView(R.id.btn_share_friend)
    View btn_share_friend;
    @BindView(R.id.view_yuan_share_schedule_101)
    TextView view_yuan_share_schedule_101;
    @BindView(R.id.view_yuan_share_schedule_102)
    TextView view_yuan_share_schedule_102;
    @BindView(R.id.view_yuan_share_schedule_103)
    TextView view_yuan_share_schedule_103;
    @BindView(R.id.view_yuan_share_schedule_104)
    TextView view_yuan_share_schedule_104;
    @BindView(R.id.view_yuan_share_schedule_105)
    TextView view_yuan_share_schedule_105;
    @BindView(R.id.view_yuan_share_schedule_106)
    TextView view_yuan_share_schedule_106;
    @BindView(R.id.view_yuan_share_schedule_107)
    TextView view_yuan_share_schedule_107;
    @BindView(R.id.view_share_schedule_1011)
    View view_share_schedule_1011;
    @BindView(R.id.view_share_schedule_1012)
    View view_share_schedule_1012;
    @BindView(R.id.view_share_schedule_1021)
    View view_share_schedule_1021;
    @BindView(R.id.view_share_schedule_1022)
    View view_share_schedule_1022;
    @BindView(R.id.view_share_schedule_1031)
    View view_share_schedule_1031;
    @BindView(R.id.view_share_schedule_1032)
    View view_share_schedule_1032;
    @BindView(R.id.view_share_schedule_1041)
    View view_share_schedule_1041;
    @BindView(R.id.view_share_schedule_1042)
    View view_share_schedule_1042;
    @BindView(R.id.view_share_schedule_1051)
    View view_share_schedule_1051;
    @BindView(R.id.view_share_schedule_1052)
    View view_share_schedule_1052;
    @BindView(R.id.view_share_schedule_1061)
    View view_share_schedule_1061;
    @BindView(R.id.view_share_schedule_1062)
    View view_share_schedule_1062;
    @BindView(R.id.tv_achievement_discounts)
    TextView tv_achievement_discounts;
    @BindView(R.id.tv_achievement_cash)
    TextView tv_achievement_cash;
    @BindView(R.id.tv_hint_umber)
    TextView tv_hint_umber;
    @BindView(R.id.tv_xi_car_number)
    TextView tv_xi_car_number;
    @BindView(R.id.lv_share_info)
    ListView lv_share_info;
    private FriendListAdapter adapter;
    private ShareDialog dialog;
    private String cId="";
    private String URL = "";
    private String BaseURL = "";
    private String BaseURLIMG = "";
    private String BaseTitle = "菁喜科技活动推广邀请好友有礼！";
    private String BaseDescription = "邀请好友成功成为菁喜平台用户，拿优惠券/获免费洗车，多邀多得！";
    private IWXAPI api;
    //支付宝
    private IAPApi aliApi;

    @Override
    protected int layoutId() {
        return R.layout.share_activity;
    }

    @Override
    public void initData() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);//状态栏颜色
        tv_title.setText("邀请好友");
        mPresenter.getUserInfo();

        //支付宝
        aliApi = APAPIFactory.createZFBApi(getApplicationContext(), Constant.ALIPAY_APPID, false);

        api = WXAPIFactory.createWXAPI(this, Constant.APP_ID, true);
        api.registerApp(Constant.APP_ID);

        dialog = new ShareDialog(this);
        dialog.setOnFenxiangClickListener(new ShareDialog.OnFenxiangClickListener() {
            @Override
            public void onFenxiangClick(int shareType) {
                if (shareType == 1) {
                    if (!isAvilible(ShareActivity.this,"com.tencent.mm")){
                        toast(ShareActivity.this,"目前您安装的微信版本过低或尚未安装");
                    }else{
                        showWeiXin();
                    }
                } else if (shareType == 2) {
//                    if (!isAvilible(ShareActivity.this,"com.tencent.mm")){
//                        toast(ShareActivity.this,"目前您安装的微信版本过低或尚未安装");
//                    }else{
//                        showWeiXinP();
//                    }
                }else if (shareType ==3){
//                    if (!isAvilible(ShareActivity.this,"com.tencent.mobileqq")){
//                        toast(ShareActivity.this,"目前您安装的QQ版本过低或尚未安装");
//                    }else{
//                        shareQQ();
//                    }
                }else if (shareType ==4){
//                    if (!isAvilible(ShareActivity.this,"com.tencent.mobileqq")){
//                        toast(ShareActivity.this,"目前您安装的QQ版本过低或尚未安装");
//                    }else{
//                        shareQzone();
//                    }
                }else if (shareType == 5){
                    //分享到支付宝
                    if (aliApi.isZFBSupportAPI()){
                        sendToAliPay();
                        //sendToAliPay("智租出行",ShareActivity.this);
                    }else{
                        toast(ShareActivity.this,"目前您安装的支付宝版本过低或尚未安装 ");
                    }
                }else if (shareType == 6){
                    //复制链接
                    //获取剪贴板管理器：
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    // 创建普通字符型ClipData
                    ClipData mClipData = ClipData.newRawUri("Label", Uri.parse(BaseURL));
                    // 将ClipData内容放到系统剪贴板里。
                    cm.setPrimaryClip(mClipData);
                    toast(ShareActivity.this,"复制成功");
                }
            }
        });
    }

    @OnClick({R.id.tv_back,R.id.tv_share_rule,R.id.btn_share_friend})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
            case R.id.tv_share_rule://规则
                ZzRouter.gotoActivity(this, ShareRuleActivity.class);
                break;
            case R.id.btn_share_friend://分享
                dialog.showShareDialog(true);
                break;
            default:
        }
    }

    //分享到微信
    public void showWeiXin() {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = BaseURL;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = BaseTitle;
        msg.description = BaseDescription;
        Bitmap bitmap = BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.shrea_icon_f);
        msg.thumbData = com.tencent.mm.sdk.platformtools.Util.bmpToByteArray(bitmap, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;
        api.sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    //分享到支付宝
    public void sendToAliPay() {
        APWebPageObject webPageObject = new APWebPageObject();
        webPageObject.webpageUrl = BaseURL;
        APMediaMessage webMessage = new APMediaMessage();
        webMessage.title = BaseTitle;
        webMessage.description = BaseDescription;
        webMessage.mediaObject = webPageObject;
        webMessage.thumbUrl = BaseURLIMG;
        SendMessageToZFB.Req webReq = new SendMessageToZFB.Req();
        webReq.message = webMessage;
        webReq.transaction = buildTransaction("webpage");

        //在支付宝版本会合并分享渠道的情况下,不需要传递分享场景参数
        if (!isAlipayIgnoreChannel()) {
            webReq.scene = SendMessageToZFB.Req.ZFBSceneTimeLine;
            //webReq.scene = SendMessageToZFB.Req.ZFBSceneSession;

        }
        aliApi.sendReq(webReq);
        //finish();
    }

    private boolean isAlipayIgnoreChannel() {
        return aliApi.getZFBVersionCode() >= 101;
    }

    //进度返回数据
    @Override
    public void getInfoCallBack(GetInfoEntity data) {
        tv_achievement_discounts.setText(data.inviteNum+"");
        tv_achievement_cash.setText(data.couponNum+"");
        tv_xi_car_number.setText("已获得"+data.couponNum+"次免费洗车奖励");
        //邀请记录
        if (data.inviteCustomer.size()>0){
            adapter = new FriendListAdapter(this,data.inviteCustomer);
            lv_share_info.setAdapter(adapter);
        }
        tv_hint_umber.setText(Html.fromHtml("当前邀请人数:<font color=\"#FFA521\"> "+data.inviteNum+"</font>人，已获赠免费洗车机会<font color=\"#FFA521\">"+data.couponNum+"</font>次。"));
        //1,6,12,18,24,30,36
        if (data.inviteNum==1){
            view_yuan_share_schedule_101.setTextColor(getResources().getColor(R.color.white));
            view_yuan_share_schedule_101.setBackgroundResource(R.drawable.share_num_bg_yes);
        }else if (data.inviteNum>1&&data.inviteNum<6){
            view_yuan_share_schedule_101.setTextColor(getResources().getColor(R.color.white));
            view_yuan_share_schedule_101.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_share_schedule_1011.setBackgroundColor(getResources().getColor(R.color.public_all));
        }else if (data.inviteNum==6){
            view_yuan_share_schedule_101.setTextColor(getResources().getColor(R.color.white));
            view_yuan_share_schedule_102.setTextColor(getResources().getColor(R.color.white));
            view_yuan_share_schedule_101.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_yuan_share_schedule_102.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_share_schedule_1011.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1012.setBackgroundColor(getResources().getColor(R.color.public_all));
        }else if (data.inviteNum>6&&data.inviteNum<12){
            view_yuan_share_schedule_101.setTextColor(getResources().getColor(R.color.white));
            view_yuan_share_schedule_102.setTextColor(getResources().getColor(R.color.white));
            view_yuan_share_schedule_101.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_yuan_share_schedule_102.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_share_schedule_1011.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1012.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1021.setBackgroundColor(getResources().getColor(R.color.public_all));
        }else if (data.inviteNum==12){
            view_yuan_share_schedule_101.setTextColor(getResources().getColor(R.color.white));
            view_yuan_share_schedule_102.setTextColor(getResources().getColor(R.color.white));
            view_yuan_share_schedule_103.setTextColor(getResources().getColor(R.color.white));
            view_yuan_share_schedule_101.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_yuan_share_schedule_102.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_yuan_share_schedule_103.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_share_schedule_1011.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1012.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1021.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1022.setBackgroundColor(getResources().getColor(R.color.public_all));
        }else if (data.inviteNum>12&&data.inviteNum<18){
            view_yuan_share_schedule_101.setTextColor(getResources().getColor(R.color.white));
            view_yuan_share_schedule_102.setTextColor(getResources().getColor(R.color.white));
            view_yuan_share_schedule_103.setTextColor(getResources().getColor(R.color.white));
            view_yuan_share_schedule_101.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_yuan_share_schedule_102.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_yuan_share_schedule_103.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_share_schedule_1011.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1012.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1021.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1022.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1031.setBackgroundColor(getResources().getColor(R.color.public_all));
        }else if (data.inviteNum==18){
            view_yuan_share_schedule_101.setTextColor(getResources().getColor(R.color.white));
            view_yuan_share_schedule_102.setTextColor(getResources().getColor(R.color.white));
            view_yuan_share_schedule_103.setTextColor(getResources().getColor(R.color.white));
            view_yuan_share_schedule_104.setTextColor(getResources().getColor(R.color.white));
            view_yuan_share_schedule_101.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_yuan_share_schedule_102.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_yuan_share_schedule_103.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_yuan_share_schedule_104.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_share_schedule_1011.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1012.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1021.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1022.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1031.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1032.setBackgroundColor(getResources().getColor(R.color.public_all));
        }else if (data.inviteNum>18&&data.inviteNum<24){
            view_yuan_share_schedule_101.setTextColor(getResources().getColor(R.color.white));
            view_yuan_share_schedule_102.setTextColor(getResources().getColor(R.color.white));
            view_yuan_share_schedule_103.setTextColor(getResources().getColor(R.color.white));
            view_yuan_share_schedule_104.setTextColor(getResources().getColor(R.color.white));
            view_yuan_share_schedule_101.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_yuan_share_schedule_102.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_yuan_share_schedule_103.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_yuan_share_schedule_104.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_share_schedule_1011.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1012.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1021.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1022.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1031.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1032.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1041.setBackgroundColor(getResources().getColor(R.color.public_all));
        }else if (data.inviteNum==24){
            view_yuan_share_schedule_101.setTextColor(getResources().getColor(R.color.white));
            view_yuan_share_schedule_102.setTextColor(getResources().getColor(R.color.white));
            view_yuan_share_schedule_103.setTextColor(getResources().getColor(R.color.white));
            view_yuan_share_schedule_104.setTextColor(getResources().getColor(R.color.white));
            view_yuan_share_schedule_105.setTextColor(getResources().getColor(R.color.white));
            view_yuan_share_schedule_101.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_yuan_share_schedule_102.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_yuan_share_schedule_103.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_yuan_share_schedule_104.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_yuan_share_schedule_105.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_share_schedule_1011.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1012.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1021.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1022.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1031.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1032.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1041.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1042.setBackgroundColor(getResources().getColor(R.color.public_all));
        }else if (data.inviteNum>24&&data.inviteNum<30){
            view_yuan_share_schedule_101.setTextColor(getResources().getColor(R.color.white));
            view_yuan_share_schedule_102.setTextColor(getResources().getColor(R.color.white));
            view_yuan_share_schedule_103.setTextColor(getResources().getColor(R.color.white));
            view_yuan_share_schedule_104.setTextColor(getResources().getColor(R.color.white));
            view_yuan_share_schedule_105.setTextColor(getResources().getColor(R.color.white));
            view_yuan_share_schedule_101.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_yuan_share_schedule_102.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_yuan_share_schedule_103.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_yuan_share_schedule_104.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_yuan_share_schedule_105.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_share_schedule_1011.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1012.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1021.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1022.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1031.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1032.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1041.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1042.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1051.setBackgroundColor(getResources().getColor(R.color.public_all));
        }else if (data.inviteNum==30){
            view_yuan_share_schedule_101.setTextColor(getResources().getColor(R.color.white));
            view_yuan_share_schedule_102.setTextColor(getResources().getColor(R.color.white));
            view_yuan_share_schedule_103.setTextColor(getResources().getColor(R.color.white));
            view_yuan_share_schedule_104.setTextColor(getResources().getColor(R.color.white));
            view_yuan_share_schedule_105.setTextColor(getResources().getColor(R.color.white));
            view_yuan_share_schedule_106.setTextColor(getResources().getColor(R.color.white));
            view_yuan_share_schedule_101.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_yuan_share_schedule_102.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_yuan_share_schedule_103.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_yuan_share_schedule_104.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_yuan_share_schedule_105.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_yuan_share_schedule_106.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_share_schedule_1011.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1012.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1021.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1022.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1031.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1032.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1041.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1042.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1051.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1052.setBackgroundColor(getResources().getColor(R.color.public_all));
        }else if (data.inviteNum>30&&data.inviteNum<36){
            view_yuan_share_schedule_101.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_yuan_share_schedule_102.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_yuan_share_schedule_103.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_yuan_share_schedule_104.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_yuan_share_schedule_105.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_yuan_share_schedule_106.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_share_schedule_1011.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1012.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1021.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1022.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1031.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1032.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1041.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1042.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1051.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1052.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1061.setBackgroundColor(getResources().getColor(R.color.public_all));
        }else if (data.inviteNum>=36){
            view_yuan_share_schedule_101.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_yuan_share_schedule_102.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_yuan_share_schedule_103.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_yuan_share_schedule_104.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_yuan_share_schedule_105.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_yuan_share_schedule_106.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_yuan_share_schedule_107.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_share_schedule_1011.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1012.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1021.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1022.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1031.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1032.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1041.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1042.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1051.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1052.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1061.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1062.setBackgroundColor(getResources().getColor(R.color.public_all));
        }
    }

    @Override
    public void getUserInfoCallBack(UserInfoEntity data) {
        cId = data.customerId;
        mPresenter.getInfo();
        mPresenter.queryActivityDetail();
    }

    //分享数据
    @Override
    public void queryActivityDetailCallBack(QueryActivityDetailEntity data) {
        BaseURL = data.activityRegisterUrl+"?cId="+cId;
        BaseTitle = data.activityTitle;
        BaseURLIMG = data.activityUi;
        BaseDescription = data.activityDescription;
    }

    /**
     * 检查手机上是否安装了指定的软件
     * @param context
     * @param packageName
     * @return
     */
    public static boolean isAvilible(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        List<String> packageNames = new ArrayList<String>();

        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        // 判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }
}
