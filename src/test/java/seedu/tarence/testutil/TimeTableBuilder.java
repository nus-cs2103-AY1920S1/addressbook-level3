package seedu.tarence.testutil;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.TreeSet;

import seedu.tarence.model.tutorial.TimeTable;
import seedu.tarence.model.tutorial.Week;


/**
 * A utility class to help with building Module objects.
 */
public class TimeTableBuilder {

    public static final int DEFAULT_DURATION = 100;
    public static final String DEFAULT_DAY = "MONDAY";
    public static final String DEFAULT_STARTTIME = "12:00:00";
    public static final Set<Week> DEFAULT_WEEKS = new TreeSet<>();

    private Duration duration;
    private DayOfWeek day;
    private LocalTime startTime;
    private Set<Week> weeks;

    public TimeTableBuilder() {
        duration = Duration.ofMinutes(DEFAULT_DURATION);
        day = DayOfWeek.valueOf(DEFAULT_DAY);
        startTime = LocalTime.parse(DEFAULT_STARTTIME, DateTimeFormatter.ISO_TIME);
        DEFAULT_WEEKS.add(new Week(1));
        weeks = DEFAULT_WEEKS;
    }

    /**
     * Initializes the TimeTableBuilder with the data of {@code timeTableToCopy}.
     */
    public TimeTableBuilder(TimeTable timeTableToCopy) {
        duration = timeTableToCopy.getDuration();
        day = timeTableToCopy.getDay();
        startTime = timeTableToCopy.getStartTime();
        weeks = timeTableToCopy.getWeeks();
    }

    /**
     * Sets the {@code Duration} of the {@code TimeTable} that we are building.
     */
    public TimeTableBuilder withDuration(int duration) {
        this.duration = Duration.ofMinutes(duration);
        return this;
    }

    /**
     * Sets the {@code DayOfWeek} of the {@code TimeTable} that we are building.
     */
    public TimeTableBuilder withDayOfWeek(String day) {
        this.day = DayOfWeek.valueOf(day);
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code TimeTable} that we are building.
     */
    public TimeTableBuilder withStartTime(String startTime) {
        this.startTime = LocalTime.parse(startTime, DateTimeFormatter.ISO_TIME);
        return this;
    }

    /**
     * Sets the {@code Weeks} of the {@code TimeTable} that we are building.
     */
    public TimeTableBuilder withWeeks(Set<Week> weeks) {
        this.weeks = weeks;
        return this;
    }

    public TimeTable build() {
        return new TimeTable(day, startTime, weeks, duration);
    }

}
