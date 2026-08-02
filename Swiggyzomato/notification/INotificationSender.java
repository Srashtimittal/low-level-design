package com.lld.swiggyzomato.notification;

// This class corresponds to observer interface in the observer design pattern
// Notification Mgr is the subject

public interface INotificationSender {
    void sendNotification(String pUserId, String msg);
}
