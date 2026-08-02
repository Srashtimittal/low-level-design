package com.lld.swiggyzomato.partner;

import com.lld.swiggyzomato.model.DeliveryMetaData;
import com.lld.swiggyzomato.notification.NotificationMgr;

public class DeliveryPartner extends IPartner {

    public DeliveryPartner(String pName) {
        super(pName);
    }

    // Order Status also needs to be updated while these steps are happening
    // We have black-boxed that
    public void performDelivery(String pOrderId, DeliveryMetaData pDeliveryMetaData) {
        NotificationMgr notificationMgr = NotificationMgr.getNotificationMgr();

        double restaurantLocLatitude = pDeliveryMetaData.getRestaurantLoc().getLatitude();
        double restaurantLocLongitude = pDeliveryMetaData.getRestaurantLoc().getLongitude();
        notificationMgr.notify(pOrderId, name + " going to pick up delivery from location "
                + restaurantLocLatitude + "," + restaurantLocLongitude);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        notificationMgr.notify(pOrderId, name + " picked up delivery!");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        notificationMgr.notify(pOrderId, name + " on the way to deliver!");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        double userLocLatitude = pDeliveryMetaData.getUserLoc().getLatitude();
        double userLocLongitude = pDeliveryMetaData.getUserLoc().getLongitude();
        notificationMgr.notify(pOrderId, name + " reached the location " + userLocLatitude + "," + userLocLongitude);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        notificationMgr.notify(pOrderId, name + " delivered the order. CONGRATULATIONS!!");
    }
}
