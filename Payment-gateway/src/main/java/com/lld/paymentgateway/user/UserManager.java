package com.lld.paymentgateway.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserManager {

    private static List<User> usersList = new ArrayList<>();

    public User addUser(User user) {
        user.setUserID(new Random().nextInt(100 - 10) + 10);
        usersList.add(user);
        return user;
    }

    public User getUser(int userID) {
        for (User user : usersList) {
            if (user.getUserID() == userID) {
                return user;
            }
        }
        return null;
    }
}
