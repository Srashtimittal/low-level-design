package com.lld.uber.manager;

import com.lld.uber.model.Driver;

import java.util.HashMap;
import java.util.Map;

public class DriverMgr {

    private static DriverMgr driverMgrInstance = null;

    private final Map<String, Driver> driversMap = new HashMap<>();

    private DriverMgr() {
    }

    public static DriverMgr getDriverMgr() {
        if (driverMgrInstance == null) {
            synchronized (DriverMgr.class) {
                if (driverMgrInstance == null) {
                    driverMgrInstance = new DriverMgr();
                }
            }
        }
        return driverMgrInstance;
    }

    public void addDriver(String driverName, Driver driver) {
        driversMap.put(driverName, driver);
    }

    public Driver getDriver(String driverName) {
        return driversMap.get(driverName);
    }

    public Map<String, Driver> getDriversMap() {
        return driversMap;
    }
}
