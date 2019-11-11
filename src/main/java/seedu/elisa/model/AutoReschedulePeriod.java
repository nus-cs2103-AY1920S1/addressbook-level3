package seedu.elisa.model;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Represents the auto-reschedule period of an event.
 */
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

    /**
     * Get an auto-reschedule period of one hour.
     * @return AutoReschedulePeriod object of one hour.
     */
    public static AutoReschedulePeriod byHour() {
        long hourPeriod = hourInMilliseconds;
        return new AutoReschedulePeriod(hourPeriod);
    }

    /**
     * Get an auto-reschedule period of one day.
     * @return AutoReschedulePeriod object of one day.
     */
    public static AutoReschedulePeriod byDay() {
        long dayPeriod = dayInMilliseconds;
        return new AutoReschedulePeriod(dayPeriod);
    }

    /**
     * Get an auto-reschedule period of one week.
     * @return AutoReschedulePeriod object of one week.
     */
    public static AutoReschedulePeriod byWeek() {
        long weekPeriod = weekInMilliseconds;
        return new AutoReschedulePeriod(weekPeriod);
    }

    /**
     * Get an auto-reschedule period of this LocalDateTime {@code then} from now.
     * @param then LocalDateTime of the time later
     * @return AutoReschedulePeriod object representing the time difference from now till then.
     */
    public static AutoReschedulePeriod from(LocalDateTime then) {
        Duration duration = Duration.between(LocalDateTime.now(), then);
        return new AutoReschedulePeriod(duration.toMillis());
    }

    /**
     * Get the period of this AutoReschedulePeriod in Millis.
     * @return a long representation of this period in Millis.
     */
    public long getPeriod() {
        return this.period;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AutoReschedulePeriod)) {
            return false;
        }

        AutoReschedulePeriod otherPeriod = (AutoReschedulePeriod) other;
        Long otherLongPeriod = Long.valueOf(otherPeriod.getPeriod());
        Long thisLongPeriod = Long.valueOf(getPeriod());
        return otherLongPeriod.equals(thisLongPeriod);
    }
}
