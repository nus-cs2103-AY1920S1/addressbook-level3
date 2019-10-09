package seedu.address.model.day;

import java.util.List;

/**
 * Represents a Day in the travel planner's itinerary.
 * Guarantees: timetable is present and not null, field values are validated, immutable.
 */
public class Day {
    public static final String MESSAGE_CONSTRAINTS = "Number of days should be an integer.";
    public static final String VALIDATION_REGEX = "\\d";

    private final Timetable timetable;

    public Day() {
        this.timetable = new Timetable();
    }

    public Day(List<ActivityWithTime> activitiesForDay) {
        this.timetable = new Timetable(activitiesForDay);
    }

    /**
     * Returns true if a given string is a valid integer.
     */
    public static boolean isValidDayNumber(String test) {
        return test.matches(VALIDATION_REGEX);
    }
}
