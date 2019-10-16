package seedu.address.testutil;

import java.time.LocalDate;
import java.time.LocalTime;

import seedu.address.model.calendar.DateTime;
import seedu.address.model.calendar.Description;
import seedu.address.model.calendar.Event;
import seedu.address.model.calendar.Reminder;
import seedu.address.model.calendar.Repetition;

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {
    public static final LocalDate DATE_FEB10 = LocalDate.of(2020, 2, 10);
    public static final LocalDate DATE_MAR22 = LocalDate.of(2020, 3, 22);
    public static final LocalTime TIME_MORN = LocalTime.of(7, 45);
    public static final LocalTime TIME_EVEN = LocalTime.of(19, 0);
    public static final Reminder AUTO_REMINDER = new Reminder(new Description("description"),
            new DateTime(DATE_FEB10, TIME_EVEN), Repetition.Once);

    public static final Event APPOINTMENT = new EventBuilder().build();
    public static final Event MEETING = new EventBuilder().withDescription("Team meeting")
            .withDateTime(DATE_MAR22, TIME_MORN).build();
    private TypicalEvents() {} // prevents instantiation
}
