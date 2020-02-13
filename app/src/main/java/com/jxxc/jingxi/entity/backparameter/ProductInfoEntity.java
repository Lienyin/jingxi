package com.jxxc.jingxi.entity.backparameter;

import java.io.Serializable;
import java.util.List;

public class ProductInfoEntity implements Serializable {
    public String comboTypeId;
    public String basicPrice;
    public String carTypeId;
    public List<ProductInfo> productList;

    public class ProductInfo{
        public String imgUrl;
        public String selectedImg;
        public String comboProductId;
        public String isDefault;
        public String productId;
        public String price;
        public String productName;
    }
}
