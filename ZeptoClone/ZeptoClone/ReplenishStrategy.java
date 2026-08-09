import java.util.Map;

public interface ReplenishStrategy {
    void replenish(InventoryManager manager, Map<Integer, Integer> itemsToReplenish);
}
