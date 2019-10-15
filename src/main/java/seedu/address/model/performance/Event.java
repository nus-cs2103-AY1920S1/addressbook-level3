package seedu.address.model.performance;

import java.util.TreeMap;

import seedu.address.model.person.Person;

public class Event {
    private String name;
    private TreeMap<Person, PerformanceEntry> performances;

    public Event(String name) {
        this.name = name;
        this.performances = new TreeMap<>();
    }

}
