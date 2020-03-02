package com.jxxc.jingxi.ui.shoplist;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jxxc.jingxi.R;
import com.jxxc.jingxi.dialog.PopFiltrate;
import com.jxxc.jingxi.dialog.PopFiltrateCity;
import com.jxxc.jingxi.dialog.PopFiltrateOne;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.utils.SPUtils;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class ShopListActivity extends MVPBaseActivity<ShopListContract.View, ShopListPresenter> implements ShopListContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_location_city)
    TextView tv_location_city;
    @BindView(R.id.ll_city_filtrate)
    LinearLayout ll_city_filtrate;
    @BindView(R.id.ll_paixu_filtrate)
    LinearLayout ll_paixu_filtrate;
    @BindView(R.id.ll_static_filtrate)
    LinearLayout ll_static_filtrate;
    private ShopListAdapter adapter;
    private PopFiltrate popFiltrate;
    private PopFiltrateOne popFiltrateOne;
    private PopFiltrateCity popFiltrateCity;
    //shop_list_adapter
    @Override
    protected int layoutId() {
        return R.layout.shop_list_activity;
    }

    @Override
    public void initData() {
        tv_title.setText("门店列表");
        tv_location_city.setText(SPUtils.get(SPUtils.K_CITY,"选择城市"));
        popFiltrate = new PopFiltrate(this);
        popFiltrateOne = new PopFiltrateOne(this);
        popFiltrateCity = new PopFiltrateCity(this);
    }

    @OnClick({R.id.tv_back,R.id.ll_city_filtrate,R.id.ll_paixu_filtrate,R.id.ll_static_filtrate})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
            case R.id.ll_city_filtrate://城市筛选
                popFiltrateCity.showPopupWindow(ll_city_filtrate);
                break;
            case R.id.ll_paixu_filtrate://排序筛选
                popFiltrate.showPopupWindow(ll_paixu_filtrate);
                break;
            case R.id.ll_static_filtrate://筛选
                popFiltrateOne.showPopupWindow(ll_static_filtrate);
                break;
            default:
        }
    }
}
