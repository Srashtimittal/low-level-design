package com.lld.uber.strategy;

import com.lld.uber.model.Driver;
import com.lld.uber.model.TripMetaData;

public interface DriverMatchingStrategy {
    Driver matchDriver(TripMetaData tripMetaData);
}
