package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.BankAccount;
import seedu.address.model.transaction.Transaction;

public class TypicalTransactions {

    public static final Transaction ALICE = new TransactionBuilder()
            .withAmount("100")
            .withDate("1")
            .withPeopleInvolved("ALICE").build();
    public static final Transaction BENSON = new TransactionBuilder()
            .withAmount("200")
            .withDate("2")
            .withPeopleInvolved("BENSON").build();
    public static final Transaction CARL = new TransactionBuilder()
            .withAmount("300")
            .withDate("3")
            .withPeopleInvolved("CARL").build();
    public static final Transaction DANIEL = new TransactionBuilder()
            .withAmount("400")
            .withDate("4")
            .withPeopleInvolved("DANIEL").build();
    public static final Transaction ELLE = new TransactionBuilder()
            .withAmount("500")
            .withDate("5")
            .withPeopleInvolved("ELLE").build();
    public static final Transaction FIONA = new TransactionBuilder()
            .withAmount("600")
            .withDate("6")
            .withPeopleInvolved("FIONA").build();
    public static final Transaction GEORGE = new TransactionBuilder()
            .withAmount("700")
            .withDate("7")
            .withPeopleInvolved("GEORGE").build();

    // Manually added
    public static final Transaction HOON = new TransactionBuilder()
            .withAmount("800")
            .withDate("8")
            .withPeopleInvolved("HOON").build();
    public static final Transaction IDA = new TransactionBuilder()
            .withAmount("900")
            .withDate("9")
            .withPeopleInvolved("IDA").build();

    private TypicalTransactions() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static BankAccount getTypicalBankAccount() {
        BankAccount ba = new BankAccount();
        for (Transaction transaction : getTypicalTransactions()) {
            ba.addTransaction(transaction);
        }
        return ba;
    }

    public static List<Transaction> getTypicalTransactions() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
