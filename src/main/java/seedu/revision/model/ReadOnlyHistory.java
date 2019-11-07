package seedu.revision.model;

import javafx.collections.ObservableList;
import seedu.revision.model.quiz.Statistics;

/**
 * Unmodifiable view of quiz history.
 */
public interface ReadOnlyHistory {
    /**
     * Returns an unmodifiable view of the history of statistics.
     */
    ObservableList<Statistics> getStatisticsList();
}
