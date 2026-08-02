package com.lld.swiggyzomato.model;

import com.lld.swiggyzomato.common.Location;

public class User {
    private String name;
    private Location location;

    public User(String pName, Location pLoc) {
        this.name = pName;
        this.location = pLoc;
    }

    public Location getLocation() {
        return location;
    }

    public String getId() {
        return name;
    }
}
