package seedu.address.model.person.schedule;

import java.util.ArrayList;

/**
 * Event of a Schedule.
 */
public class Event {
    private String eventName;
    private ArrayList<Timeslot> timeslots;

    public Event(String eventName) {
        this.eventName = eventName;
        this.timeslots = new ArrayList<Timeslot>();
    }

    public Event(String eventName, ArrayList<Timeslot> timeslots) {
        this.eventName = eventName;
        this.timeslots = timeslots;
    }

    /**
     * Adds a timeslot to the Event.
     * @param timeslots to be added
     */
    public void addTimeslot(ArrayList<Timeslot> timeslots) {
        int i;
        for (i = 0; i < timeslots.size(); i++) {
            this.timeslots.add(timeslots.get(i));
        }
    }

    public void addTimeslot(Timeslot timeslot) {
        this.timeslots.add(timeslot);
    }

    /**
     * Converts to String.
     * @return String
     */
    public String toString() {
        String output = "";
        output += "Event: " + eventName + ": ";
        int i;
        for (i = 0; i < timeslots.size(); i++) {
            output += timeslots.get(i).toString();
            if (i + 1 != timeslots.size()) {
                output += ", ";
            }
        }
        return output;
    }
}
