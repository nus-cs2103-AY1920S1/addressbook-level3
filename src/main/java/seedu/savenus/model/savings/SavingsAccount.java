package seedu.savenus.model.savings;

import javafx.collections.ObservableList;

import static java.util.Objects.requireNonNull;

public class SavingsAccount implements ReadOnlySavingsAccount {

    // Testing this without the recommended non-static initialization blocks.
    private final SavingsHistory savingsHistory = new SavingsHistory();

    public SavingsAccount() {}

    /**
     * Creates an Menu using the foods in the {@code toBeCopied}
     */
    public SavingsAccount(ReadOnlySavingsAccount toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Resets the existing data of this {@code SavingsAccount} with {@code newSavings}.
     */
    public void resetData(ReadOnlySavingsAccount newSavings) {
        requireNonNull(newSavings);

        // Overwrite the current savings history since there have been new ones added.
        savingsHistory.setSavingsHistory(newSavings.getSavingsHistory());
    }

    @Override
    public ObservableList<Savings> getSavingsHistory() {
        return savingsHistory.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof SavingsAccount
            && savingsHistory.equals(((SavingsAccount) other).savingsHistory));
    }

    @Override
    public int hashCode() {
        return savingsHistory.hashCode();
    }
}
