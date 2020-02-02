package com.jxxc.jingxi.ui.main.msg;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jxxc.jingxi.R;
import com.jxxc.jingxi.mvp.MVPBaseFragment;
import com.jxxc.jingxi.ui.main.my.MyFragment;
import com.jxxc.jingxi.utils.AnimUtils;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

@SuppressLint("ValidFragment")
public class MsgFragment extends MVPBaseFragment<MsgContract.View, MsgPresenter> implements MsgContract.View, SwipeRefreshLayout.OnRefreshListener,View.OnClickListener {

    private Context context;
    public MsgFragment(Context context){
        this.context=context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment, container, false);
        //tv_todayOrderCount = view.findViewById(R.id.tv_todayOrderCount);
        return view;
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

    }
}
