package seedu.address.model.day;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Represents a Day in the travel planner's planner.
 * Guarantees: timetable is present and not null, field values are validated, immutable.
 */
public class Day {
    public static final String MESSAGE_CONSTRAINTS = "Number of days should be an integer greater than 0.";
    public static final String VALIDATION_REGEX = "^[1-9]\\d*$";

    private final Timetable timetable;

    public Day() {
        this.timetable = new Timetable();
    }

    public Day(List<ActivityWithTime> activitiesForDay) throws CommandException {
        this.timetable = new Timetable(activitiesForDay);
    }

    /**
     * Returns true if both days contain the same activities.
     */
    public boolean isSameDay(Day otherDay) {
        if (otherDay == this) {
            return true;
        }
        return this.timetable.equals(otherDay.timetable);
    }

    /**
     * Gets all the activities present in {@code Day} and wraps them in ActivityWithTime.
     * @return list of {@code ActivityWithTime} present in {@code Day}
     */
    public List<ActivityWithTime> getListOfActivityWithTime() {
        return this.timetable.getActivitiesWithTime();
    }

    public Optional<ActivityWithTime> getActivityWithTime(LocalTime time) {
        return this.timetable.getActivityWithTimeAtTime(time);
    }

    /**
     * Returns true if a given string is a valid integer.
     */
    public static boolean isValidDayNumber(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Day)) {
            return false;
        }

        Day otherDay = (Day) other;
        return otherDay.timetable.equals(this.timetable);
    }
}
