package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.performance.Event;

/**
 * Unmodifiable view of events
 */
public interface ReadOnlyPerformance {

    /**
     * Returns an unmodifiable view of performances.
     * This list will not contain any duplicate events.
     */
    ObservableList<Event> getPerformance();

}
