package seedu.address.model.display.schedulewindow;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class that contains the free month schedules.
 */
public class MonthFreeSchedule {
    private HashMap<DayOfWeek, ArrayList<FreeTimeslot>> weekZero;
    private HashMap<DayOfWeek, ArrayList<FreeTimeslot>> weekOne;
    private HashMap<DayOfWeek, ArrayList<FreeTimeslot>> weekTwo;
    private HashMap<DayOfWeek, ArrayList<FreeTimeslot>> weekThree;

    public MonthFreeSchedule(HashMap<DayOfWeek, ArrayList<FreeTimeslot>> weekZero,
                             HashMap<DayOfWeek, ArrayList<FreeTimeslot>> weekOne,
                             HashMap<DayOfWeek, ArrayList<FreeTimeslot>> weekTwo,
                             HashMap<DayOfWeek, ArrayList<FreeTimeslot>> weekThree) {
        this.weekZero = weekZero;
        this.weekOne = weekOne;
        this.weekTwo = weekTwo;
        this.weekThree = weekThree;
    }

    public HashMap<DayOfWeek, ArrayList<FreeTimeslot>> getFreeScheduleForWeek(int i) {
        if (i == 0) {
            return weekZero;
        } else if (i == 1) {
            return weekOne;
        } else if (i == 2) {
            return weekTwo;
        } else if (i == 3) {
            return weekThree;
        } else {
            return null;
        }
    }

}
