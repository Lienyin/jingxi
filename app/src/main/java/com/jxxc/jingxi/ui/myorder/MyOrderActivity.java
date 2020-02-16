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
import com.jxxc.jingxi.entity.backparameter.MyOrderEntity;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.ui.orderdetails.OrderDetailsActivity;

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

    private BillAdapter adapter;
    private int offset = 2;
    private String orderType = "";
    @Override
    protected int layoutId() {
        return R.layout.activity_my_order;
    }

    @Override
    public void initData() {
        tv_title.setText("我的订单");
        initAdapter();
        onRefresh();
        rb_order_all.setChecked(true);
    }

    private void initAdapter() {
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(getResources().getColor(R.color.public_all));
        rvList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BillAdapter(R.layout.adapter_my_order, new ArrayList<MyOrderEntity>());
        rvList.setAdapter(adapter);
        adapter.setOnLoadMoreListener(this, rvList);
        adapter.setEmptyView(R.layout.layout_nothing);
    }

    @OnClick({R.id.tv_back,R.id.rb_order_all,R.id.rb_order_daizhifu,R.id.rb_order_jignxz,R.id.rb_order_yiwc})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            //订单状态 不传查默认所有 ( 0, “待支付”),( 1, “已支付待接单”),( 2, “已接单待服务”),( 3, “服务中”),( 4, “服务已完成”),( 5, “取消订单”)
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
            case R.id.rb_order_jignxz://进行中
                orderType = "3";
                mPresenter.myOrder(orderType,1,10);
                break;
            case R.id.rb_order_yiwc://已完成
                orderType = "4";
                mPresenter.myOrder(orderType,1,10);
                break;
            default:
        }
    }

    @Override
    public void myOrderCallBack(List<MyOrderEntity> data) {
        swipeLayout.setRefreshing(false);
        adapter.setNewData(data);
        adapter.disableLoadMoreIfNotFullPage();
    }

    @Override
    public void myOrderMoreCallBack(List<MyOrderEntity> data) {
        swipeLayout.setRefreshing(false);
        offset++;
        adapter.addData(data);
        adapter.loadMoreComplete();
        if (data.size() < 10) {
            adapter.loadMoreEnd();
        }
    }

    @Override
    public void onRefresh() {
        offset = 2;
        mPresenter.myOrder(orderType,1,10);
    }

    @Override
    public void onLoadMoreRequested() {
        swipeLayout.setRefreshing(false);
        mPresenter.myOrderMore(orderType,offset,10);
    }
}
