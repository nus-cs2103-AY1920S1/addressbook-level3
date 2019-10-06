package seedu.address.model.weekschedule;

import seedu.address.model.person.Name;
import seedu.address.model.person.schedule.Venue;

import java.time.LocalTime;

public class DayTimeslot {

    private Name name;
    private String eventName;

    private LocalTime startTime;
    private LocalTime endTime;
    private Venue venue;


    public DayTimeslot(Name name, String eventName, LocalTime startTime, LocalTime endTime, Venue venue) {

        this.name = name;
        this.eventName = eventName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.venue = venue;

    }

    public Name getName() {
        return name;
    }

    public String getEventName() {
        return eventName;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public Venue getVenue() {
        return venue;
    }
}
