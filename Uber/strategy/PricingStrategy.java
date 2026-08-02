package com.lld.uber.strategy;

import com.lld.uber.model.TripMetaData;

public interface PricingStrategy {
    double calculatePrice(TripMetaData tripMetaData);
}
