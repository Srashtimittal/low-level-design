package com.lld.swiggyzomato.model;

import java.util.ArrayList;
import java.util.List;

public class DishAddOn {
    private String name;
    private String description;
    private double price;
    private boolean isAvail;
    private List<String> images = new ArrayList<>();

    public DishAddOn(String pName, double pPrice) {
        this.name = pName;
        this.price = pPrice;
    }

    // getters setters
    public double getPrice() {
        return price;
    }
}
