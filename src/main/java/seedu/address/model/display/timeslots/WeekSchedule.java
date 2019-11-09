package seedu.address.model.display.timeslots;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;

import seedu.address.model.display.exceptions.PersonTimeslotNotFoundException;

/**
 * Class to be passed to the UI to generate a week's schedule view.
 */
public class WeekSchedule {

    private HashMap<DayOfWeek, ArrayList<PersonTimeslot>> weekSchedule;

    public WeekSchedule(HashMap<DayOfWeek, ArrayList<PersonTimeslot>> weekSchedule) {
        this.weekSchedule = weekSchedule;
        assignId();
    }

    public ArrayList<PersonTimeslot> get(DayOfWeek day) {
        return weekSchedule.get(day);
    }

    /**
     * Assign IDs to each PersonTimeslot in the WeekSchedule.
     */
    private void assignId() {

        int counter = 1;
        for (int i = 0; i < 7; i++) {
            int daynum = LocalDateTime.now().toLocalDate().getDayOfWeek().getValue();
            DayOfWeek day = DayOfWeek.of(((daynum - 1 + i) % 7) + 1);
            ArrayList<PersonTimeslot> currentPersonTimeslots = get(day);

            for (PersonTimeslot currentPersonTimeslot : currentPersonTimeslots) {
                currentPersonTimeslot.setId(counter);
                counter++;
            }
        }
    }

    public PersonTimeslot getPersonTimeslot(int id) throws PersonTimeslotNotFoundException {
        for (int i = 1; i <= 7; i++) {
            DayOfWeek day = DayOfWeek.of(i);
            ArrayList<PersonTimeslot> currentPersonTimeslots = get(day);

            for (PersonTimeslot currentPersonTimeslot : currentPersonTimeslots) {
                if (currentPersonTimeslot.getId() == id) {
                    return currentPersonTimeslot;
                }
            }
        }
        throw new PersonTimeslotNotFoundException();
    }

    public PersonTimeslot getPersonTimeslotForToday(int id) throws PersonTimeslotNotFoundException {
        DayOfWeek day = LocalDateTime.now().getDayOfWeek();
        ArrayList<PersonTimeslot> currentPersonTimeslots = get(day);

        for (PersonTimeslot currentPersonTimeslot : currentPersonTimeslots) {
            if (currentPersonTimeslot.getId() == id) {
                return currentPersonTimeslot;
            }
        }

        throw new PersonTimeslotNotFoundException();
    }

}
