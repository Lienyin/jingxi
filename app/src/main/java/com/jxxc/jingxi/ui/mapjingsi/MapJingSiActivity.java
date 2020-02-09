package com.jxxc.jingxi.ui.mapjingsi;


import android.view.View;
import android.widget.TextView;

import com.baidu.mapapi.map.MapView;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.utils.AnimUtils;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MapJingSiActivity extends MVPBaseActivity<MapJingSiContract.View, MapJingSiPresenter> implements MapJingSiContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
//    @BindView(R.id.mv_jingsi)
//    MapView mv_jingsi;
    @Override
    protected int layoutId() {
        return R.layout.map_jing_si_activity;
    }

    @Override
    public void initData() {
        tv_title.setText("附近技师");
    }

    @OnClick({R.id.tv_back})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
            default:
        }
    }
}
