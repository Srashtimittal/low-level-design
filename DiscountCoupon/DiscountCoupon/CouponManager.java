import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CouponManager {
    private static CouponManager instance;
    private Coupon head;
    private final Lock lock = new ReentrantLock();

    private CouponManager() {
        this.head = null;
    }

    public static synchronized CouponManager getInstance() {
        if (instance == null) {
            instance = new CouponManager();
        }
        return instance;
    }

    public void registerCoupon(Coupon coupon) {
        lock.lock();
        try {
            if (head == null) {
                head = coupon;
            } else {
                Coupon cur = head;
                while (cur.getNext() != null) {
                    cur = cur.getNext();
                }
                cur.setNext(coupon);
            }
        } finally {
            lock.unlock();
        }
    }

    public List<String> getApplicable(Cart cart) {
        lock.lock();
        try {
            List<String> res = new ArrayList<>();
            Coupon cur = head;
            while (cur != null) {
                if (cur.isApplicable(cart)) {
                    res.add(cur.name());
                }
                cur = cur.getNext();
            }
            return res;
        } finally {
            lock.unlock();
        }
    }

    public double applyAll(Cart cart) {
        lock.lock();
        try {
            if (head != null) {
                head.applyDiscount(cart);
            }
            return cart.getCurrentTotal();
        } finally {
            lock.unlock();
        }
    }
}
