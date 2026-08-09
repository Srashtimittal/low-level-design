public class BulkPurchaseDiscount extends Coupon {
    private double threshold;
    private double flatOff;
    private DiscountStrategy strat;

    public BulkPurchaseDiscount(double thr, double off) {
        this.threshold = thr;
        this.flatOff   = off;
        this.strat     = DiscountStrategyManager.getInstance()
                             .getStrategy(StrategyType.FLAT, flatOff, 0.0);
    }

    @Override
    public boolean isApplicable(Cart cart) {
        return cart.getOriginalTotal() >= threshold;
    }

    @Override
    public double getDiscount(Cart cart) {
        return strat.calculate(cart.getCurrentTotal());
    }

    @Override
    public String name() {
        return "Bulk Purchase Rs " + (int) flatOff + " off over " + (int) threshold;
    }
}
