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
    public static final String ALIPAY_APPID = "2021001135666632";
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
    public static final String RSA2_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDC+/F9eV47g97Gd6Z7o/XUAE+Y/5zgzH2hPPkIQsECOVHR7okiIMtoj74oVXXK2RlFOkUKrZ2c/H8O6AX/waRPUFxrZ66M4VyueU+mWvaJP7pIK7bey1cU5Ol/SGJDZ2Sh4q25ruxg215hVI/xEn2njjtHyMhmHwhZ2m1saV2eOQb1LquTYz56CAehPb2wjT4fj+eXvL+85q7tzKT59vNsAE/WM2dcMFVR0yDjV2s/x9mCw5Pdzi6s8k8q20fo3d0JWBNkRYnvlrX5xUYJTm4/++5AOLhzh2TttvApKd9WUbX87DqJziTI7wlKoAzwOTyXpbnmIN8D+KprYQTjvwF1AgMBAAECggEAT5eIfC+Ox7yMs3cj/lRvhJqb41h0MU9H+QlqnnShVMcRHyC/2a6JWaVTeiNoceUs65VdmX5FWE+5jdwolr301Gt5Tl6EYf2cV5wtZzq6aEwbPlqZy1g1s9G9freNZPZPncVqJOHCm8bwP8rE9yMVJ9BnYmYTZ5xDTGpdQGPCU1M9h1iyl3cTzdg4jmy3Jn5Unqp+hJJEopHPJgpDX5s2cEtw1JmYQMSyC/BbdRFw9t3fBB1S3ObV0PVmRE4o0KvQsPqx5Z9r5SLYy3e79LLy7GKywKo6aMRjMEzroZfQKKeyAMm7kf927VLFTLCXNTnMoILDKpGpUIXomGa4l68GAQKBgQDmYRUn8qmTK95F9KIpwogeLjmxkUk3OptP5G9WZ+rTDJb64CrLBx3NiqZRLwLiCw7HbrNXlNKR3AiCsxQc6kUHQKU0MX+ChdYYsH8RO495DQnaDyRczg4sFTNTkfX7Tr9NQKfC7Uu5O4s/rWAu3ym+uSMw3ha1m5/0biFHJzQmJwKBgQDYqyjiGF1jmkv9756WMhn+l+3RInaB4QID3GnFmH3UUet283vuwmwSpIyWY6vAPv4RJAWap/itbjP86TCwNiGz0wBRalcCeE9VMAGKu4lL3ZwKDeSigfaoawoUy9bvqVWFvsINZNMtRGqnBDx2M86OJz42h9cjkYpiaEeKTolZAwKBgQDgu9XCeXK4kXOr9F94sz7LijZXpK2yhaZagjGmLYsiREbIBoNYOOH2zwk9asm6LiVFoRSsHA5poSZgMUcuic26rHI7/wkvD1XyEcJy0hI8eN6ibGEBczTaBfhizqERfRkayDae7ZMP6CkHfh60upfjaIEOu6eVk3GvJPuriG/TwwKBgDtqlIeMDvAle/ljIKYnjyFWII1dWgo73gWZCBh7vOXgoSW5l57eCi0CmtsS8iKCI8InkrHapkNJGVCd0hBAh/V8Q3YQJ1QyEe9WftXAn+ICH6xSKwS1hyKgV99kFskaMq2+phBuOcsMHAM5EiuIAXjSIilqDwFeBAkAd73h06BpAoGAREVNJtPjHvqsfMn0UGYzgBZLO8PxSvXBcMxp4/iy/1S+sXd57wWEsepFF/dHjMnPei4G/bk8sOrMsGrpwrqaO/5RU3OKZKr4S4ICBHXhhZnKm+CI3GxlekdGIasl18lhErliYHPDGFZFlpoaeoDzhlJRwbK7YOgtNAUzPG3rI58=";
        }
