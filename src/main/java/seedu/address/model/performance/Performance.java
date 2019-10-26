package seedu.address.model.performance;

import java.util.ArrayList;

public class Performance {

    private static ArrayList<Event> events;

    public Performance() {
        this.events = new ArrayList<>();
    }
    /**
     * Retrieves a list of all events.
     * @return List of all events.
     */
    public static ArrayList<Event> getEvents() {
        return events;
    }

    /**
     * Adds an event to the event list.
     */
    public static void addEvent(Event event) {
        events.add(event);
    }

    /**
     * Returns true if this event is in Athletick.
     */
    public boolean hasEvent(Event event) {
        return events.contains(event);
    }

    /**
     * Checks if an event with the specified name exists in the event list, case insensitive.
     * @param name of event to check for.
     * @return Whether an event with the specified name exists.
     */
    public static boolean doesEventExist(String name) {
        String queryName = name.toLowerCase();
        for (Event event : events) {
            String eventName = event.getName().toLowerCase();
            if (eventName.equals(queryName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves an event of the specified name from the event list.
     * @param name of the event to be retrieved.
     * @return Event of the specified name.
     */
    public static Event retrieveEvent(String name) {
        assert doesEventExist(name);
        String queryName = name.toLowerCase();
        for (Event event : events) {
            String eventName = event.getName().toLowerCase();
            if (eventName.equals(queryName)) {
                return event;
            }
        }
        return null;
    }

}
