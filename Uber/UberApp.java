package com.lld.uber;

import com.lld.uber.common.Rating;
import com.lld.uber.manager.DriverMgr;
import com.lld.uber.manager.RiderMgr;
import com.lld.uber.manager.TripMgr;
import com.lld.uber.model.Driver;
import com.lld.uber.model.Location;
import com.lld.uber.model.Rider;
import com.lld.uber.model.Trip;

import java.util.Map;

public class UberApp {

    public static void main(String[] args) {

        // ---------------- Creating Riders and Drivers ----------------
        Rider keertiRider = new Rider("Keerti", Rating.FIVE_STARS);
        Rider gauravRider = new Rider("Gaurav", Rating.FIVE_STARS);
        RiderMgr riderMgr = RiderMgr.getRiderMgr();
        riderMgr.addRider("keerti", keertiRider);
        riderMgr.addRider("gaurav", gauravRider);

        Driver yogitaDriver = new Driver("Yogita", Rating.THREE_STARS);
        Driver riddhiDriver = new Driver("Riddhi", Rating.FOUR_STARS);
        DriverMgr driverMgr = DriverMgr.getDriverMgr();
        driverMgr.addDriver("yogita", yogitaDriver);
        driverMgr.addDriver("riddhi", riddhiDriver);

        // These details would in turn be stored in a database
        // ----------------------------------------------------------------

        TripMgr tripMgr = TripMgr.getTripMgr();

        System.out.println();
        System.out.println("Creating Trip for Keerti from location (10,10) to (30,30)");
        tripMgr.createTrip(keertiRider, new Location(10, 10), new Location(30, 30));

        System.out.println();
        System.out.println("Creating Trip for Gaurav from location (200,200) to (500,500)");
        tripMgr.createTrip(gauravRider, new Location(200, 200), new Location(500, 500));

        // ------------------- Display all the trips created --------------------
        Map<Integer, Trip> tripsMap = tripMgr.getTripsMap();
        for (Trip trip : tripsMap.values()) {
            trip.displayTripDetails();
        }
    }
}
