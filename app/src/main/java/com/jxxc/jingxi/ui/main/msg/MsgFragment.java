package com.jxxc.jingxi.ui.main.msg;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.adapter.MsgAdapter;
import com.jxxc.jingxi.adapter.RecommendSetMealAdapter;
import com.jxxc.jingxi.adapter.ShopListAdapter;
import com.jxxc.jingxi.entity.backparameter.MessageListEntity;
import com.jxxc.jingxi.entity.backparameter.RecommendComboInfoEntity;
import com.jxxc.jingxi.entity.backparameter.companyListEntity;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.mvp.MVPBaseFragment;
import com.jxxc.jingxi.ui.setmealpay.SetMealPayActivity;
import com.jxxc.jingxi.ui.shopdetails.ShopDetailsActivity;
import com.jxxc.jingxi.ui.shoplist.ShopListActivity;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.utils.SPUtils;
import com.jxxc.jingxi.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

@SuppressLint("ValidFragment")
public class MsgFragment extends MVPBaseFragment<MsgContract.View, MsgPresenter> implements MsgContract.View, SwipeRefreshLayout.OnRefreshListener,View.OnClickListener, BaseQuickAdapter.RequestLoadMoreListener {

    private Context context;
    private SwipeRefreshLayout swipeLayout_sm;
    private ListView lv_set_meal_data;
    private MsgAdapter adapter;
    private View view_shangmen_fuwu,view_daodian_fuwu;
    private RadioButton rb_shangmen_service,rb_daodian_service;
    public MsgFragment(Context context){
        this.context=context;
    }
    private int fuwuType = 0;//默认上门
    private int offset = 2;
    private String queryFlag="";//筛选 1直营 2加盟 3合作 4营业中
    private String sort="";
    private String cityId="";
    private String lat ="";
    private String lng ="";
    private ShopListAdapter shopListAdapter;
    private SwipeRefreshLayout swipeLayout;
    private RecyclerView rv_list;
    private List<companyListEntity> companyListEntityList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.msg_fragment, container, false);
        swipeLayout_sm = view.findViewById(R.id.swipeLayout_sm);
        swipeLayout = view.findViewById(R.id.swipeLayout);
        rv_list = view.findViewById(R.id.rv_list);
        lv_set_meal_data = view.findViewById(R.id.lv_set_meal_data);
        rb_shangmen_service = view.findViewById(R.id.rb_shangmen_service);
        rb_daodian_service = view.findViewById(R.id.rb_daodian_service);
        view_shangmen_fuwu = view.findViewById(R.id.view_shangmen_fuwu);
        view_daodian_fuwu = view.findViewById(R.id.view_daodian_fuwu);
        onRefresh();

        swipeLayout_sm.setOnRefreshListener(this);
        swipeLayout_sm.setColorSchemeColors(getResources().getColor(R.color.public_all));
        rb_shangmen_service.setOnClickListener(this);
        rb_daodian_service.setOnClickListener(this);
        lat = SPUtils.get(context, "lat", "");
        lng = SPUtils.get(context, "lng", "");
        initAdapter();
        return view;
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        return null;
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.rb_shangmen_service://上门
                view_shangmen_fuwu.setVisibility(View.VISIBLE);
                view_daodian_fuwu.setVisibility(View.GONE);
                fuwuType = 0;
                onRefresh();
                break;
            case R.id.rb_daodian_service://到店
                view_shangmen_fuwu.setVisibility(View.GONE);
                view_daodian_fuwu.setVisibility(View.VISIBLE);
                fuwuType = 1;
                onRefresh();
                break;
        }
    }

    private void initAdapter() {
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(getResources().getColor(R.color.public_all));
        rv_list.setLayoutManager(new LinearLayoutManager(context));
        shopListAdapter = new ShopListAdapter(R.layout.shop_list_adapter, new ArrayList<companyListEntity>());
        rv_list.setAdapter(shopListAdapter);
        shopListAdapter.setOnLoadMoreListener(this, rv_list);
        shopListAdapter.setEmptyView(R.layout.layout_nothing);

        shopListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ZzRouter.gotoActivity((Activity) context, ShopDetailsActivity.class,companyListEntityList.get(position).companyId);
            }
        });
    }

    //下拉
    @Override
    public void onRefresh() {
        if (fuwuType == 0){
            mPresenter.recommendComboInfo("0","");
        }else{
            offset=2;
            mPresenter.companyList(Double.valueOf(lng),Double.valueOf(lat),queryFlag,sort,cityId,1,10);
        }
    }

    //套餐数据
    @Override
    public void recommendComboInfoCallBack(final List<RecommendComboInfoEntity> data) {
        swipeLayout_sm.setRefreshing(false);
        RecommendSetMealAdapter recommendSetMealAdapter = new RecommendSetMealAdapter(context);
        recommendSetMealAdapter.setData(data,2);
        lv_set_meal_data.setAdapter(recommendSetMealAdapter);
        lv_set_meal_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent((Activity) context, SetMealPayActivity.class);
                intent.putExtra("recommendComboInfoEntity",data.get(i));
                intent.putExtra("serviceType","0");
                context.startActivity(intent);
            }
        });
    }

    //门店数据上拉
    @Override
    public void onLoadMoreRequested() {
        swipeLayout.setRefreshing(false);
        mPresenter.companyListMore(Double.valueOf(lng),Double.valueOf(lat),queryFlag,sort,cityId,offset,10);
    }

    //门店数据
    @Override
    public void companyListCallBack(List<companyListEntity> data) {
        companyListEntityList = data;
        swipeLayout.setRefreshing(false);
        shopListAdapter.setNewData(data);
        if (data.size() < 10) {
            shopListAdapter.loadMoreEnd();
        }else{
            shopListAdapter.disableLoadMoreIfNotFullPage();
        }
    }

    //门店数据更多
    @Override
    public void companyListCallBackMore(List<companyListEntity> data) {
        companyListEntityList.addAll(data);
        swipeLayout.setRefreshing(false);
        offset++;
        shopListAdapter.addData(data);
        shopListAdapter.loadMoreComplete();
        if (data.size() < 10) {
            shopListAdapter.loadMoreEnd();
        }
    }
}
