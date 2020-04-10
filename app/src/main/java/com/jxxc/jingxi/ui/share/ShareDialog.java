package com.jxxc.jingxi.ui.share;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jxxc.jingxi.R;

/**
 * Created by 31373 on 2018/5/28.
 * 邀请用户分享
 */

public class ShareDialog implements View.OnClickListener{

    private Context context;
    private Dialog dialog;
    private View view;
    private ImageView ex_share_wx;
    private ImageView ex_share_pyq;
    private ImageView ex_share_qq;
    private ImageView ex_share_z;
    private ImageView iv_copy_link;
    private ImageView ex_share_alipay;
    private TextView tv_share_cancel;

    public ShareDialog(Context context){
        this(context,true);
    }

    public ShareDialog(final Context context, boolean isLuck) {
        this.context = context;
//        this.ShareURL = ShareURL;
        dialog = new Dialog(context, R.style.Dialog);
        view = LayoutInflater.from(context).inflate(R.layout.layout_share_dialog, null);
        //让dialog显示在屏幕的下方
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        //指定dialog布局的宽度
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(view, params);

        ex_share_wx = (ImageView) view.findViewById(R.id.ex_share_wx);
        ex_share_pyq = (ImageView) view.findViewById(R.id.ex_share_pyq);
        ex_share_qq = (ImageView) view.findViewById(R.id.ex_share_qq);
        ex_share_z = (ImageView) view.findViewById(R.id.ex_share_z);
        iv_copy_link = (ImageView) view.findViewById(R.id.iv_copy_link);
        ex_share_alipay = (ImageView) view.findViewById(R.id.ex_share_alipay);
        tv_share_cancel = (TextView) view.findViewById(R.id.tv_share_cancel);
        ex_share_wx.setOnClickListener(this);
        ex_share_pyq.setOnClickListener(this);
        ex_share_qq.setOnClickListener(this);
        ex_share_z.setOnClickListener(this);
        iv_copy_link.setOnClickListener(this);
        ex_share_alipay.setOnClickListener(this);
        tv_share_cancel.setOnClickListener(this);

    }

    public void showShareDialog(boolean outTouchCancel) {
        dialog.setCanceledOnTouchOutside(outTouchCancel);
        dialog.show();
    }

    public void cleanDialog(){
        dialog.cancel();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ex_share_wx:
                cleanDialog();
                onFenxiangClickListener.onFenxiangClick(1);
                break;
            case R.id.ex_share_pyq:
                cleanDialog();
                onFenxiangClickListener.onFenxiangClick(2);
                break;
            case R.id.ex_share_qq:
                cleanDialog();
                onFenxiangClickListener.onFenxiangClick(3);
                break;
            case R.id.ex_share_z:
                cleanDialog();
                onFenxiangClickListener.onFenxiangClick(4);
                break;
            case R.id.iv_copy_link://复制链接
                cleanDialog();
                onFenxiangClickListener.onFenxiangClick(6);
                break;
            case R.id.ex_share_alipay://分享到支付宝
                cleanDialog();
                onFenxiangClickListener.onFenxiangClick(5);
                break;
            case R.id.tv_share_cancel:
                cleanDialog();
                break;
        }
    }
    private OnFenxiangClickListener onFenxiangClickListener;

    public void setOnFenxiangClickListener(OnFenxiangClickListener onFenxiangClickListener) {
        this.onFenxiangClickListener = onFenxiangClickListener;
    }

    public interface OnFenxiangClickListener{
        void onFenxiangClick(int shareType);
    }
}

