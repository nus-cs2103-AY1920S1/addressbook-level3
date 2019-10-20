package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import seedu.address.model.BankAccount;
import seedu.address.model.tag.Tag;
import seedu.address.model.transaction.Transaction;

/**
 * A utility class containing a list of {@code Transaction} objects to be used in tests.
 */
public class TypicalTransactions {

    public static final Transaction ALICE = new TransactionBuilder()
            .withAmount("100")
            .withDate("19112019")
            .withTags("food")
            .withPeopleInvolved("ALICE").build();
    public static final Transaction BENSON = new TransactionBuilder()
            .withAmount("200")
            .withDate("19112019")
            .withTags("breakfast")
            .withPeopleInvolved("BENSON").build();
    public static final Transaction CARL = new TransactionBuilder()
            .withAmount("300")
            .withDate("19112019")
            .withTags("dinner")
            .withPeopleInvolved("CARL").build();
    public static final Transaction DANIEL = new TransactionBuilder()
            .withAmount("400")
            .withDate("19112019")
            .withTags("drink")
            .withPeopleInvolved("DANIEL").build();
    public static final Transaction ELLE = new TransactionBuilder()
            .withAmount("500")
            .withDate("19112019")
            .withTags("lunch")
            .withPeopleInvolved("ELLE").build();
    public static final Transaction FIONA = new TransactionBuilder()
            .withAmount("600")
            .withDate("19112019")
            .withTags("club")
            .withPeopleInvolved("FIONA").build();
    public static final Transaction GEORGE = new TransactionBuilder()
            .withAmount("700")
            .withDate("19112019")
            .withTags("grocery")
            .withPeopleInvolved("GEORGE").build();

    // Manually added
    public static final Transaction HOON = new TransactionBuilder()
            .withAmount("800")
            .withDate("19112019")
            .withPeopleInvolved("HOON").build();
    public static final Transaction IDA = new TransactionBuilder()
            .withAmount("900")
            .withDate("19112019")
            .withPeopleInvolved("IDA").build();

    private TypicalTransactions() {
    } // prevents instantiation

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
