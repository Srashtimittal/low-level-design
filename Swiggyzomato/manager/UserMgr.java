package com.lld.swiggyzomato.manager;

import com.lld.swiggyzomato.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserMgr {

    private static UserMgr userMgrInstance = null;
    private static final Object mtx = new Object();
    private final Map<String, User> usersMap = new HashMap<>();

    private UserMgr() {
    }

    public static UserMgr getUserMgr() {
        if (userMgrInstance == null) {
            synchronized (mtx) {
                if (userMgrInstance == null) {
                    userMgrInstance = new UserMgr();
                }
            }
        }
        return userMgrInstance;
    }

    public void addUser(String pUserName, User pUser) {
        usersMap.put(pUserName, pUser);
    }

    public User getUser(String pUserName) {
        return usersMap.get(pUserName);
    }
}
