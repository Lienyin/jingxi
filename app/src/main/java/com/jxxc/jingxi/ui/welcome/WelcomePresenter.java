package com.jxxc.jingxi.ui.welcome;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jxxc.jingxi.R;
import com.jxxc.jingxi.http.EventCenter;
import com.jxxc.jingxi.mvp.BasePresenterImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class WelcomePresenter extends BasePresenterImpl<WelcomeContract.View> implements WelcomeContract.Presenter{

    private List<ImageView> viewList;

    @Override
    public void isShowViewPager(ViewPager viewPager, boolean isfirstlogin) {

        if (isfirstlogin) {
            viewList = new ArrayList<ImageView>();
            final int[] images=new int[]{R.mipmap.jingxi_01, R.mipmap.jingxi_02,R.mipmap.jingxi_03,R.mipmap.open_peag};
            ImageView iv1 = new ImageView(mView.getContext());
            ImageView iv2 = new ImageView(mView.getContext());
            ImageView iv3 = new ImageView(mView.getContext());
            ImageView iv4 = new ImageView(mView.getContext());
            viewList.add(iv1);
            viewList.add(iv2);
            viewList.add(iv3);
            viewList.add(iv4);
            viewPager.setAdapter(new PagerAdapter() {

                @Override
                public boolean isViewFromObject(View arg0, Object arg1) {
                    return arg0 == arg1;
                }

                @Override
                public int getCount() {
                    return viewList.size();
                }

                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {
                    ImageView imageView = (ImageView) viewList.get(position);
                    imageView.getBackground().setCallback(null);
                    imageView.setBackground(null);
                    container.removeView(viewList.get(position));
                }

                @Override
                public Object instantiateItem(ViewGroup container, int position) {
                    container.addView(viewList.get(position));
                    ImageView imageView = (ImageView) viewList.get(position);
                    imageView.setBackgroundResource(images[position%images.length]);
                    return viewList.get(position);
                }

            });
        } else {
            //mView.gotoMainNow();
        }
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            WelcomeActivity splashActivity = (WelcomeActivity)mView.getContext();
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {
                if (position == viewList.size()-1) {
                    splashActivity.button.setVisibility(View.VISIBLE);
                }else {
                    splashActivity.button.setVisibility(View.GONE);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    @Override
    public void querySetting() {

    }

    PagerAdapter pagerAdapter = new PagerAdapter() {

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(viewList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }
    };

    @Override
    protected void onEventComing(EventCenter center) {

    }
}
