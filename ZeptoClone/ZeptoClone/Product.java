public class Product {
    private int sku;
    private String name;
    private double price;

    public Product(int id, String nm, double pr) {
        sku   = id;
        name  = nm;
        price = pr;
    }

    public int getSku() {
        return this.sku;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }
}
