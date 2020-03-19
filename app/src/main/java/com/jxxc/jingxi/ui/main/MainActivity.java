package com.jxxc.jingxi.ui.main;


import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;


import com.jxxc.jingxi.R;
import com.jxxc.jingxi.dialog.ActivityDialog;
import com.jxxc.jingxi.entity.backparameter.BannerEntity;
import com.jxxc.jingxi.entity.backparameter.UserInfoEntity;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.ui.finddetails.FindDetailsActivity;
import com.jxxc.jingxi.ui.login.LoginActivity;
import com.jxxc.jingxi.ui.main.firstfragment.FirstFragment;
import com.jxxc.jingxi.ui.main.msg.MsgFragment;
import com.jxxc.jingxi.ui.main.my.MyFragment;
import com.jxxc.jingxi.ui.main.myCarfragment.MyCarFragment;
import com.jxxc.jingxi.ui.main.secondfragment.SecondFragment;
import com.jxxc.jingxi.utils.AppUtils;
import com.jxxc.jingxi.utils.MyImageView;
import com.jxxc.jingxi.utils.SPUtils;
import com.jxxc.jingxi.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;


/**
 * 主界面
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MainActivity extends MVPBaseActivity<MainContract.View, MainPresenter> implements MainContract.View, View.OnClickListener {

    private TextView txt_deal1;
    private TextView txt_deal2;
    private TextView txt_deal3;
    private TextView txt_deal4;
    private TextView txt_deal5;
    private FrameLayout ly_content;

    private FirstFragment f1;
    private SecondFragment f2;
    private MyCarFragment f3;
    private MsgFragment f4;
    private MyFragment f5;

    private FragmentManager fragmentManager;
    private long exitTime = 0;
    public static String registrationId;
    private ActivityDialog activityDialog;
    private List<View> listViews; // 图片组
    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        mPresenter.banner();//先请求广告数据，在加载界面
        mPresenter.queryAppVersion("3");//查询版本
        activityDialog = new ActivityDialog(this);
        boolean isfirstlogin =  SPUtils.get(this,"ACTIVITY", true);
        if (isfirstlogin){
            SPUtils.put(this,"ACTIVITY", false);
            activityDialog.showShareDialog(true);
        }

        //极光推送id
//        String pToken = JPushInterface.getRegistrationID(this);//1a0018970a33bcf8b75
//        Log.i("TAG","[MyReceiver] getRegistrationID===="+pToken);
    }

    //UI组件初始化与事件绑定
    private void bindView() {
        txt_deal1 = (TextView)this.findViewById(R.id.txt_deal1);
        txt_deal2 = (TextView)this.findViewById(R.id.txt_deal2);
        txt_deal3 = (TextView)this.findViewById(R.id.txt_deal3);
        txt_deal4 = (TextView)this.findViewById(R.id.txt_deal4);
        txt_deal5 = (TextView)this.findViewById(R.id.txt_deal5);
        ly_content = (FrameLayout) findViewById(R.id.fragment_container);

        txt_deal1.setOnClickListener(this);
        txt_deal2.setOnClickListener(this);
        txt_deal3.setOnClickListener(this);
        txt_deal4.setOnClickListener(this);
        txt_deal5.setOnClickListener(this);
        //默认显示第一个Fragment
        txt_deal1.performClick();//自动触发首页按钮
    }

    //重置所有文本的选中状态
    public void selected(){
        txt_deal1.setSelected(false);
        txt_deal2.setSelected(false);
        txt_deal3.setSelected(false);
        txt_deal4.setSelected(false);
        txt_deal5.setSelected(false);
    }

    //隐藏所有Fragment
    public void hideAllFragment(FragmentTransaction transaction){
        if(f1!=null){
            transaction.hide(f1);
        }
        if(f2!=null){
            transaction.hide(f2);
        }
        if(f3!=null){
            transaction.hide(f3);
        }
        if(f4!=null){
            transaction.hide(f4);
        }
        if(f5!=null){
            transaction.hide(f5);
        }
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        switch(v.getId()){
            case R.id.txt_deal1:
                selected();
                txt_deal1.setSelected(true);
                if(f1==null){
                    f1 = new FirstFragment(this,listViews);
                    transaction.add(R.id.fragment_container,f1);
                }else{
                    transaction.show(f1);
                }
                break;

            case R.id.txt_deal2:
                selected();
                txt_deal2.setSelected(true);
                if(f2==null){
                    f2 = new SecondFragment(this);
                    transaction.add(R.id.fragment_container,f2);
                }else{
                    transaction.show(f2);
                }
                break;
            case R.id.txt_deal3:
                selected();
                txt_deal3.setSelected(true);
                if(f3==null){
                    f3 = new MyCarFragment(this);
                    transaction.add(R.id.fragment_container,f3);
                }else{
                    transaction.show(f3);
                }
                break;
            case R.id.txt_deal4:
                selected();
                txt_deal4.setSelected(true);
                if(f4==null){
                    f4 = new MsgFragment(this);
                    transaction.add(R.id.fragment_container,f4);
                }else{
                    transaction.show(f4);
                }
                break;
            case R.id.txt_deal5:
                if (!AppUtils.isEmpty(SPUtils.get(SPUtils.K_TOKEN,""))){
                    selected();
                    txt_deal5.setSelected(true);
                    if(f5==null){
                        f5 = new MyFragment(this);
                        transaction.add(R.id.fragment_container,f5);
                    }else{
                        transaction.show(f5);
                    }
                }else{
                    toast(this,"请先登录后使用");
                    ZzRouter.gotoActivity(this, LoginActivity.class);
                }
                break;
        }

        transaction.commit();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            toast(MainActivity.this, "再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            System.exit(0);
        }
    }

    //刷新数据
    @Override
    protected void onRestart() {
        super.onRestart();
    }

    //个人信息返回数据
    @Override
    public void getUserInfoCallBack(UserInfoEntity data) {

    }

    @Override
    public void bannerCallBack(final List<BannerEntity> data) {
        if (data.size()>0){
            listViews = new ArrayList<View>(); // 图片组
            for (int i = 0; i < data.size(); i++) {
                MyImageView imageView = new MyImageView (this);
                final int finalI = i;
                imageView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {// 设置图片点击事件
                        if (data.get(finalI).linkType==1){
                            //跳转类型 1发现文章；2活动
                            Intent intent = new Intent(MainActivity.this, FindDetailsActivity.class);
                            intent.putExtra("linkId",data.get(finalI).linkId);
                            startActivity(intent);
                        }else{
                            toast(MainActivity.this,"暂无标签");
                        }
                    }
                });
                imageView.setImageURL(data.get(i).imgUrl);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                listViews.add(imageView);
            }
        }
        bindView();
        //推荐套餐更多
        f1.setOnButtonClick(new FirstFragment.OnButtonClick() {
            @Override
            public void onClick(View view) {
                txt_deal4.performClick();//自动触发首页按钮
            }
        });
    }
}
