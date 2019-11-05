package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.authentication.Authentication;
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

    /**
     * Checks if username exists.
     */
    public boolean hasUsername(Username userName) {
        requireNonNull(userName);
        // need to put in lowercase?????
        for (Account acc : listOfAccounts) {
            Username name = userName.lowerCase();
            if (acc.getUsername().equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the login details are correct.
     *
     * @param user Username of the user.
     * @param pass Password of the user.
     * @return Returns a boolean to see if the user can login successfully or not.
     */
    public boolean sameCredentials(Username user, String pass) {
        requireNonNull(user, pass);

        Authentication authenticator = new Authentication();
        for (Account acc : listOfAccounts) {
            if (acc.getUsername().equals(user) && authenticator.authenticate(pass.toCharArray(), acc.getPassword())) {
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
