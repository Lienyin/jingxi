package com.jxxc.jingxi.mvp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.ui.main.MainActivity;
import com.jxxc.jingxi.ui.mapjingsi.MapJingSiActivity;
import com.jxxc.jingxi.utils.StatusBarUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.lang.reflect.ParameterizedType;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public abstract class MVPBaseActivity<V extends BaseView,T extends BasePresenterImpl<V>> extends AppCompatActivity implements BaseView{
    public T mPresenter;
    public boolean isCheckToken = true; //是否检验token
    Unbinder unbinder;
    private static Toast mToast = null;//全局唯一的Toast
    private LocationManager lm;//【位置管理】
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter= getInstance(this,1);
        mPresenter.attachView((V) this);
        //MapJingSiActivity.setMapCustomFile(this, "custom_map_config.json");
        if (layoutId() != 0) {
            setContentView(layoutId());
        }
        unbinder = ButterKnife.bind(this);//初始化@BindView
        initView();
        initData();
        initListener();
        permission();
    }
    protected void initListener() {};
    protected abstract int layoutId();

    public void initView() { }
    public abstract void initData();
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter!=null)
        mPresenter.detachView();
    }

    @Override
    public Context getContext(){
        return this;
    }

    public  <T> T getInstance(Object o, int i) {
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
        }
        return null;
    }

    public static void toast(Context context, String message) {
        if (mToast == null) {
            mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(message);
        }
        mToast.show();
    }

    /**
     * 手动打开定位权限（和requestLocation方法有冲突，二选一）
     */
    public void permission() {
        new RxPermissions(this).request(Manifest.permission.MODIFY_AUDIO_SETTINGS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {
                    }
                });

        lm = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);
        boolean ok = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        //定位权限(ACCESS_FINE_LOCATION)
        if (ok) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                /**
                 * 动态获取定位权限
                 * @return
                 */
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//如果 API level 是大于等于 23(Android 6.0) 时
                    //判断是否具有权限
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        //判断
                        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                                Manifest.permission.ACCESS_COARSE_LOCATION)) {
                            Toast.makeText(MVPBaseActivity.this,"需要打开位置权限",Toast.LENGTH_SHORT).show();
                        }
                        //请求权限
                        ActivityCompat.requestPermissions(this,
                                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                1);
                    }
                }
                // 没有权限，申请权限。
//                StyledDialog.buildIosAlert("权限提示", "应用定位权限未开启，会影响您的使用哦！请在手机的“设置-应用管理-菁喜洗车-权限管理-定位服务”应用列表中，允许菁喜洗车访问位置信息；然后结束app重新打开。", new MyDialogListener() {
//                    @Override
//                    public void onFirst() {
//                        toSetting();
//                    }
//
//                    @Override
//                    public void onSecond() {
//                    }
//                }).setBtnText("去开启", "无所谓").setActivity(this).show();
            } else {
                // 有权限了，去放肆吧。
                //toast("有权限了，去放肆吧");
            }
        } else {
            //统检测到手机未开启GPS定位服务
            StyledDialog.buildIosAlert("权限提示", "应用检测到手机未开启GPS定位服务；打开定位后请结束菁喜洗车APP，然后再重新打开菁喜洗车APP。", new MyDialogListener() {
                @Override
                public void onFirst() {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
                @Override
                public void onSecond() {}
            }).setBtnText("去开启","无所谓").setActivity(this).show();
        }
    }
}
