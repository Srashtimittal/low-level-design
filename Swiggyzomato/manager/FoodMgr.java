package com.lld.swiggyzomato.manager;

import com.lld.swiggyzomato.model.Dish;
import com.lld.swiggyzomato.model.Restaurant;
import com.lld.swiggyzomato.notification.NotificationMgr;
import com.lld.swiggyzomato.notification.PushNotificationSender;

import java.util.Map;

public class FoodMgr {

    private static FoodMgr foodMgrInstance = null;
    private static final Object mtx = new Object();

    private FoodMgr() {
    }

    public static FoodMgr getFoodMgr() {
        if (foodMgrInstance == null) {
            synchronized (mtx) {
                if (foodMgrInstance == null) {
                    foodMgrInstance = new FoodMgr();
                }
            }
        }
        return foodMgrInstance;
    }

    public void addRestaurantForNotificationUpdates(String pOrderId, String pRestaurantId) {
        NotificationMgr notificationMgr = NotificationMgr.getNotificationMgr();
        // we can add push or whatsapp notifications in same way. Basically, we are keeping all notifications customisable
        notificationMgr.addNotificationSender(pOrderId, pRestaurantId, new PushNotificationSender());
    }

    public void prepareFood(String pOrderId, String pRestaurantId, Map<Dish, Integer> pDishes) {
        RestaurantMgr restaurantMgr = RestaurantMgr.getRestaurantMgr();
        Restaurant restaurant = restaurantMgr.getRestaurant(pRestaurantId);
        restaurant.prepareFood(pOrderId, pDishes);

        // Restaurant should receive the delivery partner's notifications.
        // The order in which the restaurant, user & delivery partner are added to the notification mgr
        // will decide which notifications they receive
        // For interviews, This is too much detailing though, we can just mention to interviewer and move forward
        addRestaurantForNotificationUpdates(pOrderId, pRestaurantId);
    }
}
