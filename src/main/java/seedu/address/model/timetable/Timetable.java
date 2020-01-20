package seedu.address.model.timetable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Timetable {
    private List<TimeRange> timeRanges;

    /**
     * Takes in a formatted string of timings.
     * @param timeRanges List of TimeRange to populate the timetable.
     */
    public Timetable(List<TimeRange> timeRanges) {
        this.timeRanges = timeRanges;
        Collections.sort(this.timeRanges);
    }

    public Timetable() {
        this.timeRanges = new ArrayList<>();
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
        Timetable timetable = (Timetable) o;
        return timeRanges.equals(timetable.timeRanges);
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
