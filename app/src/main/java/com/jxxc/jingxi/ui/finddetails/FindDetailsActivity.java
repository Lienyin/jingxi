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
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.utils.AnimUtils;

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
    private String findId = "";
    private LevelListDrawable mDrawable = new LevelListDrawable();
    private String picName = "networkPic.jpg";
    private String HTML_TEXT = "<p>车红是非多！本宝宝众泰自打红了以后，就自行凝聚了防护罩，对四周的吐槽声都一笑置之。</p >" +
            "<p> <img src='http://106.54.252.151:8120/profile/upload/2020/02/24/99f7771866300d7e703972e97de03e10.jpg' data-filename='/profile/upload/2020/02/24/99f7771866300d7e703972e97de03e10.jpg' style='width:1067px;'></p >" +
            "<p>前几天还有人特地跑过来拍砖：“众泰，你丫是娜扎吗，招宋体质啊，那么多人黑你骂你，你是只会抄袭的心机boy吗？”<br></p >";
//    public final static String HTML_TEXT =
//            "<p><font size=\"3\" color=\"red\">设置了字号和颜色</font></p>" +
//                    "<b><font size=\"5\" color=\"blue\">设置字体加粗 蓝色 5号</font></font></b></br>" +
//                    "<h1>这个是H1标签</h1></br>" +
//                    "<p>这里显示图片：</p><img src=\"https://img0.pconline.com.cn/pconline/1808/06/11566885_13b_thumb.jpg\"";

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1123) {
                Bitmap bitmap = (Bitmap)msg.obj;
                BitmapDrawable drawable = new BitmapDrawable(null, bitmap);
                mDrawable.addLevel(1, 1, drawable);
                mDrawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                mDrawable.setLevel(1);

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
        findId = ZzRouter.getIntentData(this, String.class);
        //find_details_context.setText(Html.fromHtml(HTML_TEXT));
//        mImageGetter = new NetworkImageGetter();
//        find_details_context.setText(Html.fromHtml(htmlThree, mImageGetter, null));
        find_details_context.setText(Html.fromHtml(HTML_TEXT, Html.FROM_HTML_MODE_COMPACT, new Html.ImageGetter() {
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
