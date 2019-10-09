package seedu.address.model.day;

import java.util.List;

/**
 * Represents a Day in the travel planner's itinerary.
 * Guarantees: timetable is present and not null, field values are validated, immutable.
 */
public class Day {
    private final Timetable timetable;

    public Day() {
        this.timetable = new Timetable();
    }

    public Day(List<ActivityWithTime> activitiesForDay) {
        this.timetable = new Timetable(activitiesForDay);
    }
}
