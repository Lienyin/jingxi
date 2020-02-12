package com.jxxc.jingxi.entity.backparameter;

import java.io.Serializable;
import java.util.List;

public class ColorEntity implements Serializable {
    public List<Color> colors;
    public class Color{
        public int color;
        public String colorCode;
    }
}
