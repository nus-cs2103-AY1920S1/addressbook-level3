package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.UserState;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.transaction.Budget;
import seedu.address.model.transaction.LedgerOperation;

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

    public static final BankAccountOperation JANE = new BankOperationBuilder()
            .withAmount("1000")
            .withDate("19112019")
            .withDescription("bread")
            .build();

    private TypicalTransactions() {
    } // prevents instantiation

    /**
     * Returns a {@code UserState} with all the typical bank account operations.
     */
    public static UserState getTypicalUserState() {
        UserState ba = new UserState();
        for (BankAccountOperation op : getTypicalTransactions()) {
            ba.add(op);
        }
        return ba;
    }

    /**
     * Returns a {@code BankAccount} with all the typical persons in unsorted amount order.
     */
    public static UserState getTypicalUnsortedUserState() {
        UserState unsortedBa = new UserState();
        for (BankAccountOperation op : getTypicalUnsortedTransactions()) {
            unsortedBa.add(op);
        }
        return unsortedBa;
    }

    public static List<BankAccountOperation> getTypicalTransactions() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    public static List<BankAccountOperation> getTypicalUnsortedTransactions() {
        return new ArrayList<>(Arrays.asList(GEORGE, FIONA, ELLE, DANIEL, CARL, BENSON, ALICE));
    }

    public static List<BankAccountOperation> getOneToTenTypicalTransactions(int size) {
        switch(size) {
        case 1:
            return new ArrayList<>(Arrays.asList(ALICE));
        case 2:
            return new ArrayList<>(Arrays.asList(ALICE, BENSON));
        case 3:
            return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL));
        case 4:
            return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL));
        case 5:
            return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE));
        case 6:
            return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA));
        case 7:
            return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
        case 8:
            return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE, HOON));
        case 9:
            return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE, HOON, IDA));
        case 10:
            return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE, HOON, IDA, JANE));
        default:
            return new ArrayList<>();
        }
    }

    //TODO: implement:
    public static List<LedgerOperation> getTypicalLedgerOperations() {
        return null;
    }

    //TODO: implement:
    public static List<Budget> getTypicalBudget() {
        return null;
    }
}
