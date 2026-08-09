public class SMSStrategy implements INotificationStrategy {
    private String mobileNumber;

    public SMSStrategy(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void sendNotification(String content) {
        // Simulate the process of sending an SMS notification,
        // representing the dispatch of messages to users via SMS.
        System.out.println("Sending SMS Notification to: " + mobileNumber + "\n" + content);
    }
}
