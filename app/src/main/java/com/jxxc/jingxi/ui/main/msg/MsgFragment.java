package com.jxxc.jingxi.ui.main.msg;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.MessageListEntity;
import com.jxxc.jingxi.entity.backparameter.MyOrderEntity;
import com.jxxc.jingxi.mvp.MVPBaseFragment;
import com.jxxc.jingxi.ui.main.my.MyFragment;
import com.jxxc.jingxi.ui.myorder.BillAdapter;
import com.jxxc.jingxi.utils.AnimUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

@SuppressLint("ValidFragment")
public class MsgFragment extends MVPBaseFragment<MsgContract.View, MsgPresenter> implements MsgContract.View, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener,View.OnClickListener {

    private Context context;
    private SwipeRefreshLayout swipeLayout;
    private RecyclerView rvList;
    private MsgAdapter adapter;
    private int offset = 2;
    public MsgFragment(Context context){
        this.context=context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.msg_fragment, container, false);
        swipeLayout = view.findViewById(R.id.swipeLayout);
        rvList = view.findViewById(R.id.rv_list);
        initAdapter();
        return view;
    }

    private void initAdapter() {
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(getResources().getColor(R.color.public_all));
        rvList.setLayoutManager(new LinearLayoutManager(context));
        adapter = new MsgAdapter(R.layout.adapter_my_order, new ArrayList<MessageListEntity>());
        rvList.setAdapter(adapter);
        adapter.setOnLoadMoreListener(this, rvList);
        adapter.setEmptyView(R.layout.layout_nothing);
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
//            case R.id.ll_gong_dan://工单
//                ZzRouter.gotoActivity((Activity) context, WorkOrderActivity.class,"2");
//                break;
        }
    }

    //刷新
    @Override
    public void onRefresh() {
        offset = 2;
        mPresenter.messageList(1,10);
    }

    @Override
    public void onLoadMoreRequested() {
        swipeLayout.setRefreshing(false);
        mPresenter.messageListMore(offset,10);
    }

    @Override
    public void messageList(List<MessageListEntity> data) {
        swipeLayout.setRefreshing(false);
        adapter.setNewData(data);
        adapter.disableLoadMoreIfNotFullPage();
    }

    @Override
    public void messageListMore(List<MessageListEntity> data) {
        swipeLayout.setRefreshing(false);
        offset++;
        adapter.addData(data);
        adapter.loadMoreComplete();
        if (data.size() < 10) {
            adapter.loadMoreEnd();
        }
    }
}
