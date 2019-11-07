package seedu.address.testutil;

import java.time.LocalTime;

import seedu.address.model.event.EventDayTime;

/**
 * Typical, Reusable {@code EventDayTime}(s). Guaranteed valid.
 */
public class TypicalEventDayTimes {
    public static final EventDayTime DEFAULT_DAY_TIME = EventDayTime.defaultEventDayTime();
    public static final EventDayTime TIME_0800_TO_1800 =
            new EventDayTime(LocalTime.of(8, 0), LocalTime.of(18, 0));
    public static final EventDayTime TIME_0800_TO_1230 =
            new EventDayTime(LocalTime.of(8, 0), LocalTime.of(12, 30));
    public static final EventDayTime TIME_1200_TO_1800 =
            new EventDayTime(LocalTime.of(12, 0), LocalTime.of(18, 0));

}
