package seedu.savenus.model.savings;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;

/**
 * A savings account to track the user's savings.
 */
public class SavingsAccount implements ReadOnlySavingsAccount {

    // Testing this without the recommended non-static initialization blocks.
    private final SavingsHistory savingsHistory;

    public SavingsAccount() {
        savingsHistory = new SavingsHistory();
    }

    /**
     * Creates an Menu using the foods in the {@code toBeCopied}
     */
    public SavingsAccount(ReadOnlySavingsAccount toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Adds a saving into the savings account.
     */
    public void addSavings(Savings savings) {
        savingsHistory.add(savings);
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
