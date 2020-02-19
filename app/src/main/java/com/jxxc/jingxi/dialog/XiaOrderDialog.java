package com.jxxc.jingxi.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jxxc.jingxi.R;


/**
 * 取消订单
 */

public class XiaOrderDialog implements View.OnClickListener{

    private Context context;
    private Dialog dialog;
    private View view;
    private ImageView iv_order_cancel;
    private Button btn_xia_order;

    public XiaOrderDialog(Context context){
        this(context,true);
    }

    public XiaOrderDialog(final Context context, boolean isLuck) {
        this.context = context;
        dialog = new Dialog(context, R.style.Dialog);
        view = LayoutInflater.from(context).inflate(R.layout.xia_order_dialog, null);
        //让dialog显示在屏幕的中间
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        //指定dialog布局的宽度
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(view, params);

        iv_order_cancel = (ImageView) view.findViewById(R.id.iv_order_cancel);
        btn_xia_order = (Button) view.findViewById(R.id.btn_xia_order);
        iv_order_cancel.setOnClickListener(this);
        btn_xia_order.setOnClickListener(this);

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
            case R.id.iv_order_cancel://取消
                cleanDialog();
                break;
            case R.id.btn_xia_order://确认
                onFenxiangClickListener.onFenxiangClick();
                cleanDialog();
                break;
        }
    }
    private OnFenxiangClickListener onFenxiangClickListener;

    public void setOnFenxiangClickListener(OnFenxiangClickListener onFenxiangClickListener) {
        this.onFenxiangClickListener = onFenxiangClickListener;
    }

    public interface OnFenxiangClickListener{
        void onFenxiangClick();
    }
}

