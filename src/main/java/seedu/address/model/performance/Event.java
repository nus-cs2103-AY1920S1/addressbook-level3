package seedu.address.model.performance;

import java.util.ArrayList;
import java.util.TreeMap;

import seedu.address.model.person.Person;

/**
 * Events are types of activities that can measure performance. Examples include '50m breaststroke' or '100m freestyle'.
 */
public class Event {

    public static final String MESSAGE_CONSTRAINTS = "Event has not been created.";

    private static ArrayList<Event> events = new ArrayList<>();

    private String name;
    private TreeMap<Person, ArrayList<PerformanceEntry>> performances;

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

    /**
     * Adds an event to the event list.
     * @param name of event added.
     */
    public static void addEvent(String name) {
        Event newEvent = new Event(name);
        events.add(newEvent);
    }

    public void addPerformance(Person athlete, PerformanceEntry performanceEntry) {
        if (!performances.containsKey(athlete)) {
            ArrayList<PerformanceEntry> initialisedPerformanceEntries = new ArrayList<>();
            initialisedPerformanceEntries.add(performanceEntry);
            performances.put(athlete, initialisedPerformanceEntries);
        } else {
            // copying the existing performances
            ArrayList<PerformanceEntry> currentPerformanceEntries = new ArrayList<>();
            currentPerformanceEntries.addAll(performances.get(athlete));
            // adding the new performance
            currentPerformanceEntries.add(performanceEntry);
            // remove the existing athelete record for this event
            performances.remove(athlete);
            // adding the athlete again with their updated record for this event
            performances.put(athlete, currentPerformanceEntries);
        }

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

    /**
     * Retrieves an event of the specified name from the event list.
     * @param name of the event to be retrieved.
     * @return Event of the specified name.
     */
    public static Event getEvent(String name) {
        assert doesExist(name);
        String queryName = name.toLowerCase();
        for (Event event : events) {
            String eventName = event.name.toLowerCase();
            if (eventName.equals(queryName)) {
                return event;
            }
        }
        return null;
    }
}
