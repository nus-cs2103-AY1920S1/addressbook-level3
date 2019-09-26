package seedu.tarence.model.tutorial;

import static seedu.tarence.commons.util.CollectionUtil.requireAllNonNull;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;

import java.util.List;
import java.util.Objects;

/**
 * Represents a TimeTable.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class TimeTable {
    private final Duration duration;
    private final DayOfWeek day;
    private final LocalTime time;
    private final List<Integer> weeks;

    /**
     * Every field must be present and not null.
     */
    public TimeTable(DayOfWeek day, LocalTime time, List<Integer> weeks, Duration duration) {
        requireAllNonNull(day, time, weeks, duration);
        this.day = day;
        this.time = time;

        for (Integer week : weeks) {
            assert(week >= 1 && week <= 13);
        }
        this.weeks = weeks;
        this.duration = duration;

    }

    public DayOfWeek getDay() {
        return day;
    }

    public LocalTime getTime() {
        return time;
    }

    public List<Integer> getWeeks() {
        return weeks;
    }

    public Duration getDuration() {
        return duration;
    }

    /**
     * Returns true if both timetables have the same identity and data fields.
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
                && otherTimeTable.getTime().equals(getTime())
                && otherTimeTable.getWeeks().equals(getWeeks())
                && otherTimeTable.getDuration().equals(getDuration());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(day, time, weeks, duration);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getWeeks())
                .append(" Day: ")
                .append(getDay())
                .append(" Time: ")
                .append(getTime())
                .append(" Weeks: ")
                .append(getWeeks())
                .append(" Duration: ")
                .append(getDuration());
        return builder.toString();
    }

}
