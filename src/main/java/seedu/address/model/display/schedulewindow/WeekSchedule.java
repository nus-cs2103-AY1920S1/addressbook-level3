package seedu.address.model.display.schedulewindow;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;

import seedu.address.model.display.detailwindow.PersonTimeslot;

/**
 * Class to be passed to the UI to generate a week's schedule view.
 */
public class WeekSchedule {
    private HashMap<DayOfWeek, ArrayList<PersonTimeslot>> weekSchedule;
    public WeekSchedule(HashMap<DayOfWeek, ArrayList<PersonTimeslot>> weekSchedule) {
        this.weekSchedule = weekSchedule;
    }

    public ArrayList<PersonTimeslot> get(DayOfWeek day) {
        return weekSchedule.get(day);
    }
}
