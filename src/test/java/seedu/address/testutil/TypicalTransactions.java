package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;

import seedu.address.transaction.model.Transaction;
import seedu.address.transaction.util.TransactionList;

public class TypicalTransactions {
    public static final Transaction ALICE_TRANSACTION_1 = new TransactionBuilder(TypicalPersons.ALICE)
            .withId(1)
            .withAmount(99.0)
            .withDate("01-Jan-2019")
            .build();
    public static final Transaction ALICE_TRANSACTION_3 = new TransactionBuilder(TypicalPersons.ALICE)
            .withId(3)
            .withAmount(20.0)
            .withDate("14-Feb-2019")
            .build();
    public static final Transaction ALICE_TRANSACTION_4 = new TransactionBuilder(TypicalPersons.ALICE)
            .withCategory("events")
            .withDescription("Room rental")
            .withAmount(11)
            .withId(4)
            .withDate("04-Sep-2019")
            .build();
    public static final Transaction BENSON_TRANSACTION_2 = new TransactionBuilder(TypicalPersons.BENSON)
            .withId(2)
            .withDate("03-Sep-2019")
            .withAmount(2)
            .build();
    public static final Transaction CARL_TRANSACTION_5 = new TransactionBuilder(TypicalPersons.CARL)
            .withId(5)
            .withAmount(22.0)
            .withDate("02-Oct-2019")
            .build();
    public static final Transaction ELLE_TRANSACTION_6 = new TransactionBuilder(TypicalPersons.ELLE)
            .withId(6)
            .withAmount(13)
            .withDescription("food")
            .withDate("12-Jun-2019")
            .build();
    public static final Transaction GEORGE_TRANSACTION_7 = new TransactionBuilder(TypicalPersons.GEORGE)
            .withId(7)
            .withDate("01-Jun-2019")
            .withAmount(12)
            .build();
    public static final Transaction FIONA_TRANSACTION_8 = new TransactionBuilder(TypicalPersons.FIONA)
            .withId(8)
            .withAmount(33.0)
            .withDate("13-Feb-2019")
            .build();
    /*public static final Transaction DANIEL_TRANSACTION_9 = new TransactionBuilder(TypicalPersons.ALICE)
            .withId(9).withAmount(44.0).build();*/

    /**
     * Returns an {@code TransactionList} with all the typical transactions.
     */
    public static TransactionList getTypicalTransactionList() {
        /*TransactionList tl = new TransactionList();
        for (Transaction transaction : getTypicalTransactions()) {
            tl.add(transaction);
        }
        return tl;*/
        return new TransactionList(getTypicalTransactions());
    }

    public static ArrayList<Transaction> getTypicalTransactions() {
        return new ArrayList<>(Arrays.asList(ALICE_TRANSACTION_1, BENSON_TRANSACTION_2,
                ALICE_TRANSACTION_3, ALICE_TRANSACTION_4, CARL_TRANSACTION_5, ELLE_TRANSACTION_6, GEORGE_TRANSACTION_7,
                FIONA_TRANSACTION_8));
    }

    public static TransactionList getAmountSortedTransactionList() {
        ArrayList<Transaction> transactionArrayList = new ArrayList<>(Arrays.asList(ALICE_TRANSACTION_1,
                FIONA_TRANSACTION_8, CARL_TRANSACTION_5, ALICE_TRANSACTION_3, ELLE_TRANSACTION_6,
                GEORGE_TRANSACTION_7, ALICE_TRANSACTION_4, BENSON_TRANSACTION_2));
        return new TransactionList(transactionArrayList);
    }

    public static TransactionList getNameSortedTransactionList() {
        ArrayList<Transaction> transactionArrayList = new ArrayList<>(Arrays.asList(ALICE_TRANSACTION_1,
                ALICE_TRANSACTION_3, ALICE_TRANSACTION_4,BENSON_TRANSACTION_2,  CARL_TRANSACTION_5,
                ELLE_TRANSACTION_6, FIONA_TRANSACTION_8, GEORGE_TRANSACTION_7));
        return new TransactionList(transactionArrayList);
    }

    public static TransactionList getDateSortedTransactionList() {
        ArrayList<Transaction> transactionArrayList = new ArrayList<>(Arrays.asList(ALICE_TRANSACTION_1,
                FIONA_TRANSACTION_8, ALICE_TRANSACTION_3, GEORGE_TRANSACTION_7, ELLE_TRANSACTION_6,
                BENSON_TRANSACTION_2, ALICE_TRANSACTION_4,  CARL_TRANSACTION_5));
        return new TransactionList(transactionArrayList);
    }

}
