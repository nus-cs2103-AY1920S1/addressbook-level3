package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.account.Account;

/**
 * Unmodifiable view of an account book
 */
public interface ReadOnlyAccountBook {

    /**
     * Returns an unmodifiable view of the account list.
     * This list will not contain any duplicate accounts.
     */
    ObservableList<Account> getAccountList();
}
