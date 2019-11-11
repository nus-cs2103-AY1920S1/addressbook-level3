package seedu.billboard.commons.core.date;

import java.time.DayOfWeek;
import java.time.Period;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Enum representing an interval for dates.
 */
public enum DateInterval {
    DAY("day", Period.ofDays(1), TemporalAdjusters.ofDateAdjuster(x -> x)),
    WEEK("week", Period.ofWeeks(1), TemporalAdjusters.previous(DayOfWeek.MONDAY)),
    MONTH("month", Period.ofMonths(1), TemporalAdjusters.firstDayOfMonth()),
    YEAR("year", Period.ofYears(1), TemporalAdjusters.firstDayOfYear());

    public static final String MESSAGE_CONSTRAINTS =
            "Names should only contain alphanumeric characters and spaces, and it should not be blank";

    private static final Map<String, DateInterval> nameToDateIntervalMap;
    private final String name;
    private final Period period;
    private final TemporalAdjuster adjuster;

    static {
        nameToDateIntervalMap = new HashMap<>();
        for (DateInterval interval : values()) {
            nameToDateIntervalMap.put(interval.getName(), interval);
        }
    }

    DateInterval(String name, Period period, TemporalAdjuster adjuster) {
        this.name = name;
        this.period = period;
        this.adjuster = adjuster;
    }

    /**
     * Returns the correct {@code DateInterval} corresponding to the input name.
     *
     * @param name String name of a {@code DateInterval}
     * @return Optional wrapper around the corresponding {@code DateInterval} or an empty optional if no
     * corresponding interval exists.
     */
    public static Optional<DateInterval> intervalFromName(String name) {
        return Optional.ofNullable(nameToDateIntervalMap.get(name));
    }

    /**
     * Checks that {@code String interval} is a valid {@code DateInterval}
     */
    public static boolean isValidDateInterval(String interval) {
        try {
            DateInterval.valueOf(interval);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }


    /**
     * Returns the name of the {@code DateInterval}.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the time period this interval represents.
     *
     * @return the time period, as a {@code Period}.
     */
    public Period getPeriod() {
        return period;
    }

    /**
     * Getter for the {@code TemporalAdjuster} to get the previous date from a {@code LocalDate} that represents the
     * start of a logical human period, such as the previous Monday, or the first day of a month.
     *
     * @return the appropriate {@code TemporalAdjuster}.
     */
    public TemporalAdjuster getAdjuster() {
        return adjuster;
    }
}
