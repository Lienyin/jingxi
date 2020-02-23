package com.jxxc.jingxi.ui.orderdetails;


import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.adapter.OrderDetailsDataAdapter;
import com.jxxc.jingxi.entity.backparameter.OrderEntity;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.ui.evaluate.EvaluateActivity;
import com.jxxc.jingxi.ui.myorder.MyOrderActivity;
import com.jxxc.jingxi.ui.submitorder.SubmitOrderActivity;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.utils.AppUtils;
import com.jxxc.jingxi.utils.GlideImgManager;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 订单详情（服务已完成）
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class OrderDetailsActivity extends MVPBaseActivity<OrderDetailsContract.View, OrderDetailsPresenter> implements OrderDetailsContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_details_order_id)
    TextView tv_details_order_id;
    @BindView(R.id.tv_details_order_xia_time)
    TextView tv_details_order_xia_time;
    @BindView(R.id.tv_details_order_static)
    TextView tv_details_order_static;
    @BindView(R.id.tv_details_order_address)
    TextView tv_details_order_address;
    @BindView(R.id.tv_details_order_memo)
    TextView tv_details_order_memo;
    @BindView(R.id.tv_details_order_money)
    TextView tv_details_order_money;
    @BindView(R.id.tv_details_jishi_name)
    TextView tv_details_jishi_name;
    @BindView(R.id.tv_details_pingjian)
    TextView tv_details_pingjian;
    @BindView(R.id.tv_details_fuwu_time)
    TextView tv_details_fuwu_time;
    @BindView(R.id.tv_details_pingjia_text)
    TextView tv_details_pingjia_text;
    @BindView(R.id.tv_details_pingjia_time)
    TextView tv_details_pingjia_time;
    @BindView(R.id.tv_details_order_coupon)
    TextView tv_details_order_coupon;
    @BindView(R.id.tv_again)
    TextView tv_again;
    @BindView(R.id.iv_jishi_hand)
    ImageView iv_jishi_hand;
    @BindView(R.id.iv_jibie)
    ImageView iv_jibie;
    @BindView(R.id.iv_jishi_jibie)
    ImageView iv_jishi_jibie;
    @BindView(R.id.gv_fuwu_data)
    GridView gv_fuwu_data;
    @BindView(R.id.ll_my_pingjia)
    LinearLayout ll_my_pingjia;
    @BindView(R.id.tv_delete)
    TextView tv_delete;
    private String OrderId;
    private OrderDetailsDataAdapter adapter;
    private OrderEntity orderEntity = new OrderEntity();
    @Override
    protected int layoutId() {
        return R.layout.acivity_order_details;
    }

    @Override
    public void initData() {
        tv_title.setText("订单详情");
        OrderId = ZzRouter.getIntentData(this,String.class);
        StyledDialog.buildLoading("数据加载中").setActivity(this).show();
        mPresenter.getOrder(OrderId);
    }

    @OnClick({R.id.tv_back,R.id.tv_details_pingjian,R.id.tv_again,R.id.tv_delete})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.tv_details_pingjian://评价
                if ("未评价".equals(tv_details_pingjian.getText().toString())){
                    ZzRouter.gotoActivity(this, EvaluateActivity.class,OrderId);
                }else{
                    toast(this,"已评价");
                }
                break;
            case R.id.tv_again://再来一单
                ZzRouter.gotoActivity(this, SubmitOrderActivity.class);
                break;
            case R.id.tv_delete://删除评论
                toast(this,"等待开发");
                break;
            default:
        }
    }

    //订单详情返回数据
    @Override
    public void getOrderCallBack(OrderEntity data) {
        orderEntity = data;
        adapter = new OrderDetailsDataAdapter(this);
        adapter.setData(orderEntity.products);
        gv_fuwu_data.setAdapter(adapter);

        tv_details_order_id.setText(data.orderId);
        tv_details_order_static.setText(data.statusName);
        tv_details_order_xia_time.setText(data.appointmentTime);
        tv_details_order_address.setText(data.address);
        tv_details_order_memo.setText(data.remark);
        tv_details_order_coupon.setText("-￥"+data.discountsPrice);
        tv_details_order_money.setText("￥"+data.price);
        tv_details_jishi_name.setText(data.technicianRealName);
        if (!AppUtils.isEmpty(data.customerCommentTime)){
            tv_details_pingjian.setText("已评价");
            ll_my_pingjia.setVisibility(View.VISIBLE);
        }else {
            tv_details_pingjian.setText("未评价");
            ll_my_pingjia.setVisibility(View.GONE);
        }
        GlideImgManager.loadCircleImage(this, data.technicianAvatar, iv_jishi_hand);
        tv_details_fuwu_time.setText("服务时长 "+data.duration+"分钟");
        //评价模块
        if (data.starLevel>=0&&data.starLevel<=1){
            iv_jibie.setImageResource(R.mipmap.icon_user_13);
        }else if (data.starLevel>1&&data.starLevel<=2){
            iv_jibie.setImageResource(R.mipmap.icon_user_21);
        }else if (data.starLevel>2&&data.starLevel<=3){
            iv_jibie.setImageResource(R.mipmap.icon_user_23);
        }else if (data.starLevel>3&&data.starLevel<=4){
            iv_jibie.setImageResource(R.mipmap.icon_user_25);
        }else{
            iv_jibie.setImageResource(R.mipmap.icon_user_27);
        }
        //技师星级
        if (data.technicianGrade>=0&&data.technicianGrade<=1){
            iv_jishi_jibie.setImageResource(R.mipmap.icon_user_13);
        }else if (data.technicianGrade>1&&data.technicianGrade<=2){
            iv_jishi_jibie.setImageResource(R.mipmap.icon_user_21);
        }else if (data.technicianGrade>2&&data.technicianGrade<=3){
            iv_jishi_jibie.setImageResource(R.mipmap.icon_user_23);
        }else if (data.technicianGrade>3&&data.technicianGrade<=4){
            iv_jishi_jibie.setImageResource(R.mipmap.icon_user_25);
        }else{
            iv_jishi_jibie.setImageResource(R.mipmap.icon_user_27);
        }
        tv_details_pingjia_text.setText(data.commentContent);
        tv_details_pingjia_time.setText(data.customerCommentTime);
    }
}
