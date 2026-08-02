package com.lld.uber.model;

import com.lld.uber.common.Rating;

/**
 * Holds all info that pricing/matching strategies need.
 * If more data is needed by strategies later, only this class
 * has to be updated - strategies and TripMgr stay untouched.
 */
public class TripMetaData {
    private final Location sourceLocation;
    private final Location destinationLocation;
    private final Rating riderRating;
    private Rating driverRating;

    public TripMetaData(Location sourceLocation, Location destinationLocation, Rating riderRating) {
        this.sourceLocation = sourceLocation;
        this.destinationLocation = destinationLocation;
        this.riderRating = riderRating;
        this.driverRating = Rating.UNASSIGNED;
    }

    public Location getSourceLocation() {
        return sourceLocation;
    }

    public Location getDestinationLocation() {
        return destinationLocation;
    }

    public Rating getRiderRating() {
        return riderRating;
    }

    public Rating getDriverRating() {
        return driverRating;
    }

    public void setDriverRating(Rating driverRating) {
        this.driverRating = driverRating;
    }
}
