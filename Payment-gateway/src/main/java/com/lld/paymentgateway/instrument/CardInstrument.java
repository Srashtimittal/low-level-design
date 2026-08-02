package com.lld.paymentgateway.instrument;

public class CardInstrument extends Instrument {

    String cardNumber;
    String cvv;

    public CardInstrument() {
    }

    public CardInstrument(int instrumentID, int userID, InstrumentType type, String cardNumber, String cvv) {
        super(instrumentID, userID, type);
        this.cardNumber = cardNumber;
        this.cvv = cvv;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
}
