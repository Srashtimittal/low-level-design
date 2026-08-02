package com.lld.swiggyzomato.manager;

import com.lld.swiggyzomato.partner.DeliveryPartner;

import java.util.HashMap;
import java.util.Map;

public class DeliveryPartnerMgr {

    private static DeliveryPartnerMgr deliveryPartnerMgrInstance = null;
    private static final Object mtx = new Object();
    private final Map<String, DeliveryPartner> deliveryPartnersMap = new HashMap<>();

    private DeliveryPartnerMgr() {
    }

    public static DeliveryPartnerMgr getDeliveryPartnerMgr() {
        if (deliveryPartnerMgrInstance == null) {
            synchronized (mtx) {
                if (deliveryPartnerMgrInstance == null) {
                    deliveryPartnerMgrInstance = new DeliveryPartnerMgr();
                }
            }
        }
        return deliveryPartnerMgrInstance;
    }

    public void addDeliveryPartner(String pDeliveryPartnerName, DeliveryPartner pDeliveryPartner) {
        deliveryPartnersMap.put(pDeliveryPartnerName, pDeliveryPartner);
    }

    public DeliveryPartner getDeliveryPartner(String pDeliveryPartnerName) {
        return deliveryPartnersMap.get(pDeliveryPartnerName);
    }

    // This getter is added so that delivery partner matching strategy can access the map and
    // select nearest delivery partners
    public Map<String, DeliveryPartner> getDeliveryPartnersMap() {
        return deliveryPartnersMap;
    }
}
