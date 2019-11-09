//@@author SakuraBlossom
package seedu.address.model.events;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import seedu.address.model.common.UniqueElementList;
import seedu.address.model.person.Person;

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
     * Returns a ListIterator of {@code Event} whose timing is in conflict with the given {@code event}.
     */
    public ListIterator<Event> getEventsInConflict(Event toCheck) {
        requireNonNull(toCheck);
        SearchEvent searchEvent = new SearchEvent(toCheck);

        int indexOfLowerBound = getLowerBound(searchEvent);
        int indexOfUpperBound = getUpperBound(searchEvent);
        return listIterator(indexOfLowerBound, indexOfUpperBound);
    }

    /**
     * Returns a list of {@code Event} whose timing is in conflict with the given {@code event}.
     */
    public List<Event> getListOfEventsInConflict(Event toCheck) {
        ListIterator<Event> iterator = getEventsInConflict(toCheck);
        List<Event> listOfConflictingEvents = new ArrayList<>();

        while (iterator.hasNext()) {
            listOfConflictingEvents.add(iterator.next());
        }

        return listOfConflictingEvents;
    }

    /**
     * Returns the number of {@code Event} whose timing is in conflict with the given {@code event}.
     */
    public int countNumberOfEventsInConflict(Event toCheck) {
        requireNonNull(toCheck);
        SearchEvent searchEvent = new SearchEvent(toCheck);
        int indexOfLowerBound = getLowerBound(searchEvent);
        int indexOfUpperBound = getUpperBound(searchEvent);
        return indexOfUpperBound - indexOfLowerBound + 1;
    }

    /**
     * Returns true if the number of unique events which timings are in conflict
     * is lesser than {@code maxNumberOfConcurrentEvents} and the events in conflict does not
     * involve the same person given in {@code event}.
     */
    public boolean allowedToSchedule(Event toCheck, int maxNumberOfConcurrentEvents) {
        SearchEvent searchEvent = new SearchEvent(toCheck);
        int indexOfLowerBound = getLowerBound(searchEvent);
        int indexOfUpperBound = getUpperBound(searchEvent);

        if (maxNumberOfConcurrentEvents <= indexOfUpperBound - indexOfLowerBound + 1) {
            return false;
        }

        ListIterator<Event> iterator = listIterator(indexOfLowerBound);

        while (iterator.nextIndex() <= indexOfUpperBound) {
            if (toCheck.getPersonId().isSameAs(iterator.next().getPersonId())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Replaces the given person details of {@code target} with {@code editedPerson}.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void updatesPersonDetails(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        if (target.getReferenceId().equals(editedPerson.getReferenceId())
            && target.getName().equals(editedPerson.getName())) {
            return;
        }

        ListIterator<Event> iterator = listIterator(0);
        while (iterator.hasNext()) {

            Event event = iterator.next();
            if (event.getPersonId().equals(target.getReferenceId())) {
                iterator.set(new Event(
                        editedPerson.getReferenceId(),
                        editedPerson.getName(),
                        event.getEventTiming(),
                        event.getStatus()));
            }
        }
    }
}

/**
 * A helper class which represents an event when searching for conflicting events in {@code UniqueEventList}
 */
class SearchEvent extends Event {
    public SearchEvent(Event event) {
        super(event.getPersonId(), event.getPersonName(), event.getEventTiming(), event.getStatus());
    }

    /**
     * Returns true if both Event of the same timing.
     * This defines a weaker notion of equality between two events.
     */
    public boolean isSameAs(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getEventTiming().equals(getEventTiming())
                && otherEvent.getStatus().equals(getStatus());
    }

    /**
     * Returns true if both events have the same timing.
     * This defines a stronger notion of equality between two events.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return otherEvent.getEventTiming().equals(getEventTiming())
                && otherEvent.getStatus().equals(getStatus());
    }

    @Override
    public int compareTo(Event o) {
        requireNonNull(o);
        if (conflictsWith(o)) {
            return 0;
        }
        return getEventTiming().compareTo(o.getEventTiming());
    }

}
