package com.jxxc.jingxi.entity.backparameter;

import java.io.Serializable;
import java.util.List;

public class SelectAllAreaEntity implements Serializable {
    public String name;
    public String value;
    public List<Citys> citys;
    public class Citys{

        public String name;
        public String value;
        public List<Districts> districts;

        public class Districts{
            public String name;
            public String value;
        }
    }
}
