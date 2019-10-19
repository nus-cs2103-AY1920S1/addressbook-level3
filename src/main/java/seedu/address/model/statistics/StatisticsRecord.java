package seedu.address.model.statistics;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import static java.util.Objects.requireNonNull;

/**
 * Wraps all data at the statistics level
 */
public class StatisticsRecord implements ReadOnlyStatisticsRecord {

    private final ObservableList<Statistics> internalList = FXCollections.observableArrayList();
    private final ObservableList<Statistics> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    public StatisticsRecord() {
    }

    /**
     * Creates an AddressBook using the Students in the {@code toBeCopied}
     */
    public StatisticsRecord(ReadOnlyStatisticsRecord toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Replaces the contents of the Student list with {@code Students}.
     * {@code Students} must not contain duplicate Students.
     */
    public void setStatistics(Statistics stats) {
        requireNonNull(stats);
        internalList.setAll(stats);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyStatisticsRecord newData) {
        requireNonNull(newData);
        internalList.setAll(newData.getProcessedStatistics());
    }

    //// util methods

    @Override
    public String toString() {
        return internalUnmodifiableList.size() + " Statistics";
        // TODO: refine later
    }

    @Override
    public ObservableList<Statistics> getProcessedStatistics() {
        return internalUnmodifiableList;
    }


    public String getStatisticsSummary() {
        return "TodoSummary";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof StatisticsRecord // instanceof handles nulls
                && internalList.equals(((StatisticsRecord) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

}