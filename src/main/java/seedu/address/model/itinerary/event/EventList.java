package seedu.address.model.itinerary.event;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import seedu.address.model.itinerary.ConsecutiveOccurrenceList;
import seedu.address.model.itinerary.event.exceptions.ClashingEventException;
import seedu.address.model.itinerary.event.exceptions.EventNotFoundException;

/**
 * List containing {@code Event}s.
 */
public class EventList extends ConsecutiveOccurrenceList<Event> {
    public static String MESSAGE_INVALID_DATETIME = "Date should be within valid duration";

    LocalDateTime currentDay;

    LocalDateTime startOfDay;
    LocalDateTime endOfDay;

    public EventList(LocalDateTime currentDay) {
        this.currentDay = currentDay;
        startOfDay = currentDay.toLocalDate().atStartOfDay();
        endOfDay = currentDay.withHour(23).withMinute(59);
    }

    public boolean isValidEvent(Event event) {
        return (event.getStartDate().compareTo(startOfDay) >= 0)
                && (event.getEndDate().compareTo(endOfDay) <= 0);
    }

    @Override
    public boolean contains(Event toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameEvent);
    }

    @Override
    public boolean containsClashing(Event toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isClashingWith);

    }

    @Override
    public void add(Event toAdd) {
        requireNonNull(toAdd);
        checkArgument(isValidEvent(toAdd), MESSAGE_INVALID_DATETIME);
        if (containsClashing(toAdd)) {
            throw new ClashingEventException();
        }
        internalList.add(toAdd);
    }

    @Override
    public void set(Event target, Event edited) {
        requireAllNonNull(target, edited);
        checkArgument(isValidEvent(edited), MESSAGE_INVALID_DATETIME);
        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new EventNotFoundException();
        }

        internalList.set(index, edited);
    }

    @Override
    public void set(List<Event> occurrences) {
        requireAllNonNull(occurrences);
        if (!areConsecutive(occurrences)) {
            throw new ClashingEventException();
        }
        internalList.setAll(occurrences);

    }

    @Override
    public void remove(Event toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new EventNotFoundException();
        }
    }

    @Override
    public boolean areConsecutive(List<Event> occurrences) {
        for (int i = 0; i < occurrences.size() - 1; i++) {
            for (int j = i + 1; j < occurrences.size(); j++) {
                if (occurrences.get(i).isClashingWith(occurrences.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean areUnique(List<Event> occurrence) {
        for (int i = 0; i < occurrence.size() - 1; i++) {
            for (int j = i + 1; j < occurrence.size(); j++) {
                if (occurrence.get(i).isSameEvent(occurrence.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventList // instanceof handles nulls
                && internalList.equals(((EventList) other).internalList));
    }
}
