package seedu.address.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class AutoReschedulePeriod {

    public static final String BY_HOUR = "HOUR";
    public static final String BY_DAY = "DAY";
    public static final String BY_WEEK = "WEEK";
    private static final Long hourInMilliseconds = Duration.ofHours(1).toMillis();
    private static final Long dayInMilliseconds = Duration.ofDays(1).toMillis();
    private static final Long weekInMilliseconds = Duration.ofDays(7).toMillis();

    private long period;

    public AutoReschedulePeriod(long period) {
        this.period = period;
    }

    public static AutoReschedulePeriod byHour() {
        long hourPeriod = hourInMilliseconds;
        return new AutoReschedulePeriod(hourPeriod);
    }

    public static AutoReschedulePeriod byDay() {
        long dayPeriod = dayInMilliseconds;
        return new AutoReschedulePeriod(dayPeriod);
    }

    public static AutoReschedulePeriod byWeek() {
        long weekPeriod = weekInMilliseconds;
        return new AutoReschedulePeriod(weekPeriod);
    }

    public static AutoReschedulePeriod from(LocalDateTime then) {
        Duration duration = Duration.between(LocalDateTime.now(), then);
        return new AutoReschedulePeriod(duration.toMillis());
    }

    public long getPeriod() {
        return this.period;
    }
}
