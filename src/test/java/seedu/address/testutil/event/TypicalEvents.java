package seedu.address.testutil.event;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.event.Event;
import seedu.address.model.event.EventRecord;
import seedu.address.model.event.RecurrenceType;

/**
 * A utility class containing a list of {@code Events} objects to be used in tests.
 */
public class TypicalEvents {

    public static final Event EVENT1 = new EventBuilder().withEventName("First Event")
            .withStartDateTime(LocalDateTime.now())
            .withEndDateTime((LocalDateTime.now().plusHours(1)))
            .withRecurrenceType(RecurrenceType.NONE)
            .withColorCategory("group01")
            .withUniqueIdentifier("typicalevent1test").build();
    public static final Event EVENT2 = new EventBuilder().withEventName("Second Event")
            .withStartDateTime(LocalDateTime.now().plusDays(1))
            .withEndDateTime((LocalDateTime.now().plusDays(1).plusHours(1)))
            .withRecurrenceType(RecurrenceType.DAILY)
            .withColorCategory("group02")
            .withUniqueIdentifier("typicalevent2test").build();
    public static final Event EVENT3 = new EventBuilder().withEventName("Third Event")
            .withStartDateTime(LocalDateTime.now().plusDays(2))
            .withEndDateTime((LocalDateTime.now().plusDays(2).plusHours(1)))
            .withRecurrenceType(RecurrenceType.WEEKLY)
            .withColorCategory("group03")
            .withUniqueIdentifier("typicalevent3test").build();
    public static final Event EVENT4 = new EventBuilder().withEventName("Fourth Event")
            .withStartDateTime(LocalDateTime.now().plusDays(3))
            .withEndDateTime((LocalDateTime.now().plusDays(3).plusHours(1)))
            .withRecurrenceType(RecurrenceType.NONE)
            .withColorCategory("group04")
            .withUniqueIdentifier("typicalevent4test").build();

    public static final Event NOT_IN_TYPICAL = new EventBuilder().withEventName("Not Typical Event")
            .withStartDateTime(LocalDateTime.now().plusDays(4))
            .withEndDateTime((LocalDateTime.now().plusDays(4).plusHours(1)))
            .withRecurrenceType(RecurrenceType.NONE)
            .withColorCategory("group05")
            .withUniqueIdentifier("notTypicalIdentifier").build();


    private TypicalEvents() {}

    /**
     * Returns an {@code EventRecord} with all the typical events.
     */
    public static EventRecord getTypicalEventsRecord() {
        EventRecord ab = new EventRecord(getTypicalEvents());
        return ab;
    }

    public static List<Event> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(EVENT1, EVENT2, EVENT3, EVENT4));
    }
}
