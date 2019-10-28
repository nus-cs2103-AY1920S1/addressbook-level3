package seedu.address.model.events;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;

import seedu.address.model.common.UniqueElementList;

/**
 * A list of events that enforces uniqueness between its elements and does not allow nulls.
 * A event is considered unique by comparing using {@code event#isSameevent(event)}. As such, adding and updating of
 * events uses event#isSameevent(event) for equality so as to ensure that the event being added or updated is
 * unique in terms of identity in the UniqueeventList. However, the removal of a event uses event#equals(Object) so
 * as to ensure that the event with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Event#isSameAs(Event)
 */
public class UniqueEventList extends UniqueElementList<Event> {

    /**
     * Returns a list of {@code Event} whose timing is in conflict with the given {@code event}.
     */
    public ObservableList<Event> inConflictWith(Event toCheck) {
        requireNonNull(toCheck);
        return null;
    }
}
