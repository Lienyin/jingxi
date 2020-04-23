package com.jxxc.jingxi.entity.backparameter;

import java.io.Serializable;
import java.util.List;

public class OrderEntity implements Serializable {
    public String serviceType;
    public String isComment;
    public String address;
    public double lng;
    public double lat;
    public double technicianLng;
    public double technicianLat;
    public String technicianPhonenumber;
    public String orderId;
    public String arriveDate;
    public String remark;
    public String cars;
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
    public int starLevel;
    public String discountsPrice;
    public int status;
    public String technicianCommentImgs;
    public String technicianAvatar;
}
