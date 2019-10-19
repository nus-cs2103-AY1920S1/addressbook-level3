package seedu.address.model.person.schedule;

import java.util.ArrayList;

import seedu.address.model.person.PersonId;
import seedu.address.model.person.exceptions.EventClashException;

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

    public void addEvent(Event event) throws EventClashException {
        if(isClash(event)) {
            throw new EventClashException(event);
        }
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

    /**
     * Checks is there is a clash in the schedule with another event.
     *
     * @param event to be checked
     * @return boolean
     */
    public boolean isClash(Event event) {
        for (int i = 0; i < events.size(); i++) {
            if (event.isClash(events.get(i))) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Event> getEvents() {
        return this.events;
    }

    public PersonId getPersonId() {
        return this.personId;
    }
}
