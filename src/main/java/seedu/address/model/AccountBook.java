package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.account.Account;
import seedu.address.model.account.Username;

/**
 * Wraps all data at the account-book level
 * Duplicates are not allowed (by .isSameAccount comparison)
 */
public class AccountBook {

    private final List<Account> listOfAccounts;

    public AccountBook() {
        listOfAccounts = new ArrayList<>();
    }

    /**
     * Returns true if an account with the same identity as {@code account} exists in the account book.
     */
    public boolean hasAccount(Account acc) {
        requireNonNull(acc);
        return listOfAccounts.contains(acc);
    }

    public void addAccount(Account a) {
        listOfAccounts.add(a);
    }

    public List<Account> getList() {
        return listOfAccounts;
    }

    public boolean hasUsername(Username userName) {
        requireNonNull(userName);
        // need to put in lowercase?????
        for (Account acc : listOfAccounts) {
            if (acc.getUsername().equals(userName)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AccountBook // instanceof handles nulls
                && listOfAccounts.equals(((AccountBook) other).listOfAccounts));
    }

    @Override
    public int hashCode() {
        return listOfAccounts.hashCode();
    }
}
