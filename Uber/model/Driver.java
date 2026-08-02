package com.lld.uber.model;

import com.lld.uber.common.Rating;

public class Driver {
    private final String name;
    private boolean available;
    private final Rating rating;

    public Driver(String name, Rating rating) {
        this.name = name;
        this.rating = rating;
        this.available = false;
    }

    public void updateAvail(boolean avail) {
        this.available = avail;
    }

    public String getDriverName() {
        return name;
    }

    public Rating getRating() {
        return rating;
    }
}
