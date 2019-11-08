package seedu.savenus.model.savings;

import javafx.collections.ObservableList;

//@@author fatclarence
/**
 * Unmodifiable view of a savings account.
 */
public interface ReadOnlySavingsHistory {

    /**
     * Returns an unmodifiable view of the SavingsHistory.
     */
    ObservableList<Savings> getSavingsHistory();

    /**
     * Returns an unmodifiable view of the SavingsHistory with only savings.
     */
    ObservableList<Savings> getSavingsOnly();

    /**
     * Returns an unmodifiable view of the SavingsHistory with only savings.
     */
    ObservableList<Savings> getWithdrawalsOnly();
}
