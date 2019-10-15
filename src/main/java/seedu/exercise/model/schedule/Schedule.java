package seedu.exercise.model.schedule;

import java.util.List;

import seedu.exercise.model.exercise.Date;
import seedu.exercise.model.exercise.Exercise;
import seedu.exercise.model.regime.Regime;

/**
 * Represents a schedule for a regime at a certain date.
 */
public class Schedule {
    private final Regime regime;
    private final Date date;

    public Schedule(Regime regime, Date date) {
        this.regime = regime;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public Regime getRegime() {
        return regime;
    }

    public List<Exercise> getExercises() {
        return regime.getExercises().asUnmodifiableObservableList();
    }

    /**
     * Returns true if both {@code schedules} have the same date
     */
    public boolean isSameSchedule(Schedule schedule) {
        return date.equals(schedule.date);
    }

    @Override
    public boolean equals(Object other) {
        return (other == this)
            || (other instanceof Schedule)
            && date.equals (((Schedule) other).date);
    }

}
