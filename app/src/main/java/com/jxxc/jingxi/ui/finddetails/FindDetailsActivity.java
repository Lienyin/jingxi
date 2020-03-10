package com.jxxc.jingxi.ui.finddetails;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.AppreciateEntity;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.utils.AppUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 * 邮箱 784787081@qq.com
 */

public class FindDetailsActivity extends MVPBaseActivity<FindDetailsContract.View, FindDetailsPresenter> implements FindDetailsContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.find_details_context)
    TextView find_details_context;
    @BindView(R.id.tv_num_data)
    TextView tv_num_data;
    @BindView(R.id.tv_type)
    TextView tv_type;
    private String findId = "";
    private String findContent = "";
    private String appreciateNum = "";
    private String type = "";
    private LevelListDrawable mDrawable = new LevelListDrawable();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1123) {
                Bitmap bitmap = (Bitmap)msg.obj;
                if (!AppUtils.isEmpty(bitmap)){
                    BitmapDrawable drawable = new BitmapDrawable(null, bitmap);
                    mDrawable.addLevel(1, 1, drawable);
                    mDrawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                    mDrawable.setLevel(1);
                }

                CharSequence charSequence = find_details_context.getText();
                find_details_context.setText(charSequence);
                find_details_context.invalidate();
            }
        }
    };

    @Override
    protected int layoutId() {
        return R.layout.find_details_activity;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void initData() {
        tv_title.setText("详情");
        findId = getIntent().getStringExtra("findId");
        findContent = getIntent().getStringExtra("findContent");
        appreciateNum = getIntent().getStringExtra("appreciateNum");
        type = getIntent().getStringExtra("type");
        tv_num_data.setText(appreciateNum);//点赞数
        find_details_context.setText(Html.fromHtml(findContent, Html.FROM_HTML_MODE_COMPACT, new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(final String source) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        mDrawable.addLevel(0, 0, getResources().getDrawable(R.mipmap.logo_108));
                        mDrawable.setBounds(0, 0, 200, 200);
                        Bitmap bitmap;
                        try {
                            bitmap = BitmapFactory.decodeStream(new URL(source).openStream());
                            Message msg = handler.obtainMessage();
                            msg.what = 1123;
                            msg.obj = bitmap;
                            handler.sendMessage(msg);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                return mDrawable;
            }
        }, null));
        if ("1".equals(type)){
            tv_type.setText("文章标签：经验/观点");
        }else if ("2".equals(type)){
            tv_type.setText("文章标签：爱车养护");
        }else if ("3".equals(type)){
            tv_type.setText("文章标签：教程");
        }else{
            tv_type.setText("文章标签：推荐");
        }
    }

    @OnClick({R.id.tv_back,R.id.tv_num_data})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
            case R.id.tv_num_data://点赞
                mPresenter.appreciate(findId);
                break;
            default:
        }
    }

    //点赞返回数据
    @Override
    public void appreciateCallBack(AppreciateEntity data) {
        tv_num_data.setText(data.appreciateNum);
    }
}
