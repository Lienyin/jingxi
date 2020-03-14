package com.jxxc.jingxi.entity.backparameter;

import java.io.Serializable;
import java.util.List;

public class RecommendComboInfoEntity implements Serializable {

    public List<RecommendCombo> combo;

    public class RecommendCombo implements Serializable{
        public String imgUrl;
        public String comboComment;
        public String salesVolume;
        public String comboName;
        public double totalPrice;
        public int comboId;
        public String imgUrls[];
        public List<ProductList> carTypePrices;
        public class ProductList implements Serializable{
            public String totalPrice;
            public String carTypeId;
        }
    }

    public List<Activitie> activities;
    public class Activitie implements Serializable{
        public String money;
        public String doorsillMoney;
        public String startTime;
        public String endTime;
        public String type;
        public String activitiesName;
    }
}
