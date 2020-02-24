package com.jxxc.jingxi.wxapi;

import java.io.Serializable;

/**
 * Created by ds on 2017/12/2.
 */

public class Constant implements Serializable {
    //微信配置
    public static final String APP_ID = "wxda559f065b7b2478";
    public static final String WECHAT_SECRET = "2d8f5984ad3944443de158527dcada0f";
    //支付宝配置
    /** 支付宝支付业务：入参app_id */
    public static final String ALIPAY_APPID = "2021001103654185";
    /** 支付宝账户登录授权业务：入参pid值 */
    public static final String ALIPAY_PID = "2088731215077504";
    /** 支付宝账户登录授权业务：入参target_id值 */
    public static final String ALIPAY_TARGET_ID = "jingxi";
    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /** 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1 */
    public static final String RSA_PRIVATE = "";
    public static final String RSA2_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCpL7Oo7nhGRdTRNS1Lx0xZb/y96uOm/6FOCIKH7TVBjktiJrwLv1ExW8xbz0R9ABJrjc/9mYRrahI8U3gOcfWEnaew2t07ES3ffRp0s+g+2WpX56RH7hNhaYbHDMpmM5MaxdcDCFI53KBp41INjOJXCD0lAHRQIKwm8PwRmNCfka2o8T716UZUkUEPFGD9rsN1AozIQRriZ4vQ6zwxhoB9zfbpEMPuw75Mw7R6tSJM7SegKyXzK2JFstNskcGHDrkPtN1hCF2C8+DMQSLEWJBSyL48SJdgra4mw+MmKCk9VV1DIr0xluN+BSmo+/Gz9UMuU6BpkT9+pDfhOdLePG+xAgMBAAECggEAcaubCeUu6ggA9I7F4CQHKBAVv4QRcCJQPh15ldnJ7kGsZEWiEkJ4SrFinEylt1ZVCe490CVU0tZxahaqkwn96xwBjtF90jG4oG9tVvuJDrEEwgrgVXOfkdNUumfdlM0SCOrjiyYMGNQi8wHIH1KIv/DJJBMqEqvg/kjrEueZQ5uFahyuQQhnl4akuyBoZvM5XIcuR1ah6LZi05g7ZMXtWFdZ/OoPZ5hBhzfIzFuAbEuWVZYIWr43JpsqLn4xmPlKzloM52RyLMHXaWCCkRrCGvicT5bwoDh1dg+37WM+og98Kphds2KLpnYYyJsM99OXH0Peh7lfGTo8WXDEQaDwAQKBgQDQ2H1oyHEtgASnwUZYriKqxTlW5FXb7nYCGxdHtdR7neHoeKmFhzjl5mwuvVtyQ+EE3l7PD/QFHI3xuh2wPtTi5NYJu1Aamf0L/3RtdpZ09axpgSGrNiSrcrMx+nrl8STJjiqMoDJloHWrv9KMccdgx+YdFI+IuT3npL2YXnlEowKBgQDPYtvhiiyysO57J3MJAxuJVtpDksYrPFxC9F5JVnp4yq98FrSRR0/QxmF5zv/wXLLRcqgSgs5UY3TItfCIN5q+rmCHXCFqQHnIpEnZG7ouZnGXSbzf8D/0SxrX4paubffX1ZgkOHX8UyG6kcpi6oxPILCAqPX0Du1B+3qRRp2rmwKBgBv51BrUNuz8aCM8rbq1YOl1T/AG2EWE1QQFMwIlJliE1r04ZKUBbyrEyhscvII6+4qWaybboTmKEK2f29g/Im9ebhmRoPJfTFluF9+N8iuquTTM2oC/2PuqeryoLAxfWCkzxRlfYEDi1MB0CrVLjIS3Ymjk1zFedx9vnwIpLZ93AoGBAJlkS7KEmw8IYzxgL2vpCoMI5KO6ei0cljlHuj2QY0HY2Pd1a9VQ465OLWcFPrUwO2tD3SDj82zjq0uRlvmJ90E2hVtZP7XxPqKzMZQOOCV9zGLUPYl9kDBHJPGtPTqDSCAycjfIMyyQ6I+wqfuD9IfKbAKd9l2wdLirIzs0+gYlAoGAAjyh0aTpW8FZrHcHOY/HppCbzyBLzYVoWEAtcuAjSkkDbguFG6yJNFYtrHO66NqplaWT5jrUlQbZvHphpGw6giiDQkrEqG6zL5tvksbV/l0wSHAQCMY+vIGxHX7tI7CiwuFhKIUHZnFHUqpWtgw84UVtO/BPLfBrPKT6xnmYy+E=";
        }
