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
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
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
    private RadioButton rb_work_order_all,rb_work_order_dai_jie;
    private LinearLayout ll_dao_dian;
    private List<ProductInfoEntity.ProductInfo> list = new ArrayList<>();

    public FirstFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment, container, false);
        tv_map_jingsi = view.findViewById(R.id.tv_map_jingsi);
        gv_home_data = view.findViewById(R.id.gv_home_data);
        rb_work_order_all = view.findViewById(R.id.rb_work_order_all);
        rb_work_order_dai_jie = view.findViewById(R.id.rb_work_order_dai_jie);
        ll_dao_dian = view.findViewById(R.id.ll_dao_dian);

        tv_map_jingsi.setOnClickListener(this);
        rb_work_order_all.setOnClickListener(this);
        rb_work_order_dai_jie.setOnClickListener(this);
        adapter = new HomeDataAdapter(context);
        adapter.setData(list);
        gv_home_data.setAdapter(adapter);
        mPresenter.comboInfo();

        gv_home_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                adapter.setSelectPosition(i);
                adapter.notifyDataSetChanged();
            }
        });
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
            case R.id.tv_map_jingsi://菁喜技师
                ZzRouter.gotoActivity((Activity) context, MapJingSiActivity.class);
                break;
            case R.id.rb_work_order_all://上门
                gv_home_data.setVisibility(View.VISIBLE);
                ll_dao_dian.setVisibility(View.GONE);
                break;
            case R.id.rb_work_order_dai_jie://到店
                gv_home_data.setVisibility(View.GONE);
                ll_dao_dian.setVisibility(View.VISIBLE);
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
        list = data.get(0).productList;
        adapter.setData(list);
        adapter.notifyDataSetChanged();
    }
}
