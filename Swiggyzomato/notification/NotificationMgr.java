package com.lld.swiggyzomato.notification;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// This class corresponds to subject in the observer design pattern
// NotificationSender is the observer interface

public class NotificationMgr {

    private static NotificationMgr notificationMgrInstance = null;
    private static final Object mtx = new Object();

    private final Map<String, List<AbstractMap.SimpleEntry<String, INotificationSender>>> notificationSendersMap = new HashMap<>();

    private NotificationMgr() {
    }

    public static NotificationMgr getNotificationMgr() {
        if (notificationMgrInstance == null) {
            synchronized (mtx) {
                if (notificationMgrInstance == null) {
                    notificationMgrInstance = new NotificationMgr();
                }
            }
        }
        return notificationMgrInstance;
    }

    // register observer
    public void addNotificationSender(String pOrderId, String pUserId, INotificationSender pNotificationSender) {
        notificationSendersMap.computeIfAbsent(pOrderId, k -> new ArrayList<>());
        List<AbstractMap.SimpleEntry<String, INotificationSender>> senders = notificationSendersMap.get(pOrderId);

        boolean found = false;
        for (AbstractMap.SimpleEntry<String, INotificationSender> entry : senders) {
            if (entry.getKey().equals(pUserId) && entry.getValue().equals(pNotificationSender)) {
                found = true;
                break;
            }
        }
        if (!found) {
            // making sure the sender is already not there in the vector
            // if this check is not put, then multiple notifications will be sent by same sender
            senders.add(new AbstractMap.SimpleEntry<>(pUserId, pNotificationSender));
        }
    }

    // unregister observer
    public void removeNotificationSender(String pOrderId, String pUserId, INotificationSender pNotificationSender) {
        notificationSendersMap.computeIfAbsent(pOrderId, k -> new ArrayList<>());
        List<AbstractMap.SimpleEntry<String, INotificationSender>> senders = notificationSendersMap.get(pOrderId);

        AbstractMap.SimpleEntry<String, INotificationSender> senderPos = null;
        for (AbstractMap.SimpleEntry<String, INotificationSender> entry : senders) {
            if (entry.getKey().equals(pUserId) && entry.getValue().equals(pNotificationSender)) {
                senderPos = entry;
                break;
            }
        }
        if (senderPos != null) {
            senders.remove(senderPos);
        }
    }

    // notify Observers
    public void notify(String pOrderId, String pMsg) {
        notificationSendersMap.computeIfAbsent(pOrderId, k -> new ArrayList<>());
        for (AbstractMap.SimpleEntry<String, INotificationSender> sender : notificationSendersMap.get(pOrderId)) {
            sender.getValue().sendNotification(sender.getKey(), pMsg);
        }
    }

    // notify particular user by a particular method
    public void notifyParticularUser(String pUserId, String pMsg, INotificationSender sender) {
        sender.sendNotification(pUserId, pMsg);
    }
}
