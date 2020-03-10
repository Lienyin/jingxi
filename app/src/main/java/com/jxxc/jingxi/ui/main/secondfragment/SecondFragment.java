package com.jxxc.jingxi.ui.main.secondfragment;

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
import android.widget.RadioButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.CommissionListEntity;
import com.jxxc.jingxi.entity.backparameter.FindEntity;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.mvp.MVPBaseFragment;
import com.jxxc.jingxi.ui.commissionlist.CommissionAdapter;
import com.jxxc.jingxi.ui.finddetails.FindDetailsActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 发现界面
 */
@SuppressLint("ValidFragment")
public class SecondFragment extends MVPBaseFragment<SecondFramentContract.View, SecondFramentPresenter> implements View.OnClickListener, SecondFramentContract.View, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    private Context context;
    private RadioButton rb_fa_all,rb_fa_guandian,rb_fa_yang,rb_fa_jiao;
    private SwipeRefreshLayout swipeLayout;
    private RecyclerView rvList;
    private FindListAdapter adapter;
    private int offset = 2;
    private String type="";
    private List<FindEntity> findEntityList = new ArrayList<>();

    public SecondFragment(Context context){
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.second_fragment,container,false);
        rb_fa_all = (RadioButton)view.findViewById(R.id.rb_fa_all);
        rb_fa_guandian = (RadioButton)view.findViewById(R.id.rb_fa_guandian);
        rb_fa_yang = (RadioButton)view.findViewById(R.id.rb_fa_yang);
        rb_fa_jiao = (RadioButton)view.findViewById(R.id.rb_fa_jiao);
        swipeLayout = view.findViewById(R.id.swipeLayout);
        rvList = view.findViewById(R.id.rv_list);

        rb_fa_all.setOnClickListener(this);
        rb_fa_guandian.setOnClickListener(this);
        rb_fa_yang.setOnClickListener(this);
        rb_fa_jiao.setOnClickListener(this);

        initAdapter();
        onRefresh();
        return view;
    }

    private void initAdapter() {
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(getResources().getColor(R.color.public_all));
        rvList.setLayoutManager(new LinearLayoutManager(context));
        adapter = new FindListAdapter(R.layout.find_adapter, new ArrayList<FindEntity>());
        rvList.setAdapter(adapter);
        adapter.setOnLoadMoreListener(this, rvList);
        adapter.setEmptyView(R.layout.layout_nothing);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent((Activity) context, FindDetailsActivity.class);
                intent.putExtra("findContent",findEntityList.get(position).content);
                intent.putExtra("appreciateNum",findEntityList.get(position).appreciateNum);
                intent.putExtra("findId",findEntityList.get(position).noticeId);
                intent.putExtra("type",findEntityList.get(position).type);
                startActivity(intent);
            }
        });
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        return null;
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rb_fa_all://全部
                type = "";
                mPresenter.find(type,1,10);
                break;
            case R.id.rb_fa_guandian://经验/观点
                type = "1";
                mPresenter.find(type,1,10);
                break;
            case R.id.rb_fa_yang://爱车养护
                type = "2";
                mPresenter.find(type,1,10);
                break;
            case R.id.rb_fa_jiao://教程
                type = "3";
                mPresenter.find(type,1,10);
                break;
        }
    }

    @Override
    public void findCallBackCall(List<FindEntity> data) {
        findEntityList= data;
        swipeLayout.setRefreshing(false);
        adapter.setNewData(data);
        // adapter.disableLoadMoreIfNotFullPage();
        if (data.size() < 10) {
            adapter.loadMoreEnd();
        }
    }

    @Override
    public void findMoreCallBackCall(List<FindEntity> data) {
        findEntityList.addAll(data);
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
        mPresenter.find(type,1,10);
    }

    @Override
    public void onLoadMoreRequested() {
        swipeLayout.setRefreshing(false);
        mPresenter.findMore(type,offset,10);
    }
}
