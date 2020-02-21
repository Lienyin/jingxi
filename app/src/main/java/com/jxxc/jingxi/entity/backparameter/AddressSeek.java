package com.jxxc.jingxi.entity.backparameter;

import java.io.Serializable;

/**
 * 地址
 */
public class AddressSeek implements Serializable {
    public String name;
    public double longitude;
    public double latitude;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
