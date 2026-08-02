package com.lld.paymentgateway.transaction;

import com.lld.paymentgateway.instrument.Instrument;

public class Processor {

    public void processPayment(Instrument senderInstrument, Instrument receiverInstrument) {

        // validate sender instrument

        // validate receiver instrument

        // process payment

        // update balance in sender instrument - debit

        // update balance in receiver instrument - credit
    }
}
