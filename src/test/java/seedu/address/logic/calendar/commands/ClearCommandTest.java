package seedu.address.logic.calendar.commands;

import static seedu.address.logic.calendar.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalTasks.getTypicalCalendarAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.calendar.CalendarAddressBook;
import seedu.address.model.calendar.CalendarModel;
import seedu.address.model.calendar.CalendarModelManager;
import seedu.address.model.calendar.CalendarUserPrefs;


public class ClearCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        CalendarModel calendarModel = new CalendarModelManager();
        CalendarModel expectedModel = new CalendarModelManager();

        assertCommandSuccess(new ClearCommand(), calendarModel, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyAddressBook_success() {
        CalendarModel calendarModel =
            new CalendarModelManager(getTypicalCalendarAddressBook(), new CalendarUserPrefs());
        CalendarModel expectedModel =
            new CalendarModelManager(getTypicalCalendarAddressBook(), new CalendarUserPrefs());
        expectedModel.setCalendarAddressBook(new CalendarAddressBook());

        assertCommandSuccess(new ClearCommand(), calendarModel, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
