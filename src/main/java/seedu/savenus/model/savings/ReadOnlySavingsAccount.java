package seedu.savenus.model.savings;

import javafx.collections.ObservableList;

public interface ReadOnlySavingsAccount {

    /**
     * Returns an unmodifiable view of the SavingsHistory.
     */
    ObservableList<Savings> getSavingsHistory();
}
