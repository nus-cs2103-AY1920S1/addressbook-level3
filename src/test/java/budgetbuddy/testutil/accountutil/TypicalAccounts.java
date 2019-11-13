package budgetbuddy.testutil.accountutil;

import java.util.List;

import budgetbuddy.model.account.Account;

/**
 * A utility class containing a list of {@code Account} objects to be used in tests.
 */
public class TypicalAccounts {

    public static final Account FOOD = new AccountBuilder().withName("food").withDescription("For food.").build();
    public static final Account JAPAN_TRIP = new AccountBuilder().withName("Japan trip")
                .withDescription("Expenses spent in Japan.").build();
    public static final Account SCHOOL_FEES = new AccountBuilder().withName("school fees")
                .withDescription("Money spent in school.").build();
    public static final Account TRANSPORT = new AccountBuilder().withName("Transport")
                .withDescription("For daily transport").build();

    public static final List<Account> ACCOUNT_LIST =
                List.of(FOOD, JAPAN_TRIP, SCHOOL_FEES, TRANSPORT);

    private TypicalAccounts() {} // prevents instantiation
}

