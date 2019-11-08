package seedu.savenus.model.savings;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//@@author fatclarence
/**
 * A list of the user's savings history.
 */
public class SavingsHistoryList implements Iterable<Savings> {
    private final Comparator<Savings> savingsComparator = new Comparator<Savings>() {
        @Override
        public int compare(Savings savings, Savings t1) {
            return (t1.getTimeStamp().getTimeStampInLocalDateTime()
                    .compareTo(savings.getTimeStamp().getTimeStampInLocalDateTime()));
        }
    };
    private final ObservableList<Savings> internalSavingsHistoryList = FXCollections.observableArrayList();
    private final ObservableList<Savings> internalUnmodifiableSavingsHistoryList =
            FXCollections.unmodifiableObservableList(internalSavingsHistoryList);

    /**
    * Adds a saving into the SavingsHistory.
    * Where savings are checked for whether they are withdrawals, if they are, they will be negated only here.
    * Before the saving is added into the savings history.
    */
    void add(Savings toAdd) {
        requireNonNull(toAdd);
        if (toAdd.isWithdraw()) {
            toAdd.makeWithdraw();
        }
        internalSavingsHistoryList.add(toAdd);
    }

    /**
     * Returns the backing list as an unmodifiable {@code SavingHistory).}
     * @return ObservableList of the unmodifiable SavingHistory.
     */
    public ObservableList<Savings> asUnmodifiableObservableList() {
        FXCollections.sort(internalSavingsHistoryList, savingsComparator);
        return internalUnmodifiableSavingsHistoryList;
    }

    @Override
    public Iterator<Savings> iterator() {
        return internalSavingsHistoryList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof SavingsHistoryList
                && internalSavingsHistoryList.equals(((SavingsHistoryList) other).internalSavingsHistoryList));
    }

    @Override
    public int hashCode() {
        return internalSavingsHistoryList.hashCode();
    }

    public void setSavingsHistory(List<Savings> savings) {
        requireNonNull(savings);
        internalSavingsHistoryList.setAll(savings);
    }
}
