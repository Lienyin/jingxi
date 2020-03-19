package com.jxxc.jingxi.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.AppointmentListEntity;
import com.jxxc.jingxi.ui.shopdetails.ShopDetailsActivity;
import com.jxxc.jingxi.ui.shopdetails.TimeAdapter;
import com.jxxc.jingxi.ui.shopdetails.WeekOfAdapter;
import com.jxxc.jingxi.utils.GlideImgManager;
import com.jxxc.jingxi.utils.HorizontalListView;
import com.jxxc.jingxi.utils.ListViewForScrollView;
import com.jxxc.jingxi.utils.MyGridView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * 取消订单
 */

public class TimeDialog implements View.OnClickListener{

    private Context context;
    private Dialog dialog;
    private View view;
    private HorizontalListView gv_weekOf_date;
    private MyGridView gv_time_data;
    private TimeAdapter timeAdapter;
    private WeekOfAdapter weekOfAdapter;
    private List<AppointmentListEntity> appointmentListEntityList = new ArrayList<>();
    private String dateStr ="";

    public TimeDialog(Context context){
        this(context,true);
    }

    public TimeDialog(final Context context, boolean isLuck) {
        this.context = context;
        dialog = new Dialog(context, R.style.Dialog);
        view = LayoutInflater.from(context).inflate(R.layout.time_dialog, null);
        //让dialog显示在屏幕的中间
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        //指定dialog布局的宽度
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setContentView(view, params);
        gv_weekOf_date = view.findViewById(R.id.gv_weekOf_date);
        gv_time_data = view.findViewById(R.id.gv_time_data);

        //日期设置
        weekOfAdapter = new WeekOfAdapter(context);
        weekOfAdapter.setData(test(30));
        gv_weekOf_date.setAdapter(weekOfAdapter);

        //获取当前日期
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        String queryDate = formatter.format(date);//今天日期
        dateStr = queryDate;//默认日期

        //获取周几
        gv_weekOf_date.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                weekOfAdapter.setSelectPosition(position);
                Calendar date = Calendar.getInstance();
                String year = String.valueOf(date.get(Calendar.YEAR));
                dateStr = year+"-"+test(30).get(position).toString().substring(0,5);
                onFenxiangClickListener.onFenxiangClick(dateStr,"","",0);
            }
        });

        //预约时间段
        gv_time_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (appointmentListEntityList.get(position).isForbidden()){
                    Toast.makeText(context,"时间已过",Toast.LENGTH_SHORT).show();
                }else{
                    timeAdapter.setSelectPosition(position);
                    String timeStr = appointmentListEntityList.get(position).title;//时间段
                    String start = dateStr+" "+timeStr.substring(0,5);
                    String end = dateStr+" "+timeStr.substring(6,11);
                    onFenxiangClickListener.onFenxiangClick("",start,end,1);
                }
            }
        });
    }

    public void showShareDialog(boolean outTouchCancel) {
        dialog.setCanceledOnTouchOutside(outTouchCancel);
        dialog.show();
    }

    //刷新时间段适配器
    public void updateTimeAdapter(List<AppointmentListEntity> data,int serveType){
        appointmentListEntityList = data;
        timeAdapter = new TimeAdapter(context,serveType);
        timeAdapter.setData(data);
        gv_time_data.setAdapter(timeAdapter);
        timeAdapter.notifyDataSetChanged();
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

    private OnFenxiangClickListener onFenxiangClickListener;

    public void setOnFenxiangClickListener(OnFenxiangClickListener onFenxiangClickListener) {
        this.onFenxiangClickListener = onFenxiangClickListener;
    }

    public interface OnFenxiangClickListener {
        void onFenxiangClick(String time,String startTime,String endTime,int type);
    }

    /**
     * 获取过去或者未来 任意天内的日期数组
     * @param intervals      intervals天内
     * @return              日期数组
     */
    public static ArrayList<String> test(int intervals ) {
        ArrayList<String> fetureDaysList = new ArrayList<>();
        for (int i = 0; i < intervals; i++) {
            fetureDaysList.add(getFetureDate(i));
        }
        return fetureDaysList;
    }

    /**
     * 获取未来 第 past 天的日期
     * @param past
     * @return
     */
    public static String getFetureDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("MM-dd");
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        Log.e(null, result);
        return result+getWeekOfDate(today);
    }
    /**
          * 获取当前日期是星期几<br>
          * @param dt
          * @return 当前日期是星期几
          */
    public static String getWeekOfDate(Date dt) {
        String[] weekDays = {"\n周日", "\n周一", "\n周二", "\n周三", "\n周四", "\n周五", "\n周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }
}

