package com.jxxc.jingxi.entity.backparameter;

import java.io.Serializable;
import java.util.List;

public class OrderEntity implements Serializable {
    public String serviceType;
    public String isComment;
    public String address;
    public String lng;
    public String technicianPhonenumber;
    public String technicianLat;
    public String orderId;
    public String arriveDate;
    public String remark;
    public String commentContent;
    public String technicianRealName;
    public List<Products> products;
    public class Products{
        public String imgUrl;
        public String productId;
        public String productName;
    }
    public String customerCommentTime;
    public String duration;
    public String appointmentTime;
    public String price;
    public String statusName;
    public int technicianGrade;
    public String starLevel;
    public String discountsPrice;
    public String lat;
    public String technicianLng;
    public String status;
    public String technicianCommentImgs;
    public String technicianAvatar;
}
