// Simple helper Pair class (since JavaFX/Android Pair might not be available)
public class Pair<K, V> {
    private K key;
    private V value;

    public Pair(K k, V v) {
        key   = k;
        value = v;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }
}
