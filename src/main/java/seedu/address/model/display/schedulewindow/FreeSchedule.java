package seedu.address.model.display.schedulewindow;

import java.time.DayOfWeek;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

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

    /**
     * Method to retrieve FreeTimeslot object from Free Schedule.
     * @param id Identifier used to identify free time.
     * @return Optional FreeTimeslot.
     */
    public Optional<FreeTimeslot> getFreeTimeslot(int id) {
        Optional<FreeTimeslot> freeTimeslot = Optional.empty();
        for (int i = 1; i <= 7; i++) {
            ArrayList<FreeTimeslot> timeslotForDay = freeSchedule.get(DayOfWeek.of(i));
            for (int j = 0; j < timeslotForDay.size(); j++) {
                if (id == timeslotForDay.get(j).getId()) {
                    freeTimeslot = Optional.of(timeslotForDay.get(j));
                    break;
                }
            }
        }
        return freeTimeslot;
    }
}
