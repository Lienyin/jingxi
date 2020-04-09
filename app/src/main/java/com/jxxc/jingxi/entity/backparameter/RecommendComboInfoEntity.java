package com.jxxc.jingxi.entity.backparameter;

import java.io.Serializable;
import java.util.List;

public class RecommendComboInfoEntity implements Serializable {

    public String imgUrl;
    public String comboComment;
    public String salesVolume;
    public String comboName;
    public double totalPrice;
    public int comboId;
    public String productIds;
    public String imgUrls[];
    public List<ProductList> carTypePrices;
    public class ProductList implements Serializable{
        public String totalPrice;
        public String carTypeId;
    }
}
