package seedu.address.model.statistics;

import javafx.collections.ObservableList;

/**
 * Unmodifiable view of a statistics record.
 */
public interface ReadOnlyStatisticsRecord {

    /**
     * Returns an unmodifiable view of the statistics list.
     * This list contains only one Statistics object at once.
     */
    ObservableList<Statistics> getProcessedStatistics();
}
