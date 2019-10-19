package seedu.address.model.statistics;

import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import java.util.HashMap;

/**
 * Unmodifiable view of a statistics record.
 */
public interface ReadOnlyStatisticsRecord {

    /**
     * Returns an unmodifiable view of the statistics list.
     * This list will not contain any duplicate notes.
     */
    ObservableList<Statistics> getProcessedStatistics();
}