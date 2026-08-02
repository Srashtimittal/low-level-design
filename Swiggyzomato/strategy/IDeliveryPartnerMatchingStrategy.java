package com.lld.swiggyzomato.strategy;

import com.lld.swiggyzomato.model.DeliveryMetaData;
import com.lld.swiggyzomato.partner.DeliveryPartner;

import java.util.List;

public interface IDeliveryPartnerMatchingStrategy {
    List<DeliveryPartner> matchDeliveryPartners(DeliveryMetaData pDeliveryMetaData);
}
