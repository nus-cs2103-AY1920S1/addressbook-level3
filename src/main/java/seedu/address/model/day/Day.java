package seedu.address.model.day;

import java.util.List;

/**
 * Represents a Day in the itinerary.
 * Guarantees: Time-slots in timetable are not null, field values are validated, immutable.
 */
public class Day {
    private final Timetable timetable;

    public Day() {
        this.timetable = new Timetable();
    }

    public Day(List<ActivityInTimeRange> activitiesInDay) {
        this.timetable = new Timetable(activitiesInDay);
    }
}
