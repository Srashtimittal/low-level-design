import java.util.List;

public interface InventoryStore {
    void addProduct(Product prod, int qty);
    void removeProduct(int sku, int qty);
    int checkStock(int sku);
    List<Product> listAvailableProducts();
}
