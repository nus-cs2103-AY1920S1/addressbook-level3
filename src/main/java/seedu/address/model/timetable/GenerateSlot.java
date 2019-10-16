package seedu.address.model.timetable;

import seedu.address.commons.exceptions.IllegalValueException;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;

public class GenerateSlot {
    public static List<TimeRange> generate(List<TimeTable> timeTables, int numberOfHours, TimeRange userSpecifiedTimeRange) throws IllegalValueException {
        List<TimeRange> uniqueTimeRanges = filterUniqueTimeRanges(timeTables);
        List<TimeRange> merged = mergeOverlappingTimeRanges(uniqueTimeRanges);
        List<TimeRange> inverted = invertTimeRange(merged);
        List<TimeRange> truncated = truncateTimeRange(inverted, userSpecifiedTimeRange);
        return getSuitableTimeRanges(truncated, numberOfHours);
    }

    public static List<TimeRange> filterUniqueTimeRanges(List<TimeTable> timeTables) {
        Set<TimeRange> timeRanges = new HashSet<>();
        for (TimeTable timeTable : timeTables) {
            timeRanges.addAll(timeTable.getTimeRanges());
        }
        return new ArrayList<>(timeRanges);
    }

    public static List<TimeRange> mergeOverlappingTimeRanges(List<TimeRange> timeRanges) throws IllegalValueException {
        Collections.sort(timeRanges);
        List<TimeRange> merged = new ArrayList<>();
        for (TimeRange timeRange : timeRanges) {
            if (merged.isEmpty()) {
                merged.add(timeRange);
                continue;
            }
            TimeRange latest = merged.get(merged.size() - 1);
            if (latest.overlap(timeRange)) { // If last TimeRange in merged overlaps with new latest
                TimeRange tr = merge(timeRange, latest);
                merged.set(merged.size() - 1, tr);
            } else {
                merged.add(timeRange);
            }
        }
        return merged;
    }

    /**
     * Merge 2 TimeRange together, assuming that they overlap.
     * @param r1 TimeRange 1.
     * @param r2 TimeRange 2.
     * @return new merged TimeRange.
     * @throws IllegalValueException If error in creating new TimeRange.
     */
    private static TimeRange merge(TimeRange r1, TimeRange r2) throws IllegalValueException {
        return new TimeRange(r1.getStart().isBefore(r2.getStart()) ? r1.getStart() : r2.getStart(), r1.getEnd().isAfter(r2.getEnd()) ? r1.getEnd() : r2.getEnd());
    }

    public static List<TimeRange> invertTimeRange(List<TimeRange> timeRanges) throws IllegalValueException {
        // Start from MONDAY 0000, to SUNDAY 2359
        List<TimeRange> inverted = new ArrayList<>();
        DayOfWeek curDay = DayOfWeek.MONDAY;
        LocalTime curTime = LocalTime.parse("00:00");
        WeekTime cur = new WeekTime(curDay, curTime);
        for (TimeRange timeRange : timeRanges) {
            TimeRange toAdd = new TimeRange(cur, timeRange.getStart());
            inverted.add(toAdd);
            cur = timeRange.getEnd();
        }
        inverted.add(new TimeRange(cur.getDay(), DayOfWeek.SUNDAY, cur.getTime(), LocalTime.parse("23:59")));
        return inverted;
    }

    public static List<TimeRange> truncateTimeRange(List<TimeRange> timeRanges, TimeRange limit) throws IllegalValueException {
        List<TimeRange> truncated = new ArrayList<>();
        for (TimeRange timeRange : timeRanges) {
            if (!timeRange.overlap(limit)) { // Start after end
                continue;
            }
            WeekTime start = timeRange.getStart().isBefore(limit.getStart()) ? limit.getStart() : timeRange.getStart();
            WeekTime end = timeRange.getEnd().isAfter(limit.getEnd()) ? limit.getEnd() : timeRange.getEnd();
            truncated.add(new TimeRange(start, end));
        }
        return truncated;
    }

    public static List<TimeRange> getSuitableTimeRanges(List<TimeRange> timeRanges, int numberOfHours) {
        List<TimeRange> possibleRanges = new ArrayList<>();
        for (TimeRange timeRange : timeRanges) {
            if (timeRange.getDurationInHours() >= numberOfHours) {
                possibleRanges.add(timeRange);
            }
        }
        return possibleRanges;
    }

}
