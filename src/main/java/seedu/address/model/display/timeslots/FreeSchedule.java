package seedu.address.model.display.timeslots;

import java.time.DayOfWeek;

import java.util.ArrayList;
import java.util.HashMap;

import seedu.address.model.person.exceptions.InvalidTimeslotException;

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
    public FreeTimeslot getFreeTimeslot(int id) throws InvalidTimeslotException {
        for (int i = 1; i <= 7; i++) {
            ArrayList<FreeTimeslot> timeslotForDay = freeSchedule.get(DayOfWeek.of(i));
            for (int j = 0; j < timeslotForDay.size(); j++) {
                if (id == timeslotForDay.get(j).getId()) {
                    return timeslotForDay.get(j);
                }
            }
        }
        throw new InvalidTimeslotException();
    }
}
