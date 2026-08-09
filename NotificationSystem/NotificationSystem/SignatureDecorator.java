// Decorator to append a signature to the content.
public class SignatureDecorator extends INotificationDecorator {
    private String signature;

    public SignatureDecorator(INotification n, String sig) {
        super(n);
        this.signature = sig;
    }

    public String getContent() {
        return notification.getContent() + "\n-- " + signature + "\n\n";
    }
}
