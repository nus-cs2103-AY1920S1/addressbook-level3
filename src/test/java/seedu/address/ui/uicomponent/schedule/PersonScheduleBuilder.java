package seedu.address.ui.uicomponent.schedule;

import seedu.address.model.display.schedulewindow.MonthSchedule;
import seedu.address.model.display.schedulewindow.PersonSchedule;
import seedu.address.model.display.sidepanel.PersonDisplay;
import seedu.address.testutil.TypicalPersons;

/**
 * Builds person schedules to test.
 */
public class PersonScheduleBuilder {
    public static PersonSchedule buildEmptySchedule() {
        MonthSchedule monthSchedule = new MonthSchedule(WeekScheduleBuilder.getEmptySchedule(),
                WeekScheduleBuilder.getEmptySchedule(), WeekScheduleBuilder.getEmptySchedule(), WeekScheduleBuilder.getEmptySchedule());
        PersonSchedule personSchedule = new PersonSchedule("", new PersonDisplay(TypicalPersons.ALICE),
                monthSchedule);
        return personSchedule;
    }

    public static PersonSchedule buildValidSchedule() {
        MonthSchedule monthSchedule = new MonthSchedule(WeekScheduleBuilder.getValidSchedule(),
                WeekScheduleBuilder.getValidSchedule(), WeekScheduleBuilder.getEmptySchedule(),
                WeekScheduleBuilder.getEmptySchedule());
        PersonSchedule personSchedule = new PersonSchedule("", new PersonDisplay(TypicalPersons.ALICE),
                monthSchedule);
        return personSchedule;
    }

    public static PersonSchedule buildInvalidSchedule() {
        MonthSchedule monthSchedule = new MonthSchedule(WeekScheduleBuilder.getInvalidSchedule(),
                WeekScheduleBuilder.getValidSchedule(), WeekScheduleBuilder.getEmptySchedule(), WeekScheduleBuilder.getEmptySchedule());
        PersonSchedule personSchedule = new PersonSchedule("", new PersonDisplay(TypicalPersons.ALICE),
                monthSchedule);
        return personSchedule;
    }
}
