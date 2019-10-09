package seedu.address.model.events;

import javafx.collections.ObservableList;

/**
 * Represents an unmodifiable EventList.
 */
public interface ReadOnlyEventList {
    ObservableList<EventSource> getReadOnlyList();
}
