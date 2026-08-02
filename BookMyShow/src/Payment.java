public class Payment {

    private final PaymentStatus status;

    public Payment(PaymentStatus status) {
        this.status = status;
    }

    public PaymentStatus getStatus() {
        return status;
    }
}
