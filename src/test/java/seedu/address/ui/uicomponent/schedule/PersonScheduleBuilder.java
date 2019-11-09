package seedu.address.ui.uicomponent.schedule;

import seedu.address.model.display.schedulewindow.MonthSchedule;
import seedu.address.model.display.schedulewindow.PersonSchedule;
import seedu.address.model.display.sidepanel.PersonDisplay;
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
        MonthSchedule monthSchedule = new MonthSchedule(WeekScheduleBuilder.getEmptySchedule(),
                WeekScheduleBuilder.getEmptySchedule(), WeekScheduleBuilder.getEmptySchedule(),
                WeekScheduleBuilder.getEmptySchedule());
        PersonSchedule personSchedule = new PersonSchedule("", new PersonDisplay(TypicalPersons.ALICE),
                monthSchedule);
        return personSchedule;
    }

    /**
     * Builds a valid schedule. (no clashes).
     * @return PersonSchedule.
     */
    public static PersonSchedule buildValidSchedule() {
        MonthSchedule monthSchedule = new MonthSchedule(WeekScheduleBuilder.getValidSchedule(),
                WeekScheduleBuilder.getValidSchedule(), WeekScheduleBuilder.getEmptySchedule(),
                WeekScheduleBuilder.getEmptySchedule());
        PersonSchedule personSchedule = new PersonSchedule("", new PersonDisplay(TypicalPersons.ALICE),
                monthSchedule);
        return personSchedule;
    }

    /**
     * Builds an invalid person schedule. (Has timeslots with clashes).
     * @return PersonSchedule.
     */
    public static PersonSchedule buildInvalidSchedule() {
        MonthSchedule monthSchedule = new MonthSchedule(WeekScheduleBuilder.getInvalidSchedule(),
                WeekScheduleBuilder.getValidSchedule(), WeekScheduleBuilder.getEmptySchedule(),
                WeekScheduleBuilder.getEmptySchedule());
        PersonSchedule personSchedule = new PersonSchedule("", new PersonDisplay(TypicalPersons.ALICE),
                monthSchedule);
        return personSchedule;
    }
}
