package com.lld.swiggyzomato.model;

import com.lld.swiggyzomato.common.Cuisine;

import java.util.ArrayList;
import java.util.List;

public class Dish {
    private String name;
    private Cuisine cuisine;
    private String description;
    private double price;
    private List<String> dishImages = new ArrayList<>();
    private List<DishAddOn> addons = new ArrayList<>(); // could have had decorator design pattern for add ons but
                                                          // seemed overkill since restaurant owners are going to create addons

    public Dish(String pName, Cuisine pCuisine, double pPrice) {
        this.name = pName;
        this.cuisine = pCuisine;
        this.price = pPrice;
    }

    public void addAddOn(DishAddOn pAddOn) {
        addons.add(pAddOn);
    }

    // remove add on function
    public double getPrice() {
        double totalPrice = price;
        for (DishAddOn addOn : addons) {
            totalPrice += addOn.getPrice();
        }
        return totalPrice;
    }

    public String getDescription() {
        return description;
    }

    public String getDishName() {
        return name;
    }

    public Cuisine getCuisine() {
        return cuisine;
    }
}
