package seedu.address.model.distinctdate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.model.event.Event;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventManpowerNeeded;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventVenue;
import seedu.address.model.tag.Tag;

class DistinctDateTest {
    private final EventName name = new EventName("Orchestra");
    private final EventVenue venue = new EventVenue("Esplanade");
    private final EventManpowerNeeded manpowerNeeded = new EventManpowerNeeded("10");
    private final EventDate startDate = new EventDate(LocalDate.of(2019, 10, 20));
    private final EventDate endDate = new EventDate(LocalDate.of(2019, 10, 25));
    private final Set<Tag> tags = new HashSet<>();
    private final Event eventTest = new Event(name, venue,
            manpowerNeeded, startDate, endDate, tags);
    private final List<Event> eventListTest = new ArrayList<Event>(Arrays.asList(eventTest));
    private final EventDate dateTest = new EventDate(LocalDate.of(2019, 10, 20));

    private final DistinctDate distinctDateTest = new DistinctDate(dateTest, eventListTest);

    @Test
    public void isSameDistinctDate() {
        //same DistinctDate object --> Return true;
        assertTrue(distinctDateTest.isSameDate(distinctDateTest));
        EventDate newEventDate = new EventDate(LocalDate.of(2019, 10, 21));
        DistinctDate newDistinctDateTest = new DistinctDate(newEventDate, eventListTest);

        assertFalse(distinctDateTest.isSameDate(newDistinctDateTest));
    }

}
