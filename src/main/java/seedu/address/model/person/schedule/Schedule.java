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
        String output = "";
        int i;

        if (events.size() == 0) {
            output += "No Events Available";
        } else {
            for (i = 0; i < events.size(); i++) {
                output += events.get(i).toString();
                if (i + 1 != events.size()) {
                    output += " || ";
                }
            }
        }
        return output;
    }

}
