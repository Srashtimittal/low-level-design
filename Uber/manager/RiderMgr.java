package com.lld.uber.manager;

import com.lld.uber.model.Rider;

import java.util.HashMap;
import java.util.Map;

public class RiderMgr {

    private static RiderMgr riderMgrInstance = null;
    private static final Object mtx = new Object();

    private final Map<String, Rider> ridersMap = new HashMap<>();

    private RiderMgr() {
    }

    public static RiderMgr getRiderMgr() {
        if (riderMgrInstance == null) {
            synchronized (mtx) {
                if (riderMgrInstance == null) {
                    riderMgrInstance = new RiderMgr();
                }
            }
        }
        return riderMgrInstance;
    }

    public void addRider(String riderName, Rider rider) {
        ridersMap.put(riderName, rider);
    }

    public Rider getRider(String riderName) {
        return ridersMap.get(riderName);
    }
}
