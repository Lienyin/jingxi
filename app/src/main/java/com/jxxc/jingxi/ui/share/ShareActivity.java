package com.jxxc.jingxi.ui.share;


import android.graphics.Color;
import android.os.Build;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.GetInfoEntity;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.ui.sharerule.ShareRuleActivity;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.utils.StatusBarUtil;

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
    @BindView(R.id.lv_share_info)
    ListView lv_share_info;
    private FriendListAdapter adapter;
    @Override
    protected int layoutId() {
        return R.layout.share_activity;
    }

    @Override
    public void initData() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.white);//状态栏颜色
        tv_title.setText("邀请好友");
        mPresenter.getInfo();
    }

    @OnClick({R.id.tv_back,R.id.tv_share_rule})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
            case R.id.tv_share_rule://规则
                ZzRouter.gotoActivity(this, ShareRuleActivity.class);
                break;
            default:
        }
    }

    //进度返回数据
    @Override
    public void getInfoCallBack(GetInfoEntity data) {
        tv_achievement_discounts.setText(data.inviteNum+"");
        tv_achievement_cash.setText(data.couponNum+"");
        //邀请记录
        if (data.inviteCustomer.size()>0){
            adapter = new FriendListAdapter(this,data.inviteCustomer);
            lv_share_info.setAdapter(adapter);
        }
        tv_hint_umber.setText(Html.fromHtml("当前邀请人数:<font color=\"#FFA521\"> "+data.inviteNum+"</font>人，已获赠免费洗车机会<font color=\"#FFA521\">"+data.couponNum+"</font>次。"));
        //1,6,12,18,24,30,36
        if (data.inviteNum==1){
            view_yuan_share_schedule_101.setBackgroundResource(R.drawable.share_num_bg_yes);
        }else if (data.inviteNum>1&&data.inviteNum<6){
            view_yuan_share_schedule_101.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_share_schedule_1011.setBackgroundColor(getResources().getColor(R.color.public_all));
        }else if (data.inviteNum==6){
            view_yuan_share_schedule_101.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_yuan_share_schedule_102.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_share_schedule_1011.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1012.setBackgroundColor(getResources().getColor(R.color.public_all));
        }else if (data.inviteNum>6&&data.inviteNum<12){
            view_yuan_share_schedule_101.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_yuan_share_schedule_102.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_share_schedule_1011.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1012.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1021.setBackgroundColor(getResources().getColor(R.color.public_all));
        }else if (data.inviteNum==12){
            view_yuan_share_schedule_101.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_yuan_share_schedule_102.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_yuan_share_schedule_103.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_share_schedule_1011.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1012.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1021.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1022.setBackgroundColor(getResources().getColor(R.color.public_all));
        }else if (data.inviteNum>12&&data.inviteNum<18){
            view_yuan_share_schedule_101.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_yuan_share_schedule_102.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_yuan_share_schedule_103.setBackgroundResource(R.drawable.share_num_bg_yes);
            view_share_schedule_1011.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1012.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1021.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1022.setBackgroundColor(getResources().getColor(R.color.public_all));
            view_share_schedule_1031.setBackgroundColor(getResources().getColor(R.color.public_all));
        }else if (data.inviteNum==18){
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
}
