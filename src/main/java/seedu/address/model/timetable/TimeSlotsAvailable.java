package seedu.address.model.timetable;

import java.util.Collection;
import java.util.List;

public class TimeSlotsAvailable {
    private final Collection<Timetable> timetables;
    private final List<TimeRange> timeRanges;
    private final boolean isEmpty;

    public TimeSlotsAvailable(Collection<Timetable> timetables, List<TimeRange> timeRanges) {
        this(timetables, timeRanges, false);
    }

    public TimeSlotsAvailable(boolean isEmpty) {
        this(null, null, true);
    }

    public TimeSlotsAvailable(Collection<Timetable> timetables, List<TimeRange> timeRanges, boolean isEmpty) {
        this.timetables = timetables;
        this.timeRanges = timeRanges;
        this.isEmpty = isEmpty;
    }

    public Collection<Timetable> getTimetables() {
        return timetables;
    }

    public List<TimeRange> getTimeRanges() {
        return timeRanges;
    }

    public boolean isEmpty() {
        return isEmpty;
    }
}
