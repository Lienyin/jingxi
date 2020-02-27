package com.jxxc.jingxi.entity.backparameter;

import java.io.Serializable;
import java.util.List;

public class ProductInfoEntity implements Serializable {

    public List<Activity> activities;
    public class Activity{
        public String money;
        public String doorsillMoney;
        public String startTime;
        public String endTime;
        public String type;
        public String activitiesName;
    }
    public List<Combo> combo;
    public static class Combo{
        public String comboTypeId;
        public String basicPrice;
        public String carTypeId;
        public List<ProductInfo> productList;

        public class ProductInfo{
            public String imgUrl;
            public String selectedImg;
            public String comboProductId;
            public String isDefault;
            public int productId;
            public String price;
            public String productName;
        }
    }
}
