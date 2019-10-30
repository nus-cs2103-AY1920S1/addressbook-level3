package seedu.address.model.person.schedule;

import java.util.ArrayList;

import seedu.address.model.person.PersonId;
import seedu.address.model.person.exceptions.EventClashException;
import seedu.address.model.person.exceptions.EventNotFoundException;

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

    /**
     * Adds an event into the schedule.
     *
     * @param event to be added
     * @throws EventClashException when there is a clash in the events
     */
    public void addEvent(Event event) throws EventClashException {
        if (isClash(event)) {
            throw new EventClashException(event);
        } else if (isEventExist(event)) {

            Event currentEvent = findEvent(event.getEventName());
            if (currentEvent != null) {
                currentEvent.addTimeslot(event.getTimeslots());
            } else {
                this.events.add(event);
            }
        } else {
            this.events.add(event);
        }
    }

    /**
     * Removes an event from the schedule.
     *
     * @param eventName to be deleted
     * @throws EventNotFoundException
     */
    public void deleteEvent(String eventName) throws EventNotFoundException {
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getEventName().equals(eventName)) {
                events.remove(i);
                return;
            }
        }
        throw new EventNotFoundException();
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

    /**
     * Checks if the event already exists in the schedule.
     *
     * @param event
     * @return boolean
     */
    private boolean isEventExist(Event event) {
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getEventName().equals(event.getEventName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the event in the schedule of the specified name.
     *
     * @param eventName of event to be found
     * @return Event
     */
    private Event findEvent(String eventName) {
        for (int i = 0; i < events.size(); i++) {
            if (events.get(i).getEventName().equals(eventName)) {
                return events.get(i);
            }
        }
        return null;
    }

    public ArrayList<Event> getEvents() {
        return this.events;
    }

    public PersonId getPersonId() {
        return this.personId;
    }
}
