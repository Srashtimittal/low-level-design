package com.lld.swiggyzomato.manager;

import com.lld.swiggyzomato.model.DeliveryMetaData;
import com.lld.swiggyzomato.model.Order;
import com.lld.swiggyzomato.notification.NotificationMgr;
import com.lld.swiggyzomato.notification.SMSNotificationSender;

import java.util.HashMap;
import java.util.Map;

public class OrderMgr {

    private static OrderMgr orderMgrInstance = null;
    private static final Object mtx = new Object();

    private final Map<String, Order> ordersMap = new HashMap<>();

    private DeliveryMgr deliveryMgr;
    private FoodMgr foodMgr;

    private OrderMgr() {
        deliveryMgr = DeliveryMgr.getDeliveryMgr();
        foodMgr = FoodMgr.getFoodMgr();
    }

    public static OrderMgr getOrderMgr() {
        if (orderMgrInstance == null) {
            synchronized (mtx) {
                if (orderMgrInstance == null) {
                    orderMgrInstance = new OrderMgr();
                }
            }
        }
        return orderMgrInstance;
    }

    // 3 stages of order creation
    private void addUserForNotificationUpdates(String pOrderId, Order pOrder) {
        NotificationMgr notificationMgr = NotificationMgr.getNotificationMgr();
        // we can add push or whatsapp notifications in same way. Basically, we are keeping all notifications customisable
        notificationMgr.addNotificationSender(pOrderId, pOrder.getUserId(), new SMSNotificationSender());
        // this configuration level should be in user class and not in order mgr
    }

    private void manageDelivery(String pOrderId, Order pOrder) {
        DeliveryMetaData metaData = new DeliveryMetaData(pOrderId, pOrder.getUserLocation(), pOrder.getRestaurantLocation());
        deliveryMgr.manageDelivery(pOrderId, metaData);
    }

    private void manageFood(String pOrderId, Order pOrder) {
        foodMgr.prepareFood(pOrderId, pOrder.getRestaurantId(), pOrder.getDishes());
    }

    public void createOrder(String pOrderId, Order pOrder) {
        addUserForNotificationUpdates(pOrderId, pOrder);

        manageFood(pOrderId, pOrder);

        manageDelivery(pOrderId, pOrder);
    }

    public Order getOrder(String pOrderName) {
        return ordersMap.get(pOrderName);
    }
}
