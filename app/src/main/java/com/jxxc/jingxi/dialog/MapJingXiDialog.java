package com.jxxc.jingxi.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jxxc.jingxi.R;


/**
 * app更新提示
 */

public class MapJingXiDialog implements View.OnClickListener{

    private Context context;
    private Dialog dialog;
    private View view;
    private SeekBar sb_radius;
    private TextView tv_radius_min,tv_radius_hint;
    private Button btn_affirm;
    private int radiu = 0;

    public MapJingXiDialog(Context context){
        this(context,true);
    }

    public MapJingXiDialog(final Context context, boolean isLuck) {
        this.context = context;
        dialog = new Dialog(context, R.style.Dialog);
        view = LayoutInflater.from(context).inflate(R.layout.map_jing_xi_dialog, null);
        //让dialog显示在屏幕的中间
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        //指定dialog布局的宽度
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(view, params);
        sb_radius = (SeekBar) view.findViewById(R.id.sb_radius);
        tv_radius_hint = (TextView) view.findViewById(R.id.tv_radius_hint);
        tv_radius_min = (TextView) view.findViewById(R.id.tv_radius_min);
        btn_affirm = (Button) view.findViewById(R.id.btn_affirm);
        btn_affirm.setOnClickListener(this);
        sb_radius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //Log.v("拖动过程中的值：", String.valueOf(progress) + ", " + String.valueOf(fromUser));
                tv_radius_min.setText(String.valueOf(progress)+".00km");
                tv_radius_hint.setText("菁喜服务半径"+String.valueOf(progress)+".00km");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                tv_radius_min.setText(String.valueOf(sb_radius.getProgress())+".00km");
                tv_radius_hint.setText("菁喜服务半径"+String.valueOf(sb_radius.getProgress())+".00km");
                radiu = sb_radius.getProgress();
            }
        });
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
            case R.id.btn_affirm://确认
                onFenxiangClickListener.onFenxiangClick(radiu);
                cleanDialog();
                break;
        }
    }
    private OnFenxiangClickListener onFenxiangClickListener;

    public void setOnFenxiangClickListener(OnFenxiangClickListener onFenxiangClickListener) {
        this.onFenxiangClickListener = onFenxiangClickListener;
    }

    public interface OnFenxiangClickListener{
        void onFenxiangClick(int radius);
    }
}

