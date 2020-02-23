package com.jxxc.jingxi.mvp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jxxc.jingxi.ConfigApplication;
import com.jxxc.jingxi.utils.AnimUtils;
import com.sdsmdg.tastytoast.TastyToast;

import java.lang.reflect.ParameterizedType;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public abstract class MVPBaseFragment<V extends BaseView,T extends BasePresenterImpl<V>> extends Fragment implements BaseView{
    public T mPresenter;
    public Context mContext;
    public Activity mActivity;
    public View mFragmentView;
    Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = getInstance(this, 1);
        mPresenter.attachView((V) this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mContext = getContext();
        mActivity = getActivity();
//        if (null == mFragmentView) {
        mFragmentView = initView(inflater, container);
        unbinder = ButterKnife.bind(this, mFragmentView);
        initData();
        initListener();
//        } else {
//            unbinder = ButterKnife.bind(this, mFragmentView);
//        }
        return mFragmentView;
    }


    public abstract View initView(LayoutInflater inflater, ViewGroup container);

    public abstract void initData();

    public void initListener() {

    }

    @Override
    public void onDestroy() {
        //        if (null != mFragmentView) {
//            ((ViewGroup) mFragmentView.getParent()).removeView(mFragmentView);
//        }
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        AnimUtils.cancelAnimator();
        if (unbinder!=null){
            unbinder.unbind();
        }
        super.onDestroy();
    }

    public static void toast(String message, int type) {
        ConfigApplication.toast(message,type);
    }

    public static void toast(Context context, String message) {
        ConfigApplication.toast(message, TastyToast.DEFAULT);
    }

    @Override
    public Context getContext() {
        return super.getContext();
    }

    public <T> T getInstance(Fragment o, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i])
                    .newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }
}
