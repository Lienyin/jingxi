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
import android.widget.ListView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.adapter.MsgAdapter;
import com.jxxc.jingxi.adapter.RecommendSetMealAdapter;
import com.jxxc.jingxi.entity.backparameter.MessageListEntity;
import com.jxxc.jingxi.entity.backparameter.RecommendComboInfoEntity;
import com.jxxc.jingxi.mvp.MVPBaseFragment;
import com.jxxc.jingxi.utils.AnimUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

@SuppressLint("ValidFragment")
public class MsgFragment extends MVPBaseFragment<MsgContract.View, MsgPresenter> implements MsgContract.View, SwipeRefreshLayout.OnRefreshListener,View.OnClickListener {

    private Context context;
    private SwipeRefreshLayout swipeLayout;
    private ListView lv_set_meal_data;
    private MsgAdapter adapter;
    public MsgFragment(Context context){
        this.context=context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.msg_fragment, container, false);
        swipeLayout = view.findViewById(R.id.swipeLayout);
        lv_set_meal_data = view.findViewById(R.id.lv_set_meal_data);
        onRefresh();

        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setColorSchemeColors(getResources().getColor(R.color.public_all));
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
//            case R.id.ll_gong_dan://工单
//                ZzRouter.gotoActivity((Activity) context, WorkOrderActivity.class,"2");
//                break;
        }
    }

    @Override
    public void onRefresh() {
        mPresenter.recommendComboInfo("0","");
    }

    @Override
    public void recommendComboInfoCallBack(RecommendComboInfoEntity data) {
        swipeLayout.setRefreshing(false);
        RecommendSetMealAdapter recommendSetMealAdapter = new RecommendSetMealAdapter(context);
        recommendSetMealAdapter.setData(data.combo,2);
        lv_set_meal_data.setAdapter(recommendSetMealAdapter);
    }
}
