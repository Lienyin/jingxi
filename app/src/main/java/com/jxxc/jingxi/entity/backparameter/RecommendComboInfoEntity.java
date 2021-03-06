package com.jxxc.jingxi.entity.backparameter;

import java.io.Serializable;
import java.util.List;

public class RecommendComboInfoEntity implements Serializable {

    public String imgUrl;
    public int serviceType;
    public String comboComment;
    public String salesVolume;
    public String comboName;
    public String serviceHours;
    public String imgUrls[];
    public double totalPrice;
    public int comboId;
    public boolean isSelect;

    public String productIds;
    public List<ProductList> carTypePrices;
    public class ProductList implements Serializable{
        public double totalPrice;
        public int carTypeId;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
