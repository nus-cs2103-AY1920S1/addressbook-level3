package seedu.exercise.model.schedule;

import static java.util.Objects.requireNonNull;
import static seedu.exercise.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.exercise.model.schedule.exceptions.DuplicateScheduleException;
import seedu.exercise.model.schedule.exceptions.ScheduleNotFoundException;

/**
 * A list of schedules that enforces uniqueness between its elements and does not allow nulls.
 * A schedule is considered unique by comparing using {@code Schedule#isSameSchedule(Schedule)}.
 * As such, adding and updating of schedules uses Schedule#isSameSchedule(Schedule) for equality so as to ensure that
 * the schedule being added does not have any potential scheduling conflicts.
 *
 * Supports a minimal set of list operations.
 *
 * @see Schedule#isSameSchedule(Schedule)
 */
public class UniqueScheduleList implements Iterable<Schedule> {

    private final ObservableList<Schedule> internalList = FXCollections.observableArrayList();
    private final ObservableList<Schedule> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent schedule as the given argument.
     */
    public boolean contains(Schedule toCheck) {
        requireNonNull(toCheck);
        for (Schedule schedule : internalList) {
            if (schedule.equals(toCheck)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a schedule to the list.
     * The schedule must not already exist in the list.
     */
    public void add(Schedule toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateScheduleException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the equivalent schedule from the list.
     * The schedule must exist in the list.
     */
    public void remove(Schedule toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ScheduleNotFoundException();
        }
    }

    public void setSchedules(List<Schedule> schedules) {
        requireAllNonNull(schedules);
        if (!schedulesAreUnique(schedules)) {
            throw new DuplicateScheduleException();
        }

        internalList.setAll(schedules);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Schedule> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Schedule> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueScheduleList // instanceof handles nulls
                && internalList.equals(((UniqueScheduleList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Checks if schedules are all unique within the list
     */
    private boolean schedulesAreUnique(List<Schedule> schedules) {
        for (int i = 0; i < schedules.size() - 1; i++) {
            for (int j = i + 1; j < schedules.size(); j++) {
                if (schedules.get(i).isSameSchedule(schedules.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
