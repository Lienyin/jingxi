package com.jxxc.jingxi.entity.backparameter;

import java.io.Serializable;
import java.util.List;

public class RecommendComboInfoEntity implements Serializable {
    public String imgUrl;
    public String comboTypeId;
    public String salesVolume;
    public String comboName;
    public String totalPrice;
    public String carTypeId;
    public List<ProductList> productList;
    public class ProductList implements Serializable{
        public int comboProductId;
        public String productName;
    }
}
