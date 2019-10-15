package seedu.exercise.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.exercise.model.schedule.Schedule;
import seedu.exercise.model.schedule.UniqueScheduleList;

/**
 * Wraps all data at the schedule-book level.
 * Conflicting schedules are not allowed.
 */
public class ScheduleBook implements ReadOnlyScheduleBook {

    private final UniqueScheduleList schedules;

    {
        schedules = new UniqueScheduleList();
    }

    public ScheduleBook() {

    }

    public ScheduleBook(ReadOnlyScheduleBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Resets the existing data of this {@code ScheduleBook} with {@code newData}.
     */
    public void resetData(ReadOnlyScheduleBook newData) {
        requireNonNull(newData);

        setSchedule(newData.getScheduleList());
    }

    /**
     * Replaces the contents of the schedule list with {@code schedules}.
     * {@code schedules} must not contain duplicate schedules.
     */
    public void setSchedule(List<Schedule> schedules) {
        this.schedules.setSchedules(schedules);
    }


    /**
     * Returns true if a schedule with the same date exists in the schedule book.
     */
    public boolean hasSchedule(Schedule schedule) {
        requireNonNull(schedules);
        return schedules.contains(schedule);
    }

    /**
     * Adds a schedule to the schedule book.
     * The Schedule must not already exist in the ScheduleBook.
     */
    public void addSchedule(Schedule r) {
        schedules.add(r);
    }

    /**
     * Removes {@code key} from this {@code ScheduleBook}.
     * {@code key} must exist in the Schedule book.
     */
    public void removeSchedule(Schedule key) {
        schedules.remove(key);
    }

    /**
     * Returns the index of {@code schedule} in Schedule book.
     */
    public int getSchedulesIndex(Schedule schedule) {
        int i = 0;
        for (Schedule r : schedules) {
            if (r.equals(schedule)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    @Override
    public ObservableList<Schedule> getScheduleList() {
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
