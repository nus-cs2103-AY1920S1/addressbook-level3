package seedu.tarence.model.tutorial;

import static seedu.tarence.commons.util.CollectionUtil.requireAllNonNull;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

import java.util.Objects;
import java.util.Set;

/**
 * Represents a TimeTable for a Tutorial. Guarantees: details are present and not null, field
 * values are validated, immutable.
 */
public class TimeTable {
    // By default, tutorials run from weeks 3-13
    public static final String DEFAULT_WEEKS = "3-13";

    private final Duration duration;
    private final DayOfWeek day;
    private final LocalTime startTime;
    private final Set<Week> weeks;

    /**
     * Every field must be present and not null.
     */
    public TimeTable(DayOfWeek day, LocalTime startTime,
            Set<Week> weeks, Duration duration) {
        requireAllNonNull(day, startTime, weeks, duration);
        this.day = day;
        this.startTime = startTime;
        this.weeks = weeks;
        this.duration = duration;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public Set<Week> getWeeks() {
        return weeks;
    }

    public Duration getDuration() {
        return duration;
    }

    /**
     * Returns true if both timetables have the same identity or data fields.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TimeTable)) {
            return false;
        }

        TimeTable otherTimeTable = (TimeTable) other;
        return otherTimeTable.getDay().equals(getDay())
                && otherTimeTable.getStartTime().equals(getStartTime())
                && otherTimeTable.getWeeks().equals(getWeeks())
                && otherTimeTable.getDuration().equals(getDuration());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(day, startTime, weeks, duration);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getWeeks())
                .append(" Day: ")
                .append(getDay())
                .append(" Start Time: ")
                .append(getStartTime())
                .append(" Weeks: ")
                .append(getWeeks())
                .append(" Duration: ")
                .append(getDuration());
        return builder.toString();
    }


}
