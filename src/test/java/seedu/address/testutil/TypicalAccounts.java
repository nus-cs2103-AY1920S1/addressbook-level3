package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_ADRIAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PASSWORD_BARBARA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_USERNAME_ADRIAN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_USERNAME_BARBARA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AccountBook;
import seedu.address.model.account.Account;

/**
 * A utility class containing a list of {@code Account} objects to be used in tests.
 */
public class TypicalAccounts {

    public static final Account ALISON_ACCOUNT = new AccountBuilder().withUsername("alison")
            .withPassword("user123").build();
    public static final Account BENDIMEER_ACCOUNT = new AccountBuilder().withUsername("bendimeer")
            .withPassword("p2ssW0rd").build();

    public static final Account ADRIAN = new AccountBuilder().withUsername(VALID_USERNAME_ADRIAN)
            .withPassword(VALID_PASSWORD_ADRIAN).build();
    public static final Account BARBARA = new AccountBuilder().withUsername(VALID_USERNAME_BARBARA)
            .withPassword(VALID_PASSWORD_BARBARA).build();

    private TypicalAccounts() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AccountBook getTypicalAccountBook() {
        AccountBook ab = new AccountBook();
        for (Account acct : getTypicalAccount()) {
            ab.addAccount(acct);
        }
        return ab;
    }

    public static List<Account> getTypicalAccount() {
        return new ArrayList<>(Arrays.asList(ALISON_ACCOUNT, BENDIMEER_ACCOUNT));
    }
}
