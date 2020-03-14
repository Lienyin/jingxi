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
import android.widget.TextView;
import android.widget.Toast;

import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.MyCoupon;
import com.jxxc.jingxi.adapter.CouponAdapter;
import com.jxxc.jingxi.utils.AnimUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * 优惠券弹窗
 */
public class DiscountCouponDialog implements View.OnClickListener {

    private Context context;
    private Dialog dialog;
    private View view;
    private TextView tv_quren,tv_cancel;
    private ListView lv_discount_coupon;
    private CouponAdapter adapter;
    private List<MyCoupon> myCouponList = new ArrayList<>();
    private MyCoupon coupon = new MyCoupon();

    public DiscountCouponDialog(Context context) {
        this(context, true);
    }

    public DiscountCouponDialog(final Context context, boolean isLuck) {
        this.context = context;
        dialog = new Dialog(context, R.style.Dialog);
        view = LayoutInflater.from(context).inflate(R.layout.discount_coupon_dialog, null);
        //让dialog显示在屏幕的中间
        Window window = dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        //指定dialog布局的宽度
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(view, params);

        tv_cancel = view.findViewById(R.id.tv_cancel);
        tv_quren = view.findViewById(R.id.tv_quren);
        lv_discount_coupon = view.findViewById(R.id.lv_discount_coupon);
        tv_quren.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);

    }

    public void showShareDialog(boolean outTouchCancel, final List<MyCoupon> list,double orderMoney) {

        for (int i=0;i<list.size();i++){
            if ("不使用优惠券".equals(list.get(i).getCounponName())){
                list.remove(i);
            }
        }
        MyCoupon myCoupon = new MyCoupon();
        myCoupon.setCounponName("不使用优惠券");
        myCoupon.setForbidden(false);
        list.add(myCoupon);
        myCouponList = list;
        adapter = new CouponAdapter(context);
        adapter.setData(list);
        adapter.setOrderMoney(orderMoney);
        lv_discount_coupon.setAdapter(adapter);
        lv_discount_coupon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //选择优惠券
                if (list.get(i).isForbidden()==false){
                    adapter.setSelectPosition(i);
                    adapter.notifyDataSetChanged();
                    coupon = myCouponList.get(i);
                }else{
                    Toast.makeText(context,"优惠券不可用",Toast.LENGTH_SHORT).show();
                }
            }
        });
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
            case R.id.tv_quren://确定
                onFenxiangClickListener.onFenxiangClick(coupon);
                cleanDialog();
                break;
        }
    }

    private OnFenxiangClickListener onFenxiangClickListener;

    public void setOnFenxiangClickListener(OnFenxiangClickListener onFenxiangClickListener) {
        this.onFenxiangClickListener = onFenxiangClickListener;
    }

    public interface OnFenxiangClickListener {
        void onFenxiangClick(MyCoupon coupon);
    }
}
