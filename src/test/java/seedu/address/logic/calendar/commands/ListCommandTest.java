package seedu.address.logic.calendar.commands;

import static seedu.address.logic.calendar.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalTasks.getTypicalCalendarAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.calendar.CalendarModel;
import seedu.address.model.calendar.CalendarModelManager;
import seedu.address.model.calendar.CalendarUserPrefs;


/**
 * Contains integration tests (interaction with the CalendarModel) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private CalendarModel calendarModel;
    private CalendarModel expectedModel;

    @BeforeEach
    public void setUp() {
        calendarModel = new CalendarModelManager(getTypicalCalendarAddressBook(), new CalendarUserPrefs());
        expectedModel = new CalendarModelManager(calendarModel.getCalendarAddressBook(), new CalendarUserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), calendarModel, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    //    @Test
    //    public void execute_listIsFiltered_showsEverything() {
    //        showPersonAtIndex(calendarModel, INDEX_FIRST_PERSON);
    //        assertCommandSuccess(new ListCommand(), calendarModel, ListCommand.MESSAGE_SUCCESS, expectedModel);
    //    }
}
