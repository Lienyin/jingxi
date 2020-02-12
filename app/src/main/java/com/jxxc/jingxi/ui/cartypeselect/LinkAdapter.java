package com.jxxc.jingxi.ui.cartypeselect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jxxc.jingxi.R;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.ui.cartypechoose.CarTypeChooseActivity;
import com.jxxc.jingxi.utils.GlideImgManager;

import java.util.ArrayList;

/**
 * Created by ruedy on 2017/3/13.
 */
public class LinkAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;//上下文
    private LayoutInflater inflater;
    private ArrayList<Person> persons;//人类
    private int index = -1;


    public LinkAdapter(Context mContext, ArrayList<Person> persons) {
        this.mContext = mContext;
        this.persons = persons;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LinkHolder(inflater.inflate(R.layout.linkview, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Person person = persons.get(position);
        String substring = person.getPinyin().substring(0, 1);//这里字母自行拓展
        LinkHolder holder1 = (LinkHolder) holder;

        if (position == 0) {
            holder1.tvTop.setVisibility(View.VISIBLE);
        } else {
            String pinYinq = persons.get(position - 1).getPinyin().substring(0, 1);//这里字母自行拓展
            if (pinYinq.equals(substring)) {
                holder1.tvTop.setVisibility(View.GONE);
            } else {
                holder1.tvTop.setVisibility(View.VISIBLE);
            }

        }

        holder1.tvTop.setText(substring);
        holder1.tvName.setText(person.getName());
        GlideImgManager.loadCircleImage(mContext, person.getCarIcon(), holder1.iv_car_icon);

        holder1.ll_item_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent((Activity) mContext, CarTypeChooseActivity.class);
                intent.putExtra("brandId",person.getId());
                intent.putExtra("brandName",person.getName());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return persons == null ? 0 : persons.size();
    }

    public class LinkHolder extends RecyclerView.ViewHolder {
        private final TextView tvTop;
        private final TextView tvName;
        private final ImageView iv_car_icon;
        private final LinearLayout ll_item_car;

        public LinkHolder(View itemView) {
            super(itemView);
            tvTop = ((TextView) itemView.findViewById(R.id.tv_top));
            tvName = ((TextView) itemView.findViewById(R.id.tv_name));
            iv_car_icon = ((ImageView) itemView.findViewById(R.id.iv_car_icon));
            ll_item_car = ((LinearLayout) itemView.findViewById(R.id.ll_item_car));
        }
    }
}
