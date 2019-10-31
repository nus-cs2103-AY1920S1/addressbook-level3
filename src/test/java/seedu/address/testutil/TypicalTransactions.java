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

    public static final BankAccountOperation ALICE = new BankOperationBuilder()
        .withAmount("100")
        .withDate("10112019")
        .withDescription("milk")
        .withCategories("food")
        .build();
    public static final BankAccountOperation BENSON = new BankOperationBuilder()
        .withAmount("200")
        .withDate("11112019")
        .withDescription("honey")
        .withCategories("breakfast")
        .build();
    public static final BankAccountOperation CARL = new BankOperationBuilder()
        .withAmount("300")
        .withDate("12112019")
        .withDescription("chicken")
        .withCategories("dinner")
        .build();
    public static final BankAccountOperation DANIEL = new BankOperationBuilder()
        .withAmount("400")
        .withDate("13112019")
        .withDescription("oil")
        .withCategories("drink")
        .build();
    public static final BankAccountOperation ELLE = new BankOperationBuilder()
        .withAmount("500")
        .withDate("14112019")
        .withDescription("eggs")
        .withCategories("lunch")
        .build();
    public static final BankAccountOperation FIONA = new BankOperationBuilder()
        .withAmount("600")
        .withDate("15112019")
        .withDescription("onion")
        .withCategories("club")
        .build();
    public static final BankAccountOperation GEORGE = new BankOperationBuilder()
        .withAmount("700")
        .withDate("16112019")
        .withDescription("garlic")
        .withCategories("grocery")
        .build();

    // Manually added
    public static final BankAccountOperation HOON = new BankOperationBuilder()
        .withAmount("800")
        .withDate("17112019")
        .withDescription("rice")
        .build();
    public static final BankAccountOperation IDA = new BankOperationBuilder()
        .withAmount("900")
        .withDate("18112019")
        .withDescription("noodle")
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
