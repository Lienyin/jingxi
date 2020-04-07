package com.jxxc.jingxi.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jxxc.jingxi.ConfigApplication;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.ui.share.ShareActivity;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.utils.AppUtils;
import com.jxxc.jingxi.utils.SPUtils;

/**
 * 首页活动弹窗
 */
public class ShareDialog implements View.OnClickListener {

    private Context context;
    private Dialog dialog;
    private View view;
    private TextView tv_cancel,tv_open;

    public ShareDialog(Context context) {
        this(context, true);
    }

    public ShareDialog(final Context context, boolean isLuck) {
        this.context = context;
        dialog = new Dialog(context, R.style.Dialog);
        view = LayoutInflater.from(context).inflate(R.layout.share_dialog, null);
        //让dialog显示在屏幕的中间
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        //指定dialog布局的宽度
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(view, params);

        tv_cancel = view.findViewById(R.id.tv_cancel);
        tv_open = view.findViewById(R.id.tv_open);
        tv_cancel.setOnClickListener(this);
        tv_open.setOnClickListener(this);

    }

    public void showShareDialog(boolean outTouchCancel) {
        dialog.setCanceledOnTouchOutside(outTouchCancel);
        dialog.show();
    }

    public void cleanDialog() {
        dialog.cancel();
    }

    @Override
    public void onClick(View v) {
        AnimUtils.clickAnimator(view);
        switch (v.getId()) {
            case R.id.tv_cancel://关闭
                cleanDialog();
                break;
            case R.id.tv_open://详情
                if (AppUtils.isEmpty(SPUtils.get(context, SPUtils.K_TOKEN, ""))) {
                    ZzRouter.gotoActivity((Activity) context, ConfigApplication.LOGIN_PATH, ZzRouter.HOST_PLUGIN);
                    return;
                }
                ZzRouter.gotoActivity((Activity) context, ShareActivity.class);
                cleanDialog();
                break;
        }
    }

    private OnFenxiangClickListener onFenxiangClickListener;

    public void setOnFenxiangClickListener(OnFenxiangClickListener onFenxiangClickListener) {
        this.onFenxiangClickListener = onFenxiangClickListener;
    }

    public interface OnFenxiangClickListener {
        void onFenxiangClick(int shareType);
    }
}
