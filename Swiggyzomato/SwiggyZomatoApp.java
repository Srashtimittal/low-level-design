package com.lld.swiggyzomato;

import com.lld.swiggyzomato.common.Cuisine;
import com.lld.swiggyzomato.common.Location;
import com.lld.swiggyzomato.manager.DeliveryPartnerMgr;
import com.lld.swiggyzomato.manager.OrderMgr;
import com.lld.swiggyzomato.manager.RestaurantMgr;
import com.lld.swiggyzomato.manager.UserMgr;
import com.lld.swiggyzomato.model.Dish;
import com.lld.swiggyzomato.model.DishAddOn;
import com.lld.swiggyzomato.model.Menu;
import com.lld.swiggyzomato.model.Order;
import com.lld.swiggyzomato.model.Restaurant;
import com.lld.swiggyzomato.model.User;
import com.lld.swiggyzomato.partner.DeliveryPartner;
import com.lld.swiggyzomato.partner.RestaurantOwner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SwiggyZomatoApp {

    public static void main(String[] args) {
        // Chinese Restaurant
        RestaurantOwner owner1 = new RestaurantOwner("owner1");
        Restaurant chineseRest = new Restaurant("chinese vala", owner1, new Location(1, 2));
        Dish noodles = new Dish("noodles", Cuisine.CHINESE, 200);
        noodles.addAddOn(new DishAddOn("premium sauce", 20));
        Dish fried_rice = new Dish("fried rice", Cuisine.CHINESE, 180);
        Dish spring_rolls = new Dish("spring rolls", Cuisine.CHINESE, 120);
        Menu chinese_menu = new Menu(Arrays.asList(noodles, fried_rice, spring_rolls));
        chineseRest.addMenu(chinese_menu);

        // South Indian Restaurant
        RestaurantOwner owner2 = new RestaurantOwner("owner2");
        Restaurant southIndianRest = new Restaurant("south indian food", owner2, new Location(2, 3));
        Dish idli = new Dish("idli", Cuisine.SOUTH_INDIAN, 200);
        Dish dosa = new Dish("dosa", Cuisine.SOUTH_INDIAN, 180);
        Dish uthappam = new Dish("uthappam", Cuisine.SOUTH_INDIAN, 120);
        Menu south_indian_menu = new Menu(Arrays.asList(idli, dosa, uthappam));
        southIndianRest.addMenu(south_indian_menu);

        // Note that restaurant owners can exist without restaurants, we have used aggregation relationship
        // This can lead to owners being present with no restaurants and thus not added in restaurant manager
        // This is how we have designed and we should know consequences of the way we have structured.

        RestaurantMgr restaurantMgr = RestaurantMgr.getRestaurantMgr();
        restaurantMgr.addRestaurant("chinese vala", chineseRest);
        restaurantMgr.addRestaurant("south indian food", southIndianRest);

        // //////////////////////////////////////////////////////////////////////////////////////////////////

        DeliveryPartner alpha = new DeliveryPartner("alpha");
        DeliveryPartner beta = new DeliveryPartner("beta");
        DeliveryPartner gamma = new DeliveryPartner("gamma");

        DeliveryPartnerMgr deliveryPartnerMgr = DeliveryPartnerMgr.getDeliveryPartnerMgr();
        deliveryPartnerMgr.addDeliveryPartner("alpha", alpha);
        deliveryPartnerMgr.addDeliveryPartner("beta", beta);
        deliveryPartnerMgr.addDeliveryPartner("gamma", gamma);

        // //////////////////////////////////////////////////////////////////////////////////////////////////

        User keerti = new User("keerti", new Location(10, 11));
        User gaurav = new User("gaurav", new Location(13, 14));
        User yogita = new User("yogita", new Location(15, 16));
        // users can exist without usermgr as well. That's why it is an aggregation relationship

        UserMgr userMgr = UserMgr.getUserMgr();
        userMgr.addUser("keerti", keerti);
        userMgr.addUser("gaurav", gaurav);
        userMgr.addUser("yogita", yogita);

        // //////////////////////////////////////////////////////////////////////////////////////////////////

        // I am passing same dish object that was created by res, for C++ folks - it should be different dish object

        Map<Dish, Integer> cart = new HashMap<>();
        cart.put(noodles, 2);
        cart.put(fried_rice, 1);
        Order order1 = new Order(keerti, chineseRest, cart);

        OrderMgr orderMgr = OrderMgr.getOrderMgr();
        orderMgr.createOrder("order1", order1); // Ideally, the id should be created in order manager when order is created
                                                  // This is just for simplicity purposes and has been mentioned in the class as well
                                                  // We have done same for all ids - user, restaurant, delivery partner etc.
    }
}
