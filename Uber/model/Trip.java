package com.lld.uber.model;

import com.lld.uber.common.TripStatus;
import com.lld.uber.strategy.DriverMatchingStrategy;
import com.lld.uber.strategy.PricingStrategy;

public class Trip {

    // This is not threadsafe and is just for demo purposes (same as C++ version)
    private static int nextTripId = 1;

    private final Rider rider;
    private final Driver driver;
    private final Location sourceLocation;
    private final Location destinationLocation;
    private TripStatus status;
    private final int tripId;
    private final double price;
    private final PricingStrategy pricingStrategy;
    private final DriverMatchingStrategy driverMatchingStrategy;

    public Trip(Rider rider, Driver driver, Location sourceLocation, Location destinationLocation,
                double price, PricingStrategy pricingStrategy,
                DriverMatchingStrategy driverMatchingStrategy) {
        this.rider = rider;
        this.driver = driver;
        this.sourceLocation = sourceLocation;
        this.destinationLocation = destinationLocation;
        this.price = price;
        this.pricingStrategy = pricingStrategy;
        this.driverMatchingStrategy = driverMatchingStrategy;
        this.status = TripStatus.DRIVER_ON_THE_WAY;
        this.tripId = nextTripId;
        nextTripId++;
    }

    public int getTripId() {
        return tripId;
    }

    public void displayTripDetails() {
        System.out.println();
        System.out.println("Trip id - " + tripId);
        System.out.println("Rider - " + rider.getRiderName());
        System.out.println("Driver - " + driver.getDriverName());
        System.out.println("Price - " + price);
        System.out.println("Locations - " + sourceLocation.latitude + "," + sourceLocation.longitude
                + " and " + destinationLocation.latitude + "," + destinationLocation.longitude);
    }
}
