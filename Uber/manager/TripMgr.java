package com.lld.uber.manager;

import com.lld.uber.model.Driver;
import com.lld.uber.model.Location;
import com.lld.uber.model.Rider;
import com.lld.uber.model.Trip;
import com.lld.uber.model.TripMetaData;
import com.lld.uber.strategy.DriverMatchingStrategy;
import com.lld.uber.strategy.PricingStrategy;

import java.util.HashMap;
import java.util.Map;

public class TripMgr {

    private static TripMgr tripMgrInstance = null;
    private static final Object mtx = new Object();

    private final RiderMgr riderMgr;
    private final DriverMgr driverMgr;

    private final Map<Integer, TripMetaData> tripsMetaDataInfo = new HashMap<>();
    private final Map<Integer, Trip> tripsInfo = new HashMap<>();

    private TripMgr() {
        this.riderMgr = RiderMgr.getRiderMgr();
        this.driverMgr = DriverMgr.getDriverMgr();
    }

    public static TripMgr getTripMgr() {
        if (tripMgrInstance == null) {
            synchronized (mtx) {
                if (tripMgrInstance == null) {
                    tripMgrInstance = new TripMgr();
                }
            }
        }
        return tripMgrInstance;
    }

    public void createTrip(Rider rider, Location srcLoc, Location dstLoc) {
        TripMetaData metaData = new TripMetaData(srcLoc, dstLoc, rider.getRating());
        StrategyMgr strategyMgr = StrategyMgr.getStrategyMgr();
        PricingStrategy pricingStrategy = strategyMgr.determinePricingStrategy(metaData);
        DriverMatchingStrategy driverMatchingStrategy = strategyMgr.determineMatchingStrategy(metaData);

        Driver driver = driverMatchingStrategy.matchDriver(metaData);
        double tripPrice = pricingStrategy.calculatePrice(metaData);

        Trip trip = new Trip(rider, driver, srcLoc, dstLoc, tripPrice, pricingStrategy, driverMatchingStrategy);
        int tripId = trip.getTripId();
        tripsInfo.put(tripId, trip);
        tripsMetaDataInfo.put(tripId, metaData);
    }

    public Map<Integer, Trip> getTripsMap() {
        return tripsInfo;
    }
}
