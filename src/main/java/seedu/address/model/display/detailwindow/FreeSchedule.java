package seedu.address.model.display.detailwindow;

import java.time.DayOfWeek;
import java.time.LocalTime;

import java.util.ArrayList;
import java.util.HashMap;

import seedu.address.model.person.schedule.Venue;

/**
 * A FreeSchedule class that represents the free timeslot among the Persons for the week.
 */
public class FreeSchedule {

    private HashMap<DayOfWeek, ArrayList<FreeTimeslot>> freeSchedule;

    public FreeSchedule(HashMap<DayOfWeek, ArrayList<FreeTimeslot>> freeSchedule) {
        this.freeSchedule = freeSchedule;
    }


    public HashMap<DayOfWeek, ArrayList<FreeTimeslot>> getFreeSchedule() {
        return freeSchedule;
    }
}
