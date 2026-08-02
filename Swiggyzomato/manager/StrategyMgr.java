package com.lld.swiggyzomato.manager;

import com.lld.swiggyzomato.model.DeliveryMetaData;
import com.lld.swiggyzomato.strategy.IDeliveryPartnerMatchingStrategy;
import com.lld.swiggyzomato.strategy.LocBasedDeliveryPartnerMatchingStrategy;

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

    public IDeliveryPartnerMatchingStrategy determineDeliveryPartnerMatchingStrategy(DeliveryMetaData metaData) {
        System.out.println("Based on location, weather and other factors, setting partner strategy");
        return new LocBasedDeliveryPartnerMatchingStrategy();
    }
}
