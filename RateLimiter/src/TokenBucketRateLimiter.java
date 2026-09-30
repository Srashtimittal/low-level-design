import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TokenBucketRateLimiter implements RateLimiter {
    private final int capacity;
    private final double refillRate;
    private final Map<String, Integer> tokens = new ConcurrentHashMap<>();
    private final Map<String, Long> lastRefillTimestamp = new ConcurrentHashMap<>();

    public TokenBucketRateLimiter(int capacity, double refillRate) {
        this.capacity = capacity;
        this.refillRate = refillRate;
    }

    @Override
    public synchronized boolean allowRequest(String userId) {
        long currentTime = System.currentTimeMillis();
        lastRefillTimestamp.putIfAbsent(userId, currentTime);
        tokens.putIfAbsent(userId, capacity);
        long lastRefill = lastRefillTimestamp.get(userId);
        long elapsedTime = (currentTime - lastRefill) / 1000;
        if (elapsedTime > 0) {
            int newTokens = Math.min(capacity, tokens.get(userId) + (int)(elapsedTime*refillRate));
            tokens.put(userId, newTokens);
            lastRefillTimestamp.put(userId, currentTime);
        }
        if (tokens.get(userId) > 0) {
            tokens.put(userId, tokens.get(userId)-1);
            return true;
        }
        return false;
    }
}
