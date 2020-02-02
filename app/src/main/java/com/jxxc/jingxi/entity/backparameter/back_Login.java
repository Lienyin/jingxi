package com.jxxc.jingxi.entity.backparameter;

import java.io.Serializable;

/**
 * @authorfeisher on 2017/11/20.15:05
 * email:458079442@qq.com
 */

public class back_Login implements Serializable{
    //{"code":0,"message":null,"data":{"customerId":1,"token":"47c03509-6ed3-4495-8c36-3ce29f588f95"}}
    public int customerId;
    public String token;
}
