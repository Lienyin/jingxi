package com.jxxc.jingxi;

import com.jxxc.jingxi.utils.SPUtils;

public class Api {
   public static final String BASEURL = "https://jx.incofuture.com/customer";//生成环境
   //public static final String BASEURL = "http://139.196.25.206:8080/customer";//测试

    //1-客户登录
    public static final String LOGIN = BASEURL + "/system/customer/login";
    //1-客户登出
    public static final String LOGOUT = BASEURL + "/system/customer/logout";
    //2-短信验证码登录
    public static final String LOGIN_BY_CODE = BASEURL + "/system/customer/login_by_code";
    //3-个人信息
    public static final String INFO_USER = BASEURL + "/system/customer/info";
    //4-优惠券
    public static final String COUPONS = BASEURL + "/biz/coupon/list";
    //5-明细
    public static final String COMMISSION_DETAIL = BASEURL + "/biz/customer_account/getAccount";
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
    //12-绑定微信
    public static final String AUTH_WECHAT = BASEURL + "/system/customer/auth_wechat";
    //13-首页基本服务项展示
    //public static final String PRODUCT_INFO = BASEURL + "/biz/order/productInfo";
    //14-获取个人车辆列表
    public static final String GET_CAR_LIST = BASEURL + "/biz/car/getCarList";
    //15-获取所有车品牌车型
    public static final String GET_BAND_AND_TYPE = BASEURL + "/biz/car/getBandAndType";
    //16-增加车辆
    public static final String ADD_CAR = BASEURL + "/biz/car/add";
    //17-修改车辆
    public static final String EDIT_CAR = BASEURL + "/biz/car/edit";
    //18-删除车辆
    public static final String REMOVE_CAR = BASEURL + "/biz/car/remove";
    //19-获取洗车组合套餐
    public static final String COMBO_INFO = BASEURL + "/biz/order/comboInfo";
    //20-充值活动
    public static final String RECHARGE_CONFIGURATION = BASEURL + "/biz/deposit_order/getGift";
    //21-充值
    public static final String RECHARGE = BASEURL + "/biz/deposit_order/pay";
    //22-我的订单
    public static final String MY_ORDER = BASEURL + "/biz/order/myOrder";
    //23-订单详情
    public static final String GET_ORDER = BASEURL + "/biz/order/getOrder";
    //24-消息列表
    public static final String MESSAGE_LIST = BASEURL + "/biz/message/messageList";
    //25-下单
    public static final String CREATE_ORDER = BASEURL + "/biz/order/create";
    //26-余额支付
    public static final String BALANCE_PAY = BASEURL + "/biz/order/balancePay";
    //27-评论
    public static final String COMMENT = BASEURL + "/biz/order/comment";
    //28-第三方支付
    public static final String ORDER_PAY = BASEURL + "/biz/order/orderPay";
    //29-取消订单
    public static final String CANCEL_ORDER = BASEURL + "/biz/order/cancel";
    //30-催单
    public static final String HASTEN_ORDER = BASEURL + "/biz/order/hasten";
    //31-删除评论
    public static final String CLEAR_COMMENT = BASEURL + "/biz/order/clearComment";
    //31-我的钱包
    public static final String WALLET = BASEURL + "/system/customer/wallet";
    //32-查询附近洗车点
    public static final String NEARBY_CONPANY = BASEURL + "/system/company/nearbyConpany";
    //33-加盟商列表
    public static final String COMPANY_LIST = BASEURL + "/system/company/companyList";
    //34-加盟商详细
    public static final String GET_COMPANY = BASEURL + "/system/company/getCompany";
    //35-加盟商预约时间列表
    public static final String APPOINTMENT_LIST = BASEURL + "/system/company/appointmentList";
    //36-获取全国存在加盟商的省市区
    public static final String AREA_LIST = BASEURL + "/system/company/areaList";
    //37-发现列表
    public static final String FIND_LIST = BASEURL + "/system/notice/list";
    //38-发现详情
    public static final String FIND_DETAILS = BASEURL + "/system/find/findDetails";
    //39-点赞接口
    public static final String APPRECIATE = BASEURL + "/system/notice/appreciate";
    //40-首页广告
    public static final String BANNER_LIST = BASEURL + "/system/banner/list";
    //41-获取推荐洗车组合套餐
    public static final String RECOMMEND_COMBO_INFO = BASEURL + "/biz/order/recommendComboInfo";
    //42-推荐加盟商列表
    public static final String RECOMMEND_COMPANY_LIST = BASEURL + "/system/company/recommendCompanyList";
    //43-推荐加盟商列表
    public static final String GET_STATE = BASEURL + "/system/customer/getState";
    //44-获取活动
    public static final String GET_ACTIVITIES = BASEURL + "/biz/order/getActivities";
    //45-产品banner
    public static final String PRODUCT_ID_LIST = BASEURL + "/system/banner/productIdList";
    //46-获取当前状态
    public static final String GET_STATUS = BASEURL + "/biz/marketing/getStatus";
    //47-获取推广进度
    public static final String GET_INFO = BASEURL + "/biz/marketing/getInfo";
    //48-查询省市区
    public static final String SELECT_ALL_AREA = BASEURL + "/system/area/selectAllArea";
    //49-查询是否已申请
    public static final String SELECT_BY_PHONE = BASEURL + "/system/company/selectByPhone";
    //50-申请加入接口
    public static final String APPLY = BASEURL + "/system/company/applyToJoin";
    //51-下单（进店服务）
    public static final String CREATE2 = BASEURL + "/biz/order/create2";
    //52-获取订单数量
    public static final String ORDER_NUM = BASEURL + "/biz/order/orderNum";
    //53-获取推广详情
    public static final String QUERY_ACTIVITY_DETAIL = BASEURL + "/system/upgrade_pack/query_activity_detail";
}
