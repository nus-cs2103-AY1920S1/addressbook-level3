package budgetbuddy.model;

import budgetbuddy.model.account.Account;
import javafx.collections.ObservableList;

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
