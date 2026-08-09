public class EmailStrategy implements INotificationStrategy {
    private String emailId;

    public EmailStrategy(String emailId) {
        this.emailId = emailId;
    }

    public void sendNotification(String content) {
        // Simulate the process of sending an email notification,
        // representing the dispatch of messages to users via email.
        System.out.println("Sending email Notification to: " + emailId + "\n" + content);
    }
}
