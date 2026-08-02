package com.lld.swiggyzomato.strategy;

import com.lld.swiggyzomato.model.DeliveryMetaData;

public class LocBasedDeliveryChargecalculationStrategy implements DeliveryChargeCalculationStrategy {
    @Override
    public double calculateDeliveryCharge(DeliveryMetaData pDeliveryMetaData) {
        System.out.println("Delivery charge is decided based on location. Setting to 20 as default value for now");
        return 20.0;
    }
}
