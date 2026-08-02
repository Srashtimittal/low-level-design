package com.lld.paymentgateway.instrument;

public abstract class Instrument {

    int instrumentID;
    int userID;
    InstrumentType instrumentType;

    public Instrument() {
    }

    public Instrument(int instrumentID, int userID, InstrumentType instrumentType) {
        this.instrumentID = instrumentID;
        this.userID = userID;
        this.instrumentType = instrumentType;
    }

    public int getInstrumentID() {
        return instrumentID;
    }

    public void setInstrumentID(int instrumentID) {
        this.instrumentID = instrumentID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public InstrumentType getInstrumentType() {
        return instrumentType;
    }

    public void setInstrumentType(InstrumentType instrumentType) {
        this.instrumentType = instrumentType;
    }
}
