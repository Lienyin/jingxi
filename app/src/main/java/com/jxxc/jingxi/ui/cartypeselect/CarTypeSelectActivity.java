package com.jxxc.jingxi.ui.cartypeselect;


import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.hss01248.dialog.StyledDialog;
import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.BandAndTypeEntity;
import com.jxxc.jingxi.entity.requestparameter.ExitLogin;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.ui.cartypechoose.CarTypeChooseActivity;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.utils.StatusBarUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class CarTypeSelectActivity extends MVPBaseActivity<CarTypeSelectContract.View, CarTypeSelectPresenter> implements CarTypeSelectContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.gv_car_data)
    GridView gv_car_data;
    private RecyclerView rvMain;
    private IndexWord iwMain;
    private TextView tvMain;
    private Handler handler = new Handler();
    /**
     * 联系人的集合
     */
    private ArrayList<Person> persons;
    private LinkAdapter linkadapter;
    private int childCount;
    private LinearLayoutManager linearmanger;
    private int childCount1;
    private ReMenCarAdapter reMenCarAdapter;
    private List<BandAndTypeEntity.CarBrand> carBrandList = new ArrayList<>();//热门品牌

    @Override
    protected int layoutId() {
        return R.layout.car_type_select_activity;
    }

    @Override
    public void initData() {
        StatusBarUtil.setStatusBarMode(this, false, R.color.white);
        tv_title.setText("品牌选择");
        StyledDialog.buildLoading("数据加载中").setActivity(this).show();
        mPresenter.getBandAndType();
        EventBus.getDefault().register(this);

        reMenCarAdapter = new ReMenCarAdapter(this);
        reMenCarAdapter.setData(carBrandList);
        gv_car_data.setAdapter(reMenCarAdapter);
        gv_car_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(CarTypeSelectActivity.this, CarTypeChooseActivity.class);
                intent.putExtra("brandId",carBrandList.get(i).brandId);
                intent.putExtra("brandName",carBrandList.get(i).brandName);
                startActivity(intent);
            }
        });
    }

    private void initview() {
        rvMain = ((RecyclerView) findViewById(R.id.rv_main));
        rvMain.setLayoutManager(linearmanger = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvMain.setAdapter(linkadapter = new LinkAdapter(this, persons));
        iwMain = ((IndexWord) findViewById(R.id.iw_main));
        tvMain = ((TextView) findViewById(R.id.tv_main));
        setTvWord();
    }

    private void setTvWord() {
        iwMain.setIndexPressWord(new IndexWord.IndexPressWord() {
            @Override
            public void setIndexPressWord(String word) {
                getWord(word);//让recycleview跳动到此字幕的位置
                tvMain.setVisibility(View.VISIBLE);
                tvMain.setText(word);
                /**
                 * 延缓两秒钟让textview消失
                 */
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvMain.setVisibility(View.GONE);
                    }
                }, 1000);
            }
        });
    }

    private void getWord(String word) {
        for (int i = 0; i < persons.size(); i++) {
            String substring = persons.get(i).getPinyin().substring(0, 1);
            if (substring.equals(word) && persons.size() >= i) {
                //如果person拼音的首位与word相同，则实现效果,并退出循环
                View childAt = rvMain.getChildAt(i);
                MoveToPosition(linearmanger, rvMain, i);
                break;
            }
        }
    }

    /**
     * 此方法是让recycleview滑动到指定位置，并且是让其到顶部
     *
     * @param manager
     * @param mRecyclerView
     * @param n
     */
    public void MoveToPosition(LinearLayoutManager manager, RecyclerView mRecyclerView, int n) {
        int firstItem = manager.findFirstVisibleItemPosition();
        int lastItem = manager.findLastVisibleItemPosition();
        if (n <= firstItem) {
            mRecyclerView.scrollToPosition(n);
        } else if (n <= lastItem) {
            int top = mRecyclerView.getChildAt(n - firstItem).getTop();
            mRecyclerView.scrollBy(0, top);
        } else {
            mRecyclerView.scrollToPosition(n);
        }
    }

    @OnClick({R.id.tv_back})
    public void onViewClicked(View view) {
        AnimUtils.clickAnimator(view);
        switch (view.getId()) {
            case R.id.tv_back://返回
                finish();
                break;
            default:
        }
    }

    //获取所有车品牌车型返回数据
    @Override
    public void getBandAndTypeCallBack(BandAndTypeEntity data) {
        persons = new ArrayList<>();
        for (int i=0;i<data.brand.size();i++){
            persons.add(new Person(data.brand.get(i).brandIcon,data.brand.get(i).brandName,data.brand.get(i).brandId));

            if (data.brand.get(i).isRecommend==1){
                carBrandList.add(data.brand.get(i));
            }
        }
        //排序
        Collections.sort(persons, new Comparator<Person>() {
            @Override
            public int compare(Person lhs, Person rhs) {
                return lhs.getPinyin().compareTo(rhs.getPinyin());
            }
        });
        initview();//初始化视图

        reMenCarAdapter.setData(carBrandList);
        reMenCarAdapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ExitLogin exitLogin) {
        finish();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
