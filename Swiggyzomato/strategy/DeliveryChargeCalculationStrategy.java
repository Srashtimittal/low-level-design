package com.lld.swiggyzomato.strategy;

import com.lld.swiggyzomato.model.DeliveryMetaData;

public interface DeliveryChargeCalculationStrategy {
    double calculateDeliveryCharge(DeliveryMetaData pDeliveryMetaData);
}
