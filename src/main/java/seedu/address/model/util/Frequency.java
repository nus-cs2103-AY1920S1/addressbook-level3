package seedu.address.model.util;

import java.time.Period;
import java.time.temporal.TemporalAmount;

/**
 * An enum containing commonly used time periods as frequency.
 */
public enum Frequency {
    DAILY(Period.ofDays(1)), WEEKLY(Period.ofDays(7)), FORTNIGHTLY(Period.ofDays(14)), MONTHLY(
            Period.ofMonths(1)), QUARTERLY(Period.ofMonths(3)), ANUALLY(Period.ofYears(1));

    private final TemporalAmount period;

    Frequency(TemporalAmount period) {
        this.period = period;
    }

    public TemporalAmount getPeriod() {
        return period;
    }
}
