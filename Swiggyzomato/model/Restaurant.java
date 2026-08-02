package com.lld.swiggyzomato.model;

import com.lld.swiggyzomato.common.Location;
import com.lld.swiggyzomato.notification.NotificationMgr;
import com.lld.swiggyzomato.partner.RestaurantOwner;

import java.util.Map;

// can also store usual timings, restaurant pics...
public class Restaurant {
    private String name; // storing name as id itself for now. id should be generated and name should be passed in ctor
    private boolean isAvail;
    private Location location;
    private Menu menu;
    private RestaurantOwner owner; // can support multiple owners, but for simplicity, one owner

    public Restaurant(String pName, RestaurantOwner pOwner, Location pLoc) {
        this.name = pName;
        this.owner = pOwner;
        this.location = pLoc;
        this.isAvail = false;
        this.menu = null; // can choose to pass in the constructor but keeping it apart for now
    }

    public void addMenu(Menu pMenu) {
        this.menu = pMenu;
    }

    public String getId() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public boolean prepareFood(String pOrderId, Map<Dish, Integer> dishes) {
        System.out.println("Restaurant accepting the order and starting to prepare it");

        NotificationMgr notificationMgr = NotificationMgr.getNotificationMgr();

        notificationMgr.notify(pOrderId, "Food is being prepared in restaurant");

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        notificationMgr.notify(pOrderId, "Food is ready and can be picked up from restaurant");

        return true;
    }
}
