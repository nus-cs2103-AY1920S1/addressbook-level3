package seedu.address.model.timetable;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TimeTable {
    private List<TimeRange> timeRanges;

    /**
     * Takes in a formatted string of timings.
     * @param timetable Newline separated TIMEOFWEEK HHMM timings for a timetable
     */
    public TimeTable(String timetable) throws IllegalValueException {
        timeRanges = new ArrayList<>();
        // for (String line : timetable.split("\n")) {
        //     timeRanges.add(ParserUtil.parseTimeRange(line));
        // }
        // TODO: THIS IS A VERY VERY UGLY HACK. CHANGE THIS WHEN WE MIGRATE TO STORING TIMETABLE IN MEMBER SO THAT WE DON'T HAVE TO PARSE IT FROM COMMAND LINE
        String[] splitted = timetable.split(" ");
        for (int i = 0; i < splitted.length; i += 4) {
            timeRanges.add(ParserUtil.parseTimeRange(splitted[i] + " " + splitted[1 + i] + " " + splitted[2 + i] + " " + splitted[3 + i]));
        }
    }

    public List<TimeRange> getTimeRanges() {
        return timeRanges;
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
