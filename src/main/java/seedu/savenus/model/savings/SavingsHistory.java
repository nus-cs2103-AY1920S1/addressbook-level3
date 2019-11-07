package seedu.savenus.model.savings;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;

//@@author fatclarence
/**
 * A savings account to track the user's savings.
 */
public class SavingsHistory implements ReadOnlySavingsHistory {

    private final SavingsHistoryList savingsHistoryList;

    public SavingsHistory() {
        savingsHistoryList = new SavingsHistoryList();
    }

    /**
     * Creates a SavingsHistory with the {@code toBeCopied}
     */
    public SavingsHistory(ReadOnlySavingsHistory toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Adds a saving into the savings History.
     */
    public void addToHistory(Savings savings) {
        savingsHistoryList.add(savings);
    }

    /**
     * Resets the existing data of this {@code SavingsAccount} with {@code newSavings}.
     */
    public void resetData(ReadOnlySavingsHistory newSavingsHistory) {
        requireNonNull(newSavingsHistory);

        // Overwrite the current savings history since there have been new ones added.
        savingsHistoryList.setSavingsHistory(newSavingsHistory.getSavingsHistory());
    }

    @Override
    public ObservableList<Savings> getSavingsHistory() {
        return savingsHistoryList.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SavingsHistory
                && savingsHistoryList.equals(((SavingsHistory) other).savingsHistoryList));
    }

    @Override
    public int hashCode() {
        return savingsHistoryList.hashCode();
    }
}
