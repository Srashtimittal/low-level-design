package com.lld.uber.model;

import com.lld.uber.common.Rating;

public class Rider {
    private final String name;
    private final Rating rating;

    public Rider(String name, Rating rating) {
        this.name = name;
        this.rating = rating;
    }

    public String getRiderName() {
        return name;
    }

    public Rating getRating() {
        return rating;
    }
}
