package com.jxxc.jingxi.ui.main.firstfragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.ProductInfoEntity;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.mvp.MVPBaseFragment;
import com.jxxc.jingxi.ui.mapjingsi.MapJingSiActivity;
import com.jxxc.jingxi.utils.AnimUtils;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class FirstFragment extends MVPBaseFragment<FirseFramentContract.View, FirseFramentPresenter> implements View.OnClickListener, FirseFramentContract.View, SwipeRefreshLayout.OnRefreshListener {
    private Context context;
    private TextView tv_map_jingsi;
    private GridView gv_home_data;
    private HomeDataAdapter adapter;
    private List<ProductInfoEntity> list = new ArrayList<>();

    public FirstFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment, container, false);
        tv_map_jingsi = view.findViewById(R.id.tv_map_jingsi);
        gv_home_data = view.findViewById(R.id.gv_home_data);

        tv_map_jingsi.setOnClickListener(this);
        adapter = new HomeDataAdapter(context);
        adapter.setData(list);
        gv_home_data.setAdapter(adapter);
        mPresenter.comboInfo();
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
            case R.id.tv_map_jingsi://工单
                ZzRouter.gotoActivity((Activity) context, MapJingSiActivity.class);
                break;
        }
    }

    //刷新
    @Override
    public void onRefresh() {

    }

    //获取洗车组合套餐返回数据
    @Override
    public void comboInfoCallBack(List<ProductInfoEntity> data) {
        list = data;
        adapter.setData(list);
        adapter.notifyDataSetChanged();
    }
}
