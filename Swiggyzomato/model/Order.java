package com.lld.swiggyzomato.model;

import com.lld.swiggyzomato.common.Location;
import com.lld.swiggyzomato.common.OrderStatus;

import java.util.Map;

public class Order {
    private User user;
    private Restaurant restaurant; // we can choose to store only ids of user and restaurant for further decoupling
    private Map<Dish, Integer> dishes; // quantity for each dish
    // We are assuming that a dish is separate from the same dish with addon
    // There are many ways to store dishes that are being ordered but
    // we should know our design assumptions properly
    private String discountCode;
    private String paymentId;
    private OrderStatus status;

    public Order(User pUser, Restaurant pRestaurant, Map<Dish, Integer> pDishes) {
        this.user = pUser;
        this.restaurant = pRestaurant;
        this.dishes = pDishes;
        this.status = OrderStatus.PLACED;
    }

    public String getUserId() {
        return user.getId();
    }

    public String getRestaurantId() {
        return restaurant.getId();
    }

    public Map<Dish, Integer> getDishes() {
        return dishes;
    }

    // Another way to get the location would be to get the entire user or location object and get location from there
    // BUT we should not expose info that is not required. Location is imp info for delivery and is imp for order
    // So, it made sense to put getters for both locations here
    public Location getUserLocation() {
        return user.getLocation();
    }

    public Location getRestaurantLocation() {
        return restaurant.getLocation();
    }
}
