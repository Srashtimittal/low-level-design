import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LeakyBucketRateLimiter implements RateLimiter {
    private final int capacity;
    private final long leakRateSecs;
    private final Queue<Long> bucket = new LinkedList<>();
    private final ScheduledExecutorService scheduledExecutorService;

    public LeakyBucketRateLimiter(int capacity, long leakRateSecs) {
        this.capacity = capacity;
        this.leakRateSecs = leakRateSecs;
        this.scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(this::leakRequests, 0, leakRateSecs, TimeUnit.SECONDS);
    }

    @Override
    public synchronized boolean allowRequest(String userId) {
        long currentTime = System.currentTimeMillis();
        if (bucket.size() < capacity) {
            bucket.offer(currentTime);
            return true;
        }
        return false;
    }

    private synchronized void leakRequests() {
        if (!bucket.isEmpty()) {
            bucket.poll();//Request processed
        }
    }
}
