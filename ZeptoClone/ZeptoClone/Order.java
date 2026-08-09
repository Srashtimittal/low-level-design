import java.util.ArrayList;
import java.util.List;

public class Order {
    private static int nextId = 1;
    public int orderId;
    public User user;
    public List<Pair<Product, Integer>> items = new ArrayList<>();
    public List<DeliveryPartner> partners = new ArrayList<>();
    public double totalAmount;

    public Order(User u) {
        orderId     = nextId++;
        user        = u;
        totalAmount = 0.0;
    }
}
