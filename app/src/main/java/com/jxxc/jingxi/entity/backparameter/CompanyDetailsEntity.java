package com.jxxc.jingxi.entity.backparameter;

import java.io.Serializable;
import java.util.List;

public class CompanyDetailsEntity implements Serializable {
    public CompanyDetails company;
    public class CompanyDetails{
        public String companyId;
        public String companyName;
        public String address;
        public String phonenumber;
        public String score;
        public String orderNum;
        public int companyType;
        public String imgUrl;
        public int isBusiness;
        public int distance;
        public String technicianNum;
        public double lng;
        public double lat;
    }

    public List<Jishi> technicians;
    public class Jishi{
        public String orderNum;
        public String avatar;
        public String userName;
        public String technicianId;
    }
}
