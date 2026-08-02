package com.lld.paymentgateway.instrument;

import java.util.ArrayList;
import java.util.List;

public class InstrumentManager {

    private InstrumentServiceFactory instrumentServiceFactory;

    public InstrumentManager() {
        this.instrumentServiceFactory = new InstrumentServiceFactory();
    }

    public Instrument addInstrument(Instrument instrument) {
        InstrumentService instrumentService = instrumentServiceFactory.getInstrumentService(instrument.getInstrumentType());
        return instrumentService.addInstrument(instrument);
    }

    public List<Instrument> getAllInstruments(int userID) {
        InstrumentService bankService = instrumentServiceFactory.getInstrumentService(InstrumentType.BANK);
        InstrumentService cardService = instrumentServiceFactory.getInstrumentService(InstrumentType.CARD);

        List<Instrument> allInstruments = new ArrayList<>();
        allInstruments.addAll(bankService.getInstrumentsByUserID(userID));
        allInstruments.addAll(cardService.getInstrumentsByUserID(userID));
        return allInstruments;
    }

    public Instrument getInstrumentByID(int userID, int instrumentID) {
        List<Instrument> instruments = getAllInstruments(userID);
        for (Instrument instrument : instruments) {
            if (instrument.getInstrumentID() == instrumentID)
                return instrument;
        }
        return null;
    }
}
