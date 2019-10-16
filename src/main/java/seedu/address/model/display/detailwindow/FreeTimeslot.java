package seedu.address.model.display.detailwindow;

import java.time.LocalTime;

import java.util.ArrayList;

import seedu.address.model.person.schedule.Venue;

/**
 * A Free Timeslot class that represents the free time of the group schedule and the previous locations of the members.
 */
public class FreeTimeslot {

    private ArrayList<Venue> venues;

    private LocalTime startTime;
    private LocalTime endTime;

    public FreeTimeslot(ArrayList<Venue> venues, LocalTime startTime, LocalTime endTime) {
        this.venues = venues;
        this.startTime = startTime;
        this.endTime = endTime;

        for (int i = 0; i < this.venues.size(); i++) {
            if (this.venues.get(i) == null) {
                this.venues.remove(i);
                i--;
            }
        }
    }

    public ArrayList<Venue> getVenues() {
        return venues;
    }

    public void setVenues(ArrayList<Venue> venues) {
        this.venues = venues;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
