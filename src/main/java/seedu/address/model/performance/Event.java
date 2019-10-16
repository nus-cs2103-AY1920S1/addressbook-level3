package seedu.address.model.performance;

import java.util.TreeMap;

import seedu.address.model.person.Person;

/**
 * Events are types of activities that can measure performance. Examples include '50m breaststroke' or '100m freestyle'.
 */
public class Event {

    private String name;
    private TreeMap<Person, PerformanceEntry> performances;

    /**
     * Creates a type of event that stores the members and their respective timings (performance) for this event.
     * @param name Name of this event.
     */
    public Event(String name) {
        this.name = name;
        this.performances = new TreeMap<>();
    }

}
