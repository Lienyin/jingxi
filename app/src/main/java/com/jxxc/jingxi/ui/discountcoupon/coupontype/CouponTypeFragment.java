package com.jxxc.jingxi.ui.discountcoupon.coupontype;


import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.MyCoupon;
import com.jxxc.jingxi.mvp.MVPBaseFragment;
import com.jxxc.jingxi.ui.discountcoupon.coupontype.adapter.MyCouponAdapter;
import com.jxxc.jingxi.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class CouponTypeFragment extends MVPBaseFragment<CouponTypeContract.View, CouponTypePresenter> implements CouponTypeContract.View, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    private String mTitle;
    private static  String DESCRIBE = "首页";
    private MyCouponAdapter adapter;
    private int type=0;

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        view = inflater.inflate(R.layout.fragment_coupon_type_one, container, false);
        return view;
    }

    @Override
    public void initData() {
//        mPresenter.initFragmentBG(view);
        initAdapter();
        initType();
        onRefresh();
    }

    private void initType() {
        if ("未使用".equals(mTitle)) {
            type = 0;
        }if ("已使用".equals(mTitle)) {
            type = 1;
        }if ("已过期".equals(mTitle)) {
            type = 2;
        }
    }

    private void initAdapter() {
        swipeLayout.setOnRefreshListener(this);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new MyCouponAdapter(R.layout.item_mycoupon, new ArrayList<MyCoupon>());
        rvList.setAdapter(adapter);
        adapter.setOnLoadMoreListener(this,rvList);

        adapter.setEmptyView(R.layout.layout_nothing);
    }

    @Override
    public void onRefresh() {
        mPresenter.queryMyCoupon(type);
    }
    @Override
    public void onLoadMoreRequested() {
       // mPresenter.queryMyCouponMore(type,adapter.getData().size(),10);
    }

    @Override
    public void queryMyCouponCallback(List<MyCoupon> data) {
        adapter.getData().clear();
        swipeLayout.setRefreshing(false);
        adapter.setNewData(data);
        adapter.disableLoadMoreIfNotFullPage();
    }

//    @Override
//    public void queryMyCouponMoreCallback(List<MyCoupon> data) {
//        swipeLayout.setRefreshing(false);
//        if (AppUtils.isEmpty(data)) {
//            adapter.loadMoreEnd();
//        }else {
//            adapter.addData(data);
//            adapter.loadMoreComplete();
//        }
//    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTitle = getArguments().getString(DESCRIBE);
        }
    }

    public static CouponTypeFragment newInstance(String describe) {
        CouponTypeFragment fragment = new CouponTypeFragment();
        fragment.setArguments(new Bundle());
        fragment.getArguments().putString(DESCRIBE, describe);
        return fragment;
    }
    private View view;
    @BindView(R.id.rv_list)
    RecyclerView rvList;

    @BindView(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
}
