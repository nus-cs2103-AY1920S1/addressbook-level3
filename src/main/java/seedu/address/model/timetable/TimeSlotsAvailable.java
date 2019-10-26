package seedu.address.model.timetable;

import java.util.Collection;
import java.util.List;

public class TimeSlotsAvailable {
    private final Collection<TimeTable> timeTables;
    private final List<TimeRange> timeRanges;
    private final boolean isEmpty;

    public TimeSlotsAvailable(Collection<TimeTable> timeTables, List<TimeRange> timeRanges) {
        this(timeTables, timeRanges, false);
    }

    public TimeSlotsAvailable(boolean isEmpty) {
        this(null, null, true);
    }

    public TimeSlotsAvailable(Collection<TimeTable> timeTables, List<TimeRange> timeRanges, boolean isEmpty) {
        this.timeTables = timeTables;
        this.timeRanges = timeRanges;
        this.isEmpty = isEmpty;
    }

    public Collection<TimeTable> getTimeTables() {
        return timeTables;
    }

    public List<TimeRange> getTimeRanges() {
        return timeRanges;
    }

    public boolean isEmpty() {
        return isEmpty;
    }
}
