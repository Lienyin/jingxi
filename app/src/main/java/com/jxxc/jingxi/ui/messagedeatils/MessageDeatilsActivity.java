package com.jxxc.jingxi.ui.messagedeatils;


import android.view.View;
import android.widget.TextView;

import com.jxxc.jingxi.R;
import com.jxxc.jingxi.entity.backparameter.MessageListEntity;
import com.jxxc.jingxi.http.ZzRouter;
import com.jxxc.jingxi.mvp.MVPBaseActivity;
import com.jxxc.jingxi.utils.AnimUtils;
import com.jxxc.jingxi.utils.StatusBarUtil;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MessageDeatilsActivity extends MVPBaseActivity<MessageDeatilsContract.View, MessageDeatilsPresenter> implements MessageDeatilsContract.View {

    @BindView(R.id.tv_back)
    TextView tv_back;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_message_content)
    TextView tv_message_content;
    private MessageListEntity messageListEntity;
    @Override
    protected int layoutId() {
        return R.layout.message_deatils_activity;
    }

    @Override
    public void initData() {
        StatusBarUtil.setStatusBarMode(this, false, R.color.white);
        tv_title.setText("消息详情");
        messageListEntity = ZzRouter.getIntentData(this,MessageListEntity.class);
        tv_message_content.setText(messageListEntity.content);
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
}
