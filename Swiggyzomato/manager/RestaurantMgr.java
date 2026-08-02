package com.lld.swiggyzomato.manager;

import com.lld.swiggyzomato.model.Restaurant;

import java.util.HashMap;
import java.util.Map;

public class RestaurantMgr {

    private static RestaurantMgr restaurantMgrInstance = null;
    private static final Object mtx = new Object();
    private final Map<String, Restaurant> restaurantsMap = new HashMap<>();

    private RestaurantMgr() {
    }

    public static RestaurantMgr getRestaurantMgr() {
        if (restaurantMgrInstance == null) {
            synchronized (mtx) {
                if (restaurantMgrInstance == null) {
                    restaurantMgrInstance = new RestaurantMgr();
                }
            }
        }
        return restaurantMgrInstance;
    }

    public void addRestaurant(String pRestaurantName, Restaurant pRestaurant) {
        restaurantsMap.put(pRestaurantName, pRestaurant);
    }

    public Restaurant getRestaurant(String pRestaurantName) {
        return restaurantsMap.get(pRestaurantName);
    }
}
