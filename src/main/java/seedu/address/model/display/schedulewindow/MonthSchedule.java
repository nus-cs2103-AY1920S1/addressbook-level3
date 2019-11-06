package seedu.address.model.display.schedulewindow;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that concatenates 4 week schedules together.
 */
public class MonthSchedule {
    private WeekSchedule weekZero;
    private WeekSchedule weekOne;
    private WeekSchedule weekTwo;
    private WeekSchedule weekThree;

    public MonthSchedule(WeekSchedule weekZero, WeekSchedule weekOne, WeekSchedule weekTwo, WeekSchedule weekThree) {
        this.weekZero = weekZero;
        this.weekOne = weekOne;
        this.weekTwo = weekTwo;
        this.weekThree = weekThree;
    }

    public static List<WeekSchedule> getWeekSchedulesOf(
            List<MonthSchedule> monthSchedules, int weekNumber) {
        List<WeekSchedule> weekSchedules = new ArrayList<>();
        for (MonthSchedule monthSchedule : monthSchedules) {
            weekSchedules.add(monthSchedule.getScheduleForWeek(weekNumber));
        }
        return weekSchedules;
    }

    public WeekSchedule getScheduleForWeek(int i) {
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
