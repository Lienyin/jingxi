package com.jxxc.jingxi.entity.backparameter;

import java.io.Serializable;
import java.util.List;

public class GetInfoEntity implements Serializable {
    public String couponNum;
    public int inviteNum;
    public List<Customer> inviteCustomer;

    public class Customer{
        public String date;
        public String mobile;
    }
}
