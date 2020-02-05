package com.jxxc.jingxi;

import com.jxxc.jingxi.utils.SPUtils;

public class Api {
//       public static final String BASEURL = "https://repair-api-sit.zhizukj.com/zhizu/repair";//测试环境
//    public static final String BASEURL = "https://zhizuxia.zhizukj.com/ydb/app/";//生产
   public static final String BASEURL = "http://106.54.252.151:8080";//于立华

    //1-客户登录
    public static final String LOGIN = BASEURL + "/system/customer/login";
    //2-短信验证码登录
    public static final String LOGIN_BY_CODE = BASEURL + "/system/customer/login_by_code";
    //3-个人信息
    public static final String INFO_USER = BASEURL + "/system/customer/info";
    //4-优惠券
    public static final String COUPONS = BASEURL + "";
    //5-明细
    public static final String COMMISSION_DETAIL = BASEURL + "";
    //6-修改密码
    public static final String UPDATE_PASSWORD = BASEURL + "/system/customer/update_password";
    //7-查询版本
    public static final String LATEST_VERSION = BASEURL + "/system/upgrade_pack/latest_version";
    //8-获取短信验证码
    public static final String GET_CODE = BASEURL + "/common/auth/get_code";
    //9-文件上传
    public static final String UPLOAD = SPUtils.get(SPUtils.K_STATIC_URL,"") + "common/upload";
    //10-修改信息（头像）
    public static final String UPDATE_INFO = BASEURL + "/system/customer/update_info";
    //11-微信登录
    public static final String LOGIN_BY_WECHAT = BASEURL + "/system/customer/login_by_wechat";
}
