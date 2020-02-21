package com.jxxc.jingxi.dialog;

import android.app.Activity;
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
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.ui.submitorder.SubmitOrderActivity;


/**
 * 取消订单
 */

public class XiaOrderDialog implements View.OnClickListener{

    private Context context;
    private Dialog dialog;
    private View view;
    private ImageView iv_order_cancel;
    private ImageView iv_car_01,iv_car_02,iv_car_03,iv_car_04,iv_car_05,iv_car_06,iv_car_07,iv_car_08;
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
        iv_car_01 = (ImageView) view.findViewById(R.id.iv_car_01);
        iv_car_02 = (ImageView) view.findViewById(R.id.iv_car_02);
        iv_car_03 = (ImageView) view.findViewById(R.id.iv_car_03);
        iv_car_04 = (ImageView) view.findViewById(R.id.iv_car_04);
        iv_car_05 = (ImageView) view.findViewById(R.id.iv_car_05);
        iv_car_06 = (ImageView) view.findViewById(R.id.iv_car_06);
        iv_car_07 = (ImageView) view.findViewById(R.id.iv_car_07);
        iv_car_08 = (ImageView) view.findViewById(R.id.iv_car_08);
        btn_xia_order = (Button) view.findViewById(R.id.btn_xia_order);
        iv_order_cancel.setOnClickListener(this);
        btn_xia_order.setOnClickListener(this);

    }

    public void showShareDialog(boolean outTouchCancel,int num1,int num2,int num3) {
        if (num1==6){
            iv_car_06.setVisibility(View.VISIBLE);
        }else {
            iv_car_06.setVisibility(View.GONE);
        }
        if (num2==7){
            iv_car_07.setVisibility(View.VISIBLE);
        }else {
            iv_car_07.setVisibility(View.GONE);
        }
        if (num3==8){
            iv_car_08.setVisibility(View.VISIBLE);
        }else{
            iv_car_08.setVisibility(View.GONE);
        }
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
                ZzRouter.gotoActivity((Activity) context, SubmitOrderActivity.class);
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

