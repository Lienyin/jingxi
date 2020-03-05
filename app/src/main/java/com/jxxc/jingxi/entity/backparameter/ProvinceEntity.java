package com.jxxc.jingxi.entity.backparameter;

import java.io.Serializable;

public class ProvinceEntity implements Serializable {
    public String name;
    public String areaId;
    public String areaCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
}
