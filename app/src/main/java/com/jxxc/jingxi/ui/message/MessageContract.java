package com.jxxc.jingxi.ui.message;

import android.content.Context;

import com.jxxc.jingxi.entity.backparameter.MessageListEntity;
import com.jxxc.jingxi.mvp.BasePresenter;
import com.jxxc.jingxi.mvp.BaseView;

import java.util.List;

/**
 * MVPPlugin
 *  邮箱 784787081@qq.com
 */

public class MessageContract {
    interface View extends BaseView {
        void messageList(List<MessageListEntity> data);
        void messageListMore(List<MessageListEntity> data);
    }

    interface  Presenter extends BasePresenter<View> {
        void messageList(int pageNum,int pageSize);
        void messageListMore(int pageNum,int pageSize);
    }
}
