package com.lld.swiggyzomato.manager;

import com.lld.swiggyzomato.model.DeliveryMetaData;
import com.lld.swiggyzomato.notification.NotificationMgr;
import com.lld.swiggyzomato.notification.PushNotificationSender;
import com.lld.swiggyzomato.partner.DeliveryPartner;
import com.lld.swiggyzomato.strategy.IDeliveryPartnerMatchingStrategy;

import java.util.List;

public class DeliveryMgr {

    private static DeliveryMgr deliveryMgrInstance = null;
    private static final Object mtx = new Object();

    private DeliveryMgr() {
    }

    public static DeliveryMgr getDeliveryMgr() {
        if (deliveryMgrInstance == null) {
            synchronized (mtx) {
                if (deliveryMgrInstance == null) {
                    deliveryMgrInstance = new DeliveryMgr();
                }
            }
        }
        return deliveryMgrInstance;
    }

    // This function should be broken down into sub parts and every method should have one small responsibility
    public void manageDelivery(String pOrderId, DeliveryMetaData pDeliveryMetaData) {
        StrategyMgr strategyMgr = StrategyMgr.getStrategyMgr();

        IDeliveryPartnerMatchingStrategy partnerMatchingStrategy =
                strategyMgr.determineDeliveryPartnerMatchingStrategy(pDeliveryMetaData);

        List<DeliveryPartner> deliverypartners =
                partnerMatchingStrategy.matchDeliveryPartners(pDeliveryMetaData);

        NotificationMgr notificationMgr = NotificationMgr.getNotificationMgr();
        // Send push notifications to the nearest delivery partners
        for (DeliveryPartner deliveryPartner : deliverypartners) {
            notificationMgr.notifyParticularUser(deliveryPartner.getName(), "Delivery Request", new PushNotificationSender());
        }

        DeliveryPartner assignedDeliveryPartner = deliverypartners.get(0);

        // Assume first delivery partner accepted it
        notificationMgr.notify(pOrderId, "Delivery Partner " + assignedDeliveryPartner.getName() + " assigned for the order " + pOrderId);

        assignedDeliveryPartner.performDelivery(pOrderId, pDeliveryMetaData);
    }
}
