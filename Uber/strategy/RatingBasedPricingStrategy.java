package com.lld.uber.strategy;

import com.lld.uber.common.Util;
import com.lld.uber.model.TripMetaData;

public class RatingBasedPricingStrategy implements PricingStrategy {

    @Override
    public double calculatePrice(TripMetaData tripMetaData) {
        double price = Util.isHighRating(tripMetaData.getRiderRating()) ? 55.0 : 65.0;
        System.out.println("Based on " + Util.ratingToString(tripMetaData.getRiderRating())
                + " rider rating, price of the trip is " + price);
        return price;
    }
}
