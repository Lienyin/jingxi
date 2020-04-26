package com.jxxc.jingxi.entity.backparameter;

import java.io.Serializable;
import java.util.List;

public class BandAndTypeEntity implements Serializable {

    public List<CarType> type;
    public class CarType{
        public String carTypeId;
        public String carTypeName;
        public String carTypeIcon;
    }

    public List<CarBrand> brand;
    public class CarBrand{
        public String brandId;
        public String brandName;
        public String brandIcon;
        public String letter;
        public int isRecommend;
    }
}
