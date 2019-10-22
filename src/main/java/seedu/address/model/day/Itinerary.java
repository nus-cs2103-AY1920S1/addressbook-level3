package seedu.address.model.day;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.day.exceptions.DayNotFoundException;
import seedu.address.model.day.exceptions.DuplicateDayException;
import seedu.address.model.field.Name;

/**
 * Itinerary class helps to manage the list of days in an Planner.
 */
public class Itinerary implements Iterable<Day> {
    private final ObservableList<Day>
            internalList = FXCollections.observableArrayList();
    private final ObservableList<Day> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    private Name name;
    private LocalDate startDate;

    public Itinerary() {
        this.name = new Name("Untitled");
        this.startDate = LocalDate.now();
    }
    /**
     * Returns true if the list contains an equivalent contacts as the given argument.
     */
    public boolean contains(Day toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameDay);
    }

    /**
     * Adds a number of days to the planner.
     */
    public void add(int numDays) {
        requireNonNull(numDays);
        for (int i = 0; i < numDays; i++) {
            Day toAdd = new Day();
            internalList.add(toAdd);
        }
    }

    public Name getName() {
        return this.name;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public void setName(Name name) {
        requireNonNull(name);
        this.name = name;
    }

    public void setStartDate(LocalDate date) {
        requireNonNull(date);
        this.startDate = date;
    }

    public void setDays(Itinerary replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code days}.
     * {@code days} must not contain duplicate day.
     */
    public void setDays(List<Day> days) {
        requireAllNonNull(days);
        //if (!daysAreUnique(days)) {
        //    throw new DuplicateDayException();
        //}

        internalList.setAll(days);
    }

    /**
     * Replaces the days {@code target} in the list with {@code editedDay}.
     * {@code target} must exist in the planner.
     * The contacts identity of {@code editedDay} must not be the same as another existing days in the
     * planner.
     */
    public void setDay(Day target,
                       Day editedDay) {
        requireAllNonNull(target, editedDay);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new DayNotFoundException();
        }

        if (!target.isSameDay(editedDay) && contains(editedDay)) {
            throw new DuplicateDayException();
        }

        internalList.set(index, editedDay);
    }

    /**
     * Removes the equivalent days from the list.
     * The days must exist in the list.
     */
    public void remove(Day toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new DayNotFoundException();
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Day> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Day> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Itinerary // instanceof handles nulls
                && internalList.equals(((Itinerary) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code activities} contains only unique activities.
     */
    private boolean daysAreUnique(List<Day> days) {
        for (int i = 0; i < days.size() - 1; i++) {
            for (int j = i + 1; j < days.size(); j++) {
                if (days.get(i).isSameDay(days.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
