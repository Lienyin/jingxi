package com.jxxc.jingxi.ui.main;


import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;


import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.UserInfoEntity;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.ui.main.firstfragment.FirstFragment;
import com.jxxc.jingxi.ui.main.msg.MsgFragment;
import com.jxxc.jingxi.ui.main.my.MyFragment;
import com.jxxc.jingxi.ui.main.myCarfragment.MyCarFragment;
import com.jxxc.jingxi.ui.main.secondfragment.SecondFragment;


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
    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        bindView();
        mPresenter.queryAppVersion("3");//查询版本
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
                    f1 = new FirstFragment(this);
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
                selected();
                txt_deal5.setSelected(true);
                if(f5==null){
                    f5 = new MyFragment(this);
                    transaction.add(R.id.fragment_container,f5);
                }else{
                    transaction.show(f5);
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
}
