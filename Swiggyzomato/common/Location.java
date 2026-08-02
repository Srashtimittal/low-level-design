package com.lld.swiggyzomato.common;

public class Location {
    private double latitude;
    private double longitude;

    public Location(double pLat, double pLong) {
        this.latitude = pLat;
        this.longitude = pLong;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
