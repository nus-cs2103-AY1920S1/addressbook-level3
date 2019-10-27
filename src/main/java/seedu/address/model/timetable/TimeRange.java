package seedu.address.model.timetable;

import seedu.address.commons.exceptions.IllegalValueException;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Objects;

public class TimeRange implements Comparable<TimeRange> {
    private final WeekTime start;
    private final WeekTime end;
    public static final String MESSAGE_CONSTRAINTS = "TimeRange should be constructed in the following format : STARTDAY ENDDAY STARTTIME ENDTIME";

    public TimeRange(WeekTime start, WeekTime end) throws IllegalValueException {
        if (!rangeIsValid(start, end)) {
            throw new IllegalValueException("start should be earlier than end");
        }
        this.start = start;
        this.end = end;
    }

    public TimeRange(DayOfWeek dayStart, LocalTime timeStart, DayOfWeek dayEnd, LocalTime timeEnd) throws IllegalValueException {
        this(new WeekTime(dayStart, timeStart), new WeekTime(dayEnd, timeEnd));
    }

    public Duration getDuration() {
        return end.minus(start);
    }

    public WeekTime getStart() {
        return start;
    }

    public WeekTime getEnd() {
        return end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TimeRange timeRange = (TimeRange) o;
        return start.equals(timeRange.start)
                && end.equals(timeRange.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    /**
     * Compare by start time.
     * @param other Other TimeRange to compare with.
     * @return Negative if start earlier, positive if start later.
     */
    @Override
    public int compareTo(TimeRange other) {
        return this.start.compareTo(other.start);
    }

    public boolean overlap(TimeRange timeRange) {
        return this.start.compareTo(timeRange.end) < 0 && this.end.compareTo(timeRange.start) > 0;
    }

    public boolean overlapInclusive(TimeRange other) {
        return this.overlap(other) || this.getEnd().equals(other.getStart()) || this.getStart().equals(other.getEnd());
    }

    private static boolean rangeIsValid(WeekTime start, WeekTime end) {
        return start.compareTo(end) < 0;
    }

    @Override
    public String toString() {
        return "From: " + this.start.toString() + " To: " + this.end.toString();
    }
}
