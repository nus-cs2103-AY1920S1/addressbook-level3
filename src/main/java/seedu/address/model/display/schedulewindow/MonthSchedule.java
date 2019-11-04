package seedu.address.model.display.schedulewindow;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import seedu.address.model.display.detailwindow.PersonTimeslot;

/**
 * A class that concatenates 4 week schedules together.
 */
public class MonthSchedule {
    private HashMap<DayOfWeek, ArrayList<PersonTimeslot>> weekZero;
    private HashMap<DayOfWeek, ArrayList<PersonTimeslot>> weekOne;
    private HashMap<DayOfWeek, ArrayList<PersonTimeslot>> weekTwo;
    private HashMap<DayOfWeek, ArrayList<PersonTimeslot>> weekThree;

    public MonthSchedule(HashMap<DayOfWeek, ArrayList<PersonTimeslot>> weekZero,
                         HashMap<DayOfWeek, ArrayList<PersonTimeslot>> weekOne,
                         HashMap<DayOfWeek, ArrayList<PersonTimeslot>> weekTwo,
                         HashMap<DayOfWeek, ArrayList<PersonTimeslot>> weekThree) {
        this.weekZero = weekZero;
        this.weekOne = weekOne;
        this.weekTwo = weekTwo;
        this.weekThree = weekThree;
    }

    public static List<HashMap<DayOfWeek, ArrayList<PersonTimeslot>>> getWeekSchedulesOf(
            List<MonthSchedule> monthSchedules, int weekNumber) {
        List<HashMap<DayOfWeek, ArrayList<PersonTimeslot>>> weekSchedule = new ArrayList<>();
        for (MonthSchedule monthSchedule : monthSchedules) {
            weekSchedule.add(monthSchedule.getScheduleForWeek(weekNumber));
        }
        return weekSchedule;
    }

    public HashMap<DayOfWeek, ArrayList<PersonTimeslot>> getScheduleForWeek(int i) {
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
