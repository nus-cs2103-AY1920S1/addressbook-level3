package seedu.billboard.commons.core.date;

import java.time.DayOfWeek;
import java.time.Period;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;


/**
 * Enum representing an interval for dates.
 */
public enum DateInterval {
    DAY(Period.ofDays(1), TemporalAdjusters.ofDateAdjuster(x -> x)),
    WEEK(Period.ofWeeks(1), TemporalAdjusters.previous(DayOfWeek.MONDAY)),
    MONTH(Period.ofMonths(1), TemporalAdjusters.firstDayOfMonth()),
    YEAR(Period.ofYears(1), TemporalAdjusters.firstDayOfYear());

    private final Period period;
    private final TemporalAdjuster adjuster;

    DateInterval(Period period, TemporalAdjuster adjuster) {
        this.period = period;
        this.adjuster = adjuster;
    }

    /**
     * Getter for the time period this interval represents.
     * @return the time period, as a {@code Period}.
     */
    public Period getPeriod() {
        return period;
    }

    /**
     * Getter for the {@code TemporalAdjuster} to get the previous date from a {@code LocalDate} that represents the
     * start of a logical human period, such as the previous Monday, or the first day of a month.
     * @return the appropriate {@code TemporalAdjuster}.
     */
    public TemporalAdjuster getAdjuster() {
        return adjuster;
    }
}
