package com.jxxc.jingxi.ui.setmealpay;


import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jxxc.jingxi.R;
import com.jxxc.jingxi.adapter.MyPagerAdapter;
import com.jxxc.jingxi.entity.backparameter.RecommendComboInfoEntity;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.ui.setmealpay.roll.MyImgScroll;
import com.jxxc.jingxi.ui.setmealpayinfo.SetMealPayInfoActivity;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.utils.AppUtils;
import com.jxxc.jingxi.utils.MyImageView;
import com.jxxc.jingxi.utils.StatusBarUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class SetMealPayActivity extends MVPBaseActivity<SetMealPayContract.View, SetMealPayPresenter> implements SetMealPayContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.myvp)
    MyImgScroll myPager;
    @BindView(R.id.vb)
    LinearLayout ovalLayout;
    @BindView(R.id.tv_pay_set_meal_name)
    TextView tv_pay_set_meal_name;
    @BindView(R.id.tv_pay_set_meal_num)
    TextView tv_pay_set_meal_num;
    @BindView(R.id.tv_pay_set_meal_con)
    TextView tv_pay_set_meal_con;
    @BindView(R.id.tv_pay_set_meal_money)
    TextView tv_pay_set_meal_money;
    @BindView(R.id.ll_pay_set_meal)
    LinearLayout ll_pay_set_meal;
    private List<View> listViews; // 图片组
    private RecommendComboInfoEntity recommendComboInfoEntity;
    private String serviceType="";
    private String companyId="";
    @Override
    protected int layoutId() {
        return R.layout.set_meal_pay_activity;
    }

    @Override
    public void initData() {
        myPager.removeAllViews();
        tv_title.setText("套餐详情");
        recommendComboInfoEntity = (RecommendComboInfoEntity) getIntent().getSerializableExtra("recommendComboInfoEntity");
        serviceType = getIntent().getStringExtra("serviceType");
        companyId = getIntent().getStringExtra("companyId");
        if (!AppUtils.isEmpty(recommendComboInfoEntity)){
            tv_pay_set_meal_name.setText(recommendComboInfoEntity.comboName);
            tv_pay_set_meal_num.setText("已售 "+recommendComboInfoEntity.salesVolume);
            tv_pay_set_meal_con.setText(recommendComboInfoEntity.comboComment);
            tv_pay_set_meal_money.setText("￥"+new DecimalFormat("0.00").format(recommendComboInfoEntity.totalPrice));
            if (recommendComboInfoEntity.imgUrls.length > 0){
                listViews = new ArrayList<View>(); // 图片组
                for (int i = 0; i < recommendComboInfoEntity.imgUrls.length; i++) {
                    MyImageView imageView = new MyImageView (this);
                    imageView.setImageURL(recommendComboInfoEntity.imgUrls[i]);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    listViews.add(imageView);
                }
            }
        }

        //开始滚动
        myPager.start(this,listViews,4000,ovalLayout);
    }

    @OnClick({R.id.tv_back,R.id.ll_pay_set_meal})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
            case R.id.ll_pay_set_meal://我要预约下单
                Intent intent = new Intent(this, SetMealPayInfoActivity.class);
                intent.putExtra("recommendComboInfoEntity",recommendComboInfoEntity);
                intent.putExtra("serviceType",serviceType);
                intent.putExtra("companyId",companyId);
                startActivity(intent);
                finish();
                break;
            default:
        }
    }
}
