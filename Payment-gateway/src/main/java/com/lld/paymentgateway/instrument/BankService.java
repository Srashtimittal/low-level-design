package com.lld.paymentgateway.instrument;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BankService extends InstrumentService {

    @Override
    public Instrument addInstrument(Instrument instrument) {
        BankInstrument bankInstrument = (BankInstrument) instrument;
        bankInstrument.setInstrumentID(new Random().nextInt(100 - 10) + 10);
        bankInstrument.setInstrumentType(InstrumentType.BANK);

        List<Instrument> userInstrumentsList = userVsInstruments.get(bankInstrument.getUserID());
        if (userInstrumentsList == null) {
            userInstrumentsList = new ArrayList<>();
            userVsInstruments.put(bankInstrument.getUserID(), userInstrumentsList);
        }
        userInstrumentsList.add(bankInstrument);
        return bankInstrument;
    }

    @Override
    public List<Instrument> getInstrumentsByUserID(int userID) {
        List<Instrument> userInstruments = userVsInstruments.get(userID);
        List<Instrument> bankInstruments = new ArrayList<>();
        if (userInstruments != null) {
            for (Instrument instrument : userInstruments) {
                if (instrument.getInstrumentType() == InstrumentType.BANK)
                    bankInstruments.add(instrument);
            }
        }
        return bankInstruments;
    }
}
