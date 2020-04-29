package com.jxxc.jingxi.entity.backparameter;

import java.io.Serializable;
import java.util.List;

public class MyOrderEntity implements Serializable {
    public int isComment;
    public String address;
    public String technicianPhonenumber;
    public String orderId;
    public String price;
    public String payPrice;
    public String statusName;
    public int status;
    public int surplusCompleteTime;
    public int serviceType;
    public List<MyOrder> products;

    public class MyOrder{
        public String imgUrl;
        public String productId;
        public String productName;
    }
}
