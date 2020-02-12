package com.jxxc.jingxi.ui.cartypeselect;

import com.jxxc.jingxi.utils.PinYinUtils;

public class Person {

    private String carIcon;
    private String name;
    private String id;
    private String pinyin;

    public Person(String carIcon,String name,String id){
        this.carIcon = carIcon;
        this.name = name;
        this.id = id;
        this.pinyin = PinYinUtils.getPinYin(name.substring(0,1));
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCarIcon() {
        return carIcon;
    }

    public void setCarIcon(String carIcon) {
        this.carIcon = carIcon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Person{" +
                "carIcon='" + carIcon + '\'' +
                "name='" + name + '\'' +
                "id='" + id + '\'' +
                ", pinyin='" + pinyin + '\'' +
                '}';
    }
}
