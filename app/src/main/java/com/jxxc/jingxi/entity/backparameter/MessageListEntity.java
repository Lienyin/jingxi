package com.jxxc.jingxi.entity.backparameter;

import java.io.Serializable;

public class MessageListEntity implements Serializable {
    public String messageTopic;
    public String isRead;
    public String messageId;
    public String content;
    public String sendTime;
    public int orderStatus;
}
