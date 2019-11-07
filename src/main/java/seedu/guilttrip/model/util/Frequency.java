package seedu.guilttrip.model.util;

import java.time.Period;
import java.time.temporal.TemporalAmount;

/**
 * An enum containing commonly used time periods as frequency.
 */
public enum Frequency {
    DAILY(Period.ofDays(1)), WEEKLY(Period.ofDays(7)), FORTNIGHTLY(Period.ofDays(14)), MONTHLY(Period.ofMonths(1)),
    QUARTERLY(Period.ofMonths(3)), ANNUALLY(Period.ofYears(1));

    private final TemporalAmount period;

    Frequency(TemporalAmount period) {
        this.period = period;
    }

    public TemporalAmount getPeriod() {
        return period;
    }

    /**
     * Quasi-NLP superpowers using switch cases.
     *
     * TODO: daily: daily, every day, everyday, every night weekly: weekly, every
     * week, every wk, wkly, wk fortnightly: fortnightly, every 2 weeks, every 2
     * wks, every two weeks, every two wks, fortnight, fortnite monthly: monthly,
     * mthly, mth quarterly: quarterly, qtrly anually: anually, yearly, every year,
     * every yr
     *
     * @param stringFreq
     * @return
     */
    public static Frequency parse(String stringFreq) {
        String sanitized = stringFreq.trim().toLowerCase();
        switch (sanitized) {
        case "daily":
        case "every day":
        case "everyday":
        case "every night":
        case "day":
            return DAILY;
        case "fortnightly":
            return FORTNIGHTLY;
        case "monthly":
        case "mth":
        case "every month":
        case "month":
            return MONTHLY;
        case "quarterly":
            return QUARTERLY;
        case "yearly":
        case "annually":
        case "year":
        case "yr":
            return ANNUALLY;
        case "weekly":
        case "wk":
        case "every week":
        case "week":
        default:
            return WEEKLY;
        }
    }
}
