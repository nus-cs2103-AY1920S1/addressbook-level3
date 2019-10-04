package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.schedule.UniqueScheduleList;

/**
 * Wraps all data at the schedule-book level
 * Duplicates are not allowed (by .isSameSchedule comparison)
 */
public class ScheduleBook implements ReadOnlyDataBook<Schedule> {

    private final UniqueScheduleList schedules;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        schedules = new UniqueScheduleList();
    }

    public ScheduleBook() {}

    /**
     * Creates an ScheduleBook using the Schedules in the {@code toBeCopied}
     */
    public ScheduleBook(ReadOnlyDataBook<Schedule> toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the schedule list with {@code schedules}.
     * {@code schedules} must not contain duplicate schedules.
     */
    public void setSchedules(List<Schedule> schedules) {
        this.schedules.setSchedules(schedules);
    }

    /**
     * Resets the existing data of this {@code ScheduleBook} with {@code newData}.
     */
    public void resetData(ReadOnlyDataBook<Schedule> newData) {
        requireNonNull(newData);

        setSchedules(newData.getList());
    }

    //// schedule-level operations

    /**
     * Returns true if a schedule with the same identity as {@code schedule} exists in the schedule book.
     */
    public boolean hasSchedule(Schedule schedule) {
        requireNonNull(schedule);
        return schedules.contains(schedule);
    }

    /**
     * Adds a schedule to the schedule book.
     * The schedule must not already exist in the schedule book.
     */
    public void addSchedule(Schedule s) {
        schedules.add(s);
    }

    /**
     * Replaces the given schedule {@code target} in the list with {@code editedSchedule}.
     * {@code target} must exist in the schedule book.
     * The schedule identity of {@code editedSchedule} must not be the same as another existing schedule.
     */
    public void setSchedule(Schedule target, Schedule editedSchedule) {
        requireNonNull(editedSchedule);

        schedules.setSchedule(target, editedSchedule);
    }

    /**
     * Removes {@code key} from this {@code ScheduleBook}.
     * {@code key} must exist in the schedule book.
     */
    public void removeSchedule(Schedule key) {
        schedules.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return schedules.asUnmodifiableObservableList().size() + " schedules";
        // TODO: refine later
    }

    @Override
    public ObservableList<Schedule> getList() {
        return schedules.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ScheduleBook // instanceof handles nulls
                && schedules.equals(((ScheduleBook) other).schedules));
    }

    @Override
    public int hashCode() {
        return schedules.hashCode();
    }
}

