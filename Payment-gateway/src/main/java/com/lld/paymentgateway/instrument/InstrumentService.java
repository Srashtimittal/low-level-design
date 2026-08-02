package com.lld.paymentgateway.instrument;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class InstrumentService {

    static Map<Integer, List<Instrument>> userVsInstruments = new HashMap<>();

    public abstract Instrument addInstrument(Instrument instrument);

    public abstract List<Instrument> getInstrumentsByUserID(int userID);
}
