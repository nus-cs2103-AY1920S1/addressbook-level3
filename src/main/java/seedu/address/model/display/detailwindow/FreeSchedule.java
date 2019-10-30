package seedu.address.model.display.detailwindow;

import java.time.DayOfWeek;

import java.util.ArrayList;
import java.util.HashMap;

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

    public FreeTimeslot getFreeTimeslot(int id) {
        for(int i = 1; i <= 7; i ++) {
            ArrayList<FreeTimeslot> freeTimeslots = freeSchedule.get(DayOfWeek.of(i));
            for(int j = 0; j < freeTimeslots.size(); j++) {
                if(id == freeTimeslots.get(j).getId()) {
                    return freeTimeslots.get(j);
                }
            }
        }
        return null;
    }
}
