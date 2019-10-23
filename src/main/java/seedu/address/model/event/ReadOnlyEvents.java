package seedu.address.model.event;

import java.util.List;

/**
 * Unmodifiable view of an events
 */
public interface ReadOnlyEvents {
    public List<Event> getAllEvents();
}
