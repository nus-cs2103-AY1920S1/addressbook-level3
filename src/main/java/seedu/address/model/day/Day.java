package seedu.address.model.day;

import java.util.List;

public class Day {
    private Timetable timetable;

    public Day() {
        this.timetable = new Timetable();
    }

    public Day(List<ActivityInTimeRange> activitiesInDay) {
        this.timetable = new Timetable(activitiesInDay);
    }
}
