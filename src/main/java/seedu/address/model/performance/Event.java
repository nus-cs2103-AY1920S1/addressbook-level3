package seedu.address.model.performance;

import java.util.ArrayList;
import java.util.HashMap;

import seedu.address.model.person.Person;

/**
 * Events are types of activities that can measure performance. Examples include '50m breaststroke' or '100m freestyle'.
 */
public class Event {

    public static final String MESSAGE_CONSTRAINTS = "%1$s event has not been created.\n"
            + "Please use the event command to create the event first.";

    public static final String INVALID_NAME_MESSAGE_CONSTRAINTS = "Evnt name should not begin with a space.\n";

    /*
     * The first character of the address must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    private String name;
    private HashMap<Person, ArrayList<Record>> performances;

    /**
     * Creates a type of event that stores the members and their respective timings (performance) for this event.
     * @param name of this event.
     */
    public Event(String name) {
        this.name = name.toLowerCase();
        this.performances = new HashMap<>();
    }

    /**
     * Returns true if both events have the same name.
     */
    public boolean isSameEvent(Event otherEvent) {

        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null && otherEvent.getName().equals(name);
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    public String getName() {
        return name;
    }

    public String addPerformance(Person athlete, Record record) {
        if (!performances.containsKey(athlete)) {
            ArrayList<Record> initialisedPerformanceEntries = new ArrayList<>();
            initialisedPerformanceEntries.add(record);
            performances.put(athlete, initialisedPerformanceEntries);
        } else {
            // copying the existing performances
            ArrayList<Record> currentPerformanceEntries = new ArrayList<>();
            currentPerformanceEntries.addAll(performances.get(athlete));
            // adding the new performance
            currentPerformanceEntries.add(record);
            // remove the existing athelete record for this event
            performances.remove(athlete);
            // adding the athlete again with their updated record for this event
            performances.put(athlete, currentPerformanceEntries);
        }
        return "For " + athlete.getName() + " in the " + name + " event, on " + record.getDate()
                + " with a timing of " + record.getTime();
    }

}
