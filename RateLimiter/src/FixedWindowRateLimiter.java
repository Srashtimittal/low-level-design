import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FixedWindowRateLimiter implements RateLimiter {
    private final int maxRequests;
    private final long windowSizeMillis;
    private final Map<String, Integer> requestCounts = new ConcurrentHashMap<>();
    private long windowStart;

    public FixedWindowRateLimiter(int maxRequests, long windowSizeMillis) {
        this.maxRequests = maxRequests;
        this.windowSizeMillis = windowSizeMillis;
        this.windowStart = System.currentTimeMillis();
    }

    @Override
    public synchronized boolean allowRequest(String userId) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - windowStart >= windowSizeMillis) {
            requestCounts.remove(userId);
            windowStart = currentTime;
        }
        requestCounts.put(userId, requestCounts.getOrDefault(userId, 0)+1);
        return requestCounts.get(userId) <= maxRequests;
    }
}
