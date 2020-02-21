package com.jxxc.jingxi.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.AddressSeek;
import com.jxxc.jingxi.ui.addressdetails.AddressSeekDialogAdapter;

import java.util.List;

/**
 * 所属公司（单位）
 */
public class AddressSeekDialog implements View.OnClickListener{

    private Context context;
    private Dialog dialog;
    private View view;
    private ListView lv_address;
    private AddressSeekDialogAdapter adapter;
    private List<AddressSeek> list;

    public AddressSeekDialog(Context context){
        this(context,true);
    }

    public AddressSeekDialog(final Context context, boolean isLuck) {
        this.context = context;
        dialog = new Dialog(context, R.style.Dialog);
        view = LayoutInflater.from(context).inflate(R.layout.address_seek_dialog, null);
        //让dialog显示在屏幕的中间
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        //指定dialog布局的宽度
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(view, params);
        lv_address = (ListView) view.findViewById(R.id.lv_address);

        lv_address.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onFenxiangClickListener.onFenxiangClick(list.get(position).name,list.get(position).latitude,list.get(position).longitude);
            }
        });
    }

    public void showShareDialog(boolean outTouchCancel,List<AddressSeek> listData) {
        list = listData;
        adapter = new AddressSeekDialogAdapter(context,list);
        lv_address.setAdapter(adapter);

        dialog.setCanceledOnTouchOutside(outTouchCancel);
        dialog.show();
    }

    public void cleanDialog(){
        dialog.cancel();
    }

    @Override
    public void onClick(View v) {
    }
    private OnFenxiangClickListener onFenxiangClickListener;

    public void setOnFenxiangClickListener(OnFenxiangClickListener onFenxiangClickListener) {
        this.onFenxiangClickListener = onFenxiangClickListener;
    }

    public interface OnFenxiangClickListener{
        void onFenxiangClick(String siteName, double lat, double lng);
    }
}
