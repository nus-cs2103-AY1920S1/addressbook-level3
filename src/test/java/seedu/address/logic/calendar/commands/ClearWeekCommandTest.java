package seedu.address.logic.calendar.commands;

import static seedu.address.logic.calendar.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.model.calendar.CalendarModel;
import seedu.address.model.calendar.CalendarModelManager;



public class ClearWeekCommandTest {

    @Test
    public void execute_emptyAddressBook_success() {
        CalendarModel calendarModel = new CalendarModelManager();
        CalendarModel expectedModel = new CalendarModelManager();

        assertCommandSuccess(new ClearWeekCommand(), calendarModel,
            ClearWeekCommand.MESSAGE_DELETE_WEEK_SUCCESS, expectedModel);
    }

}
