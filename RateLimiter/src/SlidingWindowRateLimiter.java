import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SlidingWindowRateLimiter implements RateLimiter {
    private final int maxRequests;
    private final long windowSizeMillis;
    private final Map<String, Deque<Long>> requestLogs = new ConcurrentHashMap<>();

    public SlidingWindowRateLimiter(int maxRequests, long windowSizeMillis) {
        this.maxRequests = maxRequests;
        this.windowSizeMillis = windowSizeMillis;

    }

    @Override
    public synchronized boolean allowRequest(String userId) {
        long currentTime = System.currentTimeMillis();
        requestLogs.putIfAbsent(userId, new LinkedList<>());
        Deque<Long> timestamps = requestLogs.get(userId);
        while (!timestamps.isEmpty() && currentTime - timestamps.peek() >= windowSizeMillis) {
            timestamps.pollFirst();
        }
        if (timestamps.size() < maxRequests) {
            timestamps.add(currentTime);
            return true;
        }
        return false;
    }
}
