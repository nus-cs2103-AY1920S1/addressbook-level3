package seedu.revision.stubs;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

import seedu.revision.model.ReadOnlyHistory;
import seedu.revision.model.quiz.Statistics;
import seedu.revision.model.quiz.StatisticsList;

/**
 * Wraps all data of statistics.
 */
public class HistoryStub implements ReadOnlyHistory {

    private final StatisticsList statistics;

    {
        statistics = new StatisticsList();
    }

    public HistoryStub() {
    }

    /**
     * Creates a quiz history using the Statistics in the {@code toBeCopied}
     */
    public HistoryStub(ReadOnlyHistory toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the statistics list with {@code statistics}.
     */
    public void setStatistics(List<Statistics> statistics) {
        this.statistics.setStatistics(statistics);
    }

    /**
     * Resets the existing data of this {@code History} with {@code newData}.
     */
    public void resetData(ReadOnlyHistory newData) {
        requireNonNull(newData);
        setStatistics(newData.getStatisticsList());
    }

    /**
     * Adds a statistics to the history.
     */
    public void addStatistics(Statistics s) {
        statistics.add(s);
    }

    //// util methods

    @Override
    public String toString() {
        return statistics.asUnmodifiableObservableList().size() + " quiz attempts in total";
        // TODO: refine later
    }


    /**
     * Returns the total number of quiz attempts shown from history.
     *
     * @return total number of statistics
     */
    public int size() {
        return statistics.asUnmodifiableObservableList().size();
    }

    /**
     * Returns an unmodifiable view of the list of statistics.
     */
    @Override
    public ObservableList<Statistics> getStatisticsList() {
        return statistics.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof HistoryStub // instanceof handles nulls
                && statistics.equals(((HistoryStub) other).statistics));
    }

    @Override
    public int hashCode() {
        return statistics.hashCode();
    }
}
