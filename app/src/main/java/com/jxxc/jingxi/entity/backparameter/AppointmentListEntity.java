package com.jxxc.jingxi.entity.backparameter;

import java.io.Serializable;

public class AppointmentListEntity implements Serializable {
    public int num;
    public int isFull;
    public String title;
    public int awaitMinute;

    public boolean isForbidden;

    public boolean isForbidden() {
        return isForbidden;
    }

    public void setForbidden(boolean forbidden) {
        isForbidden = forbidden;
    }
}
