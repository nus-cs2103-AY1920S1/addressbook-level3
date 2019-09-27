package thrift.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import thrift.model.AddressBook;
import thrift.model.transaction.Expense;
import thrift.model.transaction.Income;
import thrift.model.transaction.Transaction;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalTransactions {

    public static final Expense LAKSA = new ExpenseBuilder().withDescription("Laksa").withValue("3.50")
            .withDate("13/03/1937").withTags("Lunch").build();
    public static final Expense PENANG_LAKSA = new ExpenseBuilder().withDescription("Penang Laksa1").withValue("5")
            .withDate("11/10/2010").withTags("Brunch").build();
    public static final Income BURSARY = new IncomeBuilder().withDescription("Bursary").withValue("500")
            .withDate("13/11/2011").withTags("Award").build();

    private TypicalTransactions() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Transaction transaction : getTypicalTransaction()) {
            ab.addTransaction(transaction);
        }
        return ab;
    }

    public static List<Transaction> getTypicalTransaction() {
        return new ArrayList<>(Arrays.asList(LAKSA, PENANG_LAKSA));
    }
}
