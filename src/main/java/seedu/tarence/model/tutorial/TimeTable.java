package seedu.tarence.model.tutorial;

import static seedu.tarence.commons.util.AppUtil.checkArgument;
import static seedu.tarence.commons.util.CollectionUtil.requireAllNonNull;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Objects;
import java.util.Set;

import seedu.tarence.model.module.Module;

/**
 * Represents a TimeTable for a Tutorial. Guarantees: details are present and not null, field
 * values are validated, immutable.
 */
public class TimeTable {
    // By default, tutorials run from weeks 3-13
    public static final String DEFAULT_WEEKS = "3-13";

    public static final String MESSAGE_CONSTRAINTS_TIME =
            "Lessons should be contained within a single day.";

    public static final String MESSAGE_CONSTRAINTS_DURATION =
            "Duration cannot be negative.";

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
        checkArgument(isValidDuration(duration), MESSAGE_CONSTRAINTS_DURATION);
        checkArgument(isValidTimeTable(startTime, duration), MESSAGE_CONSTRAINTS_TIME);
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
     * Returns the current week. Assumes start of semester is present.
     */
    public Week getCurrWeek() {
        Calendar currCalendar = Calendar.getInstance();
        Calendar tempCalender = Calendar.getInstance();
        tempCalender.setTime(Module.getSemStart());
        tempCalender.add(Calendar.DAY_OF_MONTH, -7);

        for (int week = 1; week <= 14; week++) {
            if (tempCalender.compareTo(currCalendar) <= 0) {
                tempCalender.add(Calendar.DAY_OF_MONTH, 7);
            } else {
                return week == 1
                        ? null
                        : new Week(week - 1);
            }
        }
        return new Week(13);
    }

    /**
     * Returns true if time slot is contained within a single day.
     */
    public static boolean isValidTimeTable(LocalTime startTime, Duration duration) {
        return Duration.between(LocalTime.MIN, startTime).plus(duration).toDays() == 0;
    }

    public static boolean isValidDuration(Duration duration) {
        return !duration.isNegative();
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
