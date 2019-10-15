package seedu.address.model.performance;

import java.util.ArrayList;

public class Performance {
    private static ArrayList<Event> events;

    public static void addEvent(Event event) {
        events.add(event);
    }

}
