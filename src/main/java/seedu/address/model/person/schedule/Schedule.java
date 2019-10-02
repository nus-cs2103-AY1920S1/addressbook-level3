package seedu.address.model.person.schedule;

import java.util.ArrayList;

import seedu.address.model.person.PersonId;

/**
 * Schedule of a person.
 */
public class Schedule {
    private final PersonId personId;
    private ArrayList<Event> events;

    public Schedule(PersonId personId) {
        this.personId = personId;
        this.events = new ArrayList<Event>();
    }

    public Schedule(PersonId personId, ArrayList<Event> events) {
        this.personId = personId;
        this.events = events;
    }

    public void addEvent(Event event) {
        this.events.add(event);
    }

    /**
     * Converts to String.
     *
     * @return String
     */
    public String toString() {
        String output = "===== SCHEDULE =====\n";
        int i;

        if (events.size() == 0) {
            output += "NO EVENTS AVAILABLE\n\n";
        } else {
            for (i = 0; i < events.size(); i++) {
                output += events.get(i).toString() + "\n";
            }
        }
        output += "\n";
        return output;
    }

    public ArrayList<Event> getEvents() {
        return this.events;
    }

    public PersonId getPersonId() {
        return this.personId;
    }
}
