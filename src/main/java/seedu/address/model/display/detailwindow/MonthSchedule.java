package seedu.address.model.display.detailwindow;

import java.time.LocalDateTime;
import java.util.ArrayList;

import seedu.address.model.display.sidepanel.PersonDisplay;
import seedu.address.model.mapping.Role;
import seedu.address.model.person.Person;

/**
 * A class to store the entire month schedule of a person. Information is stored week by week.
 */
public class MonthSchedule {
    private static final int NUMBER_OF_WEEKS = 4;
    private WeekSchedule[] weekSchedules = new WeekSchedule[4];
    private PersonDisplay personDisplay;
    private Role role;

    public MonthSchedule(Person person, LocalDateTime now) {
        weekSchedules[0] = new WeekSchedule(person.getName().fullName, now, person, Role.emptyRole());
        weekSchedules[1] = new WeekSchedule(person.getName().fullName, now.plusDays(7), person, Role.emptyRole());
        weekSchedules[2] = new WeekSchedule(person.getName().fullName, now.plusDays(14), person, Role.emptyRole());
        weekSchedules[3] = new WeekSchedule(person.getName().fullName, now.plusDays(21), person, Role.emptyRole());
        personDisplay = new PersonDisplay(person);
        role = Role.emptyRole();
    }

    public MonthSchedule(Person person, LocalDateTime now, Role role) {
        this.role = role;
        weekSchedules[0] = new WeekSchedule(person.getName().fullName, now, person, Role.emptyRole());
        weekSchedules[1] = new WeekSchedule(person.getName().fullName, now.plusDays(7), person, Role.emptyRole());
        weekSchedules[2] = new WeekSchedule(person.getName().fullName, now.plusDays(14), person, Role.emptyRole());
        weekSchedules[3] = new WeekSchedule(person.getName().fullName, now.plusDays(21), person, Role.emptyRole());
        personDisplay = new PersonDisplay(person);
    }

    public static ArrayList<WeekSchedule> getWeekSchedulesForWeek(ArrayList<MonthSchedule> monthSchedules, int weekNum) {
        ArrayList<WeekSchedule> weekSchedules = new ArrayList<>();
        for (MonthSchedule m : monthSchedules) {
            weekSchedules.add(m.getWeekScheduleOf(weekNum));
        }
        return weekSchedules;
    }

    public WeekSchedule getWeekScheduleOf(int weekNumber) {
        return weekSchedules[weekNumber];
    }

    public PersonDisplay getPersonDisplay() {
        return personDisplay;
    }
}
