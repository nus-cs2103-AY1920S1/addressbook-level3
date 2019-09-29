package seedu.tarence.testutil;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.module.Module;
import seedu.tarence.model.tutorial.TimeTable;
import seedu.tarence.model.tutorial.Tutorial;


/**
 * A utility class to help with building Module objects.
 */
public class TimeTableBuilder {

    public static final int DEFAULT_DURATION = 100;
    public static final String DEFAULT_DAY= "MONDAY";
    public static final String DEFAULT_TIME = "12:00:00";
    public static final List<Integer> DEFAULT_WEEKS = new ArrayList<>();

    private Duration duration;
    private DayOfWeek day;
    private LocalTime time;
    private List<Integer> weeks;

    public TimeTableBuilder() {
        duration = Duration.ofMinutes(DEFAULT_DURATION);
        day = DayOfWeek.valueOf(DEFAULT_DAY);
        time = LocalTime.parse(DEFAULT_TIME, DateTimeFormatter.ISO_TIME);
        DEFAULT_WEEKS.add(1);
        weeks = DEFAULT_WEEKS;
    }

    /**
     * Initializes the TimeTableBuilder with the data of {@code timeTableToCopy}.
     */
    public TimeTableBuilder(TimeTable timeTableToCopy) {
        duration = timeTableToCopy.getDuration();
        day = timeTableToCopy.getDay();
        time = timeTableToCopy.getTime();
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
    public TimeTableBuilder withTime(String time) {
        this.time = LocalTime.parse(time, DateTimeFormatter.ISO_TIME);
        return this;
    }

    /**
     * Sets the {@code Weeks} of the {@code TimeTable} that we are building.
     */
    public TimeTableBuilder withWeeks(List<Integer> weeks) {
        this.weeks = weeks;
        return this;
    }

    public TimeTable build() {
        return new TimeTable(day, time, weeks, duration);
    }

}
