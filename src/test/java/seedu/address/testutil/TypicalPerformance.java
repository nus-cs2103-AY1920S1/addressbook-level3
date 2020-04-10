package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.Performance;
import seedu.address.model.performance.Event;

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalPerformance {

    public static final Event EVENT_ONE = new Event("freestyle 50m");
    public static final Event EVENT_TWO = new Event("backstroke 100m");

    private TypicalPerformance() {} // prevents instantiation

    /**
     * Returns an {@code Performance} with all the typical events.
     */
    public static Performance getTypicalPerformance() {
        Performance performance = new Performance();
        for (Event event : getTypicalEvents()) {
            performance.addEvent(event);
        }
        return performance;
    }

    public static List<Event> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(EVENT_ONE, EVENT_TWO));
    }
}
