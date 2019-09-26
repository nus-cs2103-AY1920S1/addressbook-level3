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
    public boolean addTimeslot(ArrayList<Timeslot> timeslots) {
        if(timeslots == null){
            return false;
        }

        int i;
        for (i = 0; i < timeslots.size(); i++) {
            this.timeslots.add(timeslots.get(i));
        }
        return true;
    }

    public boolean addTimeslot(Timeslot timeslot) {
        if(timeslot == null){
            return false;
        } else {
            this.timeslots.add(timeslot);
            return true;
        }
    }

    /**
     * Converts to String.
     * @return String
     */
    public String toString() {
        String output = "";
        output += "Event: " + eventName + ": \n";
        int i;
        for (i = 0; i < timeslots.size(); i++) {
            output += "    " + timeslots.get(i).toString() + "\n";
        }
        return output;
    }

    public ArrayList<Timeslot> getTimeslots(){
        return this.timeslots;
    }

    public String getEventName(){
        return this.eventName;
    }

    public boolean equals(Event otherEvent){
        if(otherEvent == null){
            return false;
        } else if (!eventName.equals(otherEvent.getEventName())){
            return false;
        } else if(otherEvent.getTimeslots().size() != timeslots.size()){
            return false;
        } else {
            int i;
            for(i = 0; i < timeslots.size(); i++){
                if(!timeslots.get(i).equals(otherEvent.getTimeslots().get(i))){
                    return false;
                }
            }
            return true;
        }
    }
}
