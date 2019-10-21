package seedu.address.model.performance;

import java.util.ArrayList;
import java.util.TreeMap;

import seedu.address.model.person.Person;

/**
 * Events are types of activities that can measure performance. Examples include '50m breaststroke' or '100m freestyle'.
 */
public class Event {

    private String name;
    private TreeMap<Person, PerformanceEntry> performances;
    private static ArrayList<Event> events = new ArrayList<>();

    /**
     * Creates a type of event that stores the members and their respective timings (performance) for this event.
     * @param name of this event.
     */
    public Event(String name) {
        this.name = name;
        this.performances = new TreeMap<>();
    }

    /**
     * Retrieves a list of all events.
     * @return List of all events.
     */
    public static ArrayList<Event> getEvents() {
        return events;
    }

    public static void addEvent(String name) {
        Event newEvent = new Event(name);
        events.add(newEvent);
    }

    /**
     * Checks if an event with the specified name exists in the event list, case insensitive.
     * @param name of event to check for.
     * @return Whether an event with the specified name exists.
     */
    public static boolean doesExist(String name) {
        String queryName = name.toLowerCase();
        for (Event event : events) {
            String eventName = event.name.toLowerCase();
            if (eventName.equals(queryName)) {
                return true;
            }
        }
        return false;
    }
}
