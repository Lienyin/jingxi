package com.jxxc.jingxi.entity.backparameter;

import java.io.Serializable;

public class AppointmentListEntity implements Serializable {
    public int isFull;
    public String title;
    public String h;

    public boolean isForbidden;

    public boolean isForbidden() {
        return isForbidden;
    }

    public void setForbidden(boolean forbidden) {
        isForbidden = forbidden;
    }
}
