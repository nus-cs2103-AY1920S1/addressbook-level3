package seedu.exercise.model;

import javafx.collections.ObservableList;
import seedu.exercise.model.schedule.Schedule;

/**
 * Unmodifiable view of a {@code Schedule Book}
 */
public interface ReadOnlyScheduleBook {

    /**
     * Returns an unmodifiable view of the schedule list.
     * This list will not contain any conflicting schedules.
     */
    ObservableList<Schedule> getScheduleList();
}
