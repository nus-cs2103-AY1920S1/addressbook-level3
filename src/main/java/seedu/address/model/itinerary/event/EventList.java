package seedu.address.model.itinerary.event;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

import seedu.address.model.expense.Expense;
import seedu.address.model.itinerary.ConsecutiveOccurrenceList;
import seedu.address.model.itinerary.event.exceptions.ClashingEventException;
import seedu.address.model.itinerary.event.exceptions.DuplicatedEventNameException;
import seedu.address.model.itinerary.event.exceptions.EventNotFoundException;

/**
 * List containing {@code Event}s.
 */
public class EventList extends ConsecutiveOccurrenceList<Event> {
    private static final String MESSAGE_INVALID_DATETIME = "Date should be within valid duration";

    private LocalDateTime currentDay;

    private LocalDateTime startOfDay;
    private LocalDateTime endOfDay;

    public EventList(LocalDateTime currentDay) {
        requireNonNull(currentDay);
        this.currentDay = currentDay;
        startOfDay = currentDay.toLocalDate().atStartOfDay();
        endOfDay = currentDay.withHour(23).withMinute(59);
    }

    /**
     * Checks if target event can be added into the list.
     */
    public boolean isValidEvent(Event event) {
        return (event.getStartDate().compareTo(startOfDay) >= 0)
                && (event.getEndDate().compareTo(endOfDay) <= 0);
    }

    /**
     * Updates expense of an event.
     * When an expense associated with an event is edited, find the event in the event list and replace
     * the expense field.
     *
     * @param expense the new expense to be used.
     * @throws EventNotFoundException exception is thrown when the matching event is not found.
     */
    public void updateExpense(Expense expense) throws EventNotFoundException {
        boolean updated = false;
        for (int i = 0; i < internalList.size(); i++) {
            Event event = internalList.get(i);
            if (event.getName().equals(expense.getName())) {
                set(event, new Event(event.getName(),
                        event.getStartDate(), event.getEndDate(), expense, event.getDestination(), null));
                updated = true;
            }
        }
        if (!updated) {
            throw new EventNotFoundException();
        }
    }

    /**
     * Checks if the event list contains an event with the same name.
     *
     * @param toCheck the event to be checked.
     * @return a boolean value representing whether there is an event with the same name.
     */
    public boolean containsSameName(Event toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::hasSameName);

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
        } else if (containsSameName(toAdd)) {
            throw new DuplicatedEventNameException();
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
        boolean hasSameEventName = IntStream.range(0, internalList.size()).filter(i -> i != index)
                .anyMatch(i -> internalList.get(i).hasSameName(edited));
        System.out.println(hasSameEventName);
        if (hasSameEventName) {
            throw new DuplicatedEventNameException();
        }

        internalList.set(index, edited);
    }

    @Override
    public void set(List<Event> occurrences) {
        requireAllNonNull(occurrences);
        if (!areConsecutive(occurrences)) {
            throw new ClashingEventException();
        }
        if (!areUnique(occurrences)) {
            throw new DuplicatedEventNameException();
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
