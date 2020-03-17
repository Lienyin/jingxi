package com.jxxc.jingxi.ui.discountcoupon;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jxxc.jingxi.R;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.ui.discountcoupon.coupontype.CouponTypeFragment;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class DiscountCouponActivity extends MVPBaseActivity<DiscountCouponContract.View, DiscountCouponPresenter> implements DiscountCouponContract.View {

    private MyPagerAdapter pagerAdapter;

    @Override
    protected int layoutId() {
        return R.layout.discount_coupon_activity;
    }

    @Override
    public void initData() {
        StatusBarUtil.setStatusBarMode(this, true, R.color.public_all);//状态栏颜色
        tvTitle.setText("优惠券");
        tvBack.setVisibility(View.VISIBLE);
        contentBeans.add("未使用");
        contentBeans.add("已使用");
        contentBeans.add("已过期");
        initViewPager();
        mPresenter.temp();
    }

    private void initViewPager() {
        for (String s:contentBeans) {
            fragments.add(CouponTypeFragment.newInstance(s));
        }
        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(pagerAdapter);
        tabs.setupWithViewPager(viewPager);
    }

    @OnClick(R.id.tv_back)
    public void onViewClicked() {
        AnimUtils.clickAnimator(tvBack);
        finish();
    }

    @Override
    protected void onDestroy() {
        tabs.removeAllTabs();
        viewPager.clearOnPageChangeListeners();
        super.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return contentBeans.get(position);
        }

        @Override
        public int getCount() {
            return contentBeans.size();
        }
    }
    List<String> contentBeans = new ArrayList<>();
    List<Fragment> fragments = new ArrayList<>();
    @BindView(R.id.tv_back)
    TextView tvBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_affirm)
    TextView tvAffirm;
    @BindView(R.id.title_head)
    RelativeLayout titleHead;
    @BindView(R.id.app_title_layout)
    LinearLayout appTitleLayout;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
}
