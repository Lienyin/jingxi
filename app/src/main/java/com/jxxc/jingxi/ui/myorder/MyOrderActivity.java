package com.jxxc.jingxi.ui.myorder;


import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.dialog.CancelOrderDialog;
import com.jxxc.jingxi.entity.backparameter.MyOrderEntity;
import com.jxxc.jingxi.entity.backparameter.OrderNumEntity;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.ui.evaluate.EvaluateActivity;
import com.jxxc.jingxi.ui.orderdetailsdaifuwu.OrderDetailsDaiFuWuActivity;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.ui.orderdetails.OrderDetailsActivity;
import com.jxxc.jingxi.utils.AppUtils;
import com.jxxc.jingxi.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 我的订单
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MyOrderActivity extends MVPBaseActivity<MyOrderContract.View, MyOrderPresenter> implements MyOrderContract.View, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.rv_list)
    RecyclerView rvList;
    @BindView(R.id.rb_order_all)
    RadioButton rb_order_all;
    @BindView(R.id.rb_order_daizhifu)
    RadioButton rb_order_daizhifu;
    @BindView(R.id.rb_order_jignxz)
    RadioButton rb_order_jignxz;
    @BindView(R.id.rb_order_yiwc)
    RadioButton rb_order_yiwc;
    @BindView(R.id.rb_order_daijiedan)
    RadioButton rb_order_daijiedan;
    @BindView(R.id.rb_order_daifuwu)
    RadioButton rb_order_daifuwu;
    @BindView(R.id.rb_order_cancel)
    RadioButton rb_order_cancel;

    private BillAdapter adapter;
    private int offset = 2;
    private String orderType = "";
    private CancelOrderDialog dialog;
    private List<MyOrderEntity> list = new ArrayList<>();
    private String OrderId;
    @Override
    protected int layoutId() {
        return R.layout.activity_my_order;
    }

    @Override
    public void initData() {
        StatusBarUtil.setStatusBarMode(this, false, R.color.white);
        tv_title.setText("我的订单");
        initAdapter();
        onRefresh();
        rb_order_all.setChecked(true);
        dialog = new CancelOrderDialog(this);
        dialog.setOnFenxiangClickListener(new CancelOrderDialog.OnFenxiangClickListener() {
            @Override
            public void onFenxiangClick() {
                //取消订单
                StyledDialog.buildLoading("正在取消").setActivity(MyOrderActivity.this).show();
                mPresenter.cancelOrder(OrderId);
            }
        });
    }

    private void initAdapter() {
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(getResources().getColor(R.color.public_all));
        rvList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BillAdapter(R.layout.adapter_my_order, new ArrayList<MyOrderEntity>());
        rvList.setAdapter(adapter);
        adapter.setOnLoadMoreListener(this, rvList);
        adapter.setEmptyView(R.layout.layout_nothing);
        adapter.setOnFenxiangClickListener(new BillAdapter.OnFenxiangClickListener() {
            @Override
            public void onFenxiangClick(int type,String mobile, String orderId,String status) {
                if (type==1){//联系技师
                    if (!AppUtils.isEmpty(mobile)){
                        AppUtils.callPhone(MyOrderActivity.this,mobile);
                    }else{
                        toast(MyOrderActivity.this,"等待技师接单");
                    }
                }else if (type==2){
                    //评价
                    ZzRouter.gotoActivity(MyOrderActivity.this, EvaluateActivity.class,orderId);
                }else if (type==3){
                    //取消订单
                    OrderId = orderId;
                    dialog.showShareDialog(true);
                }else if (type==4){
                    if ("4".equals(status)){
                        ZzRouter.gotoActivity(MyOrderActivity.this,OrderDetailsActivity.class,orderId);
                    }else {
                        ZzRouter.gotoActivity(MyOrderActivity.this, OrderDetailsDaiFuWuActivity.class,orderId);
                    }
                }
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //订单状态 不传查默认所有 ( 0, “待支付”),( 1, “已支付待接单”),
                // ( 2, “已接单待服务”),( 3, “服务中”),( 4, “服务已完成”),( 5, “取消订单”)
                if (list.get(position).status==4){
                    ZzRouter.gotoActivity(MyOrderActivity.this,OrderDetailsActivity.class,list.get(position).orderId);
                }else {
                    ZzRouter.gotoActivity(MyOrderActivity.this, OrderDetailsDaiFuWuActivity.class,list.get(position).orderId);
                }
            }
        });
    }

    @OnClick({R.id.tv_back,R.id.rb_order_all,R.id.rb_order_daizhifu,R.id.rb_order_jignxz,R.id.rb_order_yiwc,
    R.id.rb_order_daijiedan,R.id.rb_order_daifuwu,R.id.rb_order_cancel})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            //订单状态 不传查默认所有 (-1 查除服务中状态的订单) ( 0, “待支付”),( 1, “待服务”),
            // ( 3, “服务中”),( 4, “服务已完成”)
            case R.id.tv_back:
                finish();
                break;
            case R.id.rb_order_all://全部
                orderType = "";
                mPresenter.myOrder(orderType,1,10);
                break;
            case R.id.rb_order_daizhifu://待支付
                orderType = "0";
                mPresenter.myOrder(orderType,1,10);
                break;
            case R.id.rb_order_daijiedan://已支付待接单
                orderType = "1";
                mPresenter.myOrder(orderType,1,10);
                break;
            case R.id.rb_order_daifuwu://待服务
                orderType = "1";
                mPresenter.myOrder(orderType,1,10);
                break;
            case R.id.rb_order_jignxz://进行中
                orderType = "3";
                mPresenter.myOrder(orderType,1,10);
                break;
            case R.id.rb_order_yiwc://已完成
                orderType = "4";
                mPresenter.myOrder(orderType,1,10);
                break;
            case R.id.rb_order_cancel://已完成
                orderType = "5";
                mPresenter.myOrder(orderType,1,10);
                break;
            default:
        }
    }

    @Override
    public void myOrderCallBack(List<MyOrderEntity> data) {
        list = data;
        swipeLayout.setRefreshing(false);
        adapter.setNewData(data);
        if (data.size() < 10) {
            adapter.loadMoreEnd();
        }else{
            adapter.disableLoadMoreIfNotFullPage();
        }
    }

    @Override
    public void myOrderMoreCallBack(List<MyOrderEntity> data) {
        list.addAll(data);
        swipeLayout.setRefreshing(false);
        offset++;
        adapter.addData(data);
        adapter.loadMoreComplete();
        if (data.size() < 10) {
            adapter.loadMoreEnd();
        }
    }

    //取消订单
    @Override
    public void cancelOrderCallBack() {
        onRefresh();
    }

    //订单数量
    @Override
    public void orderNumCallBack(OrderNumEntity data) {
        rb_order_all.setText("全部("+data.all+")");
        rb_order_daizhifu.setText("待支付("+data.notPay+")");
        rb_order_daifuwu.setText("待服务("+data.notService+")");
        rb_order_yiwc.setText("已完成("+data.complete+")");
    }

    @Override
    public void onRefresh() {
        offset = 2;
        mPresenter.myOrder(orderType,1,10);
        mPresenter.orderNum();
    }

    @Override
    public void onLoadMoreRequested() {
        swipeLayout.setRefreshing(false);
        mPresenter.myOrderMore(orderType,offset,10);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        onRefresh();
    }
}
