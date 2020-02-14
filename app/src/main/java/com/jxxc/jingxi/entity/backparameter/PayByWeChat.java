package com.jxxc.jingxi.entity.backparameter;

import java.io.Serializable;

/**
 * @authorfeisher on 2017/11/28.15:23
 * email:458079442@qq.com
 */

public class PayByWeChat implements Serializable {
    public String packageX;
    public String appid;
    public String sign;
    public String partnerid;
    public String prepayid;
    public String noncestr;
    public String timestamp;
    public String orderId;
}
