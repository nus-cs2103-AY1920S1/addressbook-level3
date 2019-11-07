package seedu.address.testutil;

import java.time.LocalTime;

import seedu.address.model.event.EventDayTime;

public class TypicalEventDayTimes {
    public static final EventDayTime defaultDayTime = EventDayTime.defaultEventDayTime();
    public static final EventDayTime _0800_TO_1800 =
            new EventDayTime(LocalTime.of(8, 0), LocalTime.of(18, 0));
    public static final EventDayTime _0800_TO_1230 =
            new EventDayTime(LocalTime.of(8, 0), LocalTime.of(12, 30));
    public static final EventDayTime _1200_TO_1800 =
            new EventDayTime(LocalTime.of(12, 0), LocalTime.of(18, 0));

}
