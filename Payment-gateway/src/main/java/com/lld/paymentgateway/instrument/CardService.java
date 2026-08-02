package com.lld.paymentgateway.instrument;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardService extends InstrumentService {

    @Override
    public Instrument addInstrument(Instrument instrument) {
        CardInstrument cardInstrument = (CardInstrument) instrument;
        cardInstrument.setInstrumentID(new Random().nextInt(100 - 10) + 10);
        cardInstrument.setInstrumentType(InstrumentType.CARD);

        List<Instrument> userInstrumentsList = userVsInstruments.get(cardInstrument.getUserID());
        if (userInstrumentsList == null) {
            userInstrumentsList = new ArrayList<>();
            userVsInstruments.put(cardInstrument.getUserID(), userInstrumentsList);
        }
        userInstrumentsList.add(cardInstrument);
        return cardInstrument;
    }

    @Override
    public List<Instrument> getInstrumentsByUserID(int userID) {
        List<Instrument> userInstruments = userVsInstruments.get(userID);
        List<Instrument> cardInstruments = new ArrayList<>();
        if (userInstruments != null) {
            for (Instrument instrument : userInstruments) {
                if (instrument.getInstrumentType() == InstrumentType.CARD)
                    cardInstruments.add(instrument);
            }
        }
        return cardInstruments;
    }
}
