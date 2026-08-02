package com.lld.paymentgateway.transaction;

import com.lld.paymentgateway.instrument.Instrument;
import com.lld.paymentgateway.instrument.InstrumentManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionManager {

    private static Map<Integer, List<Transaction>> userVsTransactionsList = new HashMap<>();

    private InstrumentManager instrumentManager;
    private Processor processor;

    public TransactionManager() {
        this.instrumentManager = new InstrumentManager();
        this.processor = new Processor();
    }

    public Transaction makePayment(Transaction txn) {
        // validate details

        // load sender instrument
        Instrument senderInstrument = instrumentManager.getInstrumentByID(txn.getSenderID(), txn.getDebitInstrumentID());

        // load receiver instrument
        Instrument receiverInstrument = instrumentManager.getInstrumentByID(txn.getReceiverID(), txn.getCreditInstrumentID());

        // pass to processor
        processor.processPayment(senderInstrument, receiverInstrument);

        // based on processor response, set status
        txn.setStatus(TransactionStatus.SUCCESS);

        // store in history for sender
        List<Transaction> senderTxnList = userVsTransactionsList.get(txn.getSenderID());
        if (senderTxnList == null) {
            senderTxnList = new ArrayList<>();
            userVsTransactionsList.put(txn.getSenderID(), senderTxnList);
        }
        senderTxnList.add(txn);

        // store in history for receiver
        List<Transaction> receiverTxnList = userVsTransactionsList.get(txn.getReceiverID());
        if (receiverTxnList == null) {
            receiverTxnList = new ArrayList<>();
            userVsTransactionsList.put(txn.getReceiverID(), receiverTxnList);
        }
        receiverTxnList.add(txn);

        return txn;
    }

    public List<Transaction> getTransactionHistory(int userID) {
        return userVsTransactionsList.get(userID);
    }
}
