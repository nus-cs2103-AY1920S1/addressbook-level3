package seedu.address.ui.uicomponent.schedule;

import java.util.ArrayList;
import java.util.List;

import seedu.address.model.display.sidepanel.PersonDisplay;
import seedu.address.model.display.timeslots.PersonSchedule;
import seedu.address.model.display.timeslots.WeekSchedule;
import seedu.address.testutil.TypicalPersons;

/**
 * Builds person schedules to test.
 */
public class PersonScheduleBuilder {

    /**
     * Builds an empty schedule.
     * @return
     */
    public static PersonSchedule buildEmptySchedule() {
        ArrayList<WeekSchedule> monthSchedule = new ArrayList<>(List.of(WeekScheduleBuilder.getEmptySchedule(),
                WeekScheduleBuilder.getEmptySchedule(), WeekScheduleBuilder.getEmptySchedule(),
                WeekScheduleBuilder.getEmptySchedule()));
        PersonSchedule personSchedule = new PersonSchedule(new PersonDisplay(TypicalPersons.ALICE),
                monthSchedule);
        return personSchedule;
    }

    /**
     * Builds a valid schedule. (no clashes).
     * @return PersonSchedule.
     */
    public static PersonSchedule buildValidSchedule() {
        ArrayList<WeekSchedule> monthSchedule = new ArrayList<>(List.of(WeekScheduleBuilder.getValidSchedule(),
                WeekScheduleBuilder.getValidSchedule(), WeekScheduleBuilder.getEmptySchedule(),
                WeekScheduleBuilder.getEmptySchedule()));
        PersonSchedule personSchedule = new PersonSchedule(new PersonDisplay(TypicalPersons.ALICE),
                monthSchedule);
        return personSchedule;
    }

    /**
     * Builds an invalid person schedule. (Has timeslots with clashes).
     * @return PersonSchedule.
     */
    public static PersonSchedule buildInvalidSchedule() {
        ArrayList monthSchedule = new ArrayList(List.of(WeekScheduleBuilder.getInvalidSchedule(),
                WeekScheduleBuilder.getValidSchedule(), WeekScheduleBuilder.getEmptySchedule(),
                WeekScheduleBuilder.getEmptySchedule()));
        PersonSchedule personSchedule = new PersonSchedule(new PersonDisplay(TypicalPersons.ALICE),
                monthSchedule);
        return personSchedule;
    }
}
