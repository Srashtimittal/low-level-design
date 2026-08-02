package com.lld.swiggyzomato.strategy;

import com.lld.swiggyzomato.manager.DeliveryPartnerMgr;
import com.lld.swiggyzomato.model.DeliveryMetaData;
import com.lld.swiggyzomato.partner.DeliveryPartner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LocBasedDeliveryPartnerMatchingStrategy implements IDeliveryPartnerMatchingStrategy {
    @Override
    public List<DeliveryPartner> matchDeliveryPartners(DeliveryMetaData pMetaData) {
        DeliveryPartnerMgr deliveryPartnerMgr = DeliveryPartnerMgr.getDeliveryPartnerMgr();

        System.out.println("Quadtrees can be used to get nearest delivery partners, "
                + "Delivery partner manager can be used to get details of partners. "
                + "Returning all delivery partners for demo purposes for now.");

        List<DeliveryPartner> nearestDeliveryPartners = new ArrayList<>();
        for (Map.Entry<String, DeliveryPartner> deliveryPartnerDetails : deliveryPartnerMgr.getDeliveryPartnersMap().entrySet()) {
            nearestDeliveryPartners.add(deliveryPartnerDetails.getValue());
        }
        return nearestDeliveryPartners;
    }
}
