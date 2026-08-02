package com.lld.paymentgateway;

import com.lld.paymentgateway.instrument.BankInstrument;
import com.lld.paymentgateway.instrument.CardInstrument;
import com.lld.paymentgateway.instrument.Instrument;
import com.lld.paymentgateway.instrument.InstrumentManager;
import com.lld.paymentgateway.instrument.InstrumentType;
import com.lld.paymentgateway.transaction.Transaction;
import com.lld.paymentgateway.transaction.TransactionManager;
import com.lld.paymentgateway.user.User;
import com.lld.paymentgateway.user.UserManager;

import java.util.List;

public class PaymentGateway {

    public static void main(String[] args) {

        System.out.println("\nLLD Code - Payment Gateway\n");

        UserManager userManager = new UserManager();
        InstrumentManager instrumentManager = new InstrumentManager();
        TransactionManager transactionManager = new TransactionManager();

        // Add USER1
        User user1 = new User();
        user1.setUserName("Alice");
        user1.setEmail("alice@gmail.com");
        User user1Details = userManager.addUser(user1);

        // Add USER2
        User user2 = new User();
        user2.setUserName("Bob");
        user2.setEmail("bob@gmail.com");
        User user2Details = userManager.addUser(user2);

        // Add Bank Instrument to User1
        BankInstrument bankInstrument = new BankInstrument();
        bankInstrument.setBankAccountNumber("234324234324324");
        bankInstrument.setIfscCode("ER3223E");
        bankInstrument.setUserID(user1Details.getUserID());
        bankInstrument.setInstrumentType(InstrumentType.BANK);
        Instrument user1BankInstrument = instrumentManager.addInstrument(bankInstrument);
        System.out.println("Bank Instrument created for User1: " + user1BankInstrument.getInstrumentID());

        // Add Card Instrument to User2
        CardInstrument cardInstrument = new CardInstrument();
        cardInstrument.setCardNumber("1230099");
        cardInstrument.setCvv("0000");
        cardInstrument.setUserID(user2Details.getUserID());
        cardInstrument.setInstrumentType(InstrumentType.CARD);
        Instrument user2CardInstrument = instrumentManager.addInstrument(cardInstrument);
        System.out.println("Card Instrument created for User2: " + user2CardInstrument.getInstrumentID());

        // Make Payment
        Transaction txn = new Transaction();
        txn.setTxnID(101);
        txn.setAmount(500);
        txn.setSenderID(user1Details.getUserID());
        txn.setReceiverID(user2Details.getUserID());
        txn.setDebitInstrumentID(user1BankInstrument.getInstrumentID());
        txn.setCreditInstrumentID(user2CardInstrument.getInstrumentID());
        transactionManager.makePayment(txn);

        // Get all instruments of USER1
        List<Instrument> user1Instruments = instrumentManager.getAllInstruments(user1Details.getUserID());
        for (Instrument instrument : user1Instruments) {
            System.out.println("\nUser1 Name: " + user1Details.getUserName() +
                    "; UserID: " + instrument.getUserID() +
                    "; InstrumentID: " + instrument.getInstrumentID() +
                    "; InstrumentType: " + instrument.getInstrumentType().name());
        }

        // Get all instruments of USER2
        List<Instrument> user2Instruments = instrumentManager.getAllInstruments(user2Details.getUserID());
        for (Instrument instrument : user2Instruments) {
            System.out.println("User2 Name: " + user2Details.getUserName() +
                    "; UserID: " + instrument.getUserID() +
                    "; InstrumentID: " + instrument.getInstrumentID() +
                    "; InstrumentType: " + instrument.getInstrumentType().name());
        }

        // Get transaction history of USER1
        List<Transaction> user1TxnList = transactionManager.getTransactionHistory(user1Details.getUserID());
        for (Transaction t : user1TxnList) {
            System.out.println("\nUser1 txnID: " + t.getTxnID() +
                    "; Amount: " + t.getAmount() +
                    "; Sender: " + t.getSenderID() +
                    "; Receiver: " + t.getReceiverID());
        }

        // Get transaction history of USER2
        List<Transaction> user2TxnList = transactionManager.getTransactionHistory(user2Details.getUserID());
        for (Transaction t : user2TxnList) {
            System.out.println("User2 txnID: " + t.getTxnID() +
                    "; Amount: " + t.getAmount() +
                    "; Sender: " + t.getSenderID() +
                    "; Receiver: " + t.getReceiverID());
        }
    }
}
