package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.EventList;
import seedu.address.model.performance.Event;

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {

    public static final Event EVENT_ONE = new Event("freestyle 50m");
    public static final Event EVENT_TWO = new Event("backstroke 100m");

    private TypicalEvents() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static EventList getTypicalEventList() {
        EventList el = new EventList();
        for (Event event : getTypicalEvents()) {
            el.addEvent(event);
        }
        return el;
    }

    public static List<Event> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(EVENT_ONE, EVENT_TWO));
    }
}
