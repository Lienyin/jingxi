package com.jxxc.jingxi.ui.orderdetailsdaifuwu;


import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.adapter.OrderDetailsDataAdapter;
import com.jxxc.jingxi.entity.backparameter.MyOrderEntity;
import com.jxxc.jingxi.entity.backparameter.OrderEntity;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.utils.AppUtils;
import com.jxxc.jingxi.utils.GlideImgManager;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin 已接单待服务
 *  邮箱 784787081@qq.com
 */

public class OrderDetailsDaiFuWuActivity extends MVPBaseActivity<OrderDetailsDaiFuWuContract.View, OrderDetailsDaiFuWuPresenter> implements OrderDetailsDaiFuWuContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_details_call_phone)
    TextView tv_details_call_phone;
    @BindView(R.id.tv_details_cancel_order)
    TextView tv_details_cancel_order;
    @BindView(R.id.tv_details_cui_order)
    TextView tv_details_cui_order;
    @BindView(R.id.tv_details_jishi_name)
    TextView tv_details_jishi_name;
    @BindView(R.id.tv_details_order_id)
    TextView tv_details_order_id;
    @BindView(R.id.tv_details_order_static)
    TextView tv_details_order_static;
    @BindView(R.id.tv_details_order_xia_time)
    TextView tv_details_order_xia_time;
    @BindView(R.id.tv_details_order_address)
    TextView tv_details_order_address;
    @BindView(R.id.tv_details_order_memo)
    TextView tv_details_order_memo;
    @BindView(R.id.tv_details_order_coupon)
    TextView tv_details_order_coupon;
    @BindView(R.id.tv_details_order_money)
    TextView tv_details_order_money;
    @BindView(R.id.iv_jishi_hand)
    ImageView iv_jishi_hand;
    @BindView(R.id.gv_fuwu_data)
    GridView gv_fuwu_data;
    private OrderEntity orderEntity = new OrderEntity();
    private MyOrderEntity myOrderEntity;
    private OrderDetailsDataAdapter adapter;
    @Override
    protected int layoutId() {
        return R.layout.order_details_dai_fu_wu_activity;
    }

    @Override
    public void initData() {
        tv_title.setText("订单详情");
        myOrderEntity = ZzRouter.getIntentData(this,MyOrderEntity.class);
        StyledDialog.buildLoading("数据加载中").setActivity(this).show();
        mPresenter.getOrder(myOrderEntity.orderId);
    }

    @OnClick({R.id.tv_back,R.id.tv_details_call_phone,R.id.tv_details_cancel_order,R.id.tv_details_cui_order})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
            case R.id.tv_details_call_phone://联系技师
                if (!AppUtils.isEmpty(orderEntity.technicianPhonenumber)){
                    AppUtils.callPhone(this,orderEntity.technicianPhonenumber);
                }else{
                    toast(this,"空号");
                }
                break;
            case R.id.tv_details_cancel_order://取消订单
                //
                break;
            case R.id.tv_details_cui_order://催单
                //
                break;
            default:
        }
    }

    @Override
    public void getOrderCallBack(OrderEntity data) {
        orderEntity = data;
        adapter = new OrderDetailsDataAdapter(this);
        adapter.setData(orderEntity.products);
        gv_fuwu_data.setAdapter(adapter);

        GlideImgManager.loadCircleImage(this, data.technicianAvatar, iv_jishi_hand);
        tv_details_jishi_name.setText(data.technicianRealName);
        tv_details_order_id.setText(data.orderId);
        tv_details_order_static.setText(data.statusName);
        tv_details_order_xia_time.setText(data.appointmentTime);
        tv_details_order_address.setText(data.address);
        tv_details_order_memo.setText(data.remark);
        tv_details_order_coupon.setText("-￥"+data.discountsPrice);
        tv_details_order_money.setText("￥"+data.price);
    }
}
