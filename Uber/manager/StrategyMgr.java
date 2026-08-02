package com.lld.uber.manager;

import com.lld.uber.model.TripMetaData;
import com.lld.uber.strategy.DefaultPricingStrategy;
import com.lld.uber.strategy.DriverMatchingStrategy;
import com.lld.uber.strategy.LeastTimeBasedMatchingStrategy;
import com.lld.uber.strategy.PricingStrategy;

public class StrategyMgr {

    private static StrategyMgr strategyMgrInstance = null;
    private static final Object mtx = new Object();

    private StrategyMgr() {
    }

    public static StrategyMgr getStrategyMgr() {
        if (strategyMgrInstance == null) {
            synchronized (mtx) {
                if (strategyMgrInstance == null) {
                    strategyMgrInstance = new StrategyMgr();
                }
            }
        }
        return strategyMgrInstance;
    }

    public PricingStrategy determinePricingStrategy(TripMetaData metaData) {
        System.out.println("Based on location and other factors, setting pricing strategy");
        return new DefaultPricingStrategy();
    }

    public DriverMatchingStrategy determineMatchingStrategy(TripMetaData metaData) {
        System.out.println("Based on location and other factors, setting matching strategy");
        return new LeastTimeBasedMatchingStrategy();
    }
}
