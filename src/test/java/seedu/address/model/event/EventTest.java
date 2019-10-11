package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;

class EventTest {
    private final EventId id = new EventId();
    private final EventName name = new EventName("Orchestra");
    private final EventVenue venue = new EventVenue("Esplanade");
    private final EventHoursNeeded hoursNeeded = new EventHoursNeeded("20");
    private final EventManpowerNeeded manpowerNeeded = new EventManpowerNeeded("10");
    private final EventStartDate startDate = new EventStartDate(LocalDate.of(2019, 10, 20));
    private final EventEndDate endDate = new EventEndDate(LocalDate.of(2019, 10, 25));
    private final Set<Tag> tags = new HashSet<>();
    private final Event eventTest = new Event(id, name, venue, hoursNeeded,
            manpowerNeeded, startDate, endDate, tags);

    @org.junit.jupiter.api.Test

    @Test
    public void isSameEvent() {
        //same object --> Return true;
        assertTrue(eventTest.isSameEvent(eventTest));

        Event newEventTest = new Event(new EventId(), name, venue,
                hoursNeeded, manpowerNeeded, startDate, endDate, tags);

        //No two events have the same ID
        assertFalse(eventTest.isSameEvent(newEventTest));
    }

}
