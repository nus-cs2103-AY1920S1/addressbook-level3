package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.ReadOnlyUserState;
import seedu.address.model.UserState;
import seedu.address.model.category.Category;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.BankAccountOperation;
import seedu.address.model.transaction.Description;
import seedu.address.model.transaction.InTransaction;
import seedu.address.model.transaction.OutTransaction;

/**
 * Contains utility methods for populating {@code UserState} with sample data.
 */
public class SampleDataUtil {
    public static BankAccountOperation[] getSampleTransactions() {
        return new BankAccountOperation[]{
            new InTransaction(new Amount(100), Date.now(), new Description("a")),
            new OutTransaction(new Amount(44.44), Date.now(), new Description("b")),
            new OutTransaction(new Amount(23.3), Date.now(), new Description("c")),
            new InTransaction(new Amount(34.01), Date.now(), new Description("d")),
            new OutTransaction(new Amount(9.99), Date.now(), new Description("e"))
        };
    }

    /**
     * Returns a sample UserState containing sample transactions.
     */
    public static ReadOnlyUserState getSampleAccount() {
        UserState sampleUserState = new UserState();
        for (BankAccountOperation sampleTxn : getSampleTransactions()) {
            sampleUserState.add(sampleTxn);
        }
        return sampleUserState;
    }

    /**
     * Returns a category set containing the list of strings given.
     */
    public static Set<Category> getCategorySet(String... strings) {
        return Arrays.stream(strings)
            .map(Category::new)
            .collect(Collectors.toSet());
    }

}
