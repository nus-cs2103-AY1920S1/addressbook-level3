package seedu.address.model.person.schedule;

import java.util.ArrayList;

/**
 * Schedule of a person.
 */
public class Schedule {
    private ArrayList<Event> events;

    public Schedule() {
        this.events = new ArrayList<Event>();
    }

    public void addEvent(Event event) {
        this.events.add(event);
    }

    /**
     * Converts to String.
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
}
