package seedu.savenus.model.savings;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of a savings account.
 */
public interface ReadOnlySavingsAccount {

    /**
     * Returns an unmodifiable view of the SavingsHistory.
     */
    ObservableList<Savings> getSavingsHistory();
}
