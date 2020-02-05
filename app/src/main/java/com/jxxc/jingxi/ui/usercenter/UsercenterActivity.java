package com.jxxc.jingxi.ui.usercenter;


import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.ConfigApplication;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.utils.AppUtils;
import com.jxxc.jingxi.utils.GlideImgManager;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;


/**
 * 个人中心--我的
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class UsercenterActivity extends MVPBaseActivity<UsercenterContract.View, UsercenterPresenter> implements UsercenterContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.iv_user_head)
    ImageView iv_user_head;
    private static final int REQUEST_CODE_CHOOSE = 1100;

    @Override
    protected int layoutId() {
        return R.layout.activity_user_center;
    }

    @Override
    public void initData() {
        tv_title.setText("个人资料");
        mPresenter.initImageSelecter();
    }

    //修改头像返回数据
    @Override
    public void updateInfoCallBack() {

    }

    @OnClick({R.id.tv_back,R.id.iv_user_head,R.id.ll_update_password})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
            case R.id.iv_user_head://头像
                mPresenter.gotoImageSelect(this, REQUEST_CODE_CHOOSE);
                break;
//            case R.id.ll_update_password://修改密码
//                ZzRouter.gotoActivity(this, UpdatePasswordActivity.class);
//                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            final List<String> pathList = data.getStringArrayListExtra("result");
            if (!AppUtils.isEmpty(pathList)) {
                GlideImgManager.loadRectangleImage(this, pathList.get(0), iv_user_head);
                Luban.with(this)
                        .load(pathList.get(0))
                        .ignoreBy(50)
                        .setTargetDir(ConfigApplication.CACHA_URL)
                        .setCompressListener(new OnCompressListener() {
                            @Override
                            public void onStart() {
                                //toast(IDCardActivity.this,"正在上传身份证", TastyToast.WARNING);
                                StyledDialog.buildLoading("正在上传头像").setActivity(UsercenterActivity.this).show();
                            }
                            @Override public void onSuccess(File f) {
                                mPresenter.uploadImage(f.getAbsolutePath());
                            }
                            @Override public void onError(Throwable e) {
                                mPresenter.uploadImage(pathList.get(0));
                            }
                        }).launch();    //启动压缩

            }
        }
    }
}
