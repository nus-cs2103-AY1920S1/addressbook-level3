package seedu.address.model.timetable;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TimeTable {
    private List<TimeRange> timeRanges;

    /**
     * Takes in a formatted string of timings.
     * @param timeRanges List of TimeRange to populate the timetable.
     */
    public TimeTable(List<TimeRange> timeRanges) {
        this.timeRanges = timeRanges;
        Collections.sort(this.timeRanges);
    }

    public List<TimeRange> getTimeRanges() {
        return timeRanges;
    }

    public boolean isAvailable(TimeRange timeRange) {
        return this.timeRanges.stream().noneMatch(tr -> tr.overlap(timeRange));
    }

    public boolean overlaps(TimeRange timeRange) {
        return this.timeRanges.stream().anyMatch(timeRange::overlap);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TimeTable timeTable = (TimeTable) o;
        return timeRanges.equals(timeTable.timeRanges);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeRanges);
    }

    @Override
    public String toString() {
        return this.getTimeRanges().stream().map(Object::toString).collect(Collectors.joining(""));
    }
}
