package seedu.address.model.display.timeslots;

import java.util.ArrayList;

import seedu.address.model.display.sidepanel.PersonDisplay;

/**
 * Schedule of timeslots for the week.
 */
public class PersonSchedule {

    private PersonDisplay personDisplay;
    private ArrayList<WeekSchedule> scheduleDisplay;

    public PersonSchedule(PersonDisplay personDisplay,
                          ArrayList<WeekSchedule> scheduleDisplay) {

        this.personDisplay = personDisplay;
        this.scheduleDisplay = scheduleDisplay;
    }

    public PersonDisplay getPersonDisplay() {
        return this.personDisplay;
    }

    public ArrayList<WeekSchedule> getScheduleDisplay() {
        return scheduleDisplay;
    }

}
