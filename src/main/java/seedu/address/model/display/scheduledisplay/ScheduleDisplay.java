package seedu.address.model.display.scheduledisplay;

import java.util.ArrayList;

import seedu.address.model.display.exceptions.PersonTimeslotNotFoundException;
import seedu.address.model.display.sidepanel.PersonDisplay;
import seedu.address.model.display.timeslots.PersonSchedule;
import seedu.address.model.display.timeslots.PersonTimeslot;
import seedu.address.model.display.timeslots.WeekSchedule;
import seedu.address.model.person.Name;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Schedule to be Displayed.
 */
public abstract class ScheduleDisplay {

    private ArrayList<PersonSchedule> personSchedules;

    public ScheduleDisplay(ArrayList<PersonSchedule> personSchedules) {
        this.personSchedules = personSchedules;
    }

    public ArrayList<PersonSchedule> getPersonSchedules() {
        return personSchedules;
    }

    public ArrayList<PersonDisplay> getPersonDisplays() {
        ArrayList<PersonDisplay> personDisplays = new ArrayList<>();
        for (PersonSchedule p : personSchedules) {
            personDisplays.add(p.getPersonDisplay());
        }
        return personDisplays;
    }

    public PersonTimeslot getPersonTimeslot(Name name, int week, int id)
            throws PersonNotFoundException, PersonTimeslotNotFoundException {

        // find the schedule of the person with the given name
        PersonSchedule selectedPersonSchedule = null;
        for (int i = 0; i < personSchedules.size(); i++) {
            if (personSchedules.get(i).getPersonDisplay().getName().equals(name)) {
                selectedPersonSchedule = personSchedules.get(i);
            }
        }

        if (selectedPersonSchedule == null) {
            throw new PersonNotFoundException();
        }

        // get the weekSchedule
        WeekSchedule currentWeekSchedule = selectedPersonSchedule.getScheduleDisplay().get(week);

        // get the selected timeslot
        PersonTimeslot personTimeslot = currentWeekSchedule.getPersonTimeslot(id);
        return personTimeslot;
    }

    public PersonTimeslot getPersonTimeslotForToday(Name name, int id)
            throws PersonNotFoundException, PersonTimeslotNotFoundException {

        PersonSchedule selectedPersonSchedule = null;
        for (int i = 0; i < personSchedules.size(); i++) {
            if (personSchedules.get(i).getPersonDisplay().getName().equals(name)) {
                selectedPersonSchedule = personSchedules.get(i);
            }
        }
        if (selectedPersonSchedule == null) {
            throw new PersonNotFoundException();
        }

        // get the weekSchedule: week is always 0
        WeekSchedule currentWeekSchedule = selectedPersonSchedule.getScheduleDisplay().get(0);

        // get the selected timeslot
        PersonTimeslot personTimeslot = currentWeekSchedule.getPersonTimeslotForToday(id);
        return personTimeslot;
    }

    public abstract ScheduleState getState();
}
