package seedu.exercise.model.resource;

import java.util.List;
import java.util.Objects;

import seedu.exercise.model.property.Date;
import seedu.exercise.storage.resource.JsonAdaptedSchedule;

/**
 * Represents a schedule for a regime at a certain date.
 */
public class Schedule extends Resource {
    private static final String SCHEDULE_STRING_FORMATTER = "%s (%s)\n%s";
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

    public String getRegimeName() {
        return regime.getRegimeName().toString();
    }

    public List<Exercise> getExercises() {
        return regime.getRegimeExercises().asUnmodifiableObservableList();
    }

    /**
     * Returns true if both {@code schedules} have the same date
     */
    @Override
    public boolean isSameResource(Resource otherResource) {
        return this.equals(otherResource);
    }

    @Override
    public boolean equals(Object other) {
        return (other == this)
            || (other instanceof Schedule)
            && date.equals(((Schedule) other).date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(regime, date);
    }

    @Override
    public String toString() {
        return String.format(SCHEDULE_STRING_FORMATTER, getRegimeName(), date.toString(), regime.toString());
    }

    @Override
    public JsonAdaptedSchedule toJsonType() {
        return new JsonAdaptedSchedule(this);
    }

}
