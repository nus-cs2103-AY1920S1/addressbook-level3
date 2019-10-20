package seedu.savenus.model.savings;

import static java.util.Objects.requireNonNull;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Iterator;
import java.util.List;

public class SavingsHistory implements Iterable<Savings> {

    private final ObservableList<Savings> internalSavingsHistory = FXCollections.observableArrayList();
    private final ObservableList<Savings> internalUnmodifiableSavingsHistory =
            FXCollections.unmodifiableObservableList(internalSavingsHistory);

    /**
     * Adds a saving into the SavingsHistory.
     */
    public void add(Savings toAdd) {
        requireNonNull(toAdd);
        internalSavingsHistory.add(toAdd);
    }

    /**
     * Returns the backing list as an unmodifiable {@code SavingHistory).}
     * @return ObservableList of the unmodifiable SavingHistory.
     */
    public ObservableList<Savings> asUnmodifiableObservableList() {
        return internalUnmodifiableSavingsHistory;
    }

    @Override
    public Iterator<Savings> iterator() {
        return internalSavingsHistory.iterator();
    }

    // For Tests
    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof SavingsHistory
                && internalSavingsHistory.equals(((SavingsHistory) other).internalSavingsHistory));
    }

    @Override
    public int hashCode() {
        return internalSavingsHistory.hashCode();
    }

    public void setSavingsHistory(List<Savings> savings) {
        requireNonNull(savings);

        internalSavingsHistory.setAll(savings);
    }
}
