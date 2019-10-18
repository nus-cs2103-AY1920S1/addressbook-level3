package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.tag.Tag;

class EventTest {
    private final EventName name = new EventName("Orchestra");
    private final EventVenue venue = new EventVenue("Esplanade");
    private final EventManpowerNeeded manpowerNeeded = new EventManpowerNeeded("10");
    private final EventDate startDate = new EventDate(LocalDate.of(2019, 10, 20));
    private final EventDate endDate = new EventDate(LocalDate.of(2019, 10, 25));
    private final Set<Tag> tags = new HashSet<>();
    private final Event eventTest = new Event(name, venue,
            manpowerNeeded, startDate, endDate, tags);

    @Test
    public void isSameEvent() {
        //same object --> Return true;
        assertTrue(eventTest.isSameEvent(eventTest));

        Event newEventTest = new Event(name, venue, manpowerNeeded,
                new EventDate(LocalDate.of(2019, 10, 21)), endDate, tags);

        assertFalse(eventTest.isSameEvent(newEventTest));
    }

}
