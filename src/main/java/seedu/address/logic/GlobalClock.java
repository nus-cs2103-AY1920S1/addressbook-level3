package seedu.address.logic;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

/**
 * A class to get the time and date now. For simplicity of testing, there are static methods to enable the clock to
 * return a fixed date or time. The date time is 15 Oct 2019, 2pm SGT.
 */
public class GlobalClock {
    private static final String DEFAULT_DATETIME = "2019-10-15T06:00:00Z";
    private static final String DEFAULT_TIMEZONE = "Asia/Singapore";
    private static Clock clock = Clock.systemDefaultZone();

    public static LocalTime timeNow() {
        return LocalTime.now(GlobalClock.clock).truncatedTo(ChronoUnit.MINUTES);
    }

    public static LocalDate dateToday() {
        return LocalDate.now(GlobalClock.clock);
    }

    /**
     * Sets the fixed clock to 15 Oct 2019, 2pm SGT
     */
    public static void setFixedClock() {
        GlobalClock.clock = buildFixedClock(DEFAULT_DATETIME);
    }


    public static void setRealClock() {
        GlobalClock.clock = Clock.systemDefaultZone();
    }

    private static Clock buildFixedClock(String dateTime) {
        Instant fixedTime = Instant.parse(dateTime);
        ZoneId sg = ZoneId.of(DEFAULT_TIMEZONE);
        return Clock.fixed(fixedTime, sg);
    }

    /**
     * Returns a date of 15 Oct 2019
     *
     * @return the fixed date
     */
    public static LocalDate getStaticDate() {
        return LocalDate.now(buildFixedClock(DEFAULT_DATETIME));
    }

    /**
     * Returns a time of 2pm SGT.
     *
     * @return the fixed time
     */
    public static LocalTime getStaticTime() {
        return LocalTime.now(buildFixedClock(DEFAULT_DATETIME));
    }

}
