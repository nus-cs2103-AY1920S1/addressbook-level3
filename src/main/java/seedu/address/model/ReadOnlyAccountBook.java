package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.account.Account;

public interface ReadOnlyAccountBook {

    /**
     * Returns an unmodifiable view of the account list.
     * This list will not contain any duplicate accounts.
     */
    ObservableList<Account> getAccountList();
}
