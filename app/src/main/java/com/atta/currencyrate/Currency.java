package com.atta.currencyrate;

import java.io.Serializable;

public class Currency implements Serializable {

    private int image;
    private String name;
    private double value;

    public Currency(int image, String name, double value) {
        this.image = image;
        this.name = name;
        this.value = value;
    }


    public int getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }
}
