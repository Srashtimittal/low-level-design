package com.lld.uber.strategy;

import com.lld.uber.manager.DriverMgr;
import com.lld.uber.model.Driver;
import com.lld.uber.model.TripMetaData;

import java.util.Map;

public class LeastTimeBasedMatchingStrategy implements DriverMatchingStrategy {

    @Override
    public Driver matchDriver(TripMetaData tripMetaData) {
        DriverMgr driverMgr = DriverMgr.getDriverMgr();
        Map<String, Driver> driversMap = driverMgr.getDriversMap();

        if (driversMap.isEmpty()) {
            System.out.println("No drivers! What service is this huh?");
            return null;
        }

        System.out.println("Using quadtree to see nearest cabs, using driver manager "
                + "to get details of drivers and send notifications");

        // here we could call a quadtree algorithm to get the actual nearest cab;
        // for demo purposes we just grab the first available driver
        Driver driver = driversMap.values().iterator().next();
        System.out.println("Setting " + driver.getDriverName() + " as driver");
        tripMetaData.setDriverRating(driver.getRating());
        return driver;
    }
}
