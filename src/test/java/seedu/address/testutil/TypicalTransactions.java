package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.BankAccount;
import seedu.address.model.transaction.BankAccountOperation;

/**
 * A utility class containing a list of {@code Transaction} objects to be used in tests.
 */
public class TypicalTransactions {

    public static final BankAccountOperation ALICE = new TransactionBuilder()
            .withAmount("100")
            .withDate("19112019")
            .withTags("food")
            .build();
    public static final BankAccountOperation BENSON = new TransactionBuilder()
            .withAmount("200")
            .withDate("19112019")
            .withTags("breakfast")
            .build();
    public static final BankAccountOperation CARL = new TransactionBuilder()
            .withAmount("300")
            .withDate("19112019")
            .withTags("dinner")
            .build();
    public static final BankAccountOperation DANIEL = new TransactionBuilder()
            .withAmount("400")
            .withDate("19112019")
            .withTags("drink")
            .build();
    public static final BankAccountOperation ELLE = new TransactionBuilder()
            .withAmount("500")
            .withDate("19112019")
            .withTags("lunch")
            .build();
    public static final BankAccountOperation FIONA = new TransactionBuilder()
            .withAmount("600")
            .withDate("19112019")
            .withTags("club")
            .build();
    public static final BankAccountOperation GEORGE = new TransactionBuilder()
            .withAmount("700")
            .withDate("19112019")
            .withTags("grocery")
            .build();

    // Manually added
    public static final BankAccountOperation HOON = new TransactionBuilder()
            .withAmount("800")
            .withDate("19112019")
            .build();
    public static final BankAccountOperation IDA = new TransactionBuilder()
            .withAmount("900")
            .withDate("19112019")
            .build();

    private TypicalTransactions() {
    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static BankAccount getTypicalBankAccount() {
        BankAccount ba = new BankAccount();
        for (BankAccountOperation transaction : getTypicalTransactions()) {
            ba.addTransaction(transaction);
        }
        return ba;
    }

    public static List<BankAccountOperation> getTypicalTransactions() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
