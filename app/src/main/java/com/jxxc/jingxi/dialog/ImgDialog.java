package com.jxxc.jingxi.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jxxc.jingxi.R;
import com.jxxc.jingxi.ui.submitorder.SubmitOrderActivity;
import com.jxxc.jingxi.utils.GlideImgManager;


/**
 * 取消订单
 */

public class ImgDialog implements View.OnClickListener{

    private Context context;
    private Dialog dialog;
    private View view;
    private ImageView iv_lmg_icon;

    public ImgDialog(Context context){
        this(context,true);
    }

    public ImgDialog(final Context context, boolean isLuck) {
        this.context = context;
        dialog = new Dialog(context, R.style.Dialog);
        view = LayoutInflater.from(context).inflate(R.layout.img_dialog, null);
        //让dialog显示在屏幕的中间
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        //指定dialog布局的宽度
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(view, params);

        iv_lmg_icon = view.findViewById(R.id.iv_lmg_icon);

    }

    public void showShareDialog(boolean outTouchCancel,String url) {
        GlideImgManager.loadImage(context, url, iv_lmg_icon);
        dialog.setCanceledOnTouchOutside(outTouchCancel);
        dialog.show();
    }

    public void cleanDialog(){
        dialog.cancel();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.iv_order_cancel://取消
//                cleanDialog();
//                break;
        }
    }
}

