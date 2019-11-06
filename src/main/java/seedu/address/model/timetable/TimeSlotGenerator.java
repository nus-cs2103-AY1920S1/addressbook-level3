package seedu.address.model.timetable;

import seedu.address.commons.exceptions.IllegalValueException;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class TimeSlotGenerator {
    private Collection<Timetable> timetables;
    private int numberOfHours;
    private TimeRange userSpecifiedTimeRange;

    /**
     * Timeslot generator
     * @param timetables List of timetables of all members.
     * @param numberOfHours Must be <= 23 hour
     * @param userSpecifiedTimeRange TimeRange to generate timeslot within.
     */
    public TimeSlotGenerator(Collection<Timetable> timetables, int numberOfHours, TimeRange userSpecifiedTimeRange) {
        requireAllNonNull(timetables, userSpecifiedTimeRange);
        assert numberOfHours > 0;
        this.timetables = timetables;
        this.numberOfHours = numberOfHours;
        this.userSpecifiedTimeRange = userSpecifiedTimeRange;
    }

    /**
     * Return a list of TimeRange which do not overlap with any timetable, are more than the minimum number of hours and within the userSpecifiedTimeRange.
     * @return List of TimeRange where meeting is possible.
     * @throws IllegalValueException When unable to generate timeslot.
     */
    public List<TimeRange> generate() throws IllegalValueException {
        List<TimeRange> combined = combineTimetables(timetables);
        List<TimeRange> merged = mergedOverlappingTimeRanges(combined);
        List<TimeRange> inverted = getFreeTimeRanges(merged);
        List<TimeRange> truncated = truncateTimeRanges(inverted, userSpecifiedTimeRange);
        List<TimeRange> accepted = filterSuitableTimeRanges(truncated, numberOfHours);
        Collections.sort(accepted);

        return accepted;
    }

    public TimeSlotsAvailable generateWithMostPeople() throws IllegalValueException {
        Set<Timetable> set = new HashSet<>(timetables);
        Set<Set<Timetable>> powerSet = powerSet(set);
        List<Set<Timetable>> powerList = new ArrayList<>(powerSet);
        powerList.sort((x, y) -> y.size() - x.size()); // Descending order of size
        for (Set<Timetable> possibleTimetables : powerList) {
            List<TimeRange> timeRanges = new TimeSlotGenerator(possibleTimetables, numberOfHours, userSpecifiedTimeRange).generate();
            if (!timeRanges.isEmpty() && possibleTimetables.size() > 0) {
                return new TimeSlotsAvailable(possibleTimetables, timeRanges);
            }
        }
        return new TimeSlotsAvailable(true);
    }

    /**
     * Convert {@code Collection<Timetable>} to {@code List<TimeRange>}
     * @param timetables Input timetables
     * @return List of TimeRange obtained by converting timetable into timerange.
     */
    private static List<TimeRange> combineTimetables(Collection<Timetable> timetables) {
        Set<TimeRange> timeRanges = new HashSet<>();
        for (Timetable timeTable : timetables) {
            timeRanges.addAll(timeTable.getTimeRanges());
        }
        return new ArrayList<>(timeRanges);
    }

    /**
     * Merge overlapping TimeRange into 1 TimeRange.
     * @param timeRanges List of TimeRange.
     * @return Merged list of TimeRange.
     * @throws IllegalValueException
     */
    private static List<TimeRange> mergedOverlappingTimeRanges(Collection<TimeRange> timeRanges) throws IllegalValueException {
        List<TimeRange> timeRangesList = new ArrayList<>(timeRanges);
        Collections.sort(timeRangesList);
        List<TimeRange> merged = new ArrayList<>();
        for (TimeRange timeRange : timeRangesList) {
            if (merged.isEmpty()) {
                merged.add(timeRange);
                continue;
            }
            TimeRange latest = merged.get(merged.size() - 1);
            if (latest.overlapInclusive(timeRange)) { // If last TimeRange in merged overlaps with new latest
                TimeRange tr = mergeTimeRange(timeRange, latest);
                merged.set(merged.size() - 1, tr);
            } else {
                merged.add(timeRange);
            }
        }
        return merged;
    }

    // @@author Andrew Mao
    // Reused from https://stackoverflow.com/a/14818944
    private static <T> Set<Set<T>> powerSet(Set<T> originalSet) {
        List<T> list = new ArrayList<T>(originalSet);
        int n = list.size();

        Set<Set<T>> powerSet = new HashSet<Set<T>>();

        for (long i = 0; i < (1 << n); i++) {
            Set<T> element = new HashSet<T>();
            for (int j = 0; j < n; j++) {
                if ((i >> j) % 2 == 1) {
                    element.add(list.get(j));
                }
            }
            powerSet.add(element);
        }
        return powerSet;
    }

    /**
     * Merge 2 TimeRange together, assuming that they overlap.
     * @param r1 TimeRange 1.
     * @param r2 TimeRange 2.
     * @return new merged TimeRange.
     * @throws IllegalValueException If error in creating new TimeRange.
     */
    private static TimeRange mergeTimeRange(TimeRange r1, TimeRange r2) throws IllegalValueException {
        return new TimeRange(r1.getStart().isBefore(r2.getStart()) ? r1.getStart() : r2.getStart(), r1.getEnd().isAfter(r2.getEnd()) ? r1.getEnd() : r2.getEnd());
    }

    private static List<TimeRange> getFreeTimeRanges(List<TimeRange> timeRanges) throws IllegalValueException {
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
        inverted.add(new TimeRange(cur.getDay(), cur.getTime(), DayOfWeek.SUNDAY, LocalTime.parse("23:59")));
        return inverted;
    }

    /**
     * Fit all TimeRange within limit specified
     * @param timeRanges Input TimeRanges
     * @param limit Clamp input TimeRanges within limit.
     * @return Transformed TimeRanges
     * @throws IllegalValueException
     */
    private static List<TimeRange> truncateTimeRanges(List<TimeRange> timeRanges, TimeRange limit) throws IllegalValueException {
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

    /**
     * Eliminate timeRanges shorter than specified duration
     * @param timeRanges Input
     * @param numberOfHours Duration in hours
     * @return Filtered result
     */
    private static List<TimeRange> filterSuitableTimeRanges(List<TimeRange> timeRanges, int numberOfHours) {
        List<TimeRange> possibleRanges = new ArrayList<>();
        for (TimeRange timeRange : timeRanges) {
            if (timeRange.getDuration().compareTo(new Duration(0, numberOfHours, 0)) >= 0) {
                possibleRanges.add(timeRange);
            }
        }
        return possibleRanges;
    }

}
