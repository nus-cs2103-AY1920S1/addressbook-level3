package seedu.address.model.statistics;

import static java.util.Objects.requireNonNull;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
     * Creates an StatisticsRecord using the Statistics in the {@code toBeCopied}
     */
    public StatisticsRecord(ReadOnlyStatisticsRecord toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Replaces the contents of the StatisticsRecord with {@code stats}.
     *
     * @param stats the Statistics object to replace existing data with.
     */
    public void setStatistics(Statistics stats) {
        requireNonNull(stats);
        internalList.setAll(stats);
    }

    /**
     * Resets the existing data of this {@code StatisticsRecord} with {@code newData}.
     */
    public void resetData(ReadOnlyStatisticsRecord newData) {
        requireNonNull(newData);
        internalList.setAll(newData.getProcessedStatistics());
    }

    //// util methods

    @Override
    public String toString() {
        return "Statistics Record is maintaining a statistics data of size: " + internalUnmodifiableList.size();
    }

    @Override
    public ObservableList<Statistics> getProcessedStatistics() {
        return internalUnmodifiableList;
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
