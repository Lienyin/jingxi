package com.jxxc.jingxi;

public class Api {
//       public static final String BASEURL = "https://repair-api-sit.zhizukj.com/zhizu/repair";//测试环境
//    public static final String BASEURL = "https://zhizuxia.zhizukj.com/ydb/app/";//生产
   public static final String BASEURL = "http://106.54.252.151:8080";//于立华

    //1-客户登录
    public static final String LOGIN = BASEURL + "/system/customer/login";
    //2-短信验证码登录
    public static final String LOGIN_BY_CODE = BASEURL + "/system/customer/login_by_code";
}
