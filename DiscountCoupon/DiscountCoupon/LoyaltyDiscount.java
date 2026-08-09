public class LoyaltyDiscount extends Coupon {
    private double percent;
    private DiscountStrategy strat;

    public LoyaltyDiscount(double pct) {
        this.percent = pct;
        this.strat   = DiscountStrategyManager.getInstance()
                            .getStrategy(StrategyType.PERCENT, percent, 0.0);
    }

    @Override
    public boolean isApplicable(Cart cart) {
        return cart.isLoyaltyMember();
    }

    @Override
    public double getDiscount(Cart cart) {
        return strat.calculate(cart.getCurrentTotal());
    }

    @Override
    public String name() {
        return "Loyalty Discount " + (int) percent + "% off";
    }
}
